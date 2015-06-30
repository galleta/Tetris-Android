package Juego;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import Utilidades.Utilidades;
import supertetris.supertetris.R;
import supertetris.supertetris.Tetris;
import Sonido.Sonido;

/**
 * Created by francis on 24/02/15.
 */
public class PantallaJuego extends SurfaceView
{
    private SurfaceHolder holder;
    private Paint paint_go;
    private MotorJuego motor;
    private Tetris actividad;
    private Pieza piezajuego, piezasiguiente;
    private Context contexto;
    private Tablero tablero;
    private Bitmap imagenpieza;
    private int anchopantalla, altopantalla;
    private Handler escribirenUI; // Necesario para modificar la UI
    private int lineas, lineastotales;
    private final int AUMENTO_VELOCIDAD_BAJADA = 1000, LINEAS_PARA_SUBIR_NIVEL = 5;
    private static Boolean GAME_OVER;
    private Sonido sonido_choque, sonido_girar_pieza, sonido_linea_completa, sonido_tetris, sonido_game_over;
    private ConfiguracionTetris configuracion;

    public PantallaJuego(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        contexto = context;
    }

    public PantallaJuego(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        contexto = context;
    }

    public PantallaJuego(Context context)
    {
        super(context);
        contexto = context;
    }

    public void setActividad(Tetris actividad)
    {
        this.actividad = actividad;
    }

