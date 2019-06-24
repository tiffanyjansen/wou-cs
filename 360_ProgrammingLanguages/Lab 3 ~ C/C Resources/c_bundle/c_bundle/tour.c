#include <stdio.h>
#include <stdlib.h>



// structs

struct Book
{
	char author[64];
	char title[128];
	int id;
	unsigned int numberOfPages;
};

void doSomething(struct Book * pb)
{
	printf("author is %s\n",pb->author);
	pb->numberOfPages = 5;
}

typedef struct
{
	int x;
	int y;
}  Point;

// union

union TripleConstraint
{
	long int cost;
	double quality;
	char speed;
};

typedef union
{
	char kph;
	int mph;
} Speed;

int main( int argc, char * argv[] )
{
	struct Book myBook = {"Jules Verne","20,000 Leagues Under the Sea",45938223,324};
	doSomething( &myBook );
	printf("number of pages is %u\n",myBook.numberOfPages);
	printf("My book is %s by %s.\n",myBook.title,myBook.author);
	printf("sizeof(myBook) = %zd bytes\n",sizeof(myBook));
	printf("sizeof(struct Book) = %zd bytes\n\n",sizeof(struct Book));
	
	Point p1 = {100,200};
	Point p2;
	p2.x = 5;
	p2.y = 20;
	printf("p1 is at %d,%d while p2 is at %d,%d.\n",p1.x,p1.y,p2.x,p2.y); 
	printf("sizeof(p1) = %zu bytes\n",sizeof(p1));
	printf("sizeof(Point) = %zu bytes\n",sizeof(Point));
	printf("sizeof(int) = %zu bytes\n",sizeof(int));
	printf("sizeof(unsigned int) = %zu bytes\n",sizeof(unsigned int));
	printf("sizeof(char) = %zu bytes\n\n",sizeof(char));

	union TripleConstraint t;
	t.cost = 45002032;
	printf("sizeof(t) = %zu bytes\n",sizeof(t));
	printf("sizeof(union TripleConstraint) = %zu bytes\n",sizeof(union TripleConstraint));
	printf("sizeof(long int) = %zu bytes\n\n",sizeof(long int));
	
	Speed sp;
	sp.kph = 4;
	printf("sizeof(sp) = %zu bytes\n",sizeof(sp));
	
	exit(EXIT_SUCCESS);
}
