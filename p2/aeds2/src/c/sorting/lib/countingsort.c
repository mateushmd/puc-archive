#include "countingsort.h"
#include "swap.h"
#include <stdlib.h>

void countingsort(int *arr, int n)
{
    int *sorted = (int *)malloc(n * sizeof(int)), *count;
    int nc = arr[0];

    for (int i = 0; i < n; i++)
        if (arr[i] > nc)
            nc = arr[i];

    nc++;
    count = (int *)calloc(nc, sizeof(int));

    for (int i = 0; i < n; count[arr[i]]++, i++);

    for (int i = 1; i < nc; count[i] += count[i - 1], i++);

    for (int i = 0; i < n; sorted[i] = count[arr[i]]--, i++);

    for(int i = 0; i < n; arr[i] = sorted[i], i++);
}