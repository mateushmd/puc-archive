#include <stdio.h>

int main()
{
    int candidatos[4] = {0};
    int totalVotos = 0;
    int totalVotosBrancos = 0;
    int totalVotosNulos = 0;
    int votoAtual;

    scanf("%d", &votoAtual);

    while (votoAtual != 0)
    {
        if (votoAtual < 7 && votoAtual > 0)
        {
            totalVotos++;

            if (votoAtual < 5)
            {
                int nCanditado = votoAtual - 1;

                candidatos[nCanditado]++;
            }
            else if (votoAtual == 5)
                totalVotosNulos++;
            else
                totalVotosBrancos++;
        }

        scanf("%d", &votoAtual);
    }

    printf("%d %d %d %d\n", candidatos[0], candidatos[1], candidatos[2], candidatos[3]);
    printf("%d\n", totalVotosNulos);
    printf("%.2f\n", ((float)totalVotosBrancos / (float)totalVotos) * 100.0);
}