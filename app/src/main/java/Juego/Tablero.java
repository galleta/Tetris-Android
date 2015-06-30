package Juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

import supertetris.supertetris.R;

/**
 * Created by francis on 12/04/15.
 * Esta clase representa el tablero para jugar al tetris
 */
public class Tablero
{
    private Bitmap azul, verde, azulclaro, naranja, rosa, rojo, amarillo;
    private ArrayList<ArrayList<Integer>> tablero;
    private int filas, columnas;
    private Context contexto;

    /**
     * Constructor
     * @param contexto Contexto de la aplicación
     * @param filas Filas del tablero
     * @param columnas Columnas del tablero
     */
    public Tablero(Context contexto, int filas, int columnas)
    {
        this.filas = filas;
        this.columnas = columnas;
        tablero = new ArrayList<>();
        this.contexto = contexto;

        for(int i = 0; i < filas; i++)
        {
            ArrayList<Integer> fila = new ArrayList<>();
            for(int j = 0; j < columnas; j++)
                fila.add(0);
            tablero.add(fila);
        }

        azul = BitmapFactory.decodeResource(this.contexto.getResources(), R.drawable.block_blue);
        azulclaro = BitmapFactory.decodeResource(this.contexto.getResources(), R.drawable.block_lightblue);
        naranja = BitmapFactory.decodeResource(this.contexto.getResources(), R.drawable.block_orange);
        amarillo = BitmapFactory.decodeResource(this.contexto.getResources(), R.drawable.block_yelow);
        verde = BitmapFactory.decodeResource(this.contexto.getResources(), R.drawable.block_green);
        rosa = BitmapFactory.decodeResource(this.contexto.getResources(), R.drawable.block_pink);
        rojo = BitmapFactory.decodeResource(this.contexto.getResources(), R.drawable.block_red);
    }

