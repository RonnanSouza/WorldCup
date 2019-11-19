package com.company;

public class Main {

    public static void main(String[] args) throws Exception {

        Campeonato campeonato = new Campeonato("Copa do Mundo");
        CLI cli = new CLI(campeonato);
        cli.run();
    }



}
