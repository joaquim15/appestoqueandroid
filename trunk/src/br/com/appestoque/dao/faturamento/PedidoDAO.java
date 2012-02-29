package br.com.appestoque.dao.faturamento;

import android.database.Cursor;
import br.com.appestoque.dao.IDAO;
import br.com.appestoque.dominio.faturamento.Pedido;

public class PedidoDAO implements IDAO<Pedido,Long>{
	
	public static final String PEDIDO_CHAVE_ID = "_id";
	public static final String PEDIDO_CHAVE_NUMERO = "numero";
	public static final String PEDIDO_CHAVE_DATA = "data";
	public static final String PEDIDO_CHAVE_OBS = "obs";
	public static final String PEDIDO_CHAVE_CLIENTE = "idCliente";
	
	@Override
	public Cursor listar() {
		return null;
	}
	
	@Override
	public Pedido pesquisar(long id) {
		return null;
	}
	
	@Override
	public void limpar() {
	}
	
	@Override
	public void fechar() {
	}

}