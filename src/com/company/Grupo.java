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
            String pts = ("   "+t.getPontos());
            String gm = ("   "+t.getGolsMarcados());
            String gs = ("   "+t.getGolsSofridos());
            String gd = ("   "+t.getSaldo());

            ret += ("|"+this.nome + "     ").substring(0, 6)+"|"+
                    ("    "+i+1).substring(0, 5)+"|"+(t.getNome()+"                  ").substring(0, 17)+"|"+
                    pts.substring(pts.length()-4)+"|"+("   "+t.getJogos()).substring(0, 4)+"|"+
                    ("   "+t.getVitorias()).substring(0, 4)+"|"+("   "+t.getEmpates()).substring(0, 4)+"|"+
                    ("   "+t.getDerrotas()).substring(0, 4)+"|"+gm.substring(gm.length()-4)+"|"+
                    gs.substring(gs.length()-4)+"|"+gd.substring(gd.length()-4)+"|\n";
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

    public ArrayList<Time> getSaldoNegativo() {
        ArrayList<Time> times = new ArrayList<>();
        for (Time time: this.times) {
            if (time.getGolsSofridos() > time.getGolsMarcados()) {
                times.add(time);
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
