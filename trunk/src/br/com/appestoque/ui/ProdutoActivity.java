package br.com.appestoque.ui;

import br.com.appestoque.R;
import br.com.appestoque.provider.ProdutoDbAdapter;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ProdutoActivity extends BaseListaAtividade{
	
	private CursorAdapter mAdapter;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		Intent intent = getIntent();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.produto_activity);
		ProdutoDbAdapter produtoDbAdapter = new ProdutoDbAdapter(this);
		produtoDbAdapter.open();
		Cursor cursor = null;
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	        String query = intent.getStringExtra(SearchManager.QUERY);
	        cursor = produtoDbAdapter.buscar(query);
	    }else{
	    	cursor = produtoDbAdapter.listar();
	    }
	    startManagingCursor(cursor); 
		mAdapter = new ProdutosAdapter(this,cursor);
		setListAdapter(mAdapter);
	}
	
	private class ProdutosAdapter extends CursorAdapter {

		public ProdutosAdapter(Context context, Cursor cursor) {
			super(context, cursor);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			final TextView titleView = (TextView) view.findViewById(R.id.session_title);
            final TextView subtitleView = (TextView) view.findViewById(R.id.session_subtitle);
            titleView.setText(cursor.getString(2));
            subtitleView.setText(cursor.getString(1));            
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return getLayoutInflater().inflate(R.layout.produto_activity_lista, parent, false);
		}
		
	}
	
    public void onListItemClick(ListView l , View v, int posicao, long id){
    	Intent intent = new Intent(this, ProdutoEditarActivity.class);
    	intent.putExtra(ProdutoDbAdapter.PRODUTO_CHAVE_ID, id);
    	startActivity(intent);
    }
    
    public void onIniciarClick(Context context) {
        final Intent intent = new Intent(context,IniciarAtividade.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public void onBuscarClick(Activity activity) {
        activity.startSearch(null, false, Bundle.EMPTY, false);
    }
	
}