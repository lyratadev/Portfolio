/*

Write a program with three functions: upper, lower, and reverse.
The upper function should accept a pointer to a C-string as an argument.
It should step through each character in the string, converting it to uppercase.
The lower function, too, should accept a pointer to a C-string as an argument.
It should step through each character in the string, converting it to lowercase.
Like upper and lower, the reverse function should also accept a pointer to a C-string.

As it steps through the string, it should test each character to determine whether it is uppercase or lowercase. If a character is uppercase, it should be converted to lowercase. Likewise, if a character is lowercase, it should be converted to uppercase.

Test the functions by asking for a string in function main, then passing it to them in the following order: reverse, lower, and upper.
 
 */

#include <iostream>
#include <stdio.h>
#include <ctype.h>
using namespace std;

//function prototypes
void upper(char*);
void lower(char*);
void reverse(char*);

void pause()
  {
    cout << "Press ENTER to continue... \n" << flush;
    cin.ignore( numeric_limits <streamsize> ::max(), '\n' );
    cin.get();
  }

int main()
{
    
    char *myArray = new char[100];
    char *stringPtr = myArray;
    cout << "\n Please enter a string.\n" << endl;
    cin.getline(stringPtr,100);
    
    
    cout << "\n You typed: \n\n\t" << stringPtr << endl;
    cout << "\n Changing the lowercase letters to uppercase and vice versa will yield: \n\n";
    
    //cout <<"\n Calling reverse function" << endl;
    reverse(stringPtr);
    cout << "\t" << stringPtr;
    
    
    
    stringPtr = myArray;
    //cout << "\n\n redirecting pointer" << endl;
    
    cout << "\n\n Casting uppercase letters to lowercase yields: \n\n";
    lower(stringPtr);
    
    //cout << "\n\nback in main\n\n";
    stringPtr = myArray;
    
    //cout << "\n\n Present status of string: \n" << stringPtr << endl;
    cout << "\n\n Casting the lowercase letters to uppercase yields: \n\n";
    
    upper(stringPtr);

    
    cout << "\n\n End of program." << endl;
    
    delete[] myArray;
    pause();
    return 0;
}

void reverse(char *y)
    {
        //cout << &y << endl;
        while (*y != '\0')
        {
           
            if (isupper(*y) == 1)
            {
                *y= tolower(*y);
                //cout << *y;
                y++;
            }
            else if (islower(*y) == 1)
                  {
                      *y= toupper(*y);
                     // cout << *y;
                      y++;
                  }
            else
            {
                y++;
                //cout << "\n****error\n";
                //exit(1);
            }
                
        }
    }


void upper(char *stringPtr)
    {
        while (*stringPtr != '\0')
        {
            *stringPtr= toupper(*stringPtr);
            cout << *stringPtr;
            stringPtr++;
        }
    }
 
void lower(char *stringPtr)
    {
        while (*stringPtr != '\0')
        {
            *stringPtr= tolower(*stringPtr);
            cout << *stringPtr;
            stringPtr++;
        }
    }
