#ifndef BasicShape_h
#define BasicShape_h


class BasicShape; // Forward Declaration


class BasicShape //PARENT CLASS
{
private:
   double area;        // To hold a shapes area
public:
   // Default Constructor
    BasicShape();

   // Mutator functions
    void setArea(double);
    virtual void calcArea() = 0;
    double getArea() const;

};


class Circle : public virtual BasicShape //CHILD CLASS TYPE PUBLIC
{
    private:
        long centerX;//long int to hold x coordinate of cirecle
        long centerY;
        double radius;//double to hold radius
        
    public:
    Circle();
    Circle(long, long, double);
    
    void calcArea();
    //Accessor Function
    long getCenterX();
    long getCenterY();
    
};

class Rectangle : public virtual BasicShape //CHILD CLASS
{
    private:
        long width;
        long length;
    public:
    Rectangle();
    Rectangle(long, long);
    void calcArea();
    
    //Accessor
    long getWidth();
    long getLength();
    
};

#endif /* BasicShape_h */
