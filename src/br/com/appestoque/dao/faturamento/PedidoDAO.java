package br.com.appestoque.dao.faturamento;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.appestoque.dao.DatabaseHelper;
import br.com.appestoque.dao.IDAO;
import br.com.appestoque.dominio.faturamento.Pedido;

public class PedidoDAO implements IDAO<Pedido,Long>{
	
	public static final String PEDIDO_CHAVE_ID = "_id";
	public static final String PEDIDO_CHAVE_NUMERO = "numero";
	public static final String PEDIDO_CHAVE_DATA = "data";
	public static final String PEDIDO_CHAVE_OBS = "obs";
	public static final String PEDIDO_CHAVE_CLIENTE = "idCliente";
	
	public static final String TABELA = "pedidos";
	
	private DatabaseHelper databaseHelper;
	
	public PedidoDAO(Context context) {
		this.databaseHelper = new DatabaseHelper(context);
    }
	
	@Override
	public Cursor listar() {
		SQLiteDatabase db = databaseHelper.getReadableDatabase(); 
		Cursor cursor = db.query(TABELA, new String[] {	PEDIDO_CHAVE_ID,
														PEDIDO_CHAVE_NUMERO,
														PEDIDO_CHAVE_DATA,
														PEDIDO_CHAVE_OBS,
														PEDIDO_CHAVE_CLIENTE}, 
														null, null, null, null, null);
    	return cursor;
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