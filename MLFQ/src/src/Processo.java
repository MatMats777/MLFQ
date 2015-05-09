/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

/**
 *
 * @author Heitor
 */
public class Processo {

    public int Burst;
    public int BurstParcial;
    public int IO;
    public int IOParcial;
    public int Ti;
    public int Tf;
    public String Nome;
    public Boolean Esperando;
    public int InicioIO;
    public int QuantumExecutado;

    public Processo() {
    }

    public Processo(int burst, int io, String Nome) {
        this.Burst = burst;
        this.IO = io;
        this.IOParcial = 0;
        this.Nome = Nome;
        this.BurstParcial = 0;
        this.Esperando = false;
        this.QuantumExecutado = 0;
    }

    public Processo(Processo p) {
        this.Burst = p.Burst;
        this.IO = p.IO;
        this.IOParcial = p.IOParcial;
        this.Nome = p.Nome;
        this.Tf = p.Tf;
        this.Ti = p.Ti;
        this.BurstParcial = p.BurstParcial;
        this.Esperando = p.Esperando;
        this.InicioIO = p.InicioIO;
        this.QuantumExecutado = p.QuantumExecutado;
    }
}
