#include <stdio.h>

/* Declare a union type.  Unions are like alternation,
	i.e. OR, whereas structs are like composition, i.e 
	AND.*/

union Multi
{
	int id;
	float percentage;
	unsigned char pieces[4];
};

/* Helper function to print out the contents of a
	union Multi.*/
void printMulti( union Multi u )
{
	printf("int\t\tfloat\t\tchar[0]\t[1]\t[2]\t[3]\thex\n");
	printf("%11d\t%g\t%d\t%d\t%d\t%d\t0x%08X\n",u.id,u.percentage,
			u.pieces[0],u.pieces[1],u.pieces[2],u.pieces[3],*((unsigned int*)&u));
	printf("\n");
}

/* Entry function. */
int main(void)
{
	// Create (allocate memory) for a union Multi
	union Multi data;
	printf("\nsizeof(union Multi) = %d\n\n",sizeof(union Multi));

	// What's in it now?
	printMulti( data );

	// Write to the int
	data.id = 65536;
	printf("_____data.id = 65536_____\n");
	printMulti( data );

	data.percentage = 91.456e12;
	printf("_____data.percentage = 91.456e12_____\n");
	printMulti( data );

	data.pieces[0] = 0x01;
	data.pieces[1] = 0x00;
	data.pieces[2] = 0xff;
	data.pieces[3] = 0xaa;
	printf("_____data.pieces = 0x0100ffaa_____\n");
	printMulti( data );

}
