#include <stdio.h>
#include <stdlib.h>

void updatePosDiff(int *);

int main()
{
    int n;

    while (scanf("%d", &n) > 0)
    {
        int *p = (int *)malloc(n * sizeof(int));
        int *pDiffs = (int *)malloc(n * sizeof(int));

        for (int i = 0; i < n; i++)
        {
            int c;
            scanf("%d", &c);
            p[c - 1] = i;
        }

        for (int i = 0; i < n; i++)
        {
            int c;
            scanf("%d", &c);
            pDiffs[i] = p[c - 1] - i;
        }

        int o = 0;

        for (int i = 0; i < n - 1; i++)
        {
            int tmp = pDiffs[i];
            int j = i + 1;

            while (tmp != 0)
            {
                updatePosDiff(&tmp);
                updatePosDiff(&pDiffs[j]);
                pDiffs[j - 1] = pDiffs[j];

                j++;
                o++;
            }

            pDiffs[j - 1] = tmp;
        }

        printf("%d\n", o);
    }
}

void updatePosDiff(int *posDiff)
{
    if (*posDiff > 0)
        (*posDiff)--;
    else if (*posDiff < 0)
        (*posDiff)++;
}