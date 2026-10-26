
import java.util.Random;

public class TabelaHash {

    public static void main(String[] args) {

        int tamanho = 100;
        int limiteOcupacao = 90;
        int limiteElementos = 100;

        int[] matriz = new int[tamanho];
        int[] valoresInseridos = new int[limiteElementos];
        int[] colisoesElemento = new int[limiteElementos];

        Random random = new Random(1090);

        int posicoesOcupadas = 0;
        int quantidadeInserida = 0;
        int totalColisoes = 0;

        // Inicializa todas as posicoes como vazias.
        for (int i = 0; i < tamanho; i++) {
            matriz[i] = -1;
        }

        // INSERCAO DOS ELEMENTOS
        for (int tentativa = 0;
             tentativa < limiteElementos &&
             posicoesOcupadas < limiteOcupacao;
             tentativa++) {

            int numero = random.nextInt(100001);
            int indice = numero % tamanho;
            int colisoes = 0;

            // Resolve colisões procurando a proxima posicao livre.
            while (matriz[indice] != -1) {
                colisoes++;
                indice = (indice + 1) % tamanho;
            }

            // Insere diretamente na matriz.
            matriz[indice] = numero;

            valoresInseridos[quantidadeInserida] = numero;
            colisoesElemento[quantidadeInserida] = colisoes;

            quantidadeInserida++;
            posicoesOcupadas++;
            totalColisoes += colisoes;

            // Impede novas insercoes quando atingir 90% de ocupacao.
            if (posicoesOcupadas >= limiteOcupacao) {
                System.out.println(
                    "Limite de 90% atingido. Insercoes encerradas."
                );
            }
        }

        // IDENTIFICA OS TRES ELEMENTOS COM MAIS COLISOES
        int primeiro = -1;
        int segundo = -1;
        int terceiro = -1;

        for (int i = 0; i < quantidadeInserida; i++) {

            if (primeiro == -1 ||
                colisoesElemento[i] > colisoesElemento[primeiro]) {

                terceiro = segundo;
                segundo = primeiro;
                primeiro = i;

            } else if (segundo == -1 ||
                       colisoesElemento[i] > colisoesElemento[segundo]) {

                terceiro = segundo;
                segundo = i;

            } else if (terceiro == -1 ||
                       colisoesElemento[i] > colisoesElemento[terceiro]) {

                terceiro = i;
            }
        }

        // RESULTADOS DA INSERCAO
        System.out.println();
        System.out.println("===== RESULTADOS =====");
        System.out.println("Seed: 1090");
        System.out.println("Elementos inseridos: " + quantidadeInserida);
        System.out.println("Posicoes ocupadas: " + posicoesOcupadas);
        System.out.println("Ocupacao: " + posicoesOcupadas + "%");
        System.out.println("Total de colisoes: " + totalColisoes);

        // EXIBE AS POSICOES LIVRES
        System.out.println();
        System.out.println("===== POSICOES LIVRES =====");

        int quantidadeLivres = 0;

        for (int i = 0; i < tamanho; i++) {
            if (matriz[i] == -1) {
                System.out.print(i + " ");
                quantidadeLivres++;
            }
        }

        System.out.println();
        System.out.println("Total de posicoes livres: " + quantidadeLivres);

        // EXIBE OS TRES ELEMENTOS COM MAIS COLISOES
        System.out.println();
        System.out.println("===== TOP 3 COLISOES =====");

        if (primeiro != -1) {
            System.out.println(
                "1o: Valor " + valoresInseridos[primeiro] +
                " | Colisoes: " + colisoesElemento[primeiro]
            );
        }

        if (segundo != -1) {
            System.out.println(
                "2o: Valor " + valoresInseridos[segundo] +
                " | Colisoes: " + colisoesElemento[segundo]
            );
        }

        if (terceiro != -1) {
            System.out.println(
                "3o: Valor " + valoresInseridos[terceiro] +
                " | Colisoes: " + colisoesElemento[terceiro]
            );
        }

        // TESTE DE BUSCA DOS TRES ELEMENTOS
        System.out.println();
        System.out.println("===== TESTES DE BUSCA =====");

        if (primeiro != -1) {
            buscar(matriz, tamanho, valoresInseridos[primeiro]);
        }

        if (segundo != -1) {
            buscar(matriz, tamanho, valoresInseridos[segundo]);
        }

        if (terceiro != -1) {
            buscar(matriz, tamanho, valoresInseridos[terceiro]);
        }
    }

    // BUSCA UTILIZANDO A MESMA SONDA LINEAR DA INSERCAO
    public static void buscar(
        int[] matriz, int tamanho, int valorBuscado) {

        int indice = valorBuscado % tamanho;
        int verificacoes = 0;

        while (verificacoes < tamanho && matriz[indice] != -1) {

            verificacoes++;

            if (matriz[indice] == valorBuscado) {
                System.out.println(
                    "Valor " + valorBuscado +
                    " encontrado na posicao " + indice +
                    " | Posicoes verificadas: " + verificacoes
                );
                return;
            }

            indice = (indice + 1) % tamanho;
        }

        System.out.println(
            "Valor " + valorBuscado + " nao encontrado."
        );
    }
}
