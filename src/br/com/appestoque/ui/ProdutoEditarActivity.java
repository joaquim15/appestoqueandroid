package br.com.appestoque.ui;

import br.com.appestoque.R;
import br.com.appestoque.dominio.suprimento.Produto;
import br.com.appestoque.dao.suprimento.ProdutoDAO;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ProdutoEditarActivity extends Activity {

	private ProdutoDAO produtoDAO;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.produto_editar_activity);
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
	    	produtoDAO = new ProdutoDAO(this);
			Produto produto = produtoDAO.pesquisar(extras.getLong(ProdutoDAO.PRODUTO_CHAVE_ID));
			((TextView) findViewById(R.id.edtNome)).setText(produto.getNome());
			((TextView) findViewById(R.id.edtNumero)).setText(produto.getNumero());
			((TextView) findViewById(R.id.edtPreco)).setText(produto.getPreco().toString());
			((TextView) findViewById(R.id.edtEstoque)).setText(produto.getEstoque().toString());
		}
	}
	
    @Override
    protected void onPause(){
    	super.onPause();
    	setResult(RESULT_CANCELED);
    	produtoDAO.fechar();
    	finish();
    }
    
}