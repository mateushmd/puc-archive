#include <stdio.h>

int main()
{
    int n, r = 1;

    scanf("%d", &n);

    if (n == 0)
    {
        printf("1\n");
        return 0;
    }

    for (int i = 1; i <= n; i++)
    {
        r *= i;
    }

    printf("%d\n", r);
}