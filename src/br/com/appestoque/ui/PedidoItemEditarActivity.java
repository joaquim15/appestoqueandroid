package br.com.appestoque.ui;

import br.com.appestoque.R;
import br.com.appestoque.dao.faturamento.PedidoDAO;
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
		
		Bundle extras = getIntent().getExtras();
		
		Resources res = getResources(); 
	    TabHost tabHost = getTabHost(); 
	    TabHost.TabSpec spec;
	    Intent intent;

	    intent = new Intent().setClass(this,PedidoEditarActivity.class);
	    intent.putExtra(PedidoDAO.PEDIDO_CHAVE_ID, extras.getLong(PedidoDAO.PEDIDO_CHAVE_ID));
	    spec = tabHost.newTabSpec(getString(R.string.titulo_pedido))
	    		.setIndicator("Pedido",res.getDrawable(R.drawable.ic_pedido))
                .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, ItemActivity.class);
	    intent.putExtra(PedidoDAO.PEDIDO_CHAVE_ID, extras.getLong(PedidoDAO.PEDIDO_CHAVE_ID));
	    spec = tabHost.newTabSpec(getString(R.string.titulo_item))
	    		.setIndicator("Item",res.getDrawable(R.drawable.ic_item))
	    		.setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(2);
	    
	}
	
}