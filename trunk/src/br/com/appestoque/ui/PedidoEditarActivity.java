package br.com.appestoque.ui;

import android.R;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

public class PedidoEditarActivity extends TabActivity {

//	private ClienteDAO clienteDAO;
//	private PedidoDAO pedidoDAO;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this,ProdutoActivity.class);

	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("Produtos")
	    		.setIndicator("Produtos",res.getDrawable(R.drawable.arrow_down_float))
                .setContent(intent);
	    tabHost.addTab(spec);

	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, ClienteActivity.class);
	    spec = tabHost.newTabSpec("Clientes")
	    		.setIndicator("Clientes",res.getDrawable(R.drawable.arrow_down_float))
	    		.setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(2);
	    
	}

	public void onSalvarClick(View view) {
//		final EditText id = (EditText) findViewById(R.id.edtId);
//		final EditText numero = (EditText) findViewById(R.id.edtNumero);
//		final DatePicker data = (DatePicker) findViewById(R.id.dtpData);
//		final EditText obs = (EditText) findViewById(R.id.edtObs);
//		pedidoDAO = new PedidoDAO(this);
//		pedidoDAO.criar(numero.getText().toString(), Util.dateMillisegundos(data.getYear(),data.getMonth(),data.getDayOfMonth()), obs.getText().toString(), new Long(id.getText().toString()) );
//		setResult(RESULT_OK);
//		this.finish();
	}
	
	public void onCancelarClick(View v) {
//		setResult(RESULT_CANCELED);
//		this.finish();
	}
	
}