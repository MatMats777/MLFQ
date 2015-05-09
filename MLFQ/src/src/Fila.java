/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Heitor
 */
public class Fila {

    public List processos;
    public String algoritmo;
    public int quantum;

    public Fila() {
        processos = new ArrayList<>();
    }

    public Fila(ArrayList<Processo> processos, String algoritmo) {
        this.processos = processos;
        this.algoritmo = algoritmo;
        switch (this.algoritmo) {
            case "RR10":
                quantum = 10;
                break;
            case "RR20":
                quantum = 20;
                break;
            default:
                quantum = Integer.MAX_VALUE;
                break;
        }
    }

    public Fila(String algoritmo) {
        processos = new ArrayList<>();
        this.algoritmo = algoritmo;
        switch (this.algoritmo) {
            case "RR10":
                quantum = 10;
                break;
            case "RR20":
                quantum = 20;
                break;
            default:
                quantum = Integer.MAX_VALUE;
                break;
        }
    }
}
