package br.com.appestoque.ui;

import java.io.File;

import br.com.appestoque.R;
import br.com.appestoque.Util;
import br.com.appestoque.dominio.Produto;
import br.com.appestoque.dao.ProdutoDAO;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
			Produto produto = produtoDAO.buscar(extras.getLong(ProdutoDAO.PRODUTO_CHAVE_ID));
			/*
			((TextView) findViewById(R.id.edtNome)).setText(produto.getNome());
			((TextView) findViewById(R.id.edtNumero)).setText(produto.getNumero());
			((TextView) findViewById(R.id.edtPreco)).setText(produto.getPreco().toString());
			*/
			String imagem = Util.armazenamentoExterno() + produto.getId().toString() + ".jpg";			
			File file = new  File(imagem);
			if(file.exists()){
				Bitmap bitmap = BitmapFactory.decodeFile(imagem);
				((ImageView) findViewById(R.id.imgProduto)).setImageBitmap(bitmap);
			}
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
