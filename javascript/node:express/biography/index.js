const express = require("express");
const mysql = require("mysql");
const app = express();
const pool = require("./dbPool");
app.set("view engine", "ejs");
app.use(express.static("public"));
app.use(express.urlencoded({extended: true}));
 
//routes INDEX
app.get('/', (req, res) => {
  res.render('index')
});

//GET AUTHOR
app.get("/author/new", (req, res) => {
  res.render("newAuthor");
});

// ADD AUTHOR POST FORM
app.post("/author/new", async function(req, res)  { 
  let fName = req.body.fName;
  let lName = req.body.lName;
  let birthDate = req.body.birthDate;
  let deathDate = req.body.deathDate;
  let gender = req.body.gender.toUpperCase();
  let profession = req.body.profession;
  let country = req.body.country;
  let portrait = req.body.portrait;
  let bio = req.body.bio;
  
  let sql = "INSERT INTO q_authors (firstName, lastName, dob, dod, sex, profession, country, portrait, biography) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);"
  let params = [fName, lName, birthDate, deathDate, gender, profession, country, portrait, bio];
  let rows = await executeSQL(sql, params); 
  res.render("newAuthor", {"message": "Author added!"}); 
});

//LIST AUTHORS
app.get("/authors", async function(req, res){
 let sql = `SELECT *
            FROM q_authors
            ORDER BY lastName`;
 let rows = await executeSQL(sql);
res.render("authorList", {"authors":rows}); 
});

//EDIT AUTHOR
app.get("/author/edit", async function(req, res){
   let authorId = req.query.authorId;
   let sql = `SELECT *, DATE_FORMAT(dob,
  '%Y-%m-%d') dobISO, DATE_FORMAT(dod,
  '%Y-%m-%d') dodISO
              FROM q_authors
              WHERE authorId =  ${authorId}`;
   let rows = await executeSQL(sql);
  res.render("editAuthor", {"authorInfo":rows}); 
});


//AUTHOR DELETE
app.get("/author/delete", async function (req, res) {
  let sql = `DELETE 
            FROM q_authors WHERE authorID = ${req.query.authorId}`;
  let rows = await executeSQL(sql);
  res.redirect("/authors");
});

//EDIT AUTHOR POST FORM
app.post("/author/edit", async function(req, res){

  
  let fName = req.body.fName;
  let lName = req.body.lName;
  let birthDate = req.body.birthDate;
  let deathDate = req.body.deathDate;
  let gender = req.body.sex;
  let profession = req.body.profession;
  let country = req.body.country;
  let portrait = req.body.portrait;
  let bio = req.body.biography;
  let authorId = req.body.authorId;
  // res.send(fName + " " + lName + " "+ birthDate+ " " + deathDate+gender+profession+country+portrait+bio+authorId);

 let sql = `UPDATE q_authors SET firstName = ?, lastName = ?, dob = ?, dod = ?, sex = ?, profession = ?, country = ?, portrait = ?, biography = ? WHERE authorId =  ?;`;
let params = [fName, lName, birthDate, deathDate, gender, profession, country, portrait, bio, authorId]; 

let rows = await executeSQL(sql,params);
  /*
sql = `SELECT *, DATE_FORMAT(dob, '%Y-%m-%d') dobISO, DATE_FORMAT(dod, '%Y-%m-%d') dodISO, FROM q_authors
WHERE authorId= authorId`;
 rows = await executeSQL(sql);
 */

res.send("success");
//res.render("editAuthor", {"authorInfo":rows, "message": "Author Updated!"});
});



//QUOTES
//LIST QUOTES
app.get("/quotes", async function(req, res){
 let sql = `SELECT quote, firstName, lastName, quoteid
            FROM q_quotes
            natural join q_authors order by lastName;`;
 let rows = await executeSQL(sql);
res.render("listQuotes", {"quotes":rows}); 
});

//ADD QUOTES GET
app.get("/quote/new", async function(req, res){
 let sql = `SELECT distinct category
            FROM q_quotes
            natural join q_authors order by authorID;`;

let sql2 = `SELECT distinct firstName, lastName, authorID
            FROM q_quotes
            natural join q_authors order by authorID;`;

 let category = await executeSQL(sql);
 let authors = await executeSQL(sql2);

res.render("newQuote", {"category":category, "authors":authors}); 
});

//ADD QUOTE POST
app.post("/quotes/new", async function(req, res)  { 
  
let quote = req.body.nquote;
let authorid = req.body.nauthorid;
let category = req.body.ncategory;

let sql = "INSERT INTO q_quotes (quote, authorid, category) VALUES (?, ?, ?);"
let params = [quote, authorid, category];
let rows = await executeSQL(sql, params); 
  res.render("newQuote", {"message": "Quote added!"}); 
});


//QUOTES EDIT GET
app.get("/quotes/edit", async function(req, res){
 let quoteid = req.query.quoteid;
 let sql = `SELECT * FROM q_quotes natural join q_authors WHERE quoteid =  ${quoteid}`;
 let rows = await executeSQL(sql);
res.render("editQuotes", {"quotes":rows}); });

//QUOTE EDIT POST
app.post("/quotes/edit", async function(req, res){
 let sql = `UPDATE q_quotes SET quote = ?, authorid = ?, category = ? WHERE quoteid =  ?`;
let params = [req.body.quote,
              req.body.authorid, req.body.category,
req.body.quoteid]; let rows = await executeSQL(sql,params);
sql = `SELECT *,
DATE_FORMAT(dob, '%Y-%m-%d') dobISO FROM q_authors
WHERE authorId= ${req.body.authorId}`;
 rows = await executeSQL(sql);
res.render("editQuotes", {"authorInfo":rows, "message": "Quote Updated!"});
});


//QUOTE DELETE
app.get("/quotes/delete", async function (req, res) {
  let sql = `DELETE 
            FROM q_quotes WHERE quoteId = ${req.query.quoteId}`;
  let rows = await executeSQL(sql);
  res.redirect("/quotes");
});




//DB TEST
app.get("/dbTest", async function(req, res){
let sql = "SELECT CURDATE()";
let rows = await executeSQL(sql);
res.send(rows);
});//dbTest






//functions
async function executeSQL(sql, params){
return new Promise (function (resolve, reject) {
pool.query(sql, params, function (err, rows, fields) {
if (err) throw err;
   resolve(rows);
});
});
}//executeSQL








//start server
app.listen(3000, () => {
console.log("Expresss server running...")
} )