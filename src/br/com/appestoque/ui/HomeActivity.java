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
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends Activity {
	
	//private static final String URL = "http://10.0.2.2:8888/rest/UsuarioRest?email=andre.tricano@gmail.com&senha=1234";
	private static final String URL = "http://appestoque.appspot.com/rest/UsuarioRest?email=andre.tricano@gmail.com&senha=1234";

	@SuppressWarnings("unused")
	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	}

	public void onAtualizarClick(View v) {		
		Context context = getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connectivity!=null){
			NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
			if(networkInfo!=null&&networkInfo.isConnected()){
				
				//ProdutoDbAdapter produtoDbAdapter = new ProdutoDbAdapter(this);
				//produtoDbAdapter.open();
				
				HttpClient httpclient = new DefaultHttpClient();
				try {
					HttpGet httpGet = new HttpGet(URL);
			        HttpResponse httpResponse = httpclient.execute(httpGet);
					HttpEntity httpEntity = httpResponse.getEntity();
					InputStream inputStream = httpEntity.getContent();
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
					String data = bufferedReader.readLine();			
					JSONArray jsonArray = new JSONArray(data);
				} catch (ClientProtocolException e) {
					Log.e(this.toString(),e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}else{
				Toast.makeText(HomeActivity.this, "Informação de rede inexistente.", Toast.LENGTH_LONG).show();
			}
		}else{
			Toast.makeText(HomeActivity.this, "Conectividade inexistente", Toast.LENGTH_LONG).show();
		}
	}

	public void onProdutoClick(View v) {
		startActivity(new Intent(this, ProdutoActiviry.class));
	}

	public void onEstoqueClick(View v) {
		// Toast.makeText(HomeActivity.this,"Estoque",Toast.LENGTH_LONG).show();
		progressDialog = ProgressDialog.show(this, "Exemplo",
				"Buscando imagem, aguarde...", true, true);
	}

	public void onUsuarioClick(View v) {
		// Toast.makeText(HomeActivity.this,"Usuário",Toast.LENGTH_LONG).show();
		progressDialog = ProgressDialog.show(this, "",
				"Loading. Please wait...", true);
	}
	
}