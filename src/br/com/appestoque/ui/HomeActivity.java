package br.com.appestoque.ui;

import br.com.appestoque.R;
import br.com.appestoque.provider.ProdutoDbAdapter;
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