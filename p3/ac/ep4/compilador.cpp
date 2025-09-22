#include <iostream>
#include <fstream>
#include <string>
#include <bits/stdc++.h>
using namespace std;

string conversorHex(string s){
    //string tmp = s.substr(2,s.length()-2);
    int t = stoi(s);
    stringstream ss;
    ss << hex << std::uppercase << t;
    string res = ss.str();
    return res;
}

string tabelaFuncoes(string s){
    string tmp;
    //s = s.substr(2,s.length()-2); 
    if(s == "zeroL"){
        tmp = "0";
    }
    else if(s == "umL"){
        tmp = "1";
    }
    else if(s == "copiaA"){
        tmp = "2";
    }
    else if(s == "copiaB"){
        tmp = "3";
    }
    else if(s == "nA"){
        tmp = "4";
    }
    else if(s == "nB"){
        tmp = "5";
    }
    else if(s == "AenB"){
        tmp = "6";
    }
    else if(s == "nAeB"){
        tmp = "7";
    }
    else if(s == "AxB"){
        tmp = "8";
    }
    else if(s == "nAxnB"){
        tmp = "9";
    }
    else if(s == "nAxnBn"){
        tmp = "A";
    }
    else if(s == "AeB"){
        tmp = "B";
    }
    else if(s == "AeBn"){
        tmp = "C";
    }
    else if(s == "AoBn"){
        tmp = "D";
    }
    else if(s == "AoB"){
        tmp = "E";
    }
    else if(s == "nAonBn"){
        tmp = "F";
    } else {
        tmp = "ERROR";
    }
    return tmp;
}

string gerarResposta(string x, string y, string w){
    return x+y+w;
}

string readFile(string nomeArquivo) {
    ifstream arquivo(nomeArquivo); 

    if (!arquivo) { 
        cerr << "Erro ao abrir o arquivo!" << endl;
    }

    int i = 0, j = 0;
    string linha, x, y, w, valor, resposta; 
    char variavel = '_';

    // Leitura das linhas do arquivo
    while (getline(arquivo, linha)) {
        i = 0, j = 0; 

        // Encontrar os valores das variáveis
        for (i = 0; i < linha.length(); i++) {
            
            if (linha[i] == '=') {

                // Definir a variável de busca
                char variavel = linha[i - 1]; 

                // Limpar valor antes de adicionar novo conteúdo
                string valor = "";

                // Percorrer o restante da linha para encontrar o valor da variável
                j = i + 1;
                while (j < linha.length() && linha[j] != ';') {
                    valor += linha[j];
                    j++;
                }

                // Armazena o valor na variável correta
                if (variavel == 'X') {
                    x = conversorHex(valor);
                } else if (variavel == 'Y') {
                    y = conversorHex(valor);
                } else if (variavel == 'W') {
                    w = tabelaFuncoes(valor);
                    resposta += gerarResposta(x, y, w) + " ";
                }

                // Atualizar o índice principal para continuar após o ponto-e-vírgula
                i = j;
            }
        }


        }

    arquivo.close(); 
    return resposta;
}

int main() {

    cout << readFile("testeUla.txt");

    return 0;
}


