package br.com.appestoque.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProdutoDbAdapter {

    public static final String KEY_NOME = "nome";
    public static final String KEY_NUMERO = "numero";
    public static final String KEY_ID = "_id";
	
	private static final String DATABASE_TABLE = "notes";
	
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
    
    public long criar(String nome, String numero) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NOME, nome);
        initialValues.put(KEY_NUMERO, numero);
        return sqlDb.insert(DATABASE_TABLE, null, initialValues);
    }

	
}