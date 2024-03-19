package com.csumb.cst363;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.regex.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * Controller class for patient interactions.
 *   register as a new patient.
 *   update patient profile.
 */
@Controller
public class ControllerPatient {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*
     * Request patient_register form.
     */
    @GetMapping("/patient/new")
    public String newPatient(Model model) {


        // return blank form for new patient registration
        model.addAttribute("patient", new Patient());
        return "patient_register";
    }

    /*
     * Request form to search for patient.
     */
    @GetMapping("/patient/edit")
    public String getPatientForm(Model model) {
        // prompt for patient id and name
        return "patient_get";
    }

    /*
     * Process a form to create new patient.
     */
    @PostMapping("/patient/new")
    public String newPatient(Patient patient, Model model) {

        // Validation for inputs;
        try (Connection con = getConnection();) {

            // Validation
            // display message and patient information
            boolean isInvalid = runValidation(model, patient);
            if (isInvalid) {
                model.addAttribute("patient", patient);
                return "patient_register";
            }


            PreparedStatement doctorLookUp = con.prepareStatement("SELECT doctor_id, specialty from doctor WHERE doctor.name LIKE ? GROUP BY doctor_id;");
            doctorLookUp.setString(1, "%" +  patient.getPrimaryName() + "%");



            ResultSet doctorRS = doctorLookUp.executeQuery();


            if (doctorRS.next()) {

                String specilty = doctorRS.getString(2);
                LocalDate date = LocalDate.parse(patient.getBirthdate());
                int year = date.getYear();

                LocalDate todaysDate = LocalDate.now();
                int thisYear = todaysDate.getYear();
                if (thisYear - year < 18 && !Objects.equals(specilty, "Pediatrics")) {
                    if (!Objects.equals(specilty, "Pediatrics")) {
                        model.addAttribute("message", "Error: The patient is underage and requires a Pediatrics doctor as their primary care physician.");
                        model.addAttribute("patient", patient);
                        return "patient_register";
                    }
                }
                if (thisYear - year >= 18 && Objects.equals(specilty, "Pediatrics")) {
                    model.addAttribute("message", "Error: Primary physician must specialize in Family Medicine or Internal Medicine.");
                    model.addAttribute("patient", patient); //** Is this right?
                    return "patient_register";
                }

                patient.setPrimaryID(doctorRS.getInt(1));
                PreparedStatement ps = con.prepareStatement("insert into patient(name, ssn, street	, city, zip, state, birthdate, primary_doctor_id ) values(?, ?, ?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, patient.getName());
                ps.setString(2, patient.getSsn());
                ps.setString(3, patient.getStreet());
                ps.setString(4, patient.getCity());
                ps.setString(5, patient.getZipcode());
                ps.setString(6, patient.getState());
                ps.setString(7, patient.getBirthdate());
                ps.setInt(8, patient.getPrimaryID());

                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) patient.setPatientId((int) rs.getLong(1));


                // display message and patient information
                model.addAttribute("message", "Registration successful.");
                model.addAttribute("patient", patient);
                return "patient_show";
            } else {
                model.addAttribute("message", "Error: That doctor does not exist in our system...");
                model.addAttribute("patient", patient);
                return "patient_register";
            }

        } catch (SQLException e) {
            model.addAttribute("message", "SQL Error." + e.getMessage());
            model.addAttribute("patient", patient);
            return "patient_show";
        }

    }

