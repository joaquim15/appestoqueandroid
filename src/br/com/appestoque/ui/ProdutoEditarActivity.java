package br.com.appestoque.ui;

import br.com.appestoque.R;
import br.com.appestoque.dominio.Produto;
import br.com.appestoque.provider.ProdutoDbAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class ProdutoEditarActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_produto_editar);
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
	    	ProdutoDbAdapter produtoDbAdapter = new ProdutoDbAdapter(this);
			produtoDbAdapter.open();
			Produto produto = produtoDbAdapter.buscar(extras.getLong(produtoDbAdapter.PRODUTO_CHAVE_ID));
			((EditText) findViewById(R.id.edtNome)).setText(produto.getNome());
			((EditText) findViewById(R.id.edtNumero)).setText(produto.getNumero());
			((EditText) findViewById(R.id.edtPreco)).setText(produto.getPreco().toString());
	    	produtoDbAdapter.close();
		}
	}

}