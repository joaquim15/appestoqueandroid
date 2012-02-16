package br.com.appestoque.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import br.com.appestoque.HttpCliente;
import br.com.appestoque.R;
import br.com.appestoque.Util;
import br.com.appestoque.dao.ProdutoDAO;

public class IniciarAtividade extends BaseAtividade {

	//private static final String URL = "http://appestoque.appspot.com/rest/produtoRest";
	private static final String URL = "http://10.0.2.2:8888/rest/produtoRest";
	
	private ProgressDialog progressDialog;
	
	private ProdutoDAO produtoDAO;
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			progressDialog.dismiss();
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.iniciar_atividade);
	}
	
	public void onClienteClick(View v) {

		JSONObject pedido = new JSONObject();
		try {
			
			pedido.put("numero", "15000");
			pedido.put("data", new Date("15/02/2012"));
			pedido.put("idRepresentante", 10L);
			pedido.put("idCliente", 20L);
			
			JSONArray itens = new JSONArray();
			
			JSONObject item1 = new JSONObject();
			item1.put("quantidade", 1.0);
			item1.put("valor", 9.77);
			item1.put("idPedido", 1L);
			item1.put("idProduto", 2L);
			itens.put(item1);
			
			JSONObject item2 = new JSONObject();
			item2.put("quantidade", 3.0);
			item2.put("valor", 3.98);
			item2.put("idPedido", 3L);
			item2.put("idProduto", 6L);
			pedido.put("itens",item2);
			itens.put(item2);
			
			pedido.put("itens", itens);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONObject jsonObjRecv = HttpCliente.SendHttpPost("http://10.0.2.2:8888/clienteRestFull", pedido);
		
	}
	
	public void onProdutoClick(View v) {
		startActivity(new Intent(this, ProdutoActivity.class));
	}
	
	public void onExportarClick(View v) {
		CharSequence chamada =  "Notificação";
		CharSequence titulo = "Serial";
		CharSequence mensagem = Util.serial(this);
		Util.notificar(this, chamada, titulo, mensagem, IniciarAtividade.class);
	}
	
	public void onAtualizarClick(View v) {

		produtoDAO = new ProdutoDAO(this);

		Context context = getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivity != null) {
			NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {

				progressDialog = ProgressDialog.show(this, "", "Sincronizando. Aguarde...", true);

				new Thread() {

					public void run() {

						Looper.prepare();

						try {
							try {

								HttpClient httpclient = new DefaultHttpClient();
								String serial = Util.serial(IniciarAtividade.this);
								//serial = "9774d56d682e549c";
								HttpGet httpGet = new HttpGet(URL+"?serial="+serial);
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
								Double estoque = null;
								produtoDAO.limpar();
								for (int i = 0; i <= objetos.length() - 1; ++i) {
									id = objetos.getJSONObject(i).getLong(ProdutoDAO.PRODUTO_CHAVE_ID);
									nome = objetos.getJSONObject(i).getString(ProdutoDAO.PRODUTO_CHAVE_NOME);
									numero = objetos.getJSONObject(i).getString(ProdutoDAO.PRODUTO_CHAVE_NUMERO);
									preco = objetos.getJSONObject(i).getDouble(ProdutoDAO.PRODUTO_CHAVE_PRECO);
									estoque = objetos.getJSONObject(i).getDouble(ProdutoDAO.PRODUTO_CHAVE_ESTOQUE);
									produtoDAO.criar(id, nome, numero, preco, estoque);
								}
								Util.dialogo(IniciarAtividade.this,
										getString(R.string.mensagem_sincronismo_conclusao));

							} catch (ClientProtocolException e) {
								Log.e(this.toString(), e.getMessage());
								Util.dialogo(IniciarAtividade.this,	
										getString(R.string.mensagem_clientProtocolException));
							} catch (IOException e) {
								Log.e(this.toString(), e.getMessage());
								Util.dialogo(IniciarAtividade.this,
										getString(R.string.mensagem_ioexception));
							} catch (JSONException e) {
								Log.e(this.toString(), e.getMessage());
								Util.dialogo(IniciarAtividade.this,
										getString(R.string.mensagem_jsonexception));
							}

						} catch (Throwable e) {
							Log.e(this.toString(), e.getMessage());
						}

						handler.sendEmptyMessage(0);

					}

				}.start();

			} else {
				Util.dialogo(IniciarAtividade.this,"Informação de rede inexistente.");
			}
		} else {
			Util.dialogo(IniciarAtividade.this, "Conectividade inexistente");
		}
		
		produtoDAO.fechar();

	}
	
    @Override
    protected void onDestroy(){
    	super.onDestroy();
    	if(produtoDAO!=null){
    		produtoDAO.fechar();
    	}
    }
	
}