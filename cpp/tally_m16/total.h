#ifndef total_h
#define total_h
#include <stdio.h>
#include <iostream>

using namespace std;


template <typename T>
T total(T input)
{
    //T *valuePtr = new T[input];
    //T input;
    T temp;
    T totalV = 0;
    for (int i = 0; i < input; i++)
    {
        cout << "\nEnter a value: ";
        cin >> temp;
        while(!cin){cout << "\nInvalid input. Try again\n";cin.clear();cin.ignore();cin >> temp;}
        totalV = totalV+temp;
    }
    cout << "\n\tTotal: ";
    return totalV;
}



#endif /* total_hpp */
