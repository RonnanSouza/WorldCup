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

    public String getClassificacao() {
        String ret = "";

        for (int i = 0; i < this.times.size(); i++) {
            Time t = this.times.get(i);

            ret += t.info(i+1);
        }

        return ret;
    }


    public boolean containsTime(Time time) {
        for (int i = 0; i < times.size(); i++) {
            if (times.get(i).getNome().equals(time.getNome())) {
                return true;
            }
        }
        return false;
    }

    public Time getTime(String nome) {
        for (Time time: times) {
            if (time.getNome().equals(nome)) {
                return time;
            }
        }
        return null;
    }

    public ArrayList<String> getSaldoNegativo() {
        ArrayList<String> times = new ArrayList<>();
        for (Time time: this.times) {
            if (time.getGolsSofridos() > time.getGolsMarcados()) {
                times.add(time.getNome());
            }
        }
        return times;
    }

    public String getPrimeiro() {
        if (this.getTimes().size() == 0) {
            return String.format("1ª GRUPO %s", this.getNome());
        }
        return this.getTimes().get(0).getNome();
    }

    public String getSegundo() {
        if (this.getTimes().size() < 2) {
            return String.format("2ª GRUPO %s", this.getNome());
        }
        return this.getTimes().get(1).getNome();
    }

    public void removeEliminados() {
        while (this.getTimes().size() > 2) {
            this.times.remove(this.times.size() - 1);
        }
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "times=" + times +
                ", nome='" + nome + '\'' +
                '}';
    }
}
