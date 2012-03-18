package br.com.appestoque.ui;

import br.com.appestoque.R;
import br.com.appestoque.dao.faturamento.ItemDAO;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class ItemActivity extends BaseListaAtividade{
	
	private ItemDAO itemDAO;
	
	private class ItensAdapter extends CursorAdapter {

		public ItensAdapter(Context context, Cursor cursor) {
			super(context, cursor);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			final TextView numero = (TextView) view.findViewById(R.id.numero);
            final TextView nome = (TextView) view.findViewById(R.id.nome);
            numero.setText(cursor.getString(2));
            nome.setText(cursor.getString(1));
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return getLayoutInflater().inflate(R.layout.produto_activity_lista, parent, false);
		}
		
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_activity);
		itemDAO = new ItemDAO(this);
		Cursor cursor = null;
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
	        cursor = itemDAO.listar(extras.getLong(ItemDAO.ITEM_CHAVE_ID));
	    }
	    startManagingCursor(cursor);	    
		setListAdapter(new ItensAdapter(this,cursor));
		registerForContextMenu(getListView());
	}
    
    @Override
    protected void onDestroy(){
    	super.onDestroy();
    	itemDAO.fechar();
    }
	
}