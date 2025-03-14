#include <stdio.h>
#include <string.h>

int isPalindrome(char *);
int isPalindromeRecursive(char *, int);

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
    return isPalindromeRecursive(line, 0);
}

// Verifica se uma String é um palíndromo ou não de forma recursiva
int isPalindromeRecursive(char *line, int pos)
{
    if (pos > strlen(line) / 2)
        return 1;

    else if (line[pos] != line[strlen(line) - 1 - pos])
        return 0;

    return isPalindromeRecursive(line, pos + 1);
}