//Write a template for a function called total.
//The function should keep running total of values entered by the user, then return the total.
//The argument sent into the function should be the number of values the function is to read.
//Test the template in a simple driver program that sends values of various types as arguments and displays the results.

#include "total.h"
#include<iostream>
using namespace std;

void validate(int &input);//validates input
void PressEnterToContinue(); //this function will pause the program until the enter key is pressed.

int main()
{
    int input = 0;
    
    cout << "How many integer values do you wish to total?\n";
    cin  >> input;
    validate(input);
    cout << total<int>(input) << endl;
    
    cout << "\nHow many double values do you wish to total?\n";
    cin >> input;
    validate(input);
    cout << total<double>(input) << endl;
    
    PressEnterToContinue();
    return 0;
}

void validate(int &input)
{
     while(!cin)
     {
         cout << "\nInvalid input. Try again\n";
         cin.clear();cin.ignore();
         cin >> input;
     }
}

void PressEnterToContinue() //this function will pause the program until the enter key is pressed.
{
  cout << "\nPress ENTER to continue... \n" << flush;
  cin.ignore( numeric_limits <streamsize> ::max(), '\n' );
  cin.get();
}
