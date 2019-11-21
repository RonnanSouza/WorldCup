package com.company;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CLI {

    private Campeonato campeonato;
    private BufferedReader reader;
    private String input;

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
            "    digite \"sair\" para sair\n" +
            ">> ";


    CLI(Campeonato campeonato) {
        this.campeonato = campeonato;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

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
                default:
                    System.out.println("Opção inválida");
                    break;
                case "sair":
                    return;
            }
        }
    }

    private void carregaCSV() throws Exception{
        String csvPath = "/home/ronan/IdeaProjects/WorldCup/data/PracticalWork.csv";

        this.campeonato.carregaCSV(csvPath);
        System.out.print("carregando");
        Thread.sleep(100);
        System.out.print(".");
        Thread.sleep(100);
        System.out.print(".");
        Thread.sleep(100);
        System.out.print(".");
        Thread.sleep(100);
    }

    private void classificacao() {
        System.out.println(this.campeonato.toString());
    }

    private void addTime() throws IOException {
        System.out.println("Digite as informações do time, seguindo o padrão do CSV.");
        System.out.print(">> ");
        this.input = reader.readLine();
        Time t = campeonato.convertStringToTime(input);
        if (t != null) {
            campeonato.addTime(t);
        }
    }

    private void golsMarcados() {
        System.out.println(this.campeonato.getMaxGolsMarcados());
    }

    private void goslSofridos() throws IOException {
        System.out.println("Número de gols sofridos");
        System.out.print(">> ");
        this.input = reader.readLine();
        String times = null;
        try {
            times = this.campeonato.getGolsSofridos(Integer.parseInt(this.input));
        } catch (Exception e) {
            System.out.printf("Nenhum time sofreu %s gol(s)\n", input);
            return;
        }

        if (times.length() == 0) {
            System.out.printf("Nenhum time sofreu %s gol(s)\n", input);
        }else {
            System.out.println(times);
        }
    }

    private void saldoNegativo() {
        System.out.println(this.campeonato.getEquipesSaldoNegativo());
    }

    private void primeirosColocados() {
        System.out.println(this.campeonato.getPrimeiros());
    }

    private void statistics() throws IOException {
        PrintWriter writer = new PrintWriter("Statistics.txt", StandardCharsets.UTF_8);
        String stats = this.campeonato.statistics();
        writer.println(stats);
        writer.close();
    }

    private void info() throws IOException {
        System.out.println("Digite o nome do time;");
        System.out.print(">> ");
        this.input = reader.readLine();
        System.out.println(campeonato.getInfo(input));
    }

    private void removeEliminados() {
        campeonato.removeEliminados();
    }
}
