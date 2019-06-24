#include <stdio.h>

struct Stuff
{
	int a;
	unsigned char b;
	double c[300];
};

int main(void)
{
	struct Stuff myStuff;
	printf("myStuff.b = %x\n",(unsigned int)(myStuff.b));
	myStuff.a = 54;
	myStuff.b = 'b';
	myStuff.c[45]= 67.5;
	printf("sizeof(struct Stuff) = %d\n",sizeof(struct Stuff));
	printf("sizeof(unsigned char) = %d\n",sizeof(unsigned char));
	printf("sizeof(int) = %d\n",sizeof(int));
	printf("sizeof(double) = %d\n",sizeof(double));

	printf("myStuff.b = %u\n",myStuff.b);
	int i = 305;
	printf("myStuff.c[305] = %f\n",myStuff.c[i]);
	myStuff.c[-i] = 666;
}
