package br.com.appestoque.ui;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

public class BaseTabAtividade extends TabActivity {

	public void onRetornarClick(View view) {
		finish();
	}
	
	public static void onIniciarClick(Context context) {
        final Intent intent = new Intent(context, IniciarAtividade.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
	
}
