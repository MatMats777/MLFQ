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
public class Escalonador {

    public Fila fila1;
    public Fila fila2;
    public Fila fila3;
    public Fila espera;
    public int contador;
    public int tempoPreempção2;
    public int tempoPreempção3;
    public List<Processo> PExecutados;

    public Escalonador(Fila fila1) {
        this.fila1 = fila1;
        this.fila2 = new Fila("RR20");
        this.fila3 = new Fila("FCFS");
        this.espera = new Fila("espera");
        this.contador = 0;
        this.tempoPreempção3 = 0;
        this.PExecutados = new ArrayList<>();
    }

    //Escalonador(Fila fila1) {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //}
    public void Fila1() {
        while (!fila1.processos.isEmpty()) {
            EscalonarFila1();
        }
    }

    public void Fila2() {
        while (!fila2.processos.isEmpty()) {
            Processo p = (Processo) fila2.processos.get(0);
            p.Ti = contador;
            //verificar se a entrada e saida termina durante a execução de um processo
            if (!espera.processos.isEmpty()) {
                while (((Processo) espera.processos.get(0)).InicioIO + 20 - contador < fila2.quantum - p.QuantumExecutado && p.Burst - p.BurstParcial > ((Processo) espera.processos.get(0)).InicioIO + 20 - contador) {
                    p.Ti = contador;
                    p.QuantumExecutado += ((Processo) espera.processos.get(0)).InicioIO + 20 - contador;
                    tempoPreempção2 = 0;
                    if (((Processo) espera.processos.get(0)).Burst - fila1.quantum >= 0) {
                        tempoPreempção2 = fila1.quantum;
                    } else {
                        tempoPreempção2 = ((Processo) espera.processos.get(0)).Burst;
                    }

                    FinalizarIO(p);
                    p.Tf = contador;
                    PExecutados.add(new Processo(p));
                    p.Ti = contador + tempoPreempção2;
                    tempoPreempção3 += tempoPreempção2;
                    Fila1();
                    if (espera.processos.isEmpty()) {
                        break;
                    }
                }
            }

            if (p.Burst - p.BurstParcial <= fila2.quantum - p.QuantumExecutado) {
                Boolean saiudaespera = false;
                if (!espera.processos.isEmpty()) {
                    if (contador + p.Burst - p.BurstParcial == ((Processo) espera.processos.get(0)).InicioIO + 20) {
                        Processo processo = ((Processo) espera.processos.get(0));
                        processo.Esperando = false;
                        processo.IOParcial += 1;
                        fila1.processos.add(processo);
                        espera.processos.remove(0);
                        saiudaespera = true;
                    }
                }
                ExecutarProcesso(p);
                p.QuantumExecutado = 0;
                fila2.processos.remove(0);
                if (saiudaespera) {
                    Fila1();
                }
            } else {
                Boolean saiudaespera = false;
                if (!espera.processos.isEmpty()) {
                    if (contador + fila2.quantum - p.QuantumExecutado == ((Processo) espera.processos.get(0)).InicioIO + 20) {
                        Processo processo = ((Processo) espera.processos.get(0));
                        processo.Esperando = false;
                        processo.IOParcial += 1;
                        fila1.processos.add(processo);
                        espera.processos.remove(0);
                        saiudaespera = true;
                    }
                }
                contador = contador + fila2.quantum - p.QuantumExecutado;
                p.BurstParcial += fila2.quantum - p.QuantumExecutado;
                p.Tf = contador;
                p.QuantumExecutado = 0;
                fila3.processos.add(p);
                fila2.processos.remove(0);
                Processo proc = new Processo(p);
                PExecutados.add(proc);
                if (saiudaespera) {
                    Fila1();
                }
            }
        }
    }

