#include <stdio.h>
#include <wchar.h>
#include <wctype.h>

int isPalindrome(wchar_t *);

int main()
{
    wchar_t line[512];
    fgetws(line, 512, stdin);

    while (wcsncmp(line, L"FIM", 3) != 0)
    {
        line[wcscspn(line, L"\n")] = L'\0';

        printf("%s\n", isPalindrome(line) ? "SIM" : "NAO");

        fgetws(line, 512, stdin);
    }
}

// Dada uma String, retorna se ela é um palíndromo ou não
int isPalindrome(wchar_t *line)
{
    size_t len = wcslen(line);

    for (size_t i = 0; i <= len / 2; i++)
    {
        if (line[i] != line[len - 1 - i])
            return 0;
    }

    return 1;
}