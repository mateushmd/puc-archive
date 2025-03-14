#include <stdio.h>

int main()
{
    int n, d = 1, r = 0;

    scanf("%d", &n);

    while (n / d > 9)
        d *= 10;

    for (int i = d; i >= 1; i /= 10)
    {
        int o = n / i;
        r += o * (d / i);
        n -= o * i;
    }

    printf("%d", r);
}