package br.com.appestoque.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProdutoDbAdapter {

	public static final String PRODUTO_CHAVE_ID = "_id";
	public static final String PRODUTO_CHAVE_NOME = "nome";
    public static final String PRODUTO_CHAVE_NUMERO = "numero";
    public static final String PRODUTO_CHAVE_PRECO = "preco";
	
	private static final String DATABASE_TABLE = "produtos";
	
	private final Context context;
	private DatabaseHelper dbHelper;
	
	private SQLiteDatabase sqlDb;
	
	public ProdutoDbAdapter(Context context) {
        this.context = context;
    }
	
    public ProdutoDbAdapter open() throws SQLException {
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

	
}