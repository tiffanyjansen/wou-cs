#include <stdio.h>
#include <stdlib.h>

/* Generate all permutations of a set. Knuth, The Art of Computer
Programming, Volume 4, Fascicle 2, Section 7.2.1.2  Original algorithm
due to Trotter CACM 1962, and Johnson.

-Scot Morse
*/

#define NMAX 12

// What to do with each permutation
void visit(int P[], int N)
{
	int i;
	for( i = 1; i <= N; ++i )
	{
		printf("%i ",P[i]);
	}
	printf("\n");
}

int main( void )
{
	printf("N = %i\n",NMAX);
	int a[NMAX+2];		// a_1 through a_n
	int c[NMAX+2];		// c_1 through c_n
	int o[NMAX+2];		// o_1 through o_n
	
	int n = NMAX;
	int s,q,temp;
	// Initialize
	int j;
	for( j = 1; j <= n; ++j )
	{
		a[j] = j;
		c[j] = 0;
		o[j] = 1;
	}
P2: 	// Visit a[1:n]
//	visit(a,n);
	// Prepare for change
P3:	j = n;
	s = 0;
P4:	// Ready to change?
	q = c[j] + o[j];
	if( q < 0 )
		goto P7;
	else if( q == j )
		goto P6;
P5:	// Change (exchange)
	temp = a[j-c[j]+s];
	a[j-c[j]+s] = a[j-q+s];
	a[j-q+s] = temp;
	c[j] = q;
	goto P2;
P6:	// Increase s
	if( j ==1 )
		goto END;
	else
		++s;
P7:	// Switch direction
	o[j] = -o[j];
	--j;
	goto P4;
	
END:
	printf("\nDone\n");
}

