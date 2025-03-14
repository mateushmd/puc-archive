#include <stdio.h>

int main()
{
    float v[4] = {300, 200, 50, 100};
    int c[4] = {0};
    int max, min;
    float tv = 0;
    int tc = 0;
    int cc;

    scanf("%d", &cc);

    while (cc != 5)
    {
        c[cc - 1]++;

        if (c[cc - 1] > c[max])
        {
            max = cc - 1;
        }

        tc++;

        scanf("%d", &cc);
    }

    min = 0;

    for (int i = 0; i < 4; i++)
    {
        tv += (float)(v[i] * c[i]);

        if (c[i] < c[min])
        {
            min = i;
        }

        printf("%d: %d\n", i + 1, c[i]);
    }

    printf("min: %d\n", min + 1);
    printf("max: %d\n", max + 1);
    printf("total: %.2f\n", tv);
    printf("media: %.2f", (float)(tv / tc));
}