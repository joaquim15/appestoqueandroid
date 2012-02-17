package br.com.appestoque.ui;

import br.com.appestoque.R;
import br.com.appestoque.Util;
import br.com.appestoque.dao.suprimento.ProdutoDAO;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ProdutoActivity extends BaseListaAtividade{
	
	private ProdutoDAO produtoDAO;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();		
		setContentView(R.layout.produto_activity);
		produtoDAO = new ProdutoDAO(this);
		Cursor cursor = null;
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	        String query = intent.getStringExtra(SearchManager.QUERY);
	        cursor = produtoDAO.pesquisar(query);
	    }else{
	    	cursor = produtoDAO.listar();
	    }
	    startManagingCursor(cursor);	    
		setListAdapter(new ProdutosAdapter(this,cursor));
		registerForContextMenu(getListView());
	}

	private class ProdutosAdapter extends CursorAdapter {

		public ProdutosAdapter(Context context, Cursor cursor) {
			super(context, cursor);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			final TextView numero = (TextView) view.findViewById(R.id.numero);
            final TextView nome = (TextView) view.findViewById(R.id.nome);
            final ImageView icone = (ImageView) view.findViewById(R.id.icone);
            numero.setText(cursor.getString(2));
            nome.setText(cursor.getString(1));
            String imagem = Util.armazenamentoExterno() + cursor.getString(0) + "_1.png";
            if(Util.arquivoExiste(imagem)){
            	Bitmap bitmap = BitmapFactory.decodeFile(imagem);
    			icone.setImageBitmap(bitmap);
            }else{
            	icone.setImageBitmap(null);
            }
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return getLayoutInflater().inflate(R.layout.produto_activity_lista, parent, false);
		}
		
	}
	
    public void onListItemClick(ListView l , View v, int posicao, long id){
//    	Intent intent = new Intent(this, ProdutoEditarActivity.class);
//    	intent.putExtra(ProdutoDAO.PRODUTO_CHAVE_ID, id);
//    	startActivity(intent);
    }
    
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.produto_menu, menu);
	}
    
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.item_menu_texto:
			intent = new Intent(this, ProdutoEditarActivity.class);
			intent.putExtra(ProdutoDAO.PRODUTO_CHAVE_ID, info.id);
			startActivity(intent);
			return true;
		case R.id.item_menu_imagem:
			intent = new Intent(this, ProdutoImagemActivity.class);
			intent.putExtra(ProdutoDAO.PRODUTO_CHAVE_ID, info.id);
			startActivity(intent);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
    
    public void onIniciarClick(Context context) {
        final Intent intent = new Intent(context,IniciarAtividade.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public void onBuscarClick(Activity activity) {
        activity.startSearch(null, false, Bundle.EMPTY, false);
    }
    
    @Override
    protected void onDestroy(){
    	super.onDestroy();
    	produtoDAO.fechar();
    }
	
}