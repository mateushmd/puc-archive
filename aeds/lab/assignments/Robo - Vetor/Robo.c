#include <stdio.h>
#include <stdlib.h>

int main()
{
    int n, *check, sum = 0, p = -1;

    scanf("%d", &n);

    check = (int *)calloc(n, sizeof(int));

    for (int i = 0; i < n * 2; i++)
    {
        int input;

        scanf("%d", &input);

        input--;

        if (!check[input])
        {
            check[input] = 1;
            sum++;
        }

        if (sum == n && p == -1)
            p = i + 1;
    }

    printf("%d", p == -1 ? 0 : p);

    free(check);
}