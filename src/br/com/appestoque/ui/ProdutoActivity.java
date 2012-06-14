package br.com.appestoque.ui;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.stream.JsonReader;

import br.com.appestoque.Constantes;
import br.com.appestoque.HttpCliente;
import br.com.appestoque.R;
import br.com.appestoque.Util;
import br.com.appestoque.dao.suprimento.ProdutoDAO;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class ProdutoActivity extends BaseListaAtividade implements Runnable{
	
	private ProdutoDAO produtoDAO;
	private ProgressDialog progressDialog;
	private List <NameValuePair> parametros;
	
	public void run() {
		Looper.prepare();
		Message message = new Message();
		SharedPreferences preferencias = getSharedPreferences(Constantes.PREFERENCIAS, 0);
		String uuid = preferencias.getString("UUID", UUID.randomUUID().toString());
		String url = Constantes.SERVIDOR + Constantes.RESTFUL_PRODUTO;
		parametros = new ArrayList<NameValuePair>();
		parametros.add(new BasicNameValuePair("uuid", uuid));
		//parametros.add(new BasicNameValuePair("sincronismo","true"));
		InputStream inputStream = HttpCliente.recebeDados(url, parametros, ProdutoActivity.this);
		if (inputStream != null) {
			try{
				JsonReader reader = new JsonReader(new InputStreamReader(inputStream,"UTF-8"));
				Long id = null;
				String nome = null;
				String numero = null;
				Double preco = null;
				try {
					produtoDAO.abrir();
					produtoDAO.limpar();
					reader.beginArray();
					while (reader.hasNext()) {
						reader.beginObject();
						while (reader.hasNext()) {
							String name = reader.nextName();
							if (name.equals("_id")) {
								id = reader.nextLong();
							} else if (name.equals("nome")) {
								nome = reader.nextString();
							} else if (name.equals("numero")) {
								numero = reader.nextString();
							} else if (name.equals("preco")) {
								preco = reader.nextDouble();
							} else {
								reader.skipValue();
							}
						}
						reader.endObject();
						produtoDAO.criar(id, nome, numero, preco);
					}
					reader.endArray();
					produtoDAO.fechar();
				} finally {
					reader.close();
				}
				message.what = Constantes.SUCESSO;
			} catch (ClientProtocolException e) {
				message.what = Constantes.FALHA;
				Bundle bundle = new Bundle();
				bundle.putString("mensagem", e.getMessage());
				message.setData(bundle);
			} catch (IOException e) {
				message.what = Constantes.FALHA;
				Bundle bundle = new Bundle();
				bundle.putString("mensagem", e.getMessage());
				message.setData(bundle);
			}
			
		}
		handler.sendMessage(message);
		Looper.loop();
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			progressDialog.dismiss();
			switch (msg.what) {
				case Constantes.SUCESSO:
					Util.dialogo(ProdutoActivity.this,getString(R.string.mensagem_sincronismo_conclusao));
					break;
				case Constantes.FALHA:
					Util.dialogo(ProdutoActivity.this, msg.getData().getString("mensagem"));
					break;	
				default:
					break;
			}
			
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
	protected void onResume(){
		Intent intent = getIntent();		
		setContentView(R.layout.produto_activity);
		if(produtoDAO==null){
			produtoDAO = new ProdutoDAO(this);
		}	
		produtoDAO.abrir();
		Cursor cursor = null;
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	        String query = intent.getStringExtra(SearchManager.QUERY);
	        cursor = produtoDAO.pesquisar(query);
	    }else{
	    	cursor = produtoDAO.listar();
	    }
		setListAdapter(new ProdutosAdapter(this,cursor));
		registerForContextMenu(getListView());
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		produtoDAO.fechar();
		super.onPause();
	}
	
    public void onAtualizarClick(View v) {
		produtoDAO = new ProdutoDAO(this);
		Context context = getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				progressDialog = ProgressDialog.show(this, "",getString(R.string.mensagem_1), true);
				Thread thread = new Thread(this);
				thread.start();
			} else {
				Util.dialogo(ProdutoActivity.this,getString(R.string.mensagem_2));
			}
		} else {
			Util.dialogo(ProdutoActivity.this, getString(R.string.mensagem_3));
		}	
    	
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
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		Intent intent = null;
		switch (item.getItemId()) {
			case R.id.item_menu_sincronizar:
				Toast.makeText(getApplicationContext(), "Sincronizar", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.item_menu_visualizar:
				intent = new Intent(this,ProdutoEditarActivity.class);
		    	intent.putExtra(ProdutoDAO.PRODUTO_CHAVE_ID, info.id);
		    	startActivity(intent);				
				return true;
			default:
				return super.onContextItemSelected(item);
		}
	}
	
	public void onListItemClick(ListView listView, View view, int position, long itemId){
		super.onListItemClick(listView, view, position, itemId);
		Intent intent = new Intent(this, ProdutoEditarActivity.class);
    	intent.putExtra(ProdutoDAO.PRODUTO_CHAVE_ID, itemId);
    	startActivity(intent);
	}
	
}