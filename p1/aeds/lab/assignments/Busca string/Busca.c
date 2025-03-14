#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void removeNewLine(char *);

char toLowerCase(char);

int main()
{
    int n, count = 0;

    scanf("%d", &n);

    getchar();

    char target[50];

    fgets(target, 50, stdin);

    removeNewLine(target);

    char **phrases = (char **)malloc(n * sizeof(char *));

    for (int i = 0; i < n; i++)
    {
        phrases[i] = (char *)malloc(200 * sizeof(char));

        fgets(phrases[i], 200, stdin);

        removeNewLine(phrases[i]);
    }

    for (int i = 0; i < n; i++)
    {
        int len = strlen(phrases[i]);

        int k = 0;

        for (int j = 0; j < len; j++)
        {
            char phraseChar = toLowerCase(phrases[i][j]);
            char wordChar = toLowerCase(target[k++]);

            if (phraseChar != wordChar)
                k = 0;

            if (k == strlen(target))
            {
                count++;
                k = 0;
                break;
            }
        }
    }

    printf("%d\n", count);

    free(phrases);
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