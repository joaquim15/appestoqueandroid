package br.com.appestoque.provider;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProdutoDbAdapter {

	private final Context context;
	private DatabaseHelper dbHelper;
	
	@SuppressWarnings("unused")
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

	
}
