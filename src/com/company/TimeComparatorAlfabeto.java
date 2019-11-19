package com.company;

import java.util.Comparator;

public class TimeComparatorAlfabeto implements Comparator<Time> {

    @Override
    public int compare(Time t1, Time t2) {
        return t1.getNome().compareTo(t2.getNome());
    }
}
