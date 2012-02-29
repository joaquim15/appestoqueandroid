package br.com.appestoque.ui;

import br.com.appestoque.R;
import br.com.appestoque.Util;
import br.com.appestoque.dao.cadastro.ClienteDAO;
import br.com.appestoque.dominio.cadastro.Cliente;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class PedidoEditarActivity extends Activity {

	private ClienteDAO clienteDAO;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pedido_editar_activity);
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
	    	clienteDAO = new ClienteDAO(this);
			Cliente cliente = clienteDAO.pesquisar(extras.getLong(ClienteDAO.CLIENTE_CHAVE_ID));
			((TextView) findViewById(R.id.edtCliente)).setText(cliente.getNome());
			((TextView) findViewById(R.id.edtId)).setText(cliente.getId().toString());
			
		}
	}
	
	public void onSalvarClick(View view) {
		final EditText id = (EditText) findViewById(R.id.edtId);
		final EditText numero = (EditText) findViewById(R.id.edtNumero);
		final DatePicker data = (DatePicker) findViewById(R.id.dtpData);
		final EditText obs = (EditText) findViewById(R.id.edtObs);
		
		Util.dialogo(this, id.getText().toString()) ;
		Util.dialogo(this, numero.getText().toString()) ;
		Util.dialogo(this, Integer.toString(data.getDayOfMonth()) );
		Util.dialogo(this, Integer.toString(data.getMonth()+1) );
		Util.dialogo(this, Integer.toString(data.getYear()) );
		Util.dialogo(this, obs.getText().toString() ) ;
		
	}
	
	public void onCancelarClick(View v) {
		setResult(RESULT_CANCELED);
		this.finish();
	}
	
}