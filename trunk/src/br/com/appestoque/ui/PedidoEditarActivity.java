package br.com.appestoque.ui;

import br.com.appestoque.R;
import br.com.appestoque.dao.cadastro.ClienteDAO;
import br.com.appestoque.dominio.cadastro.Cliente;
import android.app.Activity;
import android.os.Bundle;
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
		}
	}
	
}