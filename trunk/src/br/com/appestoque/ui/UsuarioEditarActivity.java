package br.com.appestoque.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import br.com.appestoque.R;

public class UsuarioEditarActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usuario_editar);
	}

    public void onHomeClick(View v) {
    	Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
    }
	
}
