package br.com.appestoque.ui;

import java.util.UUID;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class PreferenciaActivity extends Activity{
	
	public static final String PREFERENCIAS = "MinhasPreferencias";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences preferencias = getSharedPreferences(PREFERENCIAS, 0);
		String uuid = preferencias.getString("UUID", "");
	}
	
	@Override
    protected void onStop(){
		super.onStop();
		SharedPreferences preferencias = getSharedPreferences(PREFERENCIAS, 0);
		SharedPreferences.Editor editor = preferencias.edit();
		editor.putString("UUID", "");
		editor.commit();
    }
	
	public void onUIIDClick(View v) {
		UUID uuid = UUID.randomUUID();	
	}

}