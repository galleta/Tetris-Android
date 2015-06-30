package Juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Collections;

import Utilidades.Utilidades;
import supertetris.supertetris.R;

/**
 * Created by francis on 12/04/15.
 * Esta clase representa una pieza del juego
 */
public class Pieza
{
    private ArrayList<ArrayList<Integer>> pieza;
    private int tipopieza, filas, columnas, x, y;
    private Bitmap imagenpieza;

    /**
     * Constructor de la clase Pieza
     * @param contexto Contexto de la aplicación
     * @param tipopieza Tipo de la pieza
     *                  1 es ....
     *                  2 es :..
     *                  3 es ..:
     *                  4 es ::
     *                  5 es .:·
     *                  6 es .:.
     *                  7 es ·:.
     */
    public Pieza(Context contexto, int tipopieza)
    {
        this.tipopieza = tipopieza;
        x = 0;
        y = 0;
        pieza = new ArrayList<>();

        switch(tipopieza)
        {
            case 1:     // Es la pieza ····
                filas = 1;
                columnas = 4;
                ArrayList<Integer> fila = new ArrayList<>();
                fila.add(tipopieza);
                fila.add(tipopieza);
                fila.add(tipopieza);
                fila.add(tipopieza);
                pieza.add(fila);
                imagenpieza = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.block_blue);
                break;
            case 2:     // Es la pieza :..
                filas = 2;
                columnas = 3;
                ArrayList<Integer> fila2 = new ArrayList<>();
                ArrayList<Integer> fila3 = new ArrayList<>();
                fila2.add(tipopieza);
                fila2.add(0);
                fila2.add(0);
                pieza.add(fila2);
                fila3.add(tipopieza);
                fila3.add(tipopieza);
                fila3.add(tipopieza);
                pieza.add(fila3);
                imagenpieza = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.block_lightblue);
                break;
            case 3:     // Es la pieza ..:
                filas = 2;
                columnas = 3;
                ArrayList<Integer> fila4 = new ArrayList<>();
                ArrayList<Integer> fila5 = new ArrayList<>();
                fila4.add(0);
                fila4.add(0);
                fila4.add(tipopieza);
                pieza.add(fila4);
                fila5.add(tipopieza);
                fila5.add(tipopieza);
                fila5.add(tipopieza);
                pieza.add(fila5);
                imagenpieza = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.block_orange);
                break;
            case 4:     // Es la pieza ::
                filas = columnas = 2;
                ArrayList<Integer> fila6 = new ArrayList<>();
                fila6.add(tipopieza);
                fila6.add(tipopieza);
                pieza.add(fila6);
                pieza.add(fila6);
                imagenpieza = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.block_yelow);
                break;
            case 5:     // Es la pieza .:·
                filas = 2;
                columnas = 3;
                ArrayList<Integer> fila7 = new ArrayList<>();
                ArrayList<Integer> fila8 = new ArrayList<>();
                fila7.add(0);
                fila7.add(tipopieza);
                fila7.add(tipopieza);
                pieza.add(fila7);
                fila8.add(tipopieza);
                fila8.add(tipopieza);
                fila8.add(0);
                pieza.add(fila8);
                imagenpieza = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.block_green);
                break;
            case 6:     // Es la pieza .:.
                filas = 2;
                columnas = 3;
                ArrayList<Integer> fila9 = new ArrayList<>();
                ArrayList<Integer> fila10 = new ArrayList<>();
                fila9.add(0);
                fila9.add(tipopieza);
                fila9.add(0);
                pieza.add(fila9);
                fila10.add(tipopieza);
                fila10.add(tipopieza);
                fila10.add(tipopieza);
                pieza.add(fila10);
                imagenpieza = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.block_pink);
                break;
            case 7:     // Es la pieza ·:.
                filas = 2;
                columnas = 3;
                ArrayList<Integer> fila11 = new ArrayList<>();
                ArrayList<Integer> fila12 = new ArrayList<>();
                fila11.add(tipopieza);
                fila11.add(tipopieza);
                fila11.add(0);
                pieza.add(fila11);
                fila12.add(0);
                fila12.add(tipopieza);
                fila12.add(tipopieza);
                pieza.add(fila12);
                imagenpieza = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.block_red);
                break;
        }
        //mostrarPieza();
    }

    /**
     * Constructor de copia de la clase Pieza
     * @param p Pieza para copiar
     */
    public Pieza(Pieza p)
    {
        tipopieza = p.getTipoPieza();
        x = p.getX();
        y = p.getY();
        filas = p.getFilasPieza();
        columnas = p.getColumnasPieza();
        pieza = new ArrayList<>();

        pieza.addAll(p.pieza);
    }

    /**
     * Posiciona la pieza la pantalla en una posición aleatoria en el eje X
     * @param anchopantalla Ancho de la pantalla de juego
     */
    public void posicionarPiezaEjeX(int anchopantalla)
    {
        x = Utilidades.numeroAleatorio(anchopantalla - columnas);
    }

    /**
     * Devuelve las filas que tiene la pieza
     * @return Filas de la pieza
     */
    public int getFilasPieza()
    {
        return filas;
    }

    /**
     * Devuelve las columnas que tiene la pieza
     * @return Columnas de la pieza
     */
    public int getColumnasPieza()
    {
        return columnas;
    }

    /**
     * Devuelve la posición X de la pieza
     * @return Posición X de la pieza
     */
    public int getX()
    {
        return x;
    }

    /**
     * Devuelve la posición y de la pieza
     * @return Posición y de la pieza
     */
    public int getY()
    {
        return y;
    }

    /**
     * Devuelve el tipo de la pieza
     * @return Tipo de la pieza
     */
    public int getTipoPieza()
    {
        return tipopieza;
    }

    /**
     * Gira la pieza a la derecha
     */
    public void girarDerecha()
    {
        // Intercambio filas por columnas
        ArrayList<ArrayList<Integer>> piezagirada = new ArrayList<>();
        for(int j = 0; j < columnas; j++)
        {
            ArrayList<Integer> nuevafila = new ArrayList<>();
            for (int i = filas - 1; i >= 0; i--)
                nuevafila.add(pieza.get(i).get(j));
            piezagirada.add(nuevafila);
        }
        filas = piezagirada.size();
        columnas = piezagirada.get(0).size();
        pieza = new ArrayList<>();
        pieza.addAll(piezagirada);
        //mostrarPieza();
    }

    /**
     * Gira la pieza a la izquierda
     */
    public void girarIzquierda()
    {
        // Giro a la derecha
        girarDerecha();
        // Invierto las columnas
        for(int i = 0; i < filas; i++)
            Collections.reverse(pieza.get(i));
        // Invierto las filas
        ArrayList<ArrayList<Integer>> filasinvertidas = new ArrayList<>();
        for(int i = filas-1; i >= 0; i--)
            filasinvertidas.add(pieza.get(i));
        pieza = new ArrayList<>();
        pieza.addAll(filasinvertidas);
        //mostrarPieza();
    }

    /**
     * Dibuja la pieza en la pantalla
     * @param c Lienzo para dibujar
     */
    public void onDraw(Canvas c)
    {
        for(int i = 0; i < filas; i++)
            for(int j = 0; j < columnas; j++)
                if( pieza.get(i).get(j) != 0 )
                    c.drawBitmap(imagenpieza, imagenpieza.getWidth()*x + imagenpieza.getWidth()*j, imagenpieza.getHeight()*y + imagenpieza.getHeight()*i, null);
    }

    /**
     * Mueve la pieza una posición hacia abajo
     */
    public void bajarPieza()
    {
        y++;
    }

    /**
     * Mueve la pieza una posición hacia la derecha o la izquierda
     * @param direccion Dirección a la que se mueve la pieza, puede ser derecha o izquierda
     */
    public void moverPiezaEjeX(Direccion direccion)
    {
        switch(direccion)
        {
            case DERECHA:
                x++;
                break;
            case IZQUIERDA:
                x--;
                break;
        }
    }

    /**
     * Obtiene un elemento de la pieza
     * @param fila Fila
     * @param columna Columna
     * @return Valor de la pieza en la posición indicada
     */
    public int getElemento(int fila, int columna)
    {
        return pieza.get(fila).get(columna);
    }

    /**
     * Muestra la pieza
     */
    public void mostrarPieza()
    {
        System.out.println("Filas: " + filas);
        System.out.println("Columnas: " + columnas);
        System.out.println("Posiciones: ");
        for(int i = 0; i < filas; i++)
        {
            for(int j = 0; j < columnas; j++)
                System.out.print("[" + (x+j) + "][" + (y+i) + "] ");
            System.out.println();
        }
        System.out.println(pieza);
    }
}
