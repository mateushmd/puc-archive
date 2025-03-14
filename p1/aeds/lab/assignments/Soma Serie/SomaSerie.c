#include <stdio.h>

int main()
{
    int t;
    float s = 0;

    scanf("%d", &t);

    for (float i = 1; i <= (float)t; i += 1)
    {
        s += (2.0 * i - 1.0) / i;
    }

    printf("%.2f", s);
}