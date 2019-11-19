package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws Exception {

        Campeonato campeonato = new Campeonato("Copa do Mundo");
        CLI cli = new CLI(campeonato);
        cli.run();
    }



}
