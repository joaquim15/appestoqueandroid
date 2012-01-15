package br.com.appestoque.ui;

import br.com.appestoque.R;
import br.com.appestoque.Util;
import br.com.appestoque.dominio.Produto;
import br.com.appestoque.dao.ProdutoDAO;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProdutoEditarActivity extends Activity {

	private ProdutoDAO produtoDAO;
	String[] imagens;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.produto_editar_activity);
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
	    	produtoDAO = new ProdutoDAO(this);
			Produto produto = produtoDAO.buscar(extras.getLong(ProdutoDAO.PRODUTO_CHAVE_ID));
			((TextView) findViewById(R.id.edtNome)).setText(produto.getNome());
			((TextView) findViewById(R.id.edtNumero)).setText(produto.getNumero());
			((TextView) findViewById(R.id.edtPreco)).setText(produto.getPreco().toString());
			((TextView) findViewById(R.id.edtEstoque)).setText(produto.getEstoque().toString());
		    imagens = new String[]{	Util.armazenamentoExterno() + produto.getId().toString() + "_1.png",
									Util.armazenamentoExterno() + produto.getId().toString() + "_2.png",
									Util.armazenamentoExterno() + produto.getId().toString() + "_3.png",
									Util.armazenamentoExterno() + produto.getId().toString() + "_4.png"};
			
		}
	}
	
    @Override
    protected void onPause(){
    	super.onPause();
    	setResult(RESULT_CANCELED);
    	produtoDAO.fechar();
    	finish();
    }
    
    public void onClick(View v) {
    	Intent intent = new Intent(this,ProdutoImagemActivity.class);
    	intent.putExtra("imagens", imagens);
		startActivity(intent);
	}

}