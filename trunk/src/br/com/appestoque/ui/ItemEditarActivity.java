package br.com.appestoque.ui;

import br.com.appestoque.R;
import br.com.appestoque.dominio.faturamento.Item;
import br.com.appestoque.dominio.suprimento.Produto;
import br.com.appestoque.dao.faturamento.ItemDAO;
import br.com.appestoque.dao.suprimento.ProdutoDAO;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ItemEditarActivity extends Activity {

	private ItemDAO itemDAO;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_editar_activity);
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
	    	itemDAO = new ItemDAO(this);
			Item item = itemDAO.pesquisar(extras.getLong(ItemDAO.ITEM_CHAVE_ID));
//			((TextView) findViewById(R.id.edtNome)).setText(produto.getNome());
//			((TextView) findViewById(R.id.edtNumero)).setText(produto.getNumero());
//			((TextView) findViewById(R.id.edtPreco)).setText(produto.getValor().toString());
		}
	}
	
    @Override
    protected void onPause(){
    	super.onPause();
    	setResult(RESULT_CANCELED);
    	itemDAO.fechar();
    	finish();
    }
    
}