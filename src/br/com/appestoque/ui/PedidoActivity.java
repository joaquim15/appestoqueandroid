package br.com.appestoque.ui;

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
import br.com.appestoque.dao.faturamento.PedidoDAO;

public class PedidoActivity extends BaseListaAtividade{

	private PedidoDAO pedidoDAO;
	
	private class PedidoAdapter extends CursorAdapter {

		public PedidoAdapter(Context context, Cursor cursor) {
			super(context, cursor);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			final TextView data = (TextView) view.findViewById(R.id.data);
            final TextView cliente = (TextView) view.findViewById(R.id.cliente);            
            data.setText(cursor.getString(2));
            ClienteDAO clienteDAO = new ClienteDAO(getApplicationContext());
            cliente.setText(clienteDAO.pesquisar(cursor.getLong(4)).getNome());
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return getLayoutInflater().inflate(R.layout.pedido_activity_lista, parent, false);
		}
		
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();		
		setContentView(R.layout.pedido_activity);
		pedidoDAO = new PedidoDAO(this);
		Cursor cursor = null;
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	        //String query = intent.getStringExtra(SearchManager.QUERY);
	        //cursor = pedidoDAO.pesquisar(query);
	    }else{
	    	cursor = pedidoDAO.listar();
	    }
	    startManagingCursor(cursor);	    
		setListAdapter(new PedidoAdapter(this,cursor));
		registerForContextMenu(getListView());
	}
	
	
}