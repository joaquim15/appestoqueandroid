package br.com.appestoque.ui;

import java.util.UUID;

import br.com.appestoque.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class PreferenciaActivity extends Activity{
	
	public static final String PREFERENCIAS = "MinhasPreferencias";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferencia_editar_activity);
		SharedPreferences preferencias = getSharedPreferences(PREFERENCIAS, 0);
		String uuid = preferencias.getString("UUID", UUID.randomUUID().toString());
		((TextView) findViewById(R.id.edtUUID)).setText(uuid);
	}
	
	@Override
    protected void onStop(){
		super.onStop();
		final EditText editUUID = (EditText) findViewById(R.id.edtUUID);
		SharedPreferences preferencias = getSharedPreferences(PREFERENCIAS, 0);
		SharedPreferences.Editor editor = preferencias.edit();
		editor.putString("UUID", editUUID.getText().toString());
		editor.commit();
    }
	
}