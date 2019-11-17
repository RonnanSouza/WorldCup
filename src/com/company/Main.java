package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        String ops = String.format("\n\n### GERENCIADOR DE CAMPEONATOS ####\n" +
                "1 - Carregar CSV\n" +
                "2 - Mostrar Classificação\n" +
                "    digite \"sair\" para sair\n" +
                ">> ");

        String csvPath = "/home/ronan/IdeaProjects/WorldCup/data/PracticalWork.csv";
        Campeonato campeonato = new Campeonato("Copa do Mundo");

        System.out.print(ops);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String input = reader.readLine();
        while (!input.equals("sair")) {

            if (input.equals("1")) {
                campeonato.carregaCSV(csvPath);
                System.out.print("carregando");
                Thread.sleep(100);
                System.out.print(".");
                Thread.sleep(100);
                System.out.print(".");
                Thread.sleep(100);
                System.out.print(".");
                Thread.sleep(100);
            }
            if (input.equals("2")) {
                System.out.println(campeonato.toString());
            }
            System.out.print(ops);
            input = reader.readLine();
        }

    }



}
