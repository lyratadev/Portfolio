/*
Write a program that uses a structure to store the following data:

Name (Student Name)
IDnum (Student ID number)
Tests (pointer to an array of test scores)
Average (Average test score)
Grade (course grade)

The program should keep a list of test scores for a group of students
ask user how many test there are,
ask how many students there are,
dynamically allocate an array of structures.
each strucutre test's members should point to a dynamically allocated array that holds test scores

ask for the id
all test scores for the student
calculate the average + store in the average structure members

Calculate grades based on
90 > A,
90 - 81 = B,
80 - 71 = C,
70 - 61 = D,
<=60 = F

©ALL RIGHTS RESERVED
*/

#include<iostream>
#include<iomanip>
#include<string>
using namespace std;


/*
struct Student
{
  string name = "";
  int IDnum = 0;
  int *test = NULL;
  char grade = '0';
};
*/

struct Student
    {
      string name;
      int IDnum;
      int *test;
      double average;
      char grade;
    };

char calcGrade(int average)
    {
        char grade = 0;
      if ( average > 90 )
      {grade = 'A';}
      else if ( average < 90 && average > 81 )
      {grade = 'B';}
      else if ( average < 80 && average > 71 )
      {grade = 'C';}
      else if ( average < 80 && average > 71 )
      {grade = 'D';}
      else {grade='F';}

      return grade;
    }

void inputValidate(int &input)
    {
      while (input < 1) //input validation
      {
        cout << "Error, enter 1 or greater \n";
        cin >> input;
      }
    }

void ClearCin() //this function clears the cin
  {
    cin.clear(); //clears errors
    cin.ignore( numeric_limits <streamsize> ::max(), '\n' ); //ignores previous cin data up until new line character
  }




int main()
{
  int studentNum = 0;
  int testNum = 0;


  cout << "Please enter the number of students" << endl;  //DECLARING DYNAMIC STRUCT
  cin  >> studentNum;
     while (!cin){cout<<"invalid input, enter an number \n";ClearCin();cin>>studentNum;}
     inputValidate(studentNum);
      Student *student = new Student[studentNum];


  cout << "Please enter the number of tests per student" << endl; //DECLARING DYNAMIC ARRAY
  cin  >> testNum;
    while (!cin){cout<<"invalid input, enter an number \n";ClearCin();cin>>testNum;}
    inputValidate(testNum);
  for (int i = 0; i < studentNum; i++)
    {
      student[i].test = new int[testNum]; //allocate dynamic array for each student struct
    }


  //loop to populate structures
  for (int i = 0; i < studentNum; i++)
  {
    cout << "\nPlease enter student name" << endl;
     cin >> student[i].name;

    cout << "ID number?" << endl;
     cin >> student[i].IDnum;
      while (!cin){cout<<"invalid input, enter an number \n";ClearCin();cin>>student[i].IDnum;}
      inputValidate(student[i].IDnum);

    cout << "\nEnter test scores" << endl;
    for (int t = 0; t < testNum; t++)
        {
            int input=0;
         cout << "Test #" << t + 1 << endl;
            cin >> input;
            inputValidate(input);
            student[i].test[t] = input;
            ClearCin();
        }
  }

      //Calculate average test score and assign average to struct.
   for (int o = 0; o < studentNum; o++)
    {
      double average = 0;
      for (int t = 0; t < testNum; t++)
             {
                 average +=student[o].test[t]; //looping to sum all test scores
             }
       student[o].average = average/testNum;  //dividing test scores by number of tests
       student[o].grade = calcGrade(student[o].average);
    }



  //OUTPUT
for (int i = 0; i < studentNum; i++) //LOOP FOR ALL STUDENTS
    {
    cout << "\n**********************" << endl;
    cout << setw(20) << left << "Student Name: " << right <<      setw(10) << student[i].name << endl;
    cout << setw(20) << left << "ID Number:  " <<   right <<      setw(10) << student[i].IDnum << endl;
    cout << setw(20) << left << "Average Test Score: " << right
         << setw(10) << setprecision(2) << fixed << student[i].average << endl;
    cout << setw(20) << left <<  "Grade: " << right << setw(10) << student[i].grade << endl;
    cout << "**********************" << endl;
    cout << '\n';
    }




//delete dynamic allocations.

      for (int i = 0; i < studentNum; i++)
      {
          delete[] student[i].test;
          student[i].test = NULL;
      }

 delete[] student;
 return 0;
 //end of main
}