    public void Fila3() {
        while (!fila3.processos.isEmpty()) {
            Processo p = ((Processo) fila3.processos.get(0));
            p.Ti = contador;
            //verificar se a entrada e saida termina durante a execução de um processo
            if (!espera.processos.isEmpty()) {
                while (p.Burst - p.BurstParcial >= ((Processo) espera.processos.get(0)).InicioIO + 20 - contador) {
                    tempoPreempção3 = 0;
                    if (((Processo) espera.processos.get(0)).Burst - fila1.quantum >= 0) {
                        tempoPreempção3 += fila1.quantum;
                        if (((Processo) espera.processos.get(0)).Burst - fila1.quantum - fila2.quantum >= 0) {
                            tempoPreempção3 += fila2.quantum;
                        } else {
                            tempoPreempção3 += ((Processo) espera.processos.get(0)).Burst - fila1.quantum;
                        }
                    } else {
                        tempoPreempção3 += ((Processo) espera.processos.get(0)).Burst;
                    }
                    FinalizarIO(p);
                    p.Tf = contador;
                    PExecutados.add(new Processo(p));
                    p.Ti = p.Tf;
                    Fila1();
                    Fila2();
                    p.Ti += tempoPreempção3;
                    //Escalonar();
                    if (espera.processos.isEmpty()) {
                        break;
                    }
                }
            }
            Boolean saiudaespera = false;
            if (!espera.processos.isEmpty()) {
                if (contador + p.Burst - p.BurstParcial == ((Processo) espera.processos.get(0)).InicioIO + 20) {
                    Processo processo = ((Processo) espera.processos.get(0));
                    processo.Esperando = false;
                    processo.IOParcial += 1;
                    fila1.processos.add(processo);
                    espera.processos.remove(0);
                    saiudaespera = true;
                }
            }
            contador = contador + p.Burst - p.BurstParcial;
            p.BurstParcial = p.Burst;
            p.Tf = contador;
            if (p.IO > p.IOParcial) {
                if (espera.processos.isEmpty()) {
                    p.InicioIO = contador;
                } else {
                    p.InicioIO = ((Processo) espera.processos.get(espera.processos.size() - 1)).InicioIO + 20;
                }
                p.Esperando = true;
                p.BurstParcial = 0;
                espera.processos.add(p);
            }

            fila3.processos.remove(0);
            Processo proc = new Processo(p);
            PExecutados.add(proc);
            if (saiudaespera) {
                Fila1();
                //Fila2();
            }
        }
    }

    public List<Processo> Escalonar() {
        while (!fila1.processos.isEmpty() || !fila2.processos.isEmpty() || !fila3.processos.isEmpty() || !espera.processos.isEmpty()) {
            Fila1();
            Fila2();
            Fila3();

            if (!espera.processos.isEmpty()) {
                Processo processo = ((Processo) espera.processos.get(0));
                contador = processo.InicioIO + 20;
                processo.Esperando = false;
                processo.IOParcial += 1;
                fila1.processos.add(processo);
                espera.processos.remove(0);
            }
        }
        return PExecutados;
    }

    public void FinalizarIO(Processo p) {
        Processo processo = ((Processo) espera.processos.get(0));
        p.BurstParcial += processo.InicioIO + 20 - contador;
        contador = processo.InicioIO + 20;
        processo.Esperando = false;
        processo.IOParcial += 1;
        fila1.processos.add(processo);
        espera.processos.remove(0);
    }

    public void ExecutarProcesso(Processo p) {
        contador = contador + p.Burst - p.BurstParcial;
        p.Tf = contador;
        if (p.IO > p.IOParcial) {
            if (espera.processos.isEmpty()) {
                p.InicioIO = contador;
            } else {
                p.InicioIO = ((Processo) espera.processos.get(espera.processos.size() - 1)).InicioIO + 20;
            }
            p.Esperando = true;
            p.BurstParcial = 0;
            espera.processos.add(p);
        }
        Processo proc = new Processo(p);
        PExecutados.add(proc);
    }

    public void EscalonarFila1() {
        Processo p = ((Processo) fila1.processos.get(0));
        fila1.processos.remove(0);
        p.Ti = contador;
        //verificar se a entrada e saida termina durante a execução de um processo
        if (!espera.processos.isEmpty()) {
            while (((Processo) espera.processos.get(0)).InicioIO + 20 - contador <= fila1.quantum && p.Burst - p.BurstParcial >= ((Processo) espera.processos.get(0)).InicioIO + 20 - contador) {
                Processo processo = ((Processo) espera.processos.get(0));
                processo.Esperando = false;
                processo.IOParcial += 1;
                fila1.processos.add(processo);
                espera.processos.remove(0);
                if (espera.processos.isEmpty()) {
                    break;
                }
            }
        }
        if (p.Burst - p.BurstParcial <= fila1.quantum) {
            ExecutarProcesso(p);
        } else {
            contador = contador + fila1.quantum;
            p.BurstParcial += fila1.quantum;
            p.Tf = contador;
            fila2.processos.add(p);
            Processo proc = new Processo(p);
            PExecutados.add(proc);
        }
    }
}
