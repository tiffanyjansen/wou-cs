#include <stdlib.h>

// static storage
float a[200];

int main(void)
{
	// stack storage allocation, "automatic" variables
	float b[300];

	// heap storage
	float * c = (float *)malloc(400*sizeof(float));
	*(c+45) = 93.4;
	c[45] = 93.4;

	//free(c);
	return 0;
}
