package supertetris.supertetris;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

/**
 * Created by francis on 5/12/14.
 */
@SuppressWarnings("deprecation")
@TargetApi(11)
public class Preferencias extends PreferenceActivity
{
    private static int recurso_preferencias = R.xml.preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if( Build.VERSION.SDK_INT >= 11 )
        {
            addPreferencesFromResource(recurso_preferencias);
        }
        else
        {
            getFragmentManager().beginTransaction().replace(android.R.id.content, new PF()).commit();
        }
    }

    public static class PF extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(recurso_preferencias);
        }
    }

}
