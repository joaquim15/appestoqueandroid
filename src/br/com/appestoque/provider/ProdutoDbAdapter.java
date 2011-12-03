package br.com.appestoque.provider;

import br.com.appestoque.Constantes;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProdutoDbAdapter {
	
	private static final String DATABASE_TABLE = "appestoque.db";
	
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
    
    public long criar(Long id, String nome, String numero, double preco) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(Constantes.PRODUTO_CHAVE_ID, id);
        initialValues.put(Constantes.PRODUTO_CHAVE_NOME, nome);
        initialValues.put(Constantes.PRODUTO_CHAVE_NUMERO, numero);        
        initialValues.put(Constantes.PRODUTO_CHAVE_PRECO, preco);
        return sqlDb.insert(DATABASE_TABLE, null, initialValues);
    }

	
}