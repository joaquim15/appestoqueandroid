package br.com.appestoque.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;

public class BaseListaAtividade extends ListActivity{
	
    public void onIniciarClick(View v) {
    	finish();
    }

    public void onBuscarClick(View v) {
    	startSearch(null, false, Bundle.EMPTY, false);
    }

}
