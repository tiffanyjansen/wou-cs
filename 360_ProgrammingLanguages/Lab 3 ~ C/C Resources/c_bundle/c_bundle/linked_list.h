#ifndef LINKED_LIST_H
#define LINKED_LIST_H

typedef struct node
{
    struct node * next;
    struct node * prev;
    Item data;
} Node;

typedef struct list
{
    Node * head;
    int size;
} DLList;

void append( DLList * list, Item * item );
void clear( DLList * list );
Item * pop( DLList * list );
// ...


#endif /* end of include guard: LINKED_LIST_H */
