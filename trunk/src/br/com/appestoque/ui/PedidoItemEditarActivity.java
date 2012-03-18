package br.com.appestoque.ui;

import br.com.appestoque.R;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class PedidoItemEditarActivity extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.pedido_item_editar_activity);
		
		Resources res = getResources(); 
	    TabHost tabHost = getTabHost(); 
	    TabHost.TabSpec spec;
	    Intent intent;

	    intent = new Intent().setClass(this,PedidoEditarActivity.class);
	    spec = tabHost.newTabSpec(getString(R.string.titulo_pedido))
	    		.setIndicator("Pedido",res.getDrawable(R.drawable.ic_pedido))
                .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, ClienteActivity.class);
	    spec = tabHost.newTabSpec(getString(R.string.titulo_item))
	    		.setIndicator("Item",res.getDrawable(R.drawable.ic_item))
	    		.setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(2);
	    
	}

}