    /*
     * Search for patient by patient id and name.
     */
    @PostMapping("/patient/show")
    public String getPatientForm(@RequestParam("patientId") int patientId, @RequestParam("name") String name,
                                 Model model) {
        /*
         * code to search for patient by id and name retrieve patient data and primary
         * doctor data create Patient object
         */
        Patient p = new Patient();

        try (Connection con = getConnection();) {
            PreparedStatement ps = con.prepareStatement("SELECT doctor.name as primaryName, patient.name as patientName," +
                    " patient.ssn as ssn, zip, street, birthdate, state, city, doctor_id, specialty, start_year, patient_id " +
                    " FROM patient INNER JOIN doctor ON patient.primary_doctor_id = " +
                    "doctor.doctor_id WHERE patient.name = ? AND patient.patient_id = ?");
            ps.setString(1, name);
            ps.setString(2, String.valueOf(patientId));


            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p.setPatientId((int) rs.getLong("patient_id"));
                p.setName(rs.getString("patientName"));
                p.setZipcode(rs.getString("zip"));
                p.setStreet(rs.getString("street"));
                p.setBirthdate(rs.getString("birthdate"));
                p.setState(rs.getString("state"));
                p.setCity(rs.getString("city"));
                p.setPrimaryID(rs.getInt("doctor_id"));
                p.setPrimaryName(rs.getString("primaryName"));
                p.setSpecialty(rs.getString("specialty"));
                p.setYears(rs.getString("start_year"));
            }

            // display message and patient information
            model.addAttribute("message", "Your Result");
            model.addAttribute("patient", p);

            return "patient_show";

        } catch (SQLException e) {
            model.addAttribute("message", "SQL Error." + e.getMessage());
            model.addAttribute("patient", p);
            return "patient_show";
        }
    }

    /*
     * Search for patient by patient id.
     */
    @GetMapping("/patient/edit/{patientId}")
    public String updatePatient(@PathVariable int patientId, Model model) {

        // TODO Complete database logic search for patient by id.
        Patient p = new Patient();
        try (Connection con = getConnection();) {
            PreparedStatement ps = con.prepareStatement("SELECT doctor.name as primaryName, patient.name as patientName," +
                    "patient.ssn as ssn, zip, street, birthdate, state, city, doctor_id, specialty, start_year, patient_id" +
                    " FROM patient INNER JOIN doctor ON patient.primary_doctor_id = " +
                    "doctor.doctor_id WHERE patient.patient_id = ?");
            ps.setString(1, String.valueOf(patientId));


            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p.setPatientId((int) rs.getLong("patient_id"));
                p.setName(rs.getString("patientName"));
                p.setZipcode(rs.getString("zip"));
                p.setStreet(rs.getString("street"));
                p.setBirthdate(rs.getString("birthdate"));
                p.setState(rs.getString("state"));
                p.setCity(rs.getString("city"));
                p.setPrimaryID(rs.getInt("doctor_id"));
                p.setPrimaryName(rs.getString("primaryName"));
                p.setSpecialty(rs.getString("specialty"));
                p.setYears(rs.getString("start_year"));
            }
            // display message and patient information
            model.addAttribute("patient", p);

            return "patient_edit";

        } catch (SQLException e) {
            model.addAttribute("message", "SQL Error." + e.getMessage());
            model.addAttribute("patient", p);
            return "patient_edit";
        }
    }


    /*
     * Process changes to patient address and primary doctor
     */
    @PostMapping("/patient/edit")
    public String updatePatient(Patient p, Model model) {

        // TODO
        try (Connection con = getConnection();) {
            boolean doctorIsReal = checkIfDoctorExist(p.getPrimaryName());

            if (doctorIsReal) {
                PreparedStatement doctorLookUp = con.prepareStatement("SELECT doctor_id from doctor WHERE doctor.name = ?");
                doctorLookUp.setString(1, p.getPrimaryName());

                doctorLookUp.executeQuery();
                ResultSet doctorRS = doctorLookUp.executeQuery();
                if (doctorRS.next()) p.setPrimaryID(doctorRS.getInt(1));

                PreparedStatement ps = con.prepareStatement("update patient set patient_id=?, name=?, zip=?, " +
                        "street=?, birthdate=?, state=?, city=?, primary_doctor_id=? where patient_id=?");
                ps.setInt(1, p.getPatientId());
                ps.setString(2, p.getName());
                ps.setString(3, p.getZipcode());
                ps.setString(4, p.getStreet());
                ps.setString(5, p.getBirthdate());
                ps.setString(6, p.getState());
                ps.setString(7, p.getCity());
                ps.setInt(8, p.getPrimaryID());
                ps.setInt(9, p.getPatientId());

                int rc = ps.executeUpdate();
                if (rc == 1) {
                    model.addAttribute("message", "Update successful");
                    model.addAttribute("patient", p);
                    return "patient_show";

                } else {
                    model.addAttribute("message", "Error. Update was not successful");
                    model.addAttribute("patient", p);
                    return "patient_edit";
                }
            } else {
                model.addAttribute("message", "Unable to locate primary doctor");
                model.addAttribute("patient", p);
                return "patient_edit";
            }


        } catch (SQLException e) {
            model.addAttribute("message", "SQL Error." + e.getMessage());
            model.addAttribute("patient", p);
            return "patient_edit";
        }

    }

    private boolean runValidation(Model model, Patient patient) {
        String errorArray[];
        errorArray = new String[20];

        boolean isEmptyStringAddress = patient.getStreet().isEmpty();
        boolean isEmptyStringState = patient.getState().isEmpty();
        boolean isEmptyStringCity = patient.getCity().isEmpty();
        boolean isEmptyStringPrimaryName = patient.getPrimaryName().isEmpty();
        boolean isEmptyStringName = patient.getName().isEmpty();
        boolean isZipCodeValid = patient.getZipcode().length() == 5 || patient.getZipcode().length() == 9;
        boolean isSSNInvalid = patient.getSsn().isEmpty() || !ssnValidator(patient.getSsn());
        // Date Validation
        LocalDate date = LocalDate.parse(patient.getBirthdate());
        int year = date.getYear();
        boolean isBirthdateValid = year >= 1900 && 2022 >= year;

        if (isEmptyStringAddress || isEmptyStringState || isEmptyStringCity
                || isEmptyStringPrimaryName || isEmptyStringName) {
            model.addAttribute("message", "Error: A field is empty when it should not be...");
        }

        if (!isZipCodeValid) {
            model.addAttribute("message", "Error: Your zip code isn't valid");
        }

        if (isSSNInvalid) {
            model.addAttribute("message", "Error: Your SSN isn't valid");
        }

        if (!isBirthdateValid) {
            model.addAttribute("message", "Error: Your birthday year must be between 1900 - 2022");
        }

        return isEmptyStringAddress || isEmptyStringState || isEmptyStringCity || isEmptyStringPrimaryName
                || isEmptyStringName || !isZipCodeValid || isSSNInvalid || !isBirthdateValid;
    }

    private boolean checkIfDoctorExist(String doctor_name) {
        boolean isDoctorReal = false;
        try (Connection con = getConnection();) {
            PreparedStatement ps = con.prepareStatement("SELECT count(*) as doctor from doctor WHERE name = ?");
            ps.setString(1, doctor_name);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isDoctorReal = rs.getInt("doctor") > 0;
            }


        } catch (SQLException e) {
            return false;
        }

        return isDoctorReal;
    }
    /*
     * return JDBC Connection using jdbcTemplate in Spring Server
     */

    private Connection getConnection() throws SQLException {
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        return conn;
    }

    public static boolean ssnValidator(String ssn) {
        String ssnRegex = "^(?!666|000|9\\d{2})\\d{3}"
                + "(?!00)\\d{2}"
                + "(?!0{4})\\d{4}$";
        Pattern regexCompiled = Pattern.compile(ssnRegex);
        Matcher matches = regexCompiled.matcher(ssn);
        return matches.matches();
    }

}
