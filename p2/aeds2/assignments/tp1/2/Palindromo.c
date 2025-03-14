#include <stdio.h>
#include <string.h>

int isPalindrome(char *);

int main()
{
    char line[512];
    fgets(line, 512, stdin);

    while (strncmp(line, "FIM", 3) != 0)
    {
        line[strcspn(line, "\n")] = '\0';

        printf("%s\n", isPalindrome(line) ? "SIM" : "NAO");

        fgets(line, 512, stdin);
    }
}

// Dada uma String, retorna se ela é um palíndromo ou não
int isPalindrome(char *line)
{
    for (int i = 0; i <= strlen(line) / 2; i++)
    {
        if (line[i] != line[strlen(line) - 1 - i])
            return 0;
    }

    return 1;
}