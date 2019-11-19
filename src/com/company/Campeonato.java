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

    private HashMap<Integer,ArrayList<String>> golsMarcados;
    private HashMap<Integer,ArrayList<String>> golsSofridos;

    public Campeonato(String nome) {
        this.nome = nome;
        this.grupos = new ArrayList<>();
        this.golsMarcados = new HashMap<>();
        this.golsSofridos = new HashMap<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
                    this.golsMarcados.get(time.getGolsMarcados()).add(time.getNome());
                } else {
                    this.golsMarcados.put(time.getGolsMarcados(), new ArrayList<>());
                    this.golsMarcados.get(time.getGolsMarcados()).add(time.getNome());
                }

                if (this.golsSofridos.containsKey(time.getGolsSofridos())) {
                    this.golsSofridos.get(time.getGolsSofridos()).add(time.getNome());
                } else {
                    this.golsSofridos.put(time.getGolsSofridos(), new ArrayList<>());
                    this.golsSofridos.get(time.getGolsSofridos()).add(time.getNome());
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
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder(String.format("Campeonato: %s \n", this.getNome()));
        if (grupos.size() == 0) {
            ret.append("NÃO HÁ GRUPOS CADASTRADOS NESSE CAMPEONATO");
        }else {
            for (Grupo grupo : grupos) {
                ret.append(grupo.getClassificação());
                ret.append("\n");
            }
        }

        return ret.toString();
    }


}
