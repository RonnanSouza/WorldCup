package com.company;

/**
 * Time é uma entidade que representa cada entrada do CSV
 */
public class Time {

    private int jogos, vitorias, empates, derrotas, pontos, golsMarcados, golsSofridos;
    private String grupo, nome;


    /**
     *
     * @param grupo grupo que esta equipe faz parte no campeonato, é representada por uma letra no arquivo CSV
     * @param nome nome da equipe
     * @param jogos número de jogos
     * @param vitorias número de vitórias da equipe na competição
     * @param empates número de empates da equipe na competição
     * @param derrotas número de derrotas da equipe na competição
     * @param golsMarcados número de gols marcados pela equipe na competição
     * @param golsSofridos número de gols sofridos pela equipe na competição
     */

    public Time(String grupo, String nome, int jogos, int vitorias, int empates, int derrotas, int golsMarcados,
                int golsSofridos) {
        this.grupo = grupo;
        this.nome = nome;
        this.jogos = jogos;
        this.vitorias = vitorias;
        this.empates = empates;
        this.derrotas = derrotas;
        this.golsMarcados = golsMarcados;
        this.golsSofridos = golsSofridos;
        this.pontos = this.vitorias * 3 + this.empates;
    }

    public int getJogos() {
        return jogos;
    }

    public int getVitorias() {
        return vitorias;
    }

    public int getEmpates() {
        return empates;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public int getPontos() {
        return pontos;
    }

    public int getGolsMarcados() {
        return golsMarcados;
    }

    public int getGolsSofridos() {
        return golsSofridos;
    }

    public String getGrupo() {
        return grupo;
    }

    public String getNome() {
        return nome;
    }

    public int getSaldo() {
        return this.getGolsMarcados() - this.golsSofridos;
    }

    /**
     *
     * @return Nome da equipe seguido pelo número de pontos da equipe na competição
     */
    @Override
    public String toString() {
        return String.format("%s, pts: %d", this.getNome(), this.getPontos());
    }

    /**
     * retorna string com informações do time
     * @param posicao posição do time no campeonato
     * @return
     */
    public String info(int posicao) {

        // eu adicionei espaços em branco nas strings para poder cortar do tamanho certo e manter todas alinhadas
        String pts = ("   "+this.getPontos());
        String gm = ("   "+this.getGolsMarcados());
        String gs = ("   "+this.getGolsSofridos());
        String gd = ("   "+this.getSaldo());

        return  ("|"+this.getGrupo() + "     ").substring(0, 6)+"|"+
                ("    "+posicao).substring(0, 5)+"|"+(this.getNome()+"                  ").substring(0, 17)+"|"+
                pts.substring(pts.length()-4)+"|"+("   "+this.getJogos()).substring(0, 4)+"|"+
                ("   "+this.getVitorias()).substring(0, 4)+"|"+("   "+this.getEmpates()).substring(0, 4)+"|"+
                ("   "+this.getDerrotas()).substring(0, 4)+"|"+gm.substring(gm.length()-4)+"|"+
                gs.substring(gs.length()-4)+"|"+gd.substring(gd.length()-4)+"|\n";

    }

}
