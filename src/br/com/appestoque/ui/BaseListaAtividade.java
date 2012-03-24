package br.com.appestoque.ui;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;

public class BaseListaAtividade extends ListActivity{
	
	public void onIniciarClick() {
		finish();
	}

    public void onBuscarClick(Activity activity) {
        activity.startSearch(null, false, Bundle.EMPTY, false);
    }
	
}