    @Override
    /**
     * Método donde se dibujan las cosas en la pantalla
     * @param canvas Lienzo de la pantalla donde se dibuja
     */
    protected void onDraw(Canvas canvas)
    {
        if( canvas != null && tablero != null && piezajuego != null )
        {
            if( !GAME_OVER )
            {
                canvas.drawColor(Color.BLACK);

                if (tablero.puedoBajarPieza(piezajuego))
                    piezajuego.bajarPieza();
                else
                {
                    if( Utilidades.sonidoActivado(contexto) && Utilidades.estaActivadoEfectosSonoros(contexto) )
                        sonido_choque.reproducir();
                    tablero.fijarPieza(piezajuego);
                    comprobarLineasCompletas();
                    generarPiezas();
                }

                tablero.onDraw(canvas);
                piezajuego.onDraw(canvas);
            }
            else
            {
                motor.pause();
                canvas.drawText("GAME OVER", (this.getWidth()/2) - 100, this.getHeight()/2, paint_go);
                if( actividad != null )
                    escribirenUI.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            actividad.pausarCronometro();
                        }
                    });
            }
        }
    }

    /**
     * Funcionalidad de la pantalla de juego
     */
    private void iniciarTodo()
    {
        GAME_OVER = Boolean.FALSE;
        paint_go = new Paint();
        paint_go.setColor(Color.WHITE);
        paint_go.setTextSize(40);
        lineastotales = 0;
        escribirenUI = new Handler();
        motor = new MotorJuego(this);
        piezajuego = null;
        piezasiguiente = null;
        cargarSonidos();
        imagenpieza = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.block_blue);

        //Log.i("PantallaJuego", "Pieza juego: " + piezajuego.getTipoPieza() + ", siguiente pieza: " + piezasiguiente.getTipoPieza());

        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback()
        {
            @Override
            /**
             * Este método se ejecutará cuando el SurfaceView se destruya
             * @param holder Holder del SurfaceView
             */
            public void surfaceDestroyed(SurfaceHolder holder)
            {

            }

            @SuppressLint("WrongCall")
            @Override
            /**
             * Este método se ejecutará cuando el SurfaceView se cree
             * @param holder Holder del SurfaceView
             */
            public void surfaceCreated(SurfaceHolder holder)
            {
                /* Con este trozo de código cuando se llamen a las preferencias mientras se juega
                   al cerrarlas y volver a la partida se pinta el tablero debajo de la pantalla
                   de pause y demás y se puede ver cómo estaba en el momento de hacer el pause
                 */
                if( tablero != null )
                {
                    Canvas c = null;
                    try
                    {
                        c = getHolder().lockCanvas();
                        synchronized (getHolder())
                        {
                            onDraw(c);
                        }
                    }
                    finally
                    {
                        if (c != null)
                            getHolder().unlockCanvasAndPost(c);
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
            {

            }
        });

        if( motor != null )
            liberarMotor();
        motor.setRunning(Boolean.TRUE);
        motor.start();
    }

    /**
     * Empezar a jugar al tetris
     */
    public void jugar()
    {
        iniciarTodo();
        anchopantalla = (getWidth() / imagenpieza.getWidth()) - 1;
        altopantalla = getHeight() / imagenpieza.getHeight();
        tablero = new Tablero(contexto, altopantalla, anchopantalla);
        generarPiezas();
        actualizarVolumenes();
        reproducirSonidoFondoTetris();
    }

    /**
     * Reproduce el sonido de fondo del tetris si se tiene que reproducir
     */
    public void reproducirSonidoFondoTetris()
    {
        if (Utilidades.sonidoActivado(contexto) && Utilidades.estaActivadoSonido(contexto))
            sonido_tetris.reproducir();
    }

    /**
     * Indica si el juego está pausado o no
     * @return Verdadero si el juego está pausado, falso en caso contrario
     */
    public Boolean getRunning()
    {
        return motor.getRunning();
    }

    /**
     * Pausa o reanuda el juego
     */
    public void pausar()
    {
        motor.pause();
        pausarSonido();
    }

    /**
     * Carga todos los sonidos del juego
     */
    private void cargarSonidos()
    {
        liberarSonidos();
        sonido_choque = new Sonido(contexto, "sonido_choque");
        sonido_girar_pieza = new Sonido(contexto, "sonido_girar_pieza");
        sonido_linea_completa = new Sonido(contexto, "sonido_linea_completa");
        sonido_tetris = new Sonido(contexto, "sonido_tetris");
        sonido_game_over = new Sonido(contexto, "sonido_game_over");
        sonido_choque.cargar(R.raw.sonido_choque, Boolean.FALSE);
        sonido_girar_pieza.cargar(R.raw.sonido_girar_pieza, Boolean.FALSE);
        sonido_linea_completa.cargar(R.raw.sonido_linea_completa, Boolean.FALSE);
        sonido_tetris.cargar(R.raw.sonido_tetris, Boolean.TRUE);
        sonido_game_over.cargar(R.raw.sonido_game_over, Boolean.FALSE);
    }

    /**
     * Actualiza la siguiente pieza que va a salir en pantalla
     */
    public void actualizarSiguientePiezaEnPantalla()
    {
        if( actividad != null )
            escribirenUI.post(new Runnable()
            {
                @Override
                public void run()
                {
                    actividad.mostrarSiguientePiezaEnPantalla(piezasiguiente.getTipoPieza());
                }
            });
    }

    /**
     * Genera las piezas del juego, tanto la pieza con la que se juega como la siguiente pieza para jugar
     */
    public void generarPiezas()
    {
        if( piezasiguiente == null )
            piezajuego = new Pieza(contexto, Utilidades.numeroAleatorio(7));
        else
            piezajuego = new Pieza(contexto, piezasiguiente.getTipoPieza());

        piezajuego.posicionarPiezaEjeX(anchopantalla);

        if( tablero.puedoBajarPieza(piezajuego) )
        {
            int veces;
            // Giro la pieza aleatoriamente a la derecha o a la izquierda
            if (Utilidades.numeroAleatorio(2) == 1)
            {
                veces = Utilidades.numeroAleatorio(3);
                for (int i = 0; i < veces; i++)
                    piezajuego.girarDerecha();
            }
            else
            {
                veces = Utilidades.numeroAleatorio(3);
                for (int i = 0; i < veces; i++)
                    piezajuego.girarIzquierda();
            }

            piezasiguiente = new Pieza(contexto, Utilidades.numeroAleatorio(7));
            actualizarSiguientePiezaEnPantalla();
            //Log.i("PantallaJuego", "Pieza juego: " + piezajuego.getTipoPieza() + ", siguiente pieza: " + piezasiguiente.getTipoPieza());
        }
        else
        {
            GAME_OVER = Boolean.TRUE;
            if( Utilidades.estaActivadoSonido(contexto) )
                sonido_tetris.stop();

            if( Utilidades.sonidoActivado(contexto) && Utilidades.estaActivadoEfectosSonoros(contexto) )
                sonido_game_over.reproducir();
        }
    }

    /**
     * Gira, si se puede, la pieza con la que se está jugando hacia una dirección
     * @param direccion Dirección para girar la pieza, puede ser derecha o izquierda
     */
    public void girarPieza(Direccion direccion)
    {
        if( tablero.puedoGirarPieza(piezajuego, direccion) )
        {
            if( Utilidades.sonidoActivado(contexto) && Utilidades.estaActivadoEfectosSonoros(contexto) )
            {
                sonido_girar_pieza.reproducir();
            }
            switch (direccion)
            {
                case DERECHA:
                    piezajuego.girarDerecha();
                    break;
                case IZQUIERDA:
                    piezajuego.girarIzquierda();
                    break;
            }
        }
    }

    /**
     * Mueve una casilla, si se puede, la pieza con la que se está jugando a una dirección
     * @param direccion Dirección a la que se mueve la pieza, puede ser derecha o izquierda
     */
    public void moverPieza(Direccion direccion)
    {
        if( tablero.puedoMoverPieza(piezajuego, direccion) )
            piezajuego.moverPiezaEjeX(direccion);
        else
        if( Utilidades.sonidoActivado(contexto) && Utilidades.estaActivadoEfectosSonoros(contexto) )
            sonido_choque.reproducir();
    }

    /**
     * Baja una casilla, si se puede, la pieza con la que se está jugando
     */
    public void bajarPieza()
    {
        if( tablero.puedoBajarPieza(piezajuego) )
            piezajuego.bajarPieza();
    }

    /**
     * Comprueba si hay líneas completas en el tablero y actualiza la puntuación del juego
     */
    private void comprobarLineasCompletas()
    {
        lineas = tablero.comprobarLineasCompletas();
        lineastotales += lineas;

        if( lineas > 0 && Utilidades.sonidoActivado(contexto) && Utilidades.estaActivadoEfectosSonoros(contexto) )
            sonido_linea_completa.reproducir();

        if (lineas > 0 && (lineastotales % LINEAS_PARA_SUBIR_NIVEL == 0))
            aumentarNivel();

        if( actividad != null )
            escribirenUI.post(new Runnable() {
                @Override
                public void run() {
                    if (lineas > 0)
                        actividad.actualizarPuntuacion(lineas);
                }
            });
    }

    /**
     * Aumenta el nivel del juego en 1 y aumenta la velocidad de caida de las piezas
     */
    private void aumentarNivel()
    {
        if( motor != null )
            if( motor.getVelocidadBajada() > 4000 )
                motor.setVelocidadBajada(motor.getVelocidadBajada() - AUMENTO_VELOCIDAD_BAJADA);

        if( actividad != null )
            escribirenUI.post(new Runnable() {
                @Override
                public void run() {
                    actividad.aumentarNivel();
                }
            });
    }

    /**
     * Actualiza los volúmenes de los sonidos según haya en las preferencias de la aplicación
     */
    public void actualizarVolumenes()
    {
        sonido_tetris.cambiarVolumen(Utilidades.getVolumenSonido(contexto));
        sonido_game_over.cambiarVolumen(Utilidades.getVolumenEfectosSonido(contexto));
        sonido_choque.cambiarVolumen(Utilidades.getVolumenEfectosSonido(contexto));
        sonido_linea_completa.cambiarVolumen(Utilidades.getVolumenEfectosSonido(contexto));
        sonido_girar_pieza.cambiarVolumen(Utilidades.getVolumenEfectosSonido(contexto));
    }

    /**
     * Pausa el sonido del juego
     */
    private void pausarSonido()
    {
        if( Utilidades.estaActivadoSonido(contexto) && Utilidades.sonidoActivado(contexto) )
            sonido_tetris.pausa();
    }

    /**
     * Libera todos los recursos del juego
     */
    public void liberarRecursosPantalla()
    {
        liberarSonidos();
        liberarMotor();
    }

    /**
     * Libera los sonidos del juego
     */
    private void liberarSonidos()
    {
        if( sonido_choque != null )
            sonido_choque.liberar();
        if( sonido_girar_pieza != null )
            sonido_girar_pieza.liberar();
        if( sonido_linea_completa != null )
            sonido_linea_completa.liberar();
        if( sonido_tetris != null )
            sonido_tetris.liberar();
        if( sonido_game_over != null )
            sonido_game_over.liberar();
    }

    /**
     * Libera los recursos del motor del juego
     */
    private void liberarMotor()
    {
        if( motor != null )
        {
            motor.setRunning(Boolean.FALSE);
            motor.matar();
        }
    }

    /**
     * Obtiene la configuración actual de la partida del tetris
     * @return Configuración de la partida
     */
    public ConfiguracionTetris getConfiguracion()
    {
        configuracion = new ConfiguracionTetris(tablero, piezajuego, motor.getVelocidadBajada());
        return configuracion;
    }

    /**
     * Reestablece la partida con una configuración
     * @param config Configuración para reestablecer la partida
     */
    public void restablecerConfiguracion(ConfiguracionTetris config)
    {
        tablero = new Tablero(config.getTablero());
        piezajuego = config.getPiezaJuego();
        motor.setVelocidadBajada(config.getVelocidadBajadaMotor());
        anchopantalla = tablero.getColumnas();
        altopantalla = tablero.getFilas();
        cargarSonidos();
    }

}


