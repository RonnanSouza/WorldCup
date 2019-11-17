package com.company;

import java.util.Comparator;

public class TimeComparator implements Comparator<Time> {

    @Override
    public int compare(Time t1, Time t2) {
        if (t1.getPontos() == t2.getPontos()) {
            if (t1.getGolsMarcados() == t2.getGolsMarcados()) {
                if (t1.getGolsSofridos() == t2.getGolsSofridos()) {
                    return t2.getNome().compareTo(t1.getNome());
                }
                return t2.getGolsSofridos() - t1.getGolsSofridos();
            }
            return t2.getGolsMarcados() - t1.getGolsMarcados();
        }
        return t2.getPontos() - t1.getPontos();
    }

}
