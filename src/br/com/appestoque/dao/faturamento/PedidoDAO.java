package br.com.appestoque.dao.faturamento;

import java.math.BigDecimal;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.appestoque.Constantes;
import br.com.appestoque.dao.DatabaseHelper;
import br.com.appestoque.dao.IDAO;
import br.com.appestoque.dominio.cadastro.Cliente;
import br.com.appestoque.dominio.faturamento.Item;
import br.com.appestoque.dominio.faturamento.Pedido;

public class PedidoDAO implements IDAO<Pedido,Long>{
	
	public static final String PEDIDO_CHAVE_ID = "_id";
	public static final String PEDIDO_CHAVE_NUMERO = "numero";
	public static final String PEDIDO_CHAVE_DATA = "data";
	public static final String PEDIDO_CHAVE_OBS = "obs";
	public static final String PEDIDO_CHAVE_CLIENTE = "idCliente";
	public static final String PEDIDO_CHAVE_SINCRONIZADO = "sincronizado";

	final String PEDIDO_SQL_CONTAR = "select count(*) from " + TABELA;
	
	public static final String TABELA = "pedidos";
	
	private DatabaseHelper databaseHelper;
	
	private SQLiteDatabase db;
	
	private Context context;
	
	public PedidoDAO(Context context) {
		this.context = context;
		this.databaseHelper = new DatabaseHelper(context);
    }
	
	@Override
	public Cursor listar() {
		Cursor cursor = db.query(TABELA, new String[] {	PEDIDO_CHAVE_ID,
														PEDIDO_CHAVE_NUMERO,
														PEDIDO_CHAVE_DATA,
														PEDIDO_CHAVE_OBS,
														PEDIDO_CHAVE_CLIENTE,
														PEDIDO_CHAVE_SINCRONIZADO}, 
														null, null, null, null, PEDIDO_CHAVE_DATA + " desc");
    	return cursor;
	}
	
	public Cursor listar(long id) {
		Cursor cursor = db.query(TABELA, new String[] {	PEDIDO_CHAVE_ID,
														PEDIDO_CHAVE_NUMERO,
														PEDIDO_CHAVE_DATA,
														PEDIDO_CHAVE_OBS,
														PEDIDO_CHAVE_CLIENTE,
														PEDIDO_CHAVE_SINCRONIZADO}, 
														PEDIDO_CHAVE_ID + " = " + id, null, null, null, null);
    	return cursor;
	}
	
	public long criar(String numero, long data, String obs, Long idCliente) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(PEDIDO_CHAVE_NUMERO,numero);
        initialValues.put(PEDIDO_CHAVE_DATA,data);
        initialValues.put(PEDIDO_CHAVE_OBS,obs);
        initialValues.put(PEDIDO_CHAVE_CLIENTE,idCliente);
        
        long ret = db.insert(TABELA, null,initialValues);
        return ret;
    }
	
	public Cursor listar(Cliente cliente) {
    	Cursor cursor = db.query(TABELA, new String[] {	PEDIDO_CHAVE_ID,
														PEDIDO_CHAVE_NUMERO,
														PEDIDO_CHAVE_DATA,
														PEDIDO_CHAVE_OBS,
														PEDIDO_CHAVE_CLIENTE,
														PEDIDO_CHAVE_SINCRONIZADO}, PEDIDO_CHAVE_CLIENTE + " = " + cliente.getId(), 
								null, null, null, PEDIDO_CHAVE_DATA + " desc ");
    	return cursor;
    } 
	
	public boolean existePedido(Cliente cliente){
    	return (db.compileStatement(PEDIDO_SQL_CONTAR + " where " + PEDIDO_CHAVE_CLIENTE + " = " + cliente.getId() ).simpleQueryForLong()>0);
    }
	
    public Cursor pesquisar(String numero) {
    	Cursor cursor = db.query(TABELA, new String[] {	PEDIDO_CHAVE_ID,
														PEDIDO_CHAVE_NUMERO,
														PEDIDO_CHAVE_DATA,
														PEDIDO_CHAVE_OBS,
														PEDIDO_CHAVE_CLIENTE,
														PEDIDO_CHAVE_SINCRONIZADO}, PEDIDO_CHAVE_NUMERO + " like '" + numero + "%'" , 
								null, null, null, null);
    	return cursor;
    }
	
	@Override
	public Pedido pesquisar(long id) {
    	Cursor cursor =  db.query(TABELA, new String[] {PEDIDO_CHAVE_ID,
														PEDIDO_CHAVE_NUMERO,
														PEDIDO_CHAVE_DATA,
														PEDIDO_CHAVE_OBS,
														PEDIDO_CHAVE_CLIENTE,
														PEDIDO_CHAVE_SINCRONIZADO
														}, PEDIDO_CHAVE_ID + " = " + id , 
    							null, null, null, null);    	
    	if(cursor.getCount()>0){
    		cursor.moveToFirst();
    		Pedido pedido = new Pedido();
    		pedido.setCliente(new Cliente());
    		pedido.setId(cursor.getLong(0));
    		pedido.setNumero(cursor.getString(1));
    		pedido.setData(new Date(cursor.getLong(2)));
    		pedido.setObs(cursor.getString(3));
    		pedido.getCliente().setId(cursor.getLong(4));
    		pedido.setSincronizado(cursor.getShort(5));
    		
    		ItemDAO itemDAO = new ItemDAO(this.context);
    		itemDAO.abrir();
    		Double total = 0d;
    		for(Item item : itemDAO.listar(pedido)){
    			total += item.getQuantidade()*item.getValor();
    		}
    		itemDAO.fechar();
    		pedido.setTotal(new BigDecimal(total).setScale(Constantes.DUAS_CASAS_DECIMAIS,BigDecimal.ROUND_DOWN).doubleValue());
    		
    		return pedido;
    	}else{
    		return null;
    	}
	}
	
	public long atualizar(Pedido pedido){
		ContentValues initialValues = new ContentValues();
		initialValues.put(PEDIDO_CHAVE_NUMERO,pedido.getNumero());
        initialValues.put(PEDIDO_CHAVE_DATA,pedido.getData().getTime());
        initialValues.put(PEDIDO_CHAVE_OBS,pedido.getObs());
        initialValues.put(PEDIDO_CHAVE_CLIENTE,pedido.getCliente().getId());
        initialValues.put(PEDIDO_CHAVE_SINCRONIZADO,pedido.isSincronizado());
		return db.update(TABELA, initialValues, PEDIDO_CHAVE_ID + " = " + pedido.getId() , null);
	}
	
	public boolean remover(Pedido pedido){
		return (db.delete(TABELA, PEDIDO_CHAVE_ID + " = " + pedido.getId(),null)>0);
	}
	
	@Override
	public void limpar() {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
    	db.delete(TABELA, null, null);
	}
	
    public void fechar(){
    	this.databaseHelper.close();
    }
    
    public void abrir(){
    	db = databaseHelper.getReadableDatabase();
    }

}