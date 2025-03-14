#include <stdio.h>
#include <stdlib.h>

int main()
{
    int n;

    float biggest, smallest, sum = 0, avarage;

    float *grades;

    scanf("%d", &n);

    grades = (float *)malloc(n * sizeof(float));

    for (int i = 0; i < n; i++)
    {
        scanf("%f", &grades[i]);
    }

    biggest = smallest = grades[0];

    for (int i = 0; i < n; i++)
    {
        if (grades[i] < smallest)
            smallest = grades[i];

        if (grades[i] > biggest)
            biggest = grades[i];

        sum += grades[i];
    }

    avarage = sum / n;

    printf("%.1f %.1f %.1f\n", biggest, smallest, avarage);
}