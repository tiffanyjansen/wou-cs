
/* Including other files */

#include <stdio.h>
#include "linked_list.h"

/* Conditional compilation */

// For GCC, predefined macros: http://gcc.gnu.org/onlinedocs/cpp/Predefined-Macros.html
#ifdef _WIN64
	// definitions specific to 64 bit windows
#elif _WIN32
	//...
#elif __APPLE__
	// for Macs
#elif __posix
	// for unix
#endif

/* Macros */

#define PI 		3.141592653589793
#define TWOPI 	6.283185307179586
#define TORADIANS(x)	((x) * 0.017453292519943295)


int main( int argc, char * argv[] )
{
    printf("Hello World!\n");
    return 0;
}
