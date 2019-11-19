package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Campeonato {

    private ArrayList<Grupo> grupos;
    private String nome;

    public Campeonato(String nome) {
        this.nome = nome;
        this.grupos = new ArrayList<>();
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
            }
        }
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
