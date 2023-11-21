//©ALL RIGHTS RESERVED
#include"BasicShape.h"
#include<iostream>

using namespace std;

void PressEnterToContinue() //this function will pause the program until the enter key is pressed.
{
  cout << "Press ENTER to continue... \n" << flush;
  cin.ignore( numeric_limits <streamsize> ::max(), '\n' );
  cin.get();
}

// ############### MAIN
int main()
{
    cout << "Please enter the x coordinate of the circle's center: " << endl;
    long xC = 0;
    cin >> xC;
    
    cout << "Please enter the y coordinate of the circles center: " << endl;
    long yC = 0;
    cin >> yC;
    
    cout << "Please enter the radius of the circle: " << endl;
    double r = 0;
    cin >> r;
    
    Circle myCircle(xC, yC, r);

    cout << "\n\tThe area of the circle is :" << myCircle.getArea() << endl;
    
   cout << "\nPlease enter the length of the rectangle: " << endl;
   long length = 0;
   cin >> length;
       
   cout << "Please enter the width of the rectangle: " << endl;
   long width = 0;
   cin >> width;
    Rectangle myRectangle(width,length);
    
    cout << "\n\tThe area of the Rectangle is: " << myRectangle.getArea() << endl;
    
    cout << "\n\n end of program" << endl;
    PressEnterToContinue();
    return 0;
    
}
