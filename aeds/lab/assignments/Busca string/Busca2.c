#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

void removeNewLine(char *);

int isWordMatch(char *, char *);

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
        phrases[i] = (char *)malloc(500 * sizeof(char));

        fgets(phrases[i], 500, stdin);

        removeNewLine(phrases[i]);
    }

    for (int i = 0; i < n; i++)
    {
        if (isWordMatch(phrases[i], target))
        {
            count++;
        }
    }

    printf("%d\n", count);

    for (int i = 0; i < n; i++)
    {
        free(phrases[i]);
    }

    free(phrases);
}

void removeNewLine(char *str)
{
    int len = strlen(str);
    if (len > 0 && str[len - 1] == '\n')
        str[len - 1] = '\0';
}

int isWordMatch(char *phrase, char *target)
{
    int phraseLen = strlen(phrase);
    int targetLen = strlen(target);
    char lowerPhrase[500];
    char lowerTarget[50];

    for (int i = 0; i < phraseLen; i++)
    {
        lowerPhrase[i] = tolower(phrase[i]);
    }
    lowerPhrase[phraseLen] = '\0';

    for (int i = 0; i < targetLen; i++)
    {
        lowerTarget[i] = tolower(target[i]);
    }
    lowerTarget[targetLen] = '\0';

    char *token = strtok(lowerPhrase, " .,;!?-");

    while (token != NULL)
    {
        if (strcmp(token, lowerTarget) == 0)
        {
            return 1;
        }
        token = strtok(NULL, " .,;!?-");
    }

    return 0;
}
