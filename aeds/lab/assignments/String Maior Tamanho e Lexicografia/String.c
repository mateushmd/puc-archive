#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <locale.h>

void removeNewLine(char *);

char toLowerCase(char);

int main()
{
    int n, biggestSize = 0, biggestCharValue = 0;

    scanf("%d", &n);

    getchar();

    char **strs = (char **)malloc(n * sizeof(char *));

    for (int i = 0; i < n; i++)
    {
        strs[i] = (char *)malloc(200 * sizeof(char));

        fgets(strs[i], 200, stdin);

        removeNewLine(strs[i]);
    }

    for (int i = 0; i < n; i++)
    {
        int len = strlen(strs[i]);

        if (len > strlen(strs[biggestSize]))
            biggestSize = i;

        if (strcmp(strs[i], strs[biggestCharValue]) > 0)
            biggestCharValue = i;
    }

    puts(strs[biggestSize]);

    puts(strs[biggestCharValue]);

    for (int i = 0; i < n; i++)
        free(strs[i]);

    free(strs);
}

void removeNewLine(char *str)
{
    int len = strlen(str);

    if (len > 0 && str[len - 1] == '\n')
        str[len - 1] = '\0';
}

char toLowerCase(char c)
{
    if (c >= 'A' && c <= 'Z')
        c += 32;

    return c;
}