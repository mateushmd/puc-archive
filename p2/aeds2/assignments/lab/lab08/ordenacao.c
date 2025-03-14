#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_STR_AMMOUNT 50
#define MAX_STR_SIZE 50

int main()
{
    int n;
    scanf("%d", &n);

    char *strArray[MAX_STR_AMMOUNT];

    fgetc(stdin);

    for (int i = 0; i < n; i++)
    {
        int size = 0;

        char followingChar = '\0';

        while (followingChar != '\n')
        {
            char *str = (char *)malloc(MAX_STR_SIZE * sizeof(char));
            scanf("%s", str);
            int strSize = strlen(str);
            int i = size - 1;

            while (i >= 0 && strlen(strArray[i]) < strSize)
            {
                strArray[i + 1] = strArray[i];
                i--;
            }

            strArray[i + 1] = str;
            size++;

            followingChar = fgetc(stdin);
        }

        for (int i = 0; i < size; i++)
        {
            printf("%s", strArray[i]);

            if (i < size - 1)
                printf(" ");
        }

        printf("\n");
    }
}
