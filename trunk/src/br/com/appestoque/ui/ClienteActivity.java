package br.com.appestoque.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import br.com.appestoque.R;
import br.com.appestoque.dao.cadastro.ClienteDAO;

public class ClienteActivity extends BaseListaAtividade{
	
	private ClienteDAO clienteDAO;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();		
		setContentView(R.layout.produto_activity);
		clienteDAO = new ClienteDAO(this);
		Cursor cursor = null;
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	        String query = intent.getStringExtra(SearchManager.QUERY);
	        cursor = clienteDAO.pesquisar(query);
	    }else{
	    	cursor = clienteDAO.listar();
	    }
	    startManagingCursor(cursor);	    
		setListAdapter(new ProdutosAdapter(this,cursor));
		registerForContextMenu(getListView());
	}
	
	private class ClientesAdapter extends CursorAdapter {

		public ClientesAdapter(Context context, Cursor cursor) {
			super(context, cursor);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
            final TextView nome = (TextView) view.findViewById(R.id.nome);
            nome.setText(cursor.getString(1));
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return getLayoutInflater().inflate(R.layout.produto_activity_lista, parent, false);
		}
		
	}

}
