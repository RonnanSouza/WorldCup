package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class Grupo {

    private ArrayList<Time> times;
    private String nome;
    private TimeComparator comparator;

    public Grupo(String nome) {
        this.times = new ArrayList<>();
        this.nome = nome;
        this.comparator = new TimeComparator();
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


    private boolean containsTime(Time time) {
        for (int i = 0; i < times.size(); i++) {
            if (times.get(i).getNome().equals(time.getNome())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "times=" + times +
                ", nome='" + nome + '\'' +
                '}';
    }
}
