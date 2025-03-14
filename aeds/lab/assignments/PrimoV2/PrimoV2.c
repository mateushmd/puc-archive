#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main()
{
    int n1, n2;

    scanf("%d", &n1);
    scanf("%d", &n2);

    /*
    Criando um vetro de tamanho n2. Cada índice representa um número,
    e o conteúdo é 1 ou 0. Ao longo do algoritmo, vamos marcando com 0
    todos os números compostos, ou seja, não primos. Os números compostos
    são sempre múltiplos de números primos.
    Ex.: múltiplos de 2 = (4, 6, 8, ...)
         múltiplos de 3 = (3, 6, 9, ...)
    */
    int *a = (int *)malloc(n2 * sizeof(int));

    // Assumiremos inicialmente que i é sempre primo (1)
    for (int i = 0; i < n2; i++)
        a[i] = 1;

    /*
    O problema é que alguns números podem ser marcados mais de uma vez.
    Vamos supor que estamos agora marcando os múltiplos de 7. Os números
    14, 21, 28, 35 e 42 já foram marcados pelos primos 2, 3, 2, 5 e 3, respectivamente.
    Para evitar marcações excessivas, começamos a marcar a partir do quadrado de 7: 49.
    Porém nem todas as marcações desnecessárias podem ser evitadas.
    Por isso, iniciamos um laço no número 2 (o primeiro número primo), e vamos até a raíz
    de n2 (truncada quando não inteira).
    */
    for (int i = 2; i <= sqrt(n2); i++)
    {
        // Se o índice i está marcado como 1, significa que o número é primo
        if (a[i])
        {
            // Começando pelo quadrado de i
            int j = pow(i, 2);

            /*
            Enquanto j for menor ou igual a n2, continuaremos marcando os múltiplos de i
            */
            while (j <= n2)
            {
                a[j] = 0;
                j += i;
            }
        }
    }

    for (int i = n1 + 1; i < n2; i++)
    {
        if (a[i])
            printf("%d ", i);
    }
}