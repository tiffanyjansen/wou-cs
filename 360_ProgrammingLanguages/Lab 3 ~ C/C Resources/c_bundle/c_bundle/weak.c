#include <stdio.h>
#include <string.h>

int add(int a, int b)
{
    return a + b;
}

int main(void)
{
    int a = 5;
    int b = 7;
    int c = add(a,b);
    printf("a + b = %d + %d = %#010x + %#010x = %#010x\n",a,b,a,b,c);
    float x = 5.5;
    float d = add(a,x);
    unsigned int ix;
    memcpy(&ix, &x, sizeof(ix));
    unsigned int id;
    memcpy(&id, &d, sizeof(id));
    printf("a + x = %d + %f = %#010x + %#010x = %#010x = %f\n",a,x,a,ix,id,d);
}

