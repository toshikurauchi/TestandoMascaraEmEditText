package br.com.sapereaude.teste;

import android.app.Activity;
import android.os.Bundle;

public class TestandoMascaraEmEditTextActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        MaskedEditText edit = (MaskedEditText) findViewById(R.id.edit);
    }
}