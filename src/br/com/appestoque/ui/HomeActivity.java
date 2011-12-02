package br.com.appestoque.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_home);
    }

    public void onAtualizarClick(View v) {
    	Toast.makeText(HomeActivity.this,"Atualizar",Toast.LENGTH_LONG).show();
    }
    
    public void onProdutoClick(View v) {
    	Toast.makeText(HomeActivity.this,"Produto",Toast.LENGTH_LONG).show();
    }
    
    public void onEstoqueClick(View v) {
    	Toast.makeText(HomeActivity.this,"Estoque",Toast.LENGTH_LONG).show();
    }
    
    public void onUsuarioClick(View v) {
    	Toast.makeText(HomeActivity.this,"Usuário",Toast.LENGTH_LONG).show();
    }

}