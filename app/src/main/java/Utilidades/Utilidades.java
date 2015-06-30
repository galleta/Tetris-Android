package Utilidades;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by francis on 22/04/15.
 * Esta clase es utilizada para implementar ciertas utilidades comunes al juego
 */
public final class Utilidades
{
    private static String PACKAGE_ID = "supertetris.supertetris";

    private static Random aleatorio = new Random();

    /**
     * Muestra un texto en la pantalla
     * @param contexto Contexto
     * @param texto Texto a mostrar
     */
    public static void mostrarToastText(Context contexto, String texto)
    {
        Toast toast = Toast.makeText(contexto, texto, Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Genera un número aleatorio entre 1 y un máximo
     * @param superior Máximo para calcular el aleatorio
     * @return Número aleatorio entre 1 y superior
     */
    public static int numeroAleatorio(int superior)
    {
        return aleatorio.nextInt(superior)+1;
    }

    /**
     * Indica si en las preferencias de la aplicación está activado el sonido
     * @param contexto Contexto de la aplicación
     * @return Verdadero si el sonido está activado, falso en caso contrario
     */
    public static Boolean estaActivadoSonido(Context contexto)
    {
        SharedPreferences preferenciasapp = contexto.getSharedPreferences(PACKAGE_ID + "_preferences", contexto.MODE_PRIVATE);
        return preferenciasapp.getBoolean("musica", Boolean.TRUE);
    }

    /**
     * Indica si en las preferencias de la aplicación están activados los efectos sonoros.
     * Tales como el sonido de la línea completa, subir un nivel, choque de piezas...
     * @param contexto Contexto de la aplicación
     * @return Verdadero si los efectos sonoros están activados, falso en caso contrario
     */
    public static Boolean estaActivadoEfectosSonoros(Context contexto)
    {
        SharedPreferences preferenciasapp = contexto.getSharedPreferences(PACKAGE_ID + "_preferences", contexto.MODE_PRIVATE);
        return preferenciasapp.getBoolean("efectos_sonoros", Boolean.TRUE);
    }

    /**
     * Obtiene el % del volumen del sonido de la música del juego
     * @param contexto Contexto de la aplicación
     * @return % del volumen del sonido de la música
     */
    public static int getVolumenSonido(Context contexto)
    {
        SharedPreferences preferenciasapp = contexto.getSharedPreferences("tetris.tetris_preferences", contexto.MODE_PRIVATE);
        return preferenciasapp.getInt("volumen_musica", 100);
    }

    /**
     * Obtiene el % del volumen del sonido de los efectos de sonido del juego
     * @param contexto Contexto de la aplicación
     * @return % del volumen de los efectos de sonido
     */
    public static int getVolumenEfectosSonido(Context contexto)
    {
        SharedPreferences preferenciasapp = contexto.getSharedPreferences("tetris.tetris_preferences", contexto.MODE_PRIVATE);
        return preferenciasapp.getInt("volumen_efectos_sonido", 100);
    }

    /**
     * Comprueba si el teléfono tiene el tono activado
     * @param c Contexto de la aplicación
     * @return Verdadero si el teléfono tiene el tono activado, falso en caso contrario
     */
    public static Boolean sonidoActivado(Context c)
    {
        Boolean activado = Boolean.TRUE;

        AudioManager am = (AudioManager)c.getSystemService(Context.AUDIO_SERVICE);
        if( am.getRingerMode() != AudioManager.RINGER_MODE_NORMAL )
            activado = Boolean.FALSE;

        return activado;
    }
}