    /**
     * Constructor con parámetros de la clase tablero
     * @param t Tablero base para construir
     */
    public Tablero(final Tablero t)
    {
        this.filas = t.filas;
        this.columnas = t.columnas;
        tablero = new ArrayList<>();
        this.contexto = t.contexto;

        tablero.addAll(t.tablero);

        azul = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.block_blue);
        azulclaro = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.block_lightblue);
        naranja = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.block_orange);
        amarillo = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.block_yelow);
        verde = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.block_green);
        rosa = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.block_pink);
        rojo = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.block_red);
    }

    /**
     * Devuelve las filas del tablero
     * @return Filas del tablero
     */
    public int getFilas()
    {
        return filas;
    }

    /**
     * Devuelve las columnas del tablero
     * @return Columnas del tablero
     */
    public int getColumnas()
    {
        return columnas;
    }

    /**
     * Devuelve un elemento del tablero de una posición
     * @param x Posición x
     * @param y Posición y
     * @return Elemento de la posición x,y del tablero
     */
    public int obtenerElemento(int x, int y)
    {
        return tablero.get(x).get(y);
    }

    public void modificarElemento(int fila, int columna, int valornuevo)
    {
        tablero.get(fila).set(columna, valornuevo);
    }

    /**
     * Dibuja el tablero del juego
     * @param c Lienzo para dibujar
     */
    public void onDraw(Canvas c)
    {
        for(int i = 0; i < getFilas(); i++)
            for(int j = 0; j < getColumnas(); j++)
                if( tablero.get(i).get(j) != 0 )
                    switch(tablero.get(i).get(j))
                    {
                        case 1:
                            c.drawBitmap(azul, azul.getWidth()*j, azul.getHeight()*i, null);
                            break;
                        case 2:
                            c.drawBitmap(azulclaro, azulclaro.getWidth()*j, azulclaro.getHeight()*i, null);
                            break;
                        case 3:
                            c.drawBitmap(naranja, naranja.getWidth()*j, naranja.getHeight()*i, null);
                            break;
                        case 4:
                            c.drawBitmap(amarillo, amarillo.getWidth()*j, amarillo.getHeight()*i, null);
                            break;
                        case 5:
                            c.drawBitmap(verde, verde.getWidth()*j, verde.getHeight()*i, null);
                            break;
                        case 6:
                            c.drawBitmap(rosa, rosa.getWidth()*j, rosa.getHeight()*i, null);
                            break;
                        case 7:
                            c.drawBitmap(rojo, rojo.getWidth()*j, rojo.getHeight()*i, null);
                            break;
                    }
    }

    /**
     * Comprueba si una pieza puede bajar una posición
     * @param p Pieza para comprobar si puede bajar
     * @return Verdadero si la pieza puede bajar una posición, falso en caso contrario
     */
    public Boolean puedoBajarPieza(Pieza p)
    {
        Boolean puedebajar = Boolean.TRUE;

        if( chocaPiezaAbajoTablero(p) || chocaAbajoConOtraPieza(p) )
            puedebajar = Boolean.FALSE;

        return puedebajar;
    }

    /**
     * Comprueba si se puede mover una pieza en el tablero a la derecha o a la izquierda
     * @param p Pieza para comprobar
     * @param direccion Dirección, puede ser derecha o izquierda
     * @return Verdadero si se puede mover la pieza, falso en caso contrario
     */
    public Boolean puedoMoverPieza(Pieza p, Direccion direccion)
    {
        Boolean puedomover = Boolean.TRUE;

        switch(direccion)
        {
            case DERECHA:
                if( chocaPiezaDerechaPantalla(p) || chocaPiezaDerechaConOtraPieza(p) )
                    puedomover = Boolean.FALSE;
                break;
            case IZQUIERDA:
                if( chocaPiezaIzquierdaPantalla(p) || chocaPiezaIzquierdaConOtraPieza(p) )
                    puedomover = Boolean.FALSE;
                break;
        }

        return puedomover;
    }

    /**
     * Comprueba si se puede girar una pieza en el tablero a la derecha o a la izquierda
     * @param p Pieza para girar
     * @param direccion Dirección, puede ser derecha o izquierda
     * @return Verdadero si se puede girar la pieza, falso en caso contrario
     */
    public Boolean puedoGirarPieza(Pieza p, Direccion direccion)
    {
        Boolean puedo = Boolean.TRUE;
        Pieza aux = new Pieza(p);

        switch(direccion)
        {
            case DERECHA:
                aux.girarDerecha();
                break;
            case IZQUIERDA:
                aux.girarIzquierda();
                break;
        }

        // Compruebo que la pieza no se salga por la derecha ni por la izquierda del tablero
        if( aux.getX() < 0 || (aux.getX()+aux.getColumnasPieza()) > columnas )
            puedo = Boolean.FALSE;

        // Si se puede girar, compruebo que no coche con otra pieza que haya fijada en el tablero
        for(int i = 0; puedo && i < aux.getFilasPieza(); i++)
            for(int j = 0; puedo && j < aux.getColumnasPieza(); j++)
                if( aux.getElemento(i, j) != 0 && getElemento(aux.getY() + i, aux.getX() + j) != 0 )
                    puedo = Boolean.FALSE;

        return puedo;
    }

    /**
     * Comprueba si la pieza no choca para abajo con otra pieza en el tablero
     * @param p Pieza para comprobar
     * @return Verdadero si la pieza choca para abajo con otra pieza en el tablero, falso en caso contrario
     */
    private Boolean chocaAbajoConOtraPieza(Pieza p)
    {
        Boolean choca = Boolean.FALSE;

        for(int i = 0; !choca && i < p.getFilasPieza(); i++)
            for(int j = 0; ! choca && j < p.getColumnasPieza(); j++)
                if( p.getElemento(i, j) != 0 && getElemento(p.getY() + i + 1, p.getX() + j) != 0 )
                    choca = Boolean.TRUE;

        return choca;
    }

    /**
     * Comprueba si la pieza pasada choca con el fondo del tablero del juego
     * @param p Pieza para comprobar
     * @return Verdadero si la pieza ha llegado al fondo del tablero, falso en caso contrario
     */
    private Boolean chocaPiezaAbajoTablero(Pieza p)
    {
        return p.getY() + p.getFilasPieza() >= filas;
    }

    /**
     * Comprueba si la pieza choca con la parte derecha de la pantalla
     * @param p Pieza para comprobar
     * @return Verdadero si choca, falso en caso contrario
     */
    private Boolean chocaPiezaDerechaPantalla(Pieza p)
    {
        return p.getX() + p.getColumnasPieza() >= columnas;
    }

    /**
     * Comprueba si la pieza no choca a la derecha con otra pieza en el tablero
     * @param p Pieza para comprobar
     * @return Verdadero si la pieza choca a la derecha con otra pieza en el tablero, falso en caso contrario
     */
    private Boolean chocaPiezaDerechaConOtraPieza(Pieza p)
    {
        Boolean choca = Boolean.FALSE;

        for(int i = 0; !choca && i < p.getFilasPieza(); i++)
            for(int j = 0; ! choca && j < p.getColumnasPieza(); j++)
                if( p.getElemento(i, j) != 0 && getElemento(p.getY() + i, p.getX() + j + 1) != 0 )
                    choca = Boolean.TRUE;

        return choca;
    }

    /**
     * Comprueba si la pieza choca con la parte izquierda de la pantalla
     * @param p Pieza para comprobar
     * @return Verdadero si choca, falso en caso contrario
     */
    private Boolean chocaPiezaIzquierdaPantalla(Pieza p)
    {
        return p.getX() <= 0;
    }

    /**
     * Comprueba si la pieza no choca a la izquierda con otra pieza en el tablero
     * @param p Pieza para comprobar
     * @return Verdadero si la pieza choca a la izquierda con otra pieza en el tablero, falso en caso contrario
     */
    private Boolean chocaPiezaIzquierdaConOtraPieza(Pieza p)
    {
        Boolean choca = Boolean.FALSE;

        for(int i = 0; !choca && i < p.getFilasPieza(); i++)
            for(int j = 0; ! choca && j < p.getColumnasPieza(); j++)
                if( p.getElemento(i, j) != 0 && getElemento(p.getY() + i, p.getX() + j - 1) != 0 )
                    choca = Boolean.TRUE;

        return choca;
    }

    /**
     * Devuelve un elemento del tablero
     * @param fila Fila
     * @param columna Columna
     * @return Elemento que se encuentra en la posición indicada
     */
    private int getElemento(int fila, int columna)
    {
        return tablero.get(fila).get(columna);
    }

    /**
     * Comprueba si hay lineas completas en el tablero del tetris.
     * Si las hay, las elimina e inserta una nueva linea vacia arriba.
     * @return Numero total de lineas completas encontradas en el tablero.
     */
    public int comprobarLineasCompletas()
    {
        int lineascompletas = 0;

        for(int i = 0; i < getFilas(); i++)
            if( lineaCompleta(i) )
            {
                lineascompletas++;
                eliminarLineaTablero(i);
                insertarLineaVaciaArribaTablero();
            }

        return lineascompletas;
    }

    /**
     * Comprueba si una línea está completa en el tablero
     * @param linea Línea a comprobar si está completa
     * @return Verdadero si la línea que se le pasa está completa, falso en caso contrario
     */
    private boolean lineaCompleta(int linea)
    {
        Boolean completa = Boolean.TRUE;

        for(int i = 0; completa && i < getColumnas(); i++)
            if( tablero.get(linea).get(i) == 0 )
                completa = Boolean.FALSE;

        return completa;
    }

    /**
     * Elimina una línea del tablero desplazando las líneas de arriba a la indicada hacia abajo
     * @param linea Línea a eliminar
     */
    private void eliminarLineaTablero(int linea)
    {
        tablero.remove(linea);
    }

    /**
     * Inserta una línea vacía arriba del tablero
     */
    private void insertarLineaVaciaArribaTablero()
    {
        ArrayList<Integer> lineavacia = new ArrayList<>();
        for(int i = 0; i < getColumnas(); i++)
            lineavacia.add(0);
        tablero.add(0, lineavacia);
    }

    /**
     * Fija una pieza en el tablero
     * @param p Pieza para fijar
     */
    public void fijarPieza(Pieza p)
    {
        for(int i = 0; i < p.getFilasPieza(); i++)
            for(int j = 0; j < p.getColumnasPieza(); j++)
                if( p.getElemento(i, j) != 0 )
                    modificarElemento(p.getY() + i, p.getX() + j, p.getElemento(i, j));
    }

    public void mostrar()
    {
        System.out.println(tablero);
    }
}
