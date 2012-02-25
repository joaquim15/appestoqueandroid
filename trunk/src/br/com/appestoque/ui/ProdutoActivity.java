package br.com.appestoque.ui;

import org.json.JSONArray;
import br.com.appestoque.Constantes;
import br.com.appestoque.HttpCliente;
import br.com.appestoque.R;
import br.com.appestoque.Util;
import br.com.appestoque.dao.suprimento.ProdutoDAO;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ProdutoActivity extends BaseListaAtividade{
	
	private ProdutoDAO produtoDAO;
	private ProgressDialog progressDialog;
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);	
			progressDialog.dismiss();
		}
	};

	private class ProdutosAdapter extends CursorAdapter {

		public ProdutosAdapter(Context context, Cursor cursor) {
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
	
    public void onListItemClick(ListView l , View v, int posicao, long id){
    	Intent intent = new Intent(this, ProdutoEditarActivity.class);
    	intent.putExtra(ProdutoDAO.PRODUTO_CHAVE_ID, id);
    	startActivity(intent);
    }
    
    public void onAtualizarClick(View v) {
    	
		produtoDAO = new ProdutoDAO(this);

		Context context = getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		if (connectivity != null) {
			NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				
				progressDialog = ProgressDialog.show(this, "", getString(R.string.mensagem_1) , true);
				
				new Thread() {
					public void run() {
						Looper.prepare();
						String os = Util.serial(ProdutoActivity.this);
						os = "9774d56d682e549c";
						//os = "6d682e549c";
						try {
							JSONArray objetos = HttpCliente.ReceiveHttpPost(Constantes.RESTFULL_PRODUTO + "?os=" + os,ProdutoActivity.this);
							if (objetos != null) {
								produtoDAO.limpar();
								Long id;
								String nome = null;
								String numero = null;
								Double preco = null;
								for (int i = 0; i <= objetos.length() - 1; ++i) {
									id = objetos.getJSONObject(i).getLong(ProdutoDAO.PRODUTO_CHAVE_ID);
									nome = objetos.getJSONObject(i).getString(ProdutoDAO.PRODUTO_CHAVE_NOME);
									numero = objetos.getJSONObject(i).getString(ProdutoDAO.PRODUTO_CHAVE_NUMERO);
									preco = objetos.getJSONObject(i).getDouble(ProdutoDAO.PRODUTO_CHAVE_PRECO);
									produtoDAO.criar(id, nome, numero, preco);
								}
							}						
						} catch (Exception e) {
						}
						handler.sendEmptyMessage(0);
					}
				}.start();
				
			} else {
				Util.dialogo(ProdutoActivity.this,getString(R.string.mensagem_2));
			}
		} else {
			Util.dialogo(ProdutoActivity.this, getString(R.string.mensagem_3));
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

//@Override
//public void onCreateContextMenu(ContextMenu menu, View v,
//		ContextMenuInfo menuInfo) {
//	super.onCreateContextMenu(menu, v, menuInfo);
//	MenuInflater inflater = getMenuInflater();
//	inflater.inflate(R.menu.produto_menu, menu);
//}

//@Override
//public boolean onContextItemSelected(MenuItem item) {
//	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
//			.getMenuInfo();
//	Intent intent = null;
//	switch (item.getItemId()) {
//	case R.id.item_menu_texto:
//		intent = new Intent(this, ProdutoEditarActivity.class);
//		intent.putExtra(ProdutoDAO.PRODUTO_CHAVE_ID, info.id);
//		startActivity(intent);
//		return true;
//	case R.id.item_menu_imagem:
//		intent = new Intent(this, ProdutoImagemActivity.class);
//		intent.putExtra(ProdutoDAO.PRODUTO_CHAVE_ID, info.id);
//		startActivity(intent);
//		return true;
//	default:
//		return super.onContextItemSelected(item);
//	}
//}