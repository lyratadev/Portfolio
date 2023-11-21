/*
Write a program that stores the following data about a soccer player in a structure:

           Player’s Name

           Player’s Number

           Points Scored by Player

The program should keep an array of 12 of these structures.
Each element is for a different player on a team.
When the program runs, it should ask the user to enter the data for each player.
It should then show a table that lists each player’s number, name, and points scored.
The program should also calculate and display the total points earned by the team.
The number and name of the player who has earned the most points should also be displayed.

     Input Validation: Do no accept negative values for players’ numbers or points scored.

©ALL RIGHTS RESERVED
*/

#include<string>
#include<iostream>
#include<iomanip>
using namespace std;

void ClearCin();//function prototypes
void PressEnterToContinue();

  struct Player
  {
    string Name;
    signed int Number;
    signed int Score;
  };

int main()
{
  struct Player card[12];

  for (int sent=0; sent < 12; sent++)
  {
    cout << "Player " << sent + 1 << '\n';
    cout << "Enter the players name. \n\t";
    getline(cin, card[sent].Name);

    cout << "Enter the players number. \n\t";
    cin  >> card[sent].Number;
        while (!card[sent].Number || card[sent].Number <= 0)
          {
            cout << "\n**Invalid input, you must use an valid positive number > 0 .**\n";
            ClearCin();
            cout << "Enter the players number. \n\t";
            cin  >> card[sent].Number;
          }

    ClearCin();
    cout << "Enter the players score. \n\t";
    cin  >> card[sent].Score;
        while (!card[sent].Score || card[sent].Score < 0)
          {
            cout<< "\n**Invalid input, you must use an valid positive number.**\n";
            ClearCin();
            cout<< "Enter the players number. \n\t";
            cin >> card[sent].Score;
          }
    ClearCin();


    cout << "\n\n\tCard " << sent+1;
    cout << "\n\n\tPlayer\n";
    cout << "\tName: "  << card[sent].Name   << '\n';
    cout << "\tNumber " << card[sent].Number << '\n';
    cout << "\tScore "  << card[sent].Score  << '\n';
    cout << "\n";
  }



  cout << setw(15) << "Name" << setw(15) << "Number"<< setw(15) <<"Score.\n"; //output formatting in table.
  cout << "*********************************************\n";

  //TOTAL POINTS AND SCOREBOARD
  int totalScore = 0;
  for (int i = 0; i < 12; i++)
  {
    cout << setw(15) << card[i].Name << setw(15)<< card[i].Number << setw(15) << card[i].Score << '\n';
    totalScore += card[i].Score;
  }
  cout << "*********************************************\n";
  cout << "Total team score: " << totalScore << '\n';

    // FIND MVP
    for(int i = 0;i < 12; i++)
    {
       if(card[0].Score < card[i].Score)
         card[0] = card[i];
    }

  cout << "MVP = " << card[0].Name << "\nPlayer Number: " << card[0].Number << "\nWith a score of " << card[0].Score << " points. \n";
  cout << "*********************\n";

  PressEnterToContinue();
  return 0;
}

void ClearCin() //this function clears the cin
  {
    cin.clear(); //clears errors
    cin.ignore( numeric_limits <streamsize> ::max(), '\n' ); //ignores previous cin data up until new line character
  }

void PressEnterToContinue() //this function will pause the program until the enter key is pressed.
  {
    cout << "Press ENTER to continue... \n" << flush;
    cin.ignore( numeric_limits <streamsize> ::max(), '\n' );
    cin.get();
  }
