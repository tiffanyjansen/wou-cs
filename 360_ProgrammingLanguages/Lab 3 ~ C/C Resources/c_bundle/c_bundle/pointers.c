#include <stdio.h>
#include <math.h>

double fun( int a, double b )
{
	return a*2.0+b;
}

struct Complex
{
	float r;
	float i;
};

int main( void )
{
	int * pi = NULL;// = 0x0045;
	//	*pi = 43;
	int A = 999999;
	int * p = &A;
	float * pF = (float *)&A;
	printf("pF = %p, *pF = %f\n",pF,*pF);
//	int & r = A;					// C++ only
	
	printf("A = %i\n",A);
	printf("&A = %i\n",&A);
	printf("&A = %p\n",&A);
	printf("p = %p\n",p);
	printf("&p = %p\n",&p);
	printf("*p = %i\n",*p);
//	printf("r = %i\n",r);		// C++ only

	*p = 22;
	printf("A = %i\n\n",A);
	
//	r = 99;						// C++ only
//	printf("A = %i\n",A);

	struct Complex z = {1.0,0.5};
	struct Complex * pZ = &z;
	z.r = 2.0;
	pZ->i = 5.0;
	printf("z = (%f,%f)\n\n",pZ->r,pZ->i);
	
	(*pZ).i = 99;
	printf("z = (%f,%f)\n\n",pZ->r,pZ->i);


	// Pointer to array
	int arr[100];
	int i;
	for(i = 0; i < 99; ++i)
		arr[i] = 4*i;
	int * ptr = arr;			// or int * ptr = &arr[0];
	for(i = 0; i < 99; ++i)
		*(arr+i) = 4*i;			// pointer arithmetic
	printf("arr[1] = %i\n\n",arr[1]);
	
	
	// Pointer to a function
	double (*fp)(int,double);
	fp = fun;
	double answer = (*fp)(2,10.0);
	printf("answer = %f\n",answer);
	answer = fp(2,10.0);
	printf("answer = %f\n",answer);
	
	// Powerful but dangerous stuff
	int val = 15;
	printf("val is %i or %X\n",val,val);
	unsigned char * pChar = (unsigned char *)&val;
	printf("Bytes of val: %i, %i, %i, %i\n",*pChar,*(pChar+1),
			*(pChar+2),*(pChar+3));
	// Intel is little endian LSB is to the left
	*(pChar+3) = 0xff;
	printf("val was 15, now is %i\n\n",val);

	return 0;	
}


