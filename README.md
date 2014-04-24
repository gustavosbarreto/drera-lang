# drera-lang

Drera lang é uma linguagem para o ensino de lógica de programação com uma sintaxe em português.

## Características

* Tipagem dinâmica (não é necessário especificar tipos para variáveis)
* Todos números são tratados como ponto flutuante de dupla precisão
* As variáveis não possuem contexto, em outras palavras, uma variável declarada
dentro de um "se" pode ser acessada fora dele

## Operadores de Comparação

Exemplo              | Nome           | Resultado
---------------------|----------------|----------
<code>X == Y</code>  | Igual          | Verdadeiro se X e Y são iguais
<code>X != Y</code>  | Diferente      | Verdadeiro se X e Y são diferentes
<code>X < Y</code>   | Menor que      | Verdadeiro se X é menor que Y
<code>X > Y</code>   | Maior que      | Verdadeiro se X é maior que Y
<code>X <= Y</code>  | Menor ou igual | Verdadeiro se X é menor ou igual a Y
<code>X >= Y</code>  | Maior ou igual | Verdadeiro se X é maior ou igual a Y

## Operadores Lógicos

Exemplo                        | Nome | Resultado
-------------------------------|------|----------
<code>X && Y</code>            | AND  | Verdadeiro se tanto X quanto Y são verdadeiros
<code>X &#124;&#124; Y</code>  | OR   | Verdadeiro se X ou Y são verdadeiros |
<code>!X</code>                | NOT  | Verdadeiro se X não é verdadeiro

## Operadores Aritméticos

Exemplo              | Nome          | Resultado
---------------------|---------------|----------
<code>X + Y</code>   | Adição        | Soma de X e Y
<code>X - Y</code>   | Subtração     | Diferença entre X e Y
<code>X * Y</code>   | Multiplicação | Produto de X e Y
<code>X / Y</code>   | Divisão       | Quociente de X por Y
<code>X % Y</code>   | Módulo        | Resto de X dividido por Y

## Variáveis

Em Drera Lang as variáveis não possuem contexto, em outras palavras,
uma variável declarada dentro de um "se" pode ser acessada fora dele.
Sintaxe para declaração de variável:

```
nome_variavel = valor_variavel
```

Caso o valor da variável seja uma string é necessário circundar com aspas duplas.

Note que a sintaxe é estritamente restritiva e é obrigatório um espaço em branco antes
e depois do simbolo `=`.

## Estruturas de Controle

### se e senão
```
se condição
    comandos
senao
    comandos
fim
```

### fazer ... enquanto

```
fazer
    comandos
enquanto condição
```

## Comandos Padrão

### imprimir

O comando `imprimir` imprime uma sequência de caracteres (string) na saida padrão (geralmente na 'tela').
Não é necessário circundar com aspas duplas uma string no parâmetro para o comando `imprimir`. Exemplo:

```
imprimir Olá, eu sou uma string
```

É possível fazer a interpolaçao de strings com o uso do caracter `$`. Exemplo:

```
ler nome
imprimir Olá, $nome
```

### ler

O comando `ler` faz a leitura do teclado do usuário até que a tecla ENTER seja pressionada.
Não é necessário se preocupar com o tipo de dado, já que a conversão é feita automaticamente.

Exemplo:

```
ler nome_variavel
```

O nome da variável passada como argumento para o comando `ler` é criada automaticamente e estará
disponível em todo o escopo do programa.

## Limitações
* Não suporta instruções "se" e "fazer ... enquanto" encadeadas
* Não exibe mensagem de erro caso a sintaxe de algum comando estiver errada
