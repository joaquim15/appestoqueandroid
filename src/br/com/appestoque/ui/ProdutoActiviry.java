package br.com.appestoque.ui;

import br.com.appestoque.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ProdutoActiviry extends ListActivity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_produto);
	}
    
    public void onHomeClick(View v) {
    	final Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
	
}
