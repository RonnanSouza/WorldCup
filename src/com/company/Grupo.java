package com.company;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Grupo faz parte do campeonato e possui uma lista de times
 */
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

    public String getNome() {
        return nome;
    }

    /**
     * adicioona time, se não existir no grupo
     * @param time
     */
    public void addTime(Time time) {
        if (containsTime(time)) {
            System.out.println("Time já existente, não é possível inserir.");
        } else {
            times.add(time);
            Collections.sort(times, comparator);
        }
    }


    /**
     * retorna a classificação dos times
     * @return
     */
    public String getClassificacao() {
        String ret = "";

        for (int i = 0; i < this.times.size(); i++) {
            Time t = this.times.get(i);

            ret += t.info(i+1);
        }

        return ret;
    }


    /**
     * verifica se o time possui um determinado time
     * @param time
     * @return
     */
    public boolean containsTime(Time time) {
        for (int i = 0; i < times.size(); i++) {
            if (times.get(i).getNome().equals(time.getNome())) {
                return true;
            }
        }
        return false;
    }

    /**
     * retonar time, se este existir no grupo
     * @param nome do time a ser retornado
     * @return
     */
    public Time getTime(String nome) {
        for (Time time: times) {
            if (time.getNome().equals(nome)) {
                return time;
            }
        }
        return null;
    }

    /**
     * retorna uma lista de times com saldo de gols negativo no grupo
     * @return
     */
    public ArrayList<String> getSaldoNegativo() {
        ArrayList<String> times = new ArrayList<>();
        for (Time time: this.times) {
            if (time.getGolsSofridos() > time.getGolsMarcados()) {
                times.add(time.getNome());
            }
        }
        return times;
    }

    /**
     * retorna o nome do primerio colocado do grupo, se este grupo tiver pelo menos um time
     * @return
     */
    public String getPrimeiro() {
        if (this.getTimes().size() == 0) {
            return String.format("1ª GRUPO %s", this.getNome());
        }
        return this.getTimes().get(0).getNome();
    }

    /**
     * retorna o nome do segundo colocado, se existir
     * @return
     */
    public String getSegundo() {
        if (this.getTimes().size() < 2) {
            return String.format("2ª GRUPO %s", this.getNome());
        }
        return this.getTimes().get(1).getNome();
    }

    /**
     * remove 3º e 4º colocado, se tiver, da lista de times desse grupo
     */
    public void removeEliminados() {
        while (this.getTimes().size() > 2) {
            this.times.remove(this.times.size() - 1);
        }
    }
}
