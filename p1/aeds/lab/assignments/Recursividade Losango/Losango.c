#include <stdio.h>
#include <math.h>

void printDiamond(int);

void print(char, int);

int main()
{
    int n;

    scanf("%d", &n);

    printDiamond(n);
}

void printDiamond(int size)
{
    int target = size / 2;

    if (size % 2 == 0)
        target--;

    int aux = target;

    for (int i = 0; i < target; i++)
    {
        print(' ', aux--);
        print('*', i + (1 * (i + 1)));
        printf("\n");
    }

    for (int i = 0; i < 2 - (size % 2); i++)
    {
        print(' ', aux);
        print('*', target + (1 * target + 1));
        printf("\n");
    }

    aux = 1;

    for (int i = target - 1; i >= 0; i--)
    {
        print(' ', aux++);
        print('*', i + (1 * (i + 1)));
        printf("\n");
    }
}

void print(char c, int ammount)
{
    for (int i = 0; i < ammount; i++)
    {
        printf("%c", c);
    }
}