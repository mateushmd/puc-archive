#define bit1 10
#define bit2 11
#define bit3 12
#define bit4 13

#define __cp 0
#define __w 1
#define __x 2
#define __y 3

#define tick 4000

int mem[100] = {0};

int programLength = 0;

void setup()
{
  Serial.begin(9600);
  pinMode(bit1, OUTPUT);
  pinMode(bit2, OUTPUT);
  pinMode(bit3, OUTPUT);
  pinMode(bit4, OUTPUT);
}

void loop()
{
// Ler as instruções de programa da entrada serial
  String raw = readInput();
  
// Armazenar as intruções na memória
  programLength = load(raw);
  
// Iniciar execução
  start();
  
  Serial.println();
}

/*
	readInput - Função para ler o código de máquina da entrada serial
    @return string - Código de máquina separado em espaços em branco
*/
String readInput() {
  Serial.println("Digite o codigo de maquina: \n");
  while (Serial.available() == 0) {}
  return Serial.readString();  
}

/*
	load - Função para converter a entrada em código de máquina para a memória
    @param raw - Entrada do programa em código de máquina
    @return length - Número total de instruções do programa
*/
int load(String raw) {
// Definir dados
  int nextPos = 4;
  char *tokens = (char*)calloc(raw.length() + 1, sizeof(char)); 
  
// Converter string lida da entrada Serial para arranjo de caractere
  raw.toCharArray(tokens, raw.length() + 1);
  
// Separar o primeiro token até um espaço em branco
  char *token = strtok(tokens, " ");
  
// Continuar separando tokens enquanto o delimitador existir
  while (token != NULL){
  // Carrega token como código de máquina
    int opcode = 0;
    int x = toDecimal(token[0]); //Separa o valor de X
    int y = toDecimal(token[1]); //Separa o valor de Y
    int instrucao = toDecimal(token[2]); //Separa o valor da instrução
    
    opcode = x * 256 + y * 16 + instrucao;
    
  // Armazena o opcode na memória e avança a posição do contador
    mem[nextPos++] = opcode;
    
  // Incrementa o tamanho do programa
    programLength++;
    
  // Lê o próximo token
    token = strtok(NULL, " ");
  }
  
// Desalocar memória  
  delete tokens;

// Retornar
  return nextPos - 4;
}

/*
	start - Função para iniciar a execução do programa
*/
void start() {
// Definindo o valor inicial do contador de programa
  mem[__cp] = 4;
  
// Definir dados
  int opcode, a, b, instrucao;
  
// Dump inicial da memória
  dumpMemory();
  
// Repetir para a quantidade de instruções do programa
  for (int i = 0; i < programLength; i++) {
  // Procurar a instrução
  	fetch(&opcode);
  
  // Incrementar o contador de programa
    mem[__cp]++;
    
  // Decodificar instrução
    decode(opcode, &a, &b, &instrucao);

  // Executar a instrução
    execute(a, b, instrucao);
    
  // Mostrar a saída no LED
    show(mem[__w]);
    
  // Mostrar o estado da memória atual
    dumpMemory();
    
  // Esperar algum tempo antes da próxima instrução
    delay(tick);
  }
}

/*
	fetch - Função para procurar a próxima instrução no contador de programa
    @param *opcode - Endereço da variável que armazena o opcode
*/
void fetch (int * opcode){
// Ler o contador de programa
  int cp = mem[__cp];
  
// Identificar instrução a ser executada
  *opcode = mem[cp]  ;
}

/*
	decode - Função para decodificar o opcode em valores de a, b e instrução
    @param opcode - Opcode a ser decodificado
    @param *a - Endereço da variável que armazena o valor de a
    @param *b - Endereço da variável que armazena o valor de b
    @param *instrucao - Endereço da variável que armazena o valor da instrução
*/
void decode (int opcode, int * a, int * b, int * instrucao) {
// Identificar o valor de a
  *a = opcode >> 8;
  
// Identificar o valor de b
  *b = opcode >> 4 & 0xF;  
  
// Identificar o valor da instrução
  *instrucao = opcode & 0xF;
}

/*
	execute - Função para executar uma instrução específica
    @param a - Valor de um operando
    @param b - Valor de outro operando
    @param instrucao - Valor da instrução a ser executada
*/
void execute (int a, int b, int instrucao) {
// Executar a instrução
  int res = eval(a, b, instrucao);
 
// Armazenar a, b e res na memória
  mem[__w] = res;
  mem[__x] = a;
  mem[__y] = b;
}

/*
  eval - Função para realizar uma operação de acordo com a instrução correspondente
  @param a - Valor do primeiro operando
  @param b - Valor do segundo operando
  @param op - Número da instrução a ser executada
  @return res - Valor resultante da operação
*/
int eval(int a, int b, int op) {
//Definir dados locais
  int res;
  
//Testar instrução
  switch(op) {
  	case 0: res = 0; break;
  	case 1: res = 0xF; break;
  	case 2: res = a; break;
  	case 3: res = b; break;
  	case 4: res = ~a; break;
  	case 5: res = ~b; break;
  	case 6: res = a & (~b); break;
  	case 7: res = (~a) & b;; break;
  	case 8: res = (a & (~b)) | ((~a) & b); break;
  	case 9: res = ((~a) & b) | (a & (~b)); break;
  	case 10: res = ~( ((~a) & b) | (a & (~b)) ); break;
  	case 11: res = a & b; break;
  	case 12: res = ~(a & b); break;
  	case 13: res = ~(a | b); break;
  	case 14: res = a | b; break;
  	case 15: res = ~((~a) | (~b)); break;
    default: res = 0; break;
  }
  
//Retornar
  return res & 0xF;
}

/*
  toDecimal - Função para converter um caractere hexadecimal para um valor inteiro
  @param hex - Caractere hexadecimal
  @return decimal - Inteiro correspondente
*/
int toDecimal (char hex) {
//Definir dados locais
  int decimal;
  
//Testar caractere em hexadecimal
  if(hex >= 48 && hex <= 57)
  	decimal = hex - 48;
  else if(hex >= 65 && hex <= 70)
    decimal = hex - 65 + 10;
   	
//Retornar
  return decimal;
}

/*
  getBit - Função para extrair o valor de um bit específico de um valor
  @param target - o valor alvo da extração
  @param bit - a posição do bit, começando em 1
  @return o valor do bit como um booleano
*/
bool getBit(int target, int bit) {
// Definindo a máscara para isolar o bit específico
  int mask = 1 << bit - 1;
  
// Se o bit isolado for > 0, retorna true
  return target & mask;
}

/*
  show - Função para escrever nos pinos de saída um valor em binário
  @param value - o valor alvo
*/
void show(int value) {
  digitalWrite(bit1, getBit(value, 1));
  digitalWrite(bit2, getBit(value, 2));
  digitalWrite(bit3, getBit(value, 3));
  digitalWrite(bit4, getBit(value, 4));
}

/*
  dumpMemory - Função para imprimir o conteúdo da memória no serial
*/
void dumpMemory() {
// Imprimindo caracteres iniciais
  Serial.print("-> ");
  Serial.print("|");
  
// Imprimindo a memória 
  for(int i = 0; i < programLength + 4; i++) {
    Serial.print(" ");
    Serial.print(mem[i], HEX);
    Serial.print(" |");
  }
  
  Serial.println();
}