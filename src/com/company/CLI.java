package com.company;

import java.io.*;
import java.nio.charset.StandardCharsets;



/**
    CLI é uma entidade que representa a interface de linha de comando que ira interagir com o usuário
 */
public class CLI {

    // Instancia do campeonato utilizado pela cli
    private Campeonato campeonato;
    // reader é o leitor do que o usuário digita
    private BufferedReader reader;
    // é onde ficará armazenada as opções digitadas pelo usuário
    private String input;

    // ops é o que será mostrado ao usuário a cada nova interação
    private String ops = "\n\n### GERENCIADOR DE CAMPEONATOS ####\n" +
            "1  - Carregar CSV\n" +
            "2  - Mostrar Classificação\n" +
            "3  - Adicionar novo Time\n" +
            "4  - Time(s) com mais gols marcados\n" +
            "5  - Time(s) que sofreram gols\n" +
            "6  - Time(s) com saldo de gols negativo\n" +
            "7  - Primeiro classificado de cada grupo\n" +
            "8  - Mostrar informações de uma equipe\n" +
            "9  - Criar arquivo com estatisticas\n" +
            "10 - Remove times eliminados\n" +
            "11 - Criar arquivo com confrontos\n" +
            "    digite \"sair\" para sair\n" +
            ">> ";


    CLI(Campeonato campeonato) {
        this.campeonato = campeonato;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     *
     * Este método itera indefinidamente (até que o usuário digite sair) em um loop lendo opções do usuário,
     * para cada opção que o usuário digita ele chama o método responsável para a opção.
     * @throws Exception esse método lança exceções quando há algum problema na leitura/escrita em arquivos ou leitura
     * do usuário
     */
    void run() throws Exception {
        while (true) {
            System.out.flush();
            System.out.print(this.ops);
            input = reader.readLine();
            switch (input) {
                case "1":
                    this.carregaCSV();
                    break;
                case "2":
                    this.classificacao();
                    break;
                case "3":
                    this.addTime();
                    break;
                case "4":
                    this.golsMarcados();
                    break;
                case "5":
                    this.goslSofridos();
                    break;
                case "6":
                    this.saldoNegativo();
                    break;
                case "7":
                    this.primeirosColocados();
                    break;
                case "8":
                    this.info();
                    break;
                case "9":
                    this.statistics();
                    break;
                case "10":
                    this.removeEliminados();
                    break;
                case "11":
                    this.confrontos();
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
                case "sair":
                    return;
            }
        }
    }

    /**
     *
     * Carrega arquivo CSV com dados dos times
     * @throws InterruptedException exceção lançada pelo Thread.sleep
     */
    private void carregaCSV() throws InterruptedException, IOException {

        System.out.println("Digite o caminho para o arquivo.");
        System.out.print(">> ");
        this.input = reader.readLine();

        try {
            this.campeonato.carregaCSV(input);
        } catch (Exception e) {
            System.out.println("Arquivo inválido!!\n\n");
            return;
        }

        /*
        Isso basicamente printa carregando, espera 100ms, printa ., espera ... só pra dar o efeito visual dos pontos
        carregando
        */
        System.out.print("carregando");
        Thread.sleep(100);
        System.out.print(".");
        Thread.sleep(100);
        System.out.print(".");
        Thread.sleep(100);
        System.out.print(".");
        Thread.sleep(100);
    }

    /**
     * Retorna a classificação dos times
     */
    private void classificacao() {
        System.out.println(this.campeonato.toString());
    }


    /**
     * Adiciona um novo time no campeonato
     * @throws IOException lança exceção quando há erro no readLine()
     */
    private void addTime() throws IOException {
        System.out.println("Digite as informações do time, seguindo o padrão do CSV.");
        System.out.print(">> ");
        this.input = reader.readLine();
        Time t = campeonato.convertStringToTime(input);
        if (t != null) {
            campeonato.addTime(t);
        }
    }

    /**
     * lista todos os times que tem o maior numero de gols marcados
     */

    private void golsMarcados() {
        System.out.println(getHeader());
        System.out.println(this.campeonato.getMaxGolsMarcados());
    }


    /**
     * lista todos os times que sofreram n gols
     * @throws IOException
     */
    private void goslSofridos() throws IOException {

        // Pergunta o número de gols
        System.out.println("Número de gols sofridos");
        System.out.print(">> ");
        this.input = reader.readLine();
        String times = null;
        System.out.println(getHeader());

        try {
            times = this.campeonato.getGolsSofridos(Integer.parseInt(this.input));
        } catch (Exception e) {
            return;
        }

        // Se nenhum time sofreu n gols, retorna
        if (times.length() == 0) {
            System.out.printf("Nenhum time sofreu %s gol(s)\n", input);
        }else {
            System.out.println(times);
        }
    }

    /**
     * lista todos os times que sofreram mais gols que marcaram
     */
    private void saldoNegativo() {
        System.out.println(getHeader());
        System.out.println(this.campeonato.getEquipesSaldoNegativo());
    }

    /**
     * lista todos os primeros colocados
     */
    private void primeirosColocados() {
        System.out.println(getHeader());
        System.out.println(this.campeonato.getPrimeiros());
    }

    /**
     * cria arquivo com estatisticas do campeonato
     * @throws IOException
     */
    private void statistics() throws IOException {
        //cria arquivo
        PrintWriter writer = new PrintWriter("Statistics.txt", StandardCharsets.UTF_8);
        //pega estatisticas do campeonato
        String stats = this.campeonato.statistics();

        // escreve no arquivo
        writer.println(stats);
        writer.close();
    }

    /**
     * retorna informações de um time específico
     * @throws IOException
     */
    private void info() throws IOException {
        System.out.println("Digite o nome do time;");
        System.out.print(">> ");
        this.input = reader.readLine();
        System.out.println(getHeader());
        System.out.println(campeonato.getInfo(input));
    }

    /**
     * remove todos os 3º e 4º colocados da memória
     */
    private void removeEliminados() {
        campeonato.removeEliminados();
    }

    /**
     * Cria arquivo com todos os confrontos
     * @throws IOException
     */
    private void confrontos() throws IOException {
        PrintWriter writer = new PrintWriter("FinalStageGames.txt", StandardCharsets.UTF_8);
        String confrontos = this.campeonato.getConfrontos();
        writer.println(confrontos);
        writer.close();
    }

    /**
     * retorna string com cabeçalho para listagem de times
     * @return
     */
    private String getHeader() {
        return "| Grp | Pos | Equipa          | Pts| J  | V  | E  | D  | GM | GS | GD |\n" +
               "|=====|=====|=================|====|====|====|====|====|====|====|====|";
    }
}
