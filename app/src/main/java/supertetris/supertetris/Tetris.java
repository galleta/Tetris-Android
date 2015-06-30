package supertetris.supertetris;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import Juego.ConfiguracionTetris;
import Juego.Cronometro;
import Juego.Direccion;
import Juego.PantallaJuego;

public class Tetris extends Activity implements View.OnClickListener
{
    private final int PUNTOS_POR_LINEA = 5;
    private static PantallaJuego pantallajuego;
    private static ImageView siguientepieza;
    private Bitmap imagenpieza;
    private int nuevoancho, nuevoalto, puntuacion = 0, nivel = 1;
    private Typeface fuentetiempo;
    private Cronometro cronometro;
    private static TextView tTiempoTranscurrido, tPuntuacion, tNivel;
    private static ImageButton ibMoverIzquierda, ibGirarIzquierda, ibMoverDerecha, ibGirarDerecha, ibBajar, ibPausa;
    private ConfiguracionTetris configuracion = null;
    private boolean ya = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);

        // ***** Obtengo los recursos de la pantalla *****
        pantallajuego = (PantallaJuego)findViewById(R.id.pantallajuego);
        ibMoverIzquierda = (ImageButton)findViewById(R.id.ibMoverIzquierda);
        ibGirarIzquierda = (ImageButton)findViewById(R.id.ibGirarIzquierda);
        ibMoverDerecha = (ImageButton)findViewById(R.id.ibMoverDerecha);
        ibGirarDerecha = (ImageButton)findViewById(R.id.ibGirarDerecha);
        ibBajar = (ImageButton)findViewById(R.id.ibBajar);
        ibPausa = (ImageButton)findViewById(R.id.ibPausa);
        siguientepieza = (ImageView)findViewById(R.id.siguientepieza);
        tTiempoTranscurrido = (TextView)findViewById(R.id.tTiempoTranscurrido);
        tPuntuacion = (TextView)findViewById(R.id.tPuntuacion);
        tNivel = (TextView)findViewById(R.id.tNivel);
        // ***********************************************

        fuentetiempo = Typeface.createFromAsset(getAssets(), "fonts/DS-DIGIB.TTF");
        tTiempoTranscurrido.setTypeface(fuentetiempo);
        cronometro = new Cronometro("Cronómetro juego", tTiempoTranscurrido);

        imagenpieza = BitmapFactory.decodeResource(getResources(), R.drawable.block_green);
        pantallajuego.setActividad(this);

        ibMoverIzquierda.setOnClickListener(this);
        ibGirarIzquierda.setOnClickListener(this);
        ibMoverDerecha.setOnClickListener(this);
        ibGirarDerecha.setOnClickListener(this);
        ibBajar.setOnClickListener(this);
        ibPausa.setOnClickListener(this);

        // Actualizo el tamaño de la pantalla para que cuadren los bloques de las piezas
        // Esto se ejecuta una vez se haya creado completamente el surfaceview
        pantallajuego.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                pantallajuego.invalidate();
                nuevoancho = pantallajuego.getWidth() - (pantallajuego.getWidth()%imagenpieza.getWidth());
                nuevoalto = pantallajuego.getHeight() - (pantallajuego.getHeight()%imagenpieza.getHeight());
                //Log.i("Main", "La pantalla mide " + pantallajuego.getWidth() + "x" + pantallajuego.getHeight() + ", y el bloque de la pieza " + imagenpieza.getWidth() + "x" + imagenpieza.getHeight());
                //Log.i("Main", "El tablero tendrá " + (pantallajuego.getWidth()/imagenpieza.getWidth()) + "x" + (pantallajuego.getHeight()/imagenpieza.getHeight()) + " bloques");
                //Log.i("Main", "La pantalla debe medir " + nuevoancho + "x" + nuevoalto);
                ViewGroup.LayoutParams tamanuevo = pantallajuego.getLayoutParams();
                tamanuevo.height = nuevoalto;
                tamanuevo.width = nuevoancho;
                pantallajuego.setLayoutParams(tamanuevo);

                if( configuracion == null)
                    pantallajuego.jugar();

                //Log.i("Main", "La pantalla mide ahora " + pantallajuego.getWidth() + "x" + pantallajuego.getHeight());
            }
        }, 1);

        // Inicio el cronómetro del juego
        new Thread(cronometro).start();

    }

    @Override
    public void onResume()
    {
        super.onResume();

        /*
            Cuando se vaya a la pantalla de las prefencias mientras se esta jugando y se cierre dicha pantalla
            se volvera a configurar la pantalla del tetris a como estaba en el momento de hacer la pausa
         */
        if( configuracion != null )
            pantallajuego.restablecerConfiguracion(configuracion);

        pantallajuego.reproducirSonidoFondoTetris();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tetris, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Muestra en la pantalla del juego la siguiente pieza que va a aparecer
     * @param pieza Pieza para mostrar
     */
    public void mostrarSiguientePiezaEnPantalla(int pieza)
    {
        switch(pieza)
        {
            case 1:
                siguientepieza.setImageResource(R.drawable.next_i);
                break;
            case 2:
                siguientepieza.setImageResource(R.drawable.next_j);
                break;
            case 3:
                siguientepieza.setImageResource(R.drawable.next_l);
                break;
            case 4:
                siguientepieza.setImageResource(R.drawable.next_o);
                break;
            case 5:
                siguientepieza.setImageResource(R.drawable.next_s);
                break;
            case 6:
                siguientepieza.setImageResource(R.drawable.next_t);
                break;
            case 7:
                siguientepieza.setImageResource(R.drawable.next_z);
                break;
        }
    }

    /**
     * Actualiza la puntuación del juego según las líneas completas que lleguen
     * @param lineascompletas Líneas completas que se han conseguido
     */
    public void actualizarPuntuacion(int lineascompletas)
    {
        puntuacion += (lineascompletas * PUNTOS_POR_LINEA);
        tPuntuacion.setText(String.valueOf(puntuacion));
    }

    /**
     * Aumenta el nivel del juego en 1
     */
    public void aumentarNivel()
    {
        nivel++;
        tNivel.setText(String.valueOf(nivel));
    }

    /**
     * Pausa el cronómetro del juego
     */
    public void pausarCronometro()
    {
        if( cronometro != null )
            cronometro.pause();
    }

    // ***** Métodos de la interfaz View.OnClickListener *****
    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.ibMoverIzquierda:
                pantallajuego.moverPieza(Direccion.IZQUIERDA);
                break;
            case R.id.ibGirarIzquierda:
                pantallajuego.girarPieza(Direccion.IZQUIERDA);
                break;
            case R.id.ibMoverDerecha:
                pantallajuego.moverPieza(Direccion.DERECHA);
                break;
            case R.id.ibGirarDerecha:
                pantallajuego.girarPieza(Direccion.DERECHA);
                break;
            case R.id.ibBajar:
                pantallajuego.bajarPieza();
                break;
            case R.id.ibPausa:
                pantallajuego.pausar();
                cronometro.pause();

                final ViewGroup viewById = (ViewGroup) findViewById(R.id.marco_principal);
                final ViewGroup dialogopausa = (ViewGroup)getLayoutInflater().inflate(R.layout.dialog_pause, null);

                // Botón para reanudar la partida
                dialogopausa.findViewById(R.id.bReanudar).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        viewById.removeView(dialogopausa);
                        pantallajuego.pausar();
                        cronometro.pause();
                    }
                });

                // Boton para ir a la configuracion
                dialogopausa.findViewById(R.id.bConfiguracionPause).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        //viewById.removeView(dialogopausa);
                        Intent intentpreferencias = new Intent(Tetris.this, Preferencias.class);
                        startActivity(intentpreferencias);
                    }
                });

                // Boton para salir del juego
                dialogopausa.findViewById(R.id.bSalir).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        viewById.removeView(dialogopausa);
                        pantallajuego.liberarRecursosPantalla();
                        finish();
                    }
                });

                // Necesario para la correcta recepción de los eventos onClick
                dialogopausa.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {}
                });

                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                viewById.addView(dialogopausa, lp);

                break;
        }
    }
    // *******************************************************

    @Override
    /**
     * Este método se ejecuta se cambia de pantalla.
     * Se usa para guardar la información de la pantalla para luego volver a restaurarla.
     */
    protected void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        /*
            Cuando se haga pause y se vaya a la pantalla de la configuracion
            se guardara el estado de la partida para luego poder restaurarlo y
            seguir jugando
         */
        configuracion = pantallajuego.getConfiguracion();
    }
}
