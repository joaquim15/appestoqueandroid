package br.com.appestoque.ui;

import br.com.appestoque.R;
import br.com.appestoque.Util;
import br.com.appestoque.dao.cadastro.ClienteDAO;
import br.com.appestoque.dao.faturamento.PedidoDAO;
import br.com.appestoque.dominio.cadastro.Cliente;
import br.com.appestoque.dominio.faturamento.Pedido;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class PedidoEditarActivity extends Activity  {
	
	private PedidoDAO pedidoDAO;
	private ClienteDAO clienteDAO;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pedido_editar_activity);
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			pedidoDAO = new PedidoDAO(this);
			Pedido pedido = pedidoDAO.pesquisar(extras.getLong(PedidoDAO.PEDIDO_CHAVE_ID));
			clienteDAO = new ClienteDAO(this);
			Cliente cliente = clienteDAO.pesquisar(pedido.getCliente().getId());
			pedido.setCliente(cliente);
			((TextView) findViewById(R.id.edtNumero)).setText(pedido.getNumero().toString());
			((TextView) findViewById(R.id.edtData)).setText(Util.dateToStr( getString(R.string.mascara_data_ddMMyyyy),pedido.getData()));
			((TextView) findViewById(R.id.edtCliente)).setText(pedido.getCliente().getNome());
			((TextView) findViewById(R.id.edtObs)).setText(pedido.getObs());
		}
	}
	
}