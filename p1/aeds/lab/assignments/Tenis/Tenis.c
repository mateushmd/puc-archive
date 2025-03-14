#include <stdio.h>

int main()
{
    char gameResult;
    int wonGames = 0, group;

    for (int i = 0; i < 6; i++)
    {
        scanf(" %c", &gameResult);

        if (gameResult == 'V')
            wonGames++;
    }

    if (wonGames > 4)
        group = 1;
    else if (wonGames > 2)
        group = 2;
    else if (wonGames > 0)
        group = 3;
    else
        group = -1;

    printf("%d\n", group);
}