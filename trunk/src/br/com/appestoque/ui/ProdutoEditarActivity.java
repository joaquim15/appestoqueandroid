package br.com.appestoque.ui;

import br.com.appestoque.R;
import br.com.appestoque.dominio.Produto;
import br.com.appestoque.dao.ProdutoDAO;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProdutoEditarActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.produto_editar_activity);
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
	    	ProdutoDAO produtoDAO = new ProdutoDAO(this);
			Produto produto = produtoDAO.buscar(extras.getLong(ProdutoDAO.PRODUTO_CHAVE_ID));
			((TextView) findViewById(R.id.edtNome)).setText(produto.getNome());
			((TextView) findViewById(R.id.edtNumero)).setText(produto.getNumero());
			((TextView) findViewById(R.id.edtPreco)).setText(produto.getPreco().toString());
		}
	}
	
    public void onHomeClick(View v) {
    	finish();
    }

}
