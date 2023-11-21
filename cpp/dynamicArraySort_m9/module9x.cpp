/*

Write a program that dynamically allocates an array large enough to hold a user-defined number of test scores.
Once all the scores are entered, the array should be passed to a function that sorts them in ascending order.
Another function should be called that calculates the average score.
The program should display the sorted list of scores and averages with appropriate headings.

Use pointer notation rather than array notation whenever possible.

Input Validation: Do not accept negative numbers for test scores.


©ALL RIGHTS RESERVED
*/

#include<limits>
#include<iostream>
#include<array>
#include<iomanip>
#include<algorithm>

using namespace std;

void SelectionSort(int (*dynamicArray), int &arraySize);//sorting algorithm
void CalculateAverage(int (*dynamicArray),int &arraySize); //calculate averages
void PressEnterToContinue(); //pause function
void ClearCin(); //clear cin for input validation

int main()
{
  int ArraySize = 0;

  //  validate input to make sure it doesn't inlcude negatives
  cout << "How many tests will you enter? \n";
  cin  >> ArraySize;
                while ( ArraySize < 0 || !cin)
                {
                  cout << "Enter a valid number > 0. \n";
                  ClearCin();
                  cin >> ArraySize;
                }
  int *dynamicArray = new int[ArraySize];

  for (int sentinel = 0; sentinel < ArraySize; sentinel++) //increment sentinel until test scores can be entered.
    {
    cout << "Enter test #" << sentinel+1 << "'s score. \n";
    cin  >> *(dynamicArray + sentinel);

      while (*(dynamicArray + sentinel) < 0 || !cin) //input validation
      {
        cout << "\t\n***Please enter an valid number.\n";
        ClearCin();
        cout << "\n\t" << "Enter test score #" <<sentinel+1 << '\n';
        cin  >> *(dynamicArray + sentinel);
        cout <<'\n';
      }
    }

  cout << "\n*****\t*****\t*****\t*****\t*****\t*****\n";
  SelectionSort(dynamicArray, ArraySize);   //sorting algorithm

  cout <<'\t' <<  "Array after sorting: \n";  //print each value of array after sorting.
  for (int sentinel2 = 0; sentinel2 < ArraySize; sentinel2++)
    {
    cout << "\tindex#: "<< sentinel2 << "\n\t   Value: " << *(dynamicArray + sentinel2) << '\n';
    }


  cout << "*****\t*****\t*****\t*****\t*****\t*****";
  cout << '\n';
  cout << "\nHere are the scores you entered in ascending order: \n";
  cout << setprecision(2) << fixed;
  cout << "*******\n";

  for (int sentinel3 = 0; sentinel3 < ArraySize; sentinel3++)
    {
      cout << *(dynamicArray + sentinel3) << '\n';
    }


    cout << "*******\n";


  cout << "\nResults \n";
  cout << "*******\n";
  CalculateAverage(dynamicArray, ArraySize);
  cout << "*******\n\n\n";


  delete[] dynamicArray;
  PressEnterToContinue();
  return 0;

}

  void SelectionSort(int (*array), int &ArraySize)//select sort algorithm
    {
          int i, j, min, count2;
          i = 0;   // i = current iteration
          j = 0;
          min = 0;  // min = smallest element

          for (i = 0; i < ArraySize - 1; i++) //major interation
          {
              min = i; //set minimum element to iteration
              for (j = i + 1; j < ArraySize ; j++) //comparison and checking loop
                {
                        if (*(array+j)< *(array+min))
                        {
                          min = j;
                        }
                }
              if (min != i)
                {
                  swap(*(array+min),*(array+i));
                }

          }
    }

  void CalculateAverage(int (*array), int &ArraySize) //calculate averages of test scores.
    {
      double total = 0;
      int average = 0;
      for (int start = 0; start < ArraySize; start++)
      {
        total = total+ *(array+start);
      }


      cout << "\nNumber of tests administered = " << ArraySize;
      cout << "\nSum of tests = " << total;
      cout << "\nAverage score = " << total/ArraySize << '\n';
    }
  void PressEnterToContinue() //this function will pause the program until the enter key is pressed.
    {
      cout << "Press ENTER to continue... \n" << flush;
      cin.ignore( numeric_limits <streamsize> ::max(), '\n' );
      cin.get();
    }
  void ClearCin() //this function clears the cin
    {
      cin.clear(); //clears errors
      cin.ignore( numeric_limits <streamsize> ::max(), '\n' ); //ignores previous cin data up until new line character
    }
