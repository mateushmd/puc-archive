#include <stdio.h>

int main()
{
    int n, divisores = 2;

    scanf("%d", &n);

    for (int i = n / 2; i > 1; i--)
    {
        if (n % i == 0)
            divisores++;
    }

    printf("%d\n", divisores);
}