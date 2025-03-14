#include <stdio.h>

void print(FILE *, long);

int main()
{
    int n;
    scanf("%d", &n);

    FILE *file = fopen("maiaiou.txt", "w");

    if (file == NULL)
    {
        printf("Erro ao abrir o arquivo.");
        return 0;
    }

    for (int i = 0; i < n; i++)
    {
        double f;
        scanf("%lf", &f);
        fprintf(file, "%lf\n", f);
    }

    fclose(file);

    file = fopen("maiaiou.txt", "r");

    if (file == NULL)
    {
        printf("Erro ao abrir o arquivo");
        return 0;
    }

    fseek(file, 0L, SEEK_END);
    long pos = ftell(file);

    // Reposiciona o ponteiro do arquivo até encontrar uma quebra de linha
    while (pos > 0)
    {
        fseek(file, --pos, SEEK_SET);

        if (fgetc(file) == '\n' && pos != 0)
            print(file, --pos);
    }

    fclose(file);
}

// Procura um número decimal em um arquivo até chegar em uma quebra de linha ou no início do arquivo
// Imprime o número decimal encontrado
void print(FILE *file, long pos)
{
    long endingPos = -1;
    char c;

    while (pos >= 0)
    {
        fseek(file, --pos, SEEK_SET);
        c = fgetc(file);

        if (c == '\n')
            break;
        if (c == EOF)
            continue;

        if (endingPos == -1)
        {
            if (c == '.')
                endingPos = pos - 1;
            else if (c != '0')
                endingPos = pos;
        }
    }

    fseek(file, ++pos, SEEK_SET);

    while (pos <= endingPos)
    {
        pos++;
        c = fgetc(file);
        putchar(c);
    }

    printf("\n");
}