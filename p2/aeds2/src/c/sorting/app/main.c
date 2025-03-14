#include "arrays.h"
#include "countingsort.h"

#define SIZE 10

int main()
{
    int *arr = generateArray(SIZE);
    printArray(arr, SIZE);
    countingsort(arr, SIZE);
    printArray(arr, SIZE);
}