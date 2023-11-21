/*
Write a function that accepts a pointer to a C-string as its argument.
The function should count the number of vowels appearing in the string and return that number.
Write another function that accepts a pointer to a C-string as its argument.
This function should count the number of consonants appearing in the string and return that number.

     Demonstrate these two functions in a program that performs the following steps:

    The user is asked to enter a string.
    The program displays the following menu:
        A) Count the number of vowels in the string
        B) Count the number of consonants in the string
        C) Count both the vowels and consonants in the string
        D) Enter another string
        E) Exit the program
    The program performs the operation selected by the user and repeats until the last user selects E to exit the program.

Do not pass an item captured with a string into an c-string array

©ALL RIGHTS RESERVED
*/

#include<iostream>
#include<cctype>
#include<string>
#include<cstring>
using namespace std;

//function prototypes
int countVowels(char (*cstringInput));
int countConsonants(char (*cstringInput));
void ClearCin();

int main()
{
  //variable declaration

  int size = 500;
  char input = '\0';
  char sentinel = '\0';
  char *cstringInput = new char[size];

  cout << "Please enter a string.\n";
  cout << "\t";
  cin.getline(cstringInput, size);


  do
  {
      cout << "_______________________________________\n";
      cout << "\n";
      cout << "\tYour string is ''" << cstringInput << "''.\n\n";
      cout << "A) Count the number of vowels in the string\n";
      cout << "B) Count the number of consonants in the string\n";
      cout << "C) Count both the vowels and consonants in the string\n";
      cout << "D) Enter another string \n";
      cout << "E) Exit the program \n";
      cout << "\t";
      cin >> input;

      if (input == 'A' || input == 'a')
      {
          cout << "_________________________________________\n";
          cout << "\n\tYou have selected A.\n";
          //count the number of vowels with functions
          cout << "\tThe number of vowels in the string is " << countVowels(cstringInput) << ".\n";
      }

      else if (input == 'B' || input == 'b')
      {
        cout << "_________________________________________\n";
        cout << "\n\tYou have selected B.\n";
        cout << "\tThe number of consonants in the string is " << (countConsonants(cstringInput) ) << ".\n";
      }

        else if (input == 'C' || input == 'c')
        {
          cout << "_________________________________________\n";
          cout << "\n\tYou have selected C.\n";
          //count both consonant and vowel
          cout << "\tThe number of vowels in the string is "     << countVowels(cstringInput) << ".\n";
          cout << "\tThe number of consonants in the string is " << (countConsonants(cstringInput) ) << ".\n\n";

        }

      else if (input == 'D' || input == 'd')
      {
        cout << "_________________________________________\n";
        cout << "\n\tYou have selected D.\n";
        cout << "\tPlease enter a new string.\n";
        ClearCin();
        cin.getline(cstringInput,size);
      }

        else if (input == 'E' || input== 'e')
        {
          sentinel = 'E';
        }

      else
      {
        cout << "_________________________________________\n";
        cout << "Invalid selection please try again.\n\n";
      }
  } while (sentinel != 'E');


  delete[] cstringInput;
  return 0;
}

//functions
void ClearCin() //this function clears the cin
  {
    cin.clear(); //clears errors
    cin.ignore( numeric_limits <streamsize> ::max(), '\n' ); //ignores previous cin data up until new line character
  }


int countVowels(char (*cstringInput))
{
    int count=0;
    while (*(cstringInput) != '\0')
    {
      if (
          (*cstringInput) == 'A' || (*cstringInput) == 'a' ||
          (*cstringInput) == 'E' || (*cstringInput) == 'e' ||
          (*cstringInput) == 'I' || (*cstringInput) == 'i' ||
          (*cstringInput) == 'O' || (*cstringInput) == 'o' ||
          (*cstringInput) == 'U' || (*cstringInput) == 'u'
         )
       {
       count++;
       }
       cstringInput++;
    }

    return count;
}

int countConsonants(char (*cstringInput))
{
  int count=0;
  while (*(cstringInput) != '\0')
  {
      if (
          (*cstringInput) == 'A' || (*cstringInput) == 'a' ||
          (*cstringInput) == 'E' || (*cstringInput) == 'e' ||
          (*cstringInput) == 'I' || (*cstringInput) == 'i' ||
          (*cstringInput) == 'O' || (*cstringInput) == 'o' ||
          (*cstringInput) == 'U' || (*cstringInput) == 'u'
         )
       {
         cstringInput++;
       }
     else
     {
       count++;
       cstringInput++;
     }
  }

  return count;
}
