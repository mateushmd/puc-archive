#include <stdio.h>

int main()
{
    int n, sn, sum = 0;

    scanf("%d", &n);

    scanf("%d", &sn);

    int m[n][n];

    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < n; j++)
        {
            scanf("%d", &m[i][j]);
        }
    }

    for (int i = 0; i < sn; i++)
    {
        for (int j = 0; j < sn; j++)
        {
            if ((i != 0 && i != sn - 1) && (j != 0 && j != sn - 1))
                continue;

            sum += m[i][j];
        }
    }

    printf("%d", sum);
}