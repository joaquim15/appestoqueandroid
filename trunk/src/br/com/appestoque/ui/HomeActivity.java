package br.com.appestoque.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import br.com.appestoque.R;
import br.com.appestoque.Util;
import br.com.appestoque.provider.DatabaseHelper;
import br.com.appestoque.provider.ProdutoDbAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

public class HomeActivity extends Activity {

	ProdutoDbAdapter produtoDbAdapter;
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			progressDialog.dismiss();
		}
	};
	
	private static final String URL = "http://appestoque.appspot.com/rest/produtoRest?email=appestoque@gmail.com&senha=7872427@perfil";
	//private static final String URL = "http://10.0.2.2:8888/rest/produtoRest?email=andre.tricano@gmail.com&senha=1234";

	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	}
	
	public void onAtualizarClick(View v) {
		
//		DatabaseHelper databaseHelper = new DatabaseHelper(this);
//		databaseHelper.onUpgrade(databaseHelper.getWritableDatabase(), 0, 0);
		produtoDbAdapter = new ProdutoDbAdapter(this);
	
		Context context = getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		if (connectivity != null) {
			NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				
				progressDialog = ProgressDialog.show(this, "","Sincronizando. Aguarde...", true);
		
		new Thread() {

			public void run() {
				
				Looper.prepare();
				
				try {
					try{
						
						
								HttpClient httpclient = new DefaultHttpClient();
								HttpGet httpGet = new HttpGet(URL);
								HttpResponse httpResponse = httpclient.execute(httpGet);
								HttpEntity httpEntity = httpResponse.getEntity();
								InputStream inputStream = httpEntity.getContent();
								BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
								String data = bufferedReader.readLine();
								JSONArray objetos = new JSONArray(data);
								Long id;
								String nome = null;
								String numero = null;
								Double preco = null;
								produtoDbAdapter.open();
								produtoDbAdapter.limpar();
								for (int i = 0; i <= objetos.length() - 1; ++i) {
									id = objetos.getJSONObject(i).getLong(ProdutoDbAdapter.PRODUTO_CHAVE_ID);
									nome = objetos.getJSONObject(i).getString(ProdutoDbAdapter.PRODUTO_CHAVE_NOME);
									numero = objetos.getJSONObject(i).getString(ProdutoDbAdapter.PRODUTO_CHAVE_NUMERO);
									preco = objetos.getJSONObject(i).getDouble(ProdutoDbAdapter.PRODUTO_CHAVE_PRECO);
									produtoDbAdapter.criar(id, nome, numero, preco);
								}
								produtoDbAdapter.close();
								Util.dialogo(HomeActivity.this,getString(R.string.mensagem_sincronismo_conclusao));
			
												
					} catch (ClientProtocolException e) {
						Log.e(this.toString(), e.getMessage());
						Util.dialogo(HomeActivity.this,getString(R.string.mensagem_clientProtocolException));
					} catch (IOException e) {
						Log.e(this.toString(), e.getMessage());
						Util.dialogo(HomeActivity.this,getString(R.string.mensagem_ioexception));
					} catch (JSONException e) {
						Log.e(this.toString(), e.getMessage());
						Util.dialogo(HomeActivity.this,getString(R.string.mensagem_jsonexception));					
					}
				
				}catch (Throwable e) { 
					Log.e(this.toString(), e.getMessage());
				}
				
				handler.sendEmptyMessage(0);
				
			}

		}.start();
		
			} else {
				Util.dialogo(HomeActivity.this,"Informação de rede inexistente.");
			}
		} else {
			Util.dialogo(HomeActivity.this, "Conectividade inexistente");
		}	
		
	}

	public void onProdutoClick(View v) {
		startActivity(new Intent(this, ProdutoActiviry.class));
	}

	public void onEstoqueClick(View v) {
		startActivity(new Intent(this, ProdutoActivity.class));
		// Toast.makeText(HomeActivity.this,"Estoque",Toast.LENGTH_LONG).show();
//		progressDialog = ProgressDialog.show(this, "Sincronismo",
//				"Processando. Aguarde...", true, true);
	}

	public void onUsuarioClick(View v) {
		startActivity(new Intent(this,UsuarioEditarActivity.class));
	}

}