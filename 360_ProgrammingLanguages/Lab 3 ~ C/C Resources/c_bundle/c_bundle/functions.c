#include <stdio.h>

/* Function prototypes. */
void take_float( float );
void take_int( int );
void take_int2( int * );
void take_array( int *, int );

int main( void )
{
	float a = 57.4;
	printf("\nfloat a has value %f at address %p\n",a,&a);
	take_float(a);
	
	int b = 33;
	printf("\nint b has value %d at address %p\n",b,&b);
	take_int(b);
	
	int c = 99;
	printf("\nint c has value %d at address %p\n",c,&c);
	take_int2(&c);
	printf("\nint c has value %d at address %p\n",c,&c);
	
	int d[40];
	printf("\nint d[40] starts at address %p, i.e. %p\n",d,&d[0]);
	take_array(d,40);
	
	printf("\n");
}

/* Function implementations. */
void take_float( float x)
{
	printf("Inside function take_float, received %f at address %p\n",x,&x);
}

void take_int( int x )
{
	printf("Inside function take_int, received %d at address %p\n",x,&x);
}

void take_int2( int * x )
{
	printf("Inside function take_int2, received %d at address %p\n",*x,x);
	*x = 67234;
}
 
void take_array( int * x, int length )
{
	printf("Inside take_array, received array at address %p\n",x);
}
