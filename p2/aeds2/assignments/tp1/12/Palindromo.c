#include <stdio.h>
#include <string.h>

int main()
{
    char line[512];
    scanf(" %[^\n]", line);

    while (strncmp(line, "FIM", 3) != 0)
    {
        line[strlen(line) - 1] = '\0';

        int check = 1;

        for (int i = 0; i <= strlen(line) / 2; i++)
        {
            if (line[i] != line[strlen(line) - i - 1])
            {
                check = 0;
                break;
            }
        }

        if (check)
            printf("SIM\n");
        else
            printf("NAO\n");

        scanf(" %[^\n]", line);
    }
}