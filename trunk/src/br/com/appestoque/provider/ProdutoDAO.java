package br.com.appestoque.provider;

import br.com.appestoque.dominio.Produto;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProdutoDAO {

	public static final String PRODUTO_CHAVE_ID = "_id";
	public static final String PRODUTO_CHAVE_NOME = "nome";
    public static final String PRODUTO_CHAVE_NUMERO = "numero";
    public static final String PRODUTO_CHAVE_PRECO = "preco";
    public static final String PRODUTO_CHAVE_ESTOQUE = "estoque";
    public static final String PRODUTO_CHAVE_IMAGEM = "imagem";
	
	private static final String DATABASE_TABLE = "produtos";
	
	private final Context context;
	private DatabaseHelper dbHelper;
	
	private SQLiteDatabase sqlDb;
	
	public ProdutoDAO(Context context) {
        this.context = context;
    }
	
    public ProdutoDAO open() throws SQLException {
    	dbHelper = new DatabaseHelper(context);
    	sqlDb = dbHelper.getWritableDatabase();    	
        return this;
    }

    public void close() {
    	dbHelper.close();
    }
    
    public Cursor listar() {
    	return sqlDb.query(DATABASE_TABLE, new String[] {PRODUTO_CHAVE_ID, 
    			PRODUTO_CHAVE_NOME, PRODUTO_CHAVE_NUMERO, PRODUTO_CHAVE_PRECO}, null, null, null, null, null);
    }
    
    public long criar(Long id, String nome, String numero, double preco) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(PRODUTO_CHAVE_ID, id);
        initialValues.put(PRODUTO_CHAVE_NOME, nome);
        initialValues.put(PRODUTO_CHAVE_NUMERO, numero);        
        initialValues.put(PRODUTO_CHAVE_PRECO, preco);
        return sqlDb.insert(DATABASE_TABLE, null, initialValues);
    }

//    public Cursor buscar(long id) {
//    	return sqlDb.query(DATABASE_TABLE, new String[] {PRODUTO_CHAVE_ID, 
//    			PRODUTO_CHAVE_NOME, PRODUTO_CHAVE_NUMERO, PRODUTO_CHAVE_PRECO}, PRODUTO_CHAVE_ID + " = " + id , null, null, null, null);
//    }
    
    public Cursor buscar(String numero) {
    	numero = numero + "%"; 
    	return sqlDb.query(DATABASE_TABLE, new String[] {PRODUTO_CHAVE_ID, 
    			PRODUTO_CHAVE_NOME, PRODUTO_CHAVE_NUMERO, PRODUTO_CHAVE_PRECO}, PRODUTO_CHAVE_NUMERO + " like '" + numero + "'" , null, null, null, null);
    }
    
    public void limpar(){
    	sqlDb.delete(DATABASE_TABLE, null, null);
    }
    
    public Produto buscar(long id){
    	Cursor cursor =  sqlDb.query(DATABASE_TABLE, new String[] {PRODUTO_CHAVE_ID, 
    							PRODUTO_CHAVE_NOME, PRODUTO_CHAVE_NUMERO, PRODUTO_CHAVE_PRECO}, PRODUTO_CHAVE_ID + " = " + id , 
    							null, null, null, null);
    	if(cursor.getCount()>0){
    		cursor.moveToFirst();
    		Produto produto = new Produto();
    		produto.setId(cursor.getLong(0));
    		produto.setNome(cursor.getString(1));
    		produto.setNumero(cursor.getString(2));
    		produto.setPreco(cursor.getDouble(3));
    		return produto;
    	}
    	return null;
    }
	
}