package br.com.appestoque.ui;

import br.com.appestoque.R;
import android.app.ListActivity;
import android.os.Bundle;

public class ProdutoActiviry extends ListActivity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_produto);
	}
	
}
