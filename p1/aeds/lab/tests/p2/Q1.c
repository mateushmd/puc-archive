#include <stdio.h>

void f(char[], int);

int main()
{
    char a[] = {'M', 'A', 'T', 'E', 'U'};

    f(a, 5);

    for (int i = 0; i < 5; i++)
    {
        printf("%c ", a[i]);
    }
}

void f(char a[], int n)
{
    if (n <= 1)
        return;

    char temp = a[0];
    a[0] = a[n - 1];
    a[n - 1] = temp;

    f(&a[1], n - 2);
}
