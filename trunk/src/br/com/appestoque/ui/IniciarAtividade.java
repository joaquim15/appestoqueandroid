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

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import br.com.appestoque.R;
import br.com.appestoque.Util;
import br.com.appestoque.provider.ProdutoDbAdapter;

public class IniciarAtividade extends BaseAtividade {

	private static final String URL = "http://appestoque.appspot.com/rest/produtoRest";
	//private static final String URL = "http://10.0.2.2:8888/rest/produtoRest";
	
	private ProgressDialog progressDialog;
	
	private ProdutoDbAdapter produtoDbAdapter;
	
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

		produtoDbAdapter = new ProdutoDbAdapter(this);

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
								//serial = "200141a5bb0c7345";
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
								String imagem = null;
								produtoDbAdapter.open();
								produtoDbAdapter.limpar();
								for (int i = 0; i <= objetos.length() - 1; ++i) {
									id = objetos.getJSONObject(i).getLong(ProdutoDbAdapter.PRODUTO_CHAVE_ID);
									nome = objetos.getJSONObject(i).getString(ProdutoDbAdapter.PRODUTO_CHAVE_NOME);
									numero = objetos.getJSONObject(i).getString(ProdutoDbAdapter.PRODUTO_CHAVE_NUMERO);
									preco = objetos.getJSONObject(i).getDouble(ProdutoDbAdapter.PRODUTO_CHAVE_PRECO);
									estoque = objetos.getJSONObject(i).getDouble(ProdutoDbAdapter.PRODUTO_CHAVE_ESTOQUE);
									imagem = objetos.getJSONObject(i).getString(ProdutoDbAdapter.PRODUTO_CHAVE_IMAGEM);
									produtoDbAdapter.criar(id, nome, numero, preco);
									
								}
								produtoDbAdapter.close();
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

	}
	
	protected void criarNotificacao(Context context, CharSequence mensagemBarraStatus, CharSequence titulo,CharSequence mensagem, Class<?> activity) {

		// Recupera o serviço do NotificationManager
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		Notification n = new Notification(R.drawable.icon, mensagemBarraStatus, System.currentTimeMillis());

		// PendingIntent para executar a Activity se o usuário selecionar a
		// notificação
		PendingIntent p = PendingIntent.getActivity(this, 0, new Intent(this, activity), 0);

		// Informações
		n.setLatestEventInfo(this, titulo, mensagem, p);

		// Precisa de permissão: <uses-permission
		// android:name="android.permission.VIBRATE" />
		// espera 100ms e vibra por 250ms, depois espera por 100 ms e vibra por
		// 500ms.
		n.vibrate = new long[] { 100, 250, 100, 500 };

		// id (numero único) que identifica esta notificação
		nm.notify(0, n);
	}

	
}
