package br.com.appestoque.ui;

import java.util.UUID;

import br.com.appestoque.Constantes;
import br.com.appestoque.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PreferenciaActivity extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferencia_editar_activity);
		SharedPreferences preferencias = getSharedPreferences(Constantes.PREFERENCIAS, 0);
		String uuid = preferencias.getString("UUID", UUID.randomUUID().toString());
		if(uuid.equals("")){
			uuid = UUID.randomUUID().toString();
		}
		((TextView) findViewById(R.id.edtUUID)).setText(uuid);
	}
	
	public void onSalvarClick(View view) {
		final EditText uuid = (EditText) findViewById(R.id.edtUUID);
		SharedPreferences preferencias = getSharedPreferences(Constantes.PREFERENCIAS, 0);
		SharedPreferences.Editor editor = preferencias.edit();
		editor.putString("UUID", uuid.getText().toString());
		editor.commit();		
		setResult(RESULT_OK);
		this.finish();
	}
	
	public void onCancelarClick(View v) {
		setResult(RESULT_CANCELED);
		this.finish();
	}
	
}