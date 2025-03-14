#include <stdio.h>
#include <string.h>
#include <stdlib.h>

char *combine(char *, char *);

int main()
{
    char input[256];
    char str1[128], str2[128];

    while (fgets(input, sizeof(input), stdin) != NULL)
    {
        input[strcspn(input, "\n")] = 0;

        if (strlen(input) == 0)
            break;

        sscanf(input, "%127s %127s", str1, str2);

        char *combination = combine(str1, str2);

        fputs(combination, stdout);
        printf("\n");

        free(combination);
    }
}

char *combine(char *s1, char *s2)
{
    int sizeS1 = strlen(s1);
    int sizeS2 = strlen(s2);

    int i = 0, j = 0;

    char *combined = (char *)malloc((sizeS1 + sizeS2) * sizeof(char));

    while (i < sizeS1 || j < sizeS2)
    {
        if (i < sizeS1)
        {
            combined[i + j] = s1[i];
            i++;
        }
        if (j < sizeS2)
        {
            combined[i + j] = s2[j];
            j++;
        }
    }

    return combined;
}