
//  Design your own linked list class to hold a series of integers.
//  The class should have member functions for appending, inserting, and deleting nodes.
//  Don’t forget to add a destructor that destroys the list. Demonstrate the class with a driver program.


#include"LinkedList.h"
#include <iostream>
using namespace std;

int main()
{
    //creating linked list objcet
    cout << "Creating Linked List with three nodes of values 1, 6, and 13 \n";
    LinkedList list;
    
    //building a list
    list.appendNode(1);
    list.appendNode(6);
    list.appendNode(9);
    
    //displaying the relevant nodes
    cout << "Here's the nodes. \n";
    list.displayList();
    cout << endl;
    
    
    //
    cout << "Inserting a node with the value of 5.\n" << endl;
    list.insertNode(5);
    
    //display list again
    cout << "Here's the list so far.\n" << endl;
    list.displayList();
    cout << endl;
    
    //delete the node 6
    cout << "Deleting the 5 node.\n" <<endl;
    list.deleteNode(5);
    
    
    //display list again
    cout << "Here's the list again. \n" <<endl;
    list.displayList();
    cout << endl;
    
    //Insert -3 to the list
    cout << "Lets append the list with the value -3 \n" << endl;
    list.insertNode(-3);
    
    //display list again
    cout << "Here's the list so far...\n" << endl;
    list.displayList();
    cout << endl;
    
    cout << "Would you like to insert a node? \n Enter an integer to add to the list:  \n\t";
    
    int y;
    cin >> y;
    list.insertNode(y);
    cout << "Here's the new list with your appended value. \n\n";
    list.displayList();
    

    cout << "\nEnd of program\n";
    PressEnterToContinue();
    return 0;
}
