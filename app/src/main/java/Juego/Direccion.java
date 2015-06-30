package Juego;

/**
 * Created by francis on 22/04/15.
 */
public enum Direccion
{
    IZQUIERDA("IZQUIERDA", 1),
    DERECHA("DERECHA", 0);

    private String stringValue;
    private int intValue;

    private Direccion(String toString, int value)
    {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString()
    {
        return stringValue;
    }

    public int toInt()
    {
        return intValue;
    }
}
