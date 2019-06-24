/* CS315 Lab 3: C data types */

#include <stdio.h>
#include <stdlib.h>
#include "Lab3Help.h"

int 
main( int argc, char ** argv )
{
	int num = intitialize(argc, argv);
  if(num != 0){
    return(EXIT_FAILURE);
  }
  struct fileRead data = readFile();
  // We now have a pointer to the first byte of data in a copy of the file, have fun
	// unsigned char * data    <--- this is the pointer
  printInfo(findInfo(&data));
}