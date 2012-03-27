package br.com.appestoque.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BaseListaAtividade extends ListActivity{
	
	public void onIniciarClick(View view) {
		final Intent intent = new Intent(this, IniciarAtividade.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.startActivity(intent);
	}

    public void onBuscarClick(View view) {
        this.startSearch(null, false, Bundle.EMPTY, false);
    }
	
}