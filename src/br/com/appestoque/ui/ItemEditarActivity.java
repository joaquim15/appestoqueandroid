package br.com.appestoque.ui;

import java.util.List;

import br.com.appestoque.R;
import br.com.appestoque.dominio.faturamento.Item;
import br.com.appestoque.dominio.suprimento.Produto;
import br.com.appestoque.dao.faturamento.ItemDAO;
import br.com.appestoque.dao.suprimento.ProdutoDAO;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class ItemEditarActivity extends Activity {

	private ItemDAO itemDAO;
	private ProdutoDAO produtoDAO;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_editar_activity);
		Bundle extras = getIntent().getExtras();
		itemDAO = new ItemDAO(this);
		produtoDAO = new ProdutoDAO(this);
		
		List<Produto> lista = produtoDAO.produtos();
		String[] produtos = new String[lista.size()];
		
		for(int i = 0; i<lista.size();++i){
			produtos[i] = lista.get(i).getNumero();
		}
		
		AutoCompleteTextView txtProduto = (AutoCompleteTextView) findViewById(R.id.edtProduto);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.produto_listar, produtos);
	    txtProduto.setAdapter(adapter);
		
		if(extras!=null){	    	
			Item item = itemDAO.pesquisar(extras.getLong(ItemDAO.ITEM_CHAVE_ID));
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