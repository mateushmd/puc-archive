#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void randomReplacement(char *, char, char);
void randomReplacementRecursive(char *, char, char, int);

int main()
{
    srand(4);

    char line[512];
    fgets(line, 511, stdin);

    while (strncmp(line, "FIM", 3) != 0)
    {
        char target = 'a' + (rand() % 26);
        char replace = 'a' + (rand() % 26);

        randomReplacement(line, target, replace);
        fputs(line, stdout);

        fgets(line, 511, stdin);
    }
}

void randomReplacement(char *buffer, char target, char replace)
{
    randomReplacementRecursive(buffer, target, replace, 0);
}

void randomReplacementRecursive(char *buffer, char target, char replace, int pos)
{
    if (pos > strlen(buffer) - 1)
        return;

    if (buffer[pos] == target)
        buffer[pos] = replace;

    randomReplacementRecursive(buffer, target, replace, pos + 1);
}