#include"BasicShape.h"
#include <stdio.h>
#include <iostream>


//DEFINING CLASS FUNCTIONS
//BASIC SHAPE

   BasicShape::BasicShape()
      { area = 0; }

   // Mutator functions
   void BasicShape::setArea(double f)     //sets value of private area member
      { area = f; }

   // Accessor functions
   double BasicShape::getArea() const
      { return area; }


//#############CIRCLE

Circle::Circle()//default constructor
        {
            centerX = 0;
            centerY = 0;
            radius = 0;
        }
Circle::Circle(long cX, long cY, double R)
           {
               centerX = cX;
               centerY = cY;
               radius = R;
               calcArea();
           }
    
void Circle::calcArea() //virtual function instance
    {
        double temparea = 3.14159*radius*radius;
        setArea(temparea);
    }

    
    //Accessor Function
long Circle::getCenterX()
    {
        return centerX;
    }
long Circle::getCenterY()
    {
        return centerY;
    }


//##########RECTANGLE
   

Rectangle::Rectangle()//default constructor
        {
            width = 0;
            length = 0;
        }
Rectangle::Rectangle(long w, long l)
           {
               width = w;
               length = l;
               calcArea();
           }
void Rectangle::calcArea() //overridden calc area for rectangle
    {
        double Tarea =length*width;
        setArea(Tarea);
    }
    
    //Accessor
long Rectangle::getWidth()
    {
        return width;
    }
long Rectangle::getLength()
    {
        return length;
    }
    
