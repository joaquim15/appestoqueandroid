package br.com.appestoque.ui;

import br.com.appestoque.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UsuarioActivity extends BaseAtividade{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usuario_activity);
	}

    public void onHomeClick(View v) {
    	Intent intent = new Intent(this,IniciarAtividade.class);
        startActivity(intent);
    }

}
