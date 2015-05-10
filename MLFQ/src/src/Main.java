/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author Heitor
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    //Declaração das filas
    private static Fila fila1;
    private static Fila fila2;
    private static Fila fila3;
    private static Scanner input = new Scanner(System.in);
    private static int n;

    public static void main(String[] args) {
        // TODO code application logic here
        fila1 = new Fila("RR10");//Inicialização da fila 1
        fila2 = new Fila("RR20");//Inicialização da fila 2
        fila3 = new Fila("FCFS");//Inicialização da fila 3
        System.out.printf("Quantidade de processos: ");
        n = input.nextInt();

        //Entrada de dados pelo usuário
        for (int i = 1; i <= n; i++) {
            System.out.printf("Tamanho do burst de P%d: ", i);
            int burst = input.nextInt();//lê tamanho do burst
            System.out.printf("Quantidade de operações de I/O de P%d: ", i);
            int io = input.nextInt();// lê número de e/s
            Processo p = new Processo(burst, io, "P" + i);////Inicialização do processo
            fila1.processos.add(p);//Adição do processo a fila 1
        }

        Escalonador escalonador = new Escalonador(fila1);//Inicialização do escalonador a partir da fila 1
        ArrayList ProcessosExecutados = (ArrayList) escalonador.Escalonar();//Inicialização do ArrayList
        Iterator itePE = ProcessosExecutados.iterator();//Inicialização do iterator que percorrerá o ArrayList

        //Impressão do diagrama de Gantt
        while (itePE.hasNext()) {
            Processo processo = (Processo) itePE.next();
            if (processo.Ti != processo.Tf) {
                System.out.println("[ " + processo.Ti + " " + processo.Nome + " " + processo.Tf + " ]");
            }
        }
    }
}
