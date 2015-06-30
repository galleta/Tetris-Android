package supertetris.supertetris;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener
{
    public static Button bJugar, bConfiguracion, bSalirApp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ***** Obtengo los recursos de la pantalla *****
        bJugar = (Button)findViewById(R.id.bJugar);
        bConfiguracion = (Button)findViewById(R.id.bConfiguracion);
        bSalirApp = (Button)findViewById(R.id.bSalirApp);
        // ***********************************************

        bJugar.setOnClickListener(this);
        bConfiguracion.setOnClickListener(this);
        bSalirApp.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    // ***** Métodos de la interfaz View.OnClickListener *****
    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.bJugar:
                Intent intentjuego = new Intent(MainActivity.this, Tetris.class);
                startActivity(intentjuego);
                break;
            case R.id.bConfiguracion:
                Intent intentpreferencias = new Intent(MainActivity.this, Preferencias.class);
                startActivity(intentpreferencias);
                break;
            case R.id.bSalirApp:
                finish();
                break;
        }
    }
    // *******************************************************
}
