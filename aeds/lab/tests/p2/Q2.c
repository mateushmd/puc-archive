#include <stdio.h>

void printBinary(int);

int main()
{
    for(int i = 0; i < 256; i++)
    {
        printBinary(i);
        printf("\n");
    }
}

void printBinary(int n)
{
    if(n == 0)
    {
        printf("0");
        return;
    }

    if (n < 2)
    {
        printf("1");
        return;
    }
    
    printBinary(n / 2);

    printf("%d", n % 2);
}