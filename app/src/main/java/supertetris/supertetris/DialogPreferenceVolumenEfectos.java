package supertetris.supertetris;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import Utilidades.Utilidades;

/**
 * Created by francis on 26/04/15.
 */
public class DialogPreferenceVolumenEfectos extends DialogPreference
{
    private Context contexto;
    private NumberPicker volumenEfectos;

    public DialogPreferenceVolumenEfectos(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        funcionalidad(context);
    }

    public DialogPreferenceVolumenEfectos(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        funcionalidad(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DialogPreferenceVolumenEfectos(Context context)
    {
        super(context);
        funcionalidad(context);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult)
    {
        super.onDialogClosed(positiveResult);
        persistBoolean(positiveResult);

        if( positiveResult )
        {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(contexto).edit();
            editor.putInt("volumen_efectos_sonido", volumenEfectos.getValue());
            editor.commit();
        }
    }

    private void funcionalidad(Context context)
    {
        contexto = context;
        setDialogLayoutResource(R.layout.dialog_preference_layout_efectos);
        setPersistent(false);
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder)
    {
        TextView title = new TextView(contexto);
        title.setText(this.getTitle());
        title.setGravity(Gravity.CENTER);
        title.setPadding(10, 10, 10, 10);
        title.setTextColor(Color.parseColor("#00abe5"));
        title.setTextSize(20);
        builder.setCancelable(Boolean.FALSE);
        builder.setCustomTitle(title);

        super.onPrepareDialogBuilder(builder);
    }

    @Override
    /**
     * Este método sirve para dar funcionalidad a los elementos del dialog preference personalizado
     */
    public void onBindDialogView(View view)
    {
        volumenEfectos = (NumberPicker)view.findViewById(R.id.volumenEfectos);

        volumenEfectos.setMaxValue(100);
        volumenEfectos.setMinValue(0);
        volumenEfectos.setValue(Utilidades.getVolumenEfectosSonido(contexto));

        super.onBindDialogView(view);
    }
}
