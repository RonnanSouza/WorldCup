package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class Grupo {

    private ArrayList<Time> times;
    private String nome;
    private TimeComparatorPorPontos comparator;

    public Grupo(String nome) {
        this.times = new ArrayList<>();
        this.nome = nome;
        this.comparator = new TimeComparatorPorPontos();
    }

    public ArrayList<Time> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<Time> times) {
        this.times = times;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void addTime(Time time) {
        if (containsTime(time)) {
            System.out.println("Time já existente, não é possível inserir.");
        }else {
            times.add(time);
            Collections.sort(times, comparator);
        }
    }

    public String getPrimeiro() {
        return String.format("Grupo %s: %s\n", this.getNome(), this.times.get(0));
    }

    public String getClassificação() {
        StringBuilder ret = new StringBuilder(String.format("GRUPO %s ", nome));
        if (times.size() == 0) {
            return ret+" \n NÃO HÁ TIMES NESSE GRUPO";
        }
        else {
            for (int i = 0; i < times.size(); i++) {
                ret.append(String.format("\n  %d - %s", i + 1, times.get(i).toString()));
            }
        }
        return ret.toString();
    }


    public boolean containsTime(Time time) {
        for (int i = 0; i < times.size(); i++) {
            if (times.get(i).getNome().equals(time.getNome())) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Time> getSaldoNegativo() {
        ArrayList<Time> times = new ArrayList<>();
        for (Time time: this.times) {
            if (time.getGolsSofridos() > time.getGolsMarcados()) {
                times.add(time);
            }
        }
        return times;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "times=" + times +
                ", nome='" + nome + '\'' +
                '}';
    }
}
