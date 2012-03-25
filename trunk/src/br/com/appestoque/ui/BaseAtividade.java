package br.com.appestoque.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class BaseAtividade extends Activity{

	public void onIniciarClick(View view) {
		final Intent intent = new Intent(this, IniciarAtividade.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.startActivity(intent);
	}
	
	public void onRetornarClick(View view) {
		finish();
	}
	
}