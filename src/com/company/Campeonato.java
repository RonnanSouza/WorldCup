package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Campeonato {

    private ArrayList<Grupo> grupos;
    private String nome;

    private HashMap<Integer,ArrayList<Time>> golsMarcados;
    private HashMap<Integer,ArrayList<Time>> golsSofridos;

    public Campeonato(String nome) {
        this.nome = nome;
        this.grupos = new ArrayList<>();
        this.golsMarcados = new HashMap<>();
        this.golsSofridos = new HashMap<>();
        this.criarGrupos();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    private void criarGrupos() {
        for(char alphabet = 'A'; alphabet <='H'; alphabet++ ) {
            this.grupos.add(new Grupo(String.valueOf(alphabet)));
        }
    }

    public void carregaCSV(String csvPath) throws IOException {
        String row;
        BufferedReader csvReader = new BufferedReader(new FileReader(csvPath));

        // Pula csv header
        csvReader.readLine();

        while ((row = csvReader.readLine()) != null) {
            Time time = convertStringToTime(row);
            if (time != null) {
                this.addTime(time);
            }

        }
        csvReader.close();
    }

    private boolean containsGrupo(String grupo) {
        for (Grupo value : grupos) {
            if (value.getNome().equals(grupo)) {
                return false;
            }
        }
        return true;
    }

    public Time convertStringToTime(String str) {
        Time time = null;
        String[] data = str.split(",");
        try {
            time = new Time(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Integer.parseInt(data[7]));
        } catch (Exception e) {
            System.out.println("Entrada Inválida!");

        }
        return time;
    }

    private boolean containsTime(Time time) {
        for (Grupo grupo: grupos) {
            if (grupo.containsTime(time)) {
                return true;
            }
        }
        return false;
    }

    private void addGrupo(Grupo grupo) {
        if (this.containsGrupo(grupo.getNome())) {
            this.grupos.add(grupo);
            grupos.sort(new GrupoComparator());
        }
    }

    public void addTime(Time time) {
        if (this.containsTime(time)) {
            System.out.println("Time já existente");
            return;
        }
        this.addGrupo(new Grupo(time.getGrupo()));
        for (Grupo grupo: grupos) {
            if (grupo.getNome().equals(time.getGrupo())) {
                grupo.addTime(time);
                if (this.golsMarcados.containsKey(time.getGolsMarcados())) {
                    this.golsMarcados.get(time.getGolsMarcados()).add(time);
                } else {
                    this.golsMarcados.put(time.getGolsMarcados(), new ArrayList<>());
                    this.golsMarcados.get(time.getGolsMarcados()).add(time);
                }

                if (this.golsSofridos.containsKey(time.getGolsSofridos())) {
                    this.golsSofridos.get(time.getGolsSofridos()).add(time);
                } else {
                    this.golsSofridos.put(time.getGolsSofridos(), new ArrayList<>());
                    this.golsSofridos.get(time.getGolsSofridos()).add(time);
                }
            }
        }
    }

    public String getMaxGolsMarcados() {
        Integer max = Collections.max(this.golsMarcados.keySet());

        return this.golsMarcados.get(max).toString();
    }

    public String getGolsSofridos(int numGols) throws Exception{
        return this.golsSofridos.get(numGols).toString();
    }

    public String getEquipesSaldoNegativo() {
        ArrayList<Time> times = new ArrayList<>();
        for (Grupo grupo : grupos) {
            times.addAll(grupo.getSaldoNegativo());
        }

        Collections.sort(times, new TimeComparatorAlfabeto());

        return times.toString();
    }

    public String getPrimeiros() {
        String primeiros = null;
        for (Grupo grupo : grupos) {
            primeiros += grupo.getPrimeiro();
        }

        return primeiros;
    }

    public String getInfo(String nome) {
        for (Grupo grupo : grupos) {
            Time t = grupo.getTime(nome);
            if (t != null) {
                return t.info();
            }
        }

        return String.format("Nenhum time chamado %s nesse campeonato", nome);
    }

    public String statistics() {
        int jogos = 0, vitorias = 0, empates = 0, derrotas = 0, golsMarcados = 0, golsSofridos = 0;
        float mediaGolsMarcados, mediaGolsSofridos;

        for (Grupo grupo: this.grupos) {
            for (Time time : grupo.getTimes()) {
                jogos += time.getJogos();
                vitorias += time.getVitorias();
                empates += time.getEmpates();
                derrotas += time.getDerrotas();
                golsMarcados += time.getGolsMarcados();
                golsSofridos += time.getGolsSofridos();
            }
        }

        mediaGolsMarcados = golsMarcados / (float) jogos;
        mediaGolsSofridos = golsMarcados / (float) jogos;

        return String.format("Total de jogos= %d\n" +
                "Total de vitórias= %d\n" +
                "Total de empates= %d\n" +
                "Total de derrotas= %d\n" +
                "Total de golos marcados= %d\n" +
                "Total de golos sofridos= %d\n" +
                "Média de golos marcados por jogo= %.2f\n" +
                "Média de golos sofridos por jogo= %.1f",
                jogos, vitorias, empates, derrotas, golsMarcados, golsSofridos, mediaGolsMarcados, mediaGolsSofridos);
    }

    public void removeEliminados() {
        for (Grupo grupo :this.grupos ) {
            grupo.removeEliminados();
        }
    }

    public String getConfrontos() {
        StringBuilder confrontos = new StringBuilder();
        for (int i = 0; i < this.grupos.size(); i += 2) {
            Grupo g1 = this.grupos.get(i);
            Grupo g2 = this.grupos.get(i+1);

            confrontos.append(String.format("%s,1º,%s - %s,1º,%s\n",
                    g1.getNome(), g1.getPrimeiro(), g2.getNome(), g2.getSegundo()));
            confrontos.append(String.format("%s,1º,%s - %s,1º,%s\n",
                    g1.getNome(), g1.getSegundo(), g2.getNome(), g2.getPrimeiro()));
        }

        return confrontos.toString();
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("" +
                "| Grp | Pos | Equipa          | Pts| J  | V  | E  | D  | GM | GS | GD |\n" +
                "|=====|=====|=================|====|====|====|====|====|====|====|====|\n");
        for (Grupo grupo : grupos) {
            ret.append(grupo.getClassificacao());
        }

        return ret.toString();
    }


}
