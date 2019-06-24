#include<stdio.h>

struct piece
{
	int a;
	float b;
};

void func1( struct piece x )
{
	x.a = 99;
	x.b = 99.9f;
}

void func2( struct piece * x )
{
	x->a = 99;
	x->b = 99.9f;
}

int main(void)
{
	struct piece i = {2,4.5f};
	
	printf("i.a = %i, i.b = %f",i.a, i.b);
	
	printf("\nCalling func1");
	func1(i);
	printf("\nAfter func1:");
	printf("\n\ti.a = %i, i.b = %f",i.a, i.b);
	
	printf("\nCalling func2");
	func2(&i);
	printf("\nAfter func2:");
	printf("\n\ti.a = %i, i.b = %f",i.a, i.b);
	printf("\n");
}
