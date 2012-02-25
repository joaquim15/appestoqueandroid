package br.com.appestoque.ui;

import br.com.appestoque.R;
import br.com.appestoque.dominio.cadastro.Cliente;
import br.com.appestoque.dao.cadastro.ClienteDAO;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ClienteEditarActivity extends Activity {

	private ClienteDAO clienteDAO;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.produto_editar_activity);
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
	    	clienteDAO = new ClienteDAO(this);
			Cliente cliente = clienteDAO.pesquisar(extras.getLong(ClienteDAO.CLIENTE_CHAVE_ID));
			((TextView) findViewById(R.id.edtCnpj)).setText(cliente.getCnpj());
			((TextView) findViewById(R.id.edtNome)).setText(cliente.getNome());
		}
	}
	
    @Override
    protected void onPause(){
    	super.onPause();
    	setResult(RESULT_CANCELED);
    	clienteDAO.fechar();
    	finish();
    }
    
}