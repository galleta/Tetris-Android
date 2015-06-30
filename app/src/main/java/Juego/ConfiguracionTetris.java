package Juego;

/**
 * Created by francis on 15/06/15.
 */
public class ConfiguracionTetris
{
    private Tablero tablero;
    private Pieza piezajuego;
    private int velocidadbajadamotor;

    /**
     * Constructor de la clase
     * @param tablero Tablero del juego
     * @param piezajuego Pieza con la que se juega
     * @param velocidadbajadamotor Velocidad de bajada del motor
     */
    public ConfiguracionTetris(Tablero tablero, Pieza piezajuego, int velocidadbajadamotor)
    {
        this.tablero = tablero;
        this.piezajuego = piezajuego;
        this.velocidadbajadamotor = velocidadbajadamotor;
    }

    /**
     * Devuelve el tablero de la partida
     * @return Tablero de la partida
     */
    public Tablero getTablero()
    {
        return tablero;
    }

    /**
     * Devuelve la pieza con la que se estaba jugando
     * @return Pieza con la que se estaba jugando en el momento de haber guardado la configuracion
     */
    public Pieza getPiezaJuego()
    {
        return piezajuego;
    }

    /**
     * Devuelve la velocidad de baja del motor
     * @return Velocidad de bajada del motor del juego en el momento de haber guardado la configuracion
     */
    public int getVelocidadBajadaMotor()
    {
        return velocidadbajadamotor;
    }
}
