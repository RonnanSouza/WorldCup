package com.company;

import java.util.Comparator;


public class GrupoComparator implements Comparator<Grupo> {

    @Override
    public int compare(Grupo g1, Grupo g2) {
        return g1.getNome().compareTo(g2.getNome());
    }
}

