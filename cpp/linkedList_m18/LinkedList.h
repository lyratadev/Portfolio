

#ifndef LinkedList_h
#define LinkedList_h
#include <stdio.h>

//forward declarations
class LinkedList;
class Node;


class Node
{
    public:
    int data;
    class Node *next; //self Referential
    ~Node();
};


class LinkedList
{
    private:
    Node *head; //Starting point of node
    public:
    LinkedList();
    ~LinkedList(); //Destructor
    void appendNode(int);
    void deleteNode(int);
    void insertNode(int);
    void displayList();
};

void PressEnterToContinue();

#endif /*  */
