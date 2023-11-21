
#include "LinkedList.h"
#include<iostream>

using namespace std;

Node::~Node()
{
    next=nullptr;
};

// class Constructor
LinkedList::LinkedList()
{
      head = nullptr;
      //next = nullptr;
}

// class Destructor
LinkedList::~LinkedList()
{
    Node *nodePtr, *nextNode = nullptr;
    nodePtr = head;
    while (nodePtr != nullptr)
    {
        nextNode = nodePtr->next;
        delete nodePtr;
        nodePtr = nextNode;
    }
};







//class function definitions
void LinkedList::appendNode(int y)
{
        Node *newNode, *nodePtr = nullptr;
        newNode = new Node;
        newNode->data = y;
        newNode->next=nullptr;
        
    if (!head)//if the head is null, there is no nodes, ergo we are pointing to our new node as the head
    {
        head = newNode;
    }
    else //traverse the list to find the last node.
    {
        nodePtr = head; //start at the first node
        while (nodePtr->next) //while we are able to traverse to the chain until next = nullptr
        {
            nodePtr = nodePtr->next;
        }
            nodePtr->next=newNode;
        
    }
}



void LinkedList::deleteNode(int y)
{
    Node *nodePtr, *previousNode = nullptr;
    if (!head) //if head is empty
    {
        return;
    }
    
    if(head->data==y) //if head equals target node
    {
        nodePtr = head->next; // use traversing pointer to point to next node
        delete head; //delete present node head is pointing at
        head = nodePtr; //reassign head to point to next node.
    }
    
    else
    {
        nodePtr = head; //start at the head
        
        while (nodePtr != nullptr && nodePtr->data != y) //while node is not at end and not target value
        {
            previousNode = nodePtr; //increment
            nodePtr = nodePtr->next; //point to next node
        }
        
        if (nodePtr) //if node pointer is not null
        {
            previousNode->next = nodePtr->next;
            delete nodePtr;
        }
    }
}



void LinkedList::insertNode(int y)
    {
        Node *newNode, *nodePtr, *previousNode = nullptr; //node pointer variables
        newNode = new Node;           //creating new node
        newNode->data=y;              // assigning data to the new nodes data variable
        
        if (!head)                   //if the list is empty + pointing to null
        {
            head = newNode;          //point to list we just created
            newNode->next = nullptr; //the next value will be null with no nodes following
        }
        else                         //if list is not empty, we must traverse the list
            {
                nodePtr = head;      // start at the first node
                previousNode = nullptr;
            
                while (nodePtr != nullptr && nodePtr->data < y)
                {
                    previousNode = nodePtr; // have previous node point to the last node, while we increment the nodePtr to the next node.
                    nodePtr = nodePtr->next; //point to the next node
                }
                
                
                if (previousNode == nullptr)
                {
                    newNode->next=head;
                    head=newNode;
                    nodePtr=nullptr;
                }
                else
                {
                    previousNode->next=newNode;
                    newNode->next= nodePtr;
                }
                
            }
        

            
    }

void LinkedList::displayList()
{
    Node *nodePtr = nullptr;
    nodePtr = head;
    while (nodePtr) //while nodePtr is not null
    {
    cout << nodePtr->data << endl;
    nodePtr = nodePtr->next;//go to next node in chain
    }
    
}

void PressEnterToContinue() //this function will pause the program until the enter key is pressed.
{
  cout << "\nPress ENTER to continue... \n" << flush;
  cin.ignore( numeric_limits <streamsize> ::max(), '\n' );
  cin.get();
}
