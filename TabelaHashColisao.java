import java.util.Random;

import tabelaHash.No;

class No {
    int valor;
    No proximo;

    public No(int valor) {
        this.valor = valor;
        this.proximo = null;
    }
}

public class TabelaHashColisao {
    public static void main(String[] args) {
        
        int tamanhoTabela = 100;
        int totalElemento = 1000;

        No[] tabela = new No[tamanhoTabela];
        
        Random random = new Random(1090);

        // Vetor simples para guardar os 3 últimos valores de colisão
        int[] ultimosColisoes = new int[3];
        int totalColisoes = 0;

        for (int i = 0; i < totalElemento; i++) {

            int numero = random.nextInt(100001);
            int indice = numero % tamanhoTabela;

            if (tabela[indice] == null) {
                tabela[indice] = new No(numero);
            } else {
                No novoNo = new No(numero);
                novoNo.proximo = tabela[indice];
                tabela[indice] = novoNo;

                // Atualiza o histórico dos 3 últimos valores que colidiram
                ultimosColisoes[0] = ultimosColisoes[1];
                ultimosColisoes[1] = ultimosColisoes[2];
                ultimosColisoes[2] = numero;
                
                totalColisoes++;
            }
        }

        int posicoesVazias = 0;
        int totalListasGeradas = 0;
        int maiorTamanhoLista = 0;
        No inicioMaiorLista = null; // Guarda o início da maior lista encadeada

        for (int i = 0; i < tamanhoTabela; i++) {

            if (tabela[i] == null) {
                posicoesVazias++;
            } else {
                totalListasGeradas++;

                int tamanhoAtual = 0;
                No atual = tabela[i];

                while (atual != null) {
                    tamanhoAtual++;
                    atual = atual.proximo;
                }

                if (tamanhoAtual > maiorTamanhoLista) {
                    maiorTamanhoLista = tamanhoAtual;
                    inicioMaiorLista = tabela[i]; // Salva a referência da maior lista
                }
            }
        }

        System.out.println("=== RESULTADOS DA EXECUÇÃO ===");
        System.out.println("Quantidade de posições que ficaram vazias: " + posicoesVazias);
        System.out.println("Quantidade de listas encadeadas geradas (posições com elementos): " + totalListasGeradas);
        
        // Imprimindo a maior lista encadeada manualmente percorrendo os nós
        System.out.print("Maior lista encadeada encontrada (tamanho " + maiorTamanhoLista + "): [");
        No atual = inicioMaiorLista;
        while (atual != null) {
            System.out.print(atual.valor);
            if (atual.proximo != null) {
                System.out.print(", ");
            }
            atual = atual.proximo;
        }
        System.out.println("]");

        // Imprimindo os três últimos valores que colidiram
        System.out.print("Os três últimos valores que foram inseridos em listas encadeadas (colisões): ");
        if (totalColisoes >= 3) {
            System.out.println(ultimosColisoes[0] + ", " + ultimosColisoes[1] + ", " + ultimosColisoes[2]);
        } else if (totalColisoes > 0) {
            System.out.println("Houve menos de 3 colisões.");
        } else {
            System.out.println("Nenhum valor gerou colisão.");
        }
    }
}