package br.com.appestoque.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;
import br.com.appestoque.R;

public class IniciarAtividade extends BaseAtividade {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.iniciar_atividade);
	}
	
	public void onProdutoClick(View v) {
		startActivity(new Intent(this, ProdutoActivity.class));
	}
	
	public void onUsuarioClick(View v) {
		String deviceId = Settings.System.getString(getContentResolver(),Settings.System.ANDROID_ID);
		Toast.makeText(IniciarAtividade.this,deviceId,Toast.LENGTH_LONG).show();
//		startActivity(new Intent(this,UsuarioActivity.class));
	}
	
}
