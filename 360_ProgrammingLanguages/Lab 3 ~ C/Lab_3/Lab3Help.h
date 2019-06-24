/*Lab 3 Help file. Contains important methods and structs to be used
in the Lab 3 file. */

/* Tiffany Jansen 
   CS 360: Lab 3 */
   
#include <stdio.h>

#define MegaByte 1048576

/* Function Prototypes */
int intitialize(int num, char ** args);
struct fileRead readFile(void);
unsigned char * findInfo(struct fileRead *);
void printInfo(unsigned char *);
void copyRightInfo(unsigned char);
void freqBitInfo(unsigned char);

FILE * fp;

struct 
fileRead
{
  unsigned char *dataBytes;
  int containedBytes;
}

;int 
intitialize(int num, char ** args)
{
  // Open the file given on the command line
  if(num != 2){
    printf("Usage: %s filename.mp3\n", args[0] );
		return(1);
  }
  fp = fopen(args[1], "rb");
	if( fp == NULL )
	{
		printf( "Can't open file %s\n", args[1] );
		return(1);
	}
  return(0);
}

struct fileRead 
readFile()
{
  // How many bytes are there in the file?  If you know the OS you're
	// on you can use a system API call to find out.  Here we use ANSI standard
	// function calls.
	struct fileRead file;
  
  double size = 0;
	fseek( fp, 0, SEEK_END );		// go to 0 bytes from the end
	size = ftell(fp);				// how far from the beginning?
	rewind(fp);						// go back to the beginning
	
	if( size < 1 || size > 10*MegaByte)
	{
		printf("File size is not within the allowed range\n"); 
		fclose(fp);				// close and free the file
    exit(EXIT_SUCCESS);		// or return 0;
	}
	
	printf("File size: %.2f MB\n", size/MegaByte);
	// Allocate memory on the heap for a copy of the file
  //free(data);
	unsigned char * data = (unsigned char *)malloc(size);
	// Read it into our block of memory
	size_t bytesRead = fread( data, sizeof(unsigned char), size, fp );
  if( bytesRead != size )
	{
		printf( "Error reading file. Unexpected number of bytes read: %li\n",bytesRead );
		fclose(fp);				// close and free the file
	  exit(EXIT_SUCCESS);		// or return 0;
	}
  file.dataBytes = data;
  file.containedBytes = bytesRead;
  //free(data);
  return file;
}

unsigned char *
findInfo(struct fileRead *data)
{
  int num = data->containedBytes;
  printf("\n");
  unsigned char *fBytes = data->dataBytes;
  unsigned char list[4];
  unsigned char *pList = list;
  for(int i = num - 1; i > -1; i--){
    if(*(fBytes+i) == 0xFF && *(fBytes + i + 1) >= 0xF0){
        for(int n =0; n < 4; n++){
          list[n] = *(fBytes + (i + n));
        }
        return pList;
        break;
    }
  }
  return pList;
}

void
printInfo(unsigned char *pList){
  unsigned char bitFreq = *(pList + 2);
  unsigned char copyRight = *(pList + 3);
  unsigned char mLay = *(pList + 1);
  if(mLay == 0xFA || mLay == 0xFB){
    printf("This is a MPEG Layer 3 file.\n");
    freqBitInfo(bitFreq);
    copyRightInfo(copyRight);
  }
  else{
    printf("This is not a MPEG Layer 3 file.");
  }
}

void
freqBitInfo(unsigned char bitFreq){
  bitFreq >>= 2;
  double freq = 0;
  if((bitFreq & 01) == 1){
    freq = 48.0;
  }
  else if((bitFreq & 10) == 1){
    freq = 32.0;
  }
  else{
    freq = 44.1;
  }
  bitFreq >>= 2;
  switch(bitFreq){
    case 1 : printf("32kbps at "); 
            break;
    case 2 : printf("40kbps at ");
            break;
    case 3 : printf("48kbps at ");
            break;
    case 4 : printf("56kbps at ");
            break;
    case 5 : printf("64kbps at ");
            break;
    case 6 : printf("80kbps at "); 
            break;
    case 7 : printf("96kbps at ");
            break;
    case 8 : printf("112kbps at ");
            break;
    case 9 : printf("128kbps at ");
            break;
    case 10 : printf("160kbps at ");
            break;
    case 11 : printf("192kbps at ");
            break;
    case 12 : printf("224kbps at ");
            break;
    case 13 : printf("256kbps at ");
            break;
    case 14 : printf("320kbps at ");
            break; 
    default : printf("Ummmm... something went wrong.");
            break;
  }
  printf("%.1fkHz\n", freq);
}

void
copyRightInfo(unsigned char copyRight){
  copyRight >>= 2;
  if((copyRight & 01) == 1){
    printf("This is not copyrighted and it is not a copy.\n");
  }
  else if((copyRight & 10) == 1){
    printf("This is copyrighted and it is a copy.\n");
  }
  else if((copyRight & 11) == 1){
     printf("This is copyrighted and it is not a copy.\n");
  }
  else{
    printf("This is not copyrighted and it is a copy.\n");
  }
}

