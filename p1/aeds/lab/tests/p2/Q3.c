#include <stdio.h>
#include <stdlib.h>

float *sumVet(float *, float *, int, int);

int main()
{
    float v1[] = {1, 2, 3, 4, 5};
    float v2[] = {1, 2, 3};

    float *sum = sumVet(v1, v2, 5, 3);

    for (int i = 0; i < 5; i++)
    {
        printf("%f ", sum[i]);
    }
}

float *sumVet(float *v1, float *v2, int t1, int t2)
{
    int biggest = t1 > t2 ? t1 : t2;

    float *sum = (float *)malloc(biggest * sizeof(float));

    for (int i = 0; i < biggest; i++)
    {
        int aux1, aux2;

        if (t1 - 1 < i)
            aux1 = 0;
        else
            aux1 = v1[i];

        if (t2 - 1 < i)
            aux2 = 0;
        else
            aux2 = v2[i];

        sum[i] = aux1 + aux2;
    }

    return sum;
}