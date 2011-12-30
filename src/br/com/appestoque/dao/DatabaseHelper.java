package br.com.appestoque.dao;

import br.com.appestoque.dao.ProdutoDAO;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{

	private static final String TAG = "Appestoque";
	
	public static final String BASEDEDADOS_ARQUIVO = "appestoque.db";
	
	private static final int VERSAO_LANCAMENTO = 2;
    private static final int VERSAO_3 = 3;

    private static final int BASEDEDADOS_VERSAO = VERSAO_3;
	
	private static final String DATABASE_CREATE =
        " create table "+ Tabelas.PRODUTOS +" ( "
			+ ProdutoDAO.PRODUTO_CHAVE_ID      + " integer primary key,  "
	        + ProdutoDAO.PRODUTO_CHAVE_NOME    + " text not null, 	 	 " 
	        + ProdutoDAO.PRODUTO_CHAVE_NUMERO  + " text not null,	 	 "
	        + ProdutoDAO.PRODUTO_CHAVE_PRECO   + " real not null,	 	 "
	        + ProdutoDAO.PRODUTO_CHAVE_ESTOQUE + " real not null,	 	 "
	        + ProdutoDAO.PRODUTO_CHAVE_IMAGEM  + " text  	 		 	 "
        + "  );";
	
	public DatabaseHelper(Context context) {
        super(context, BASEDEDADOS_ARQUIVO, null, BASEDEDADOS_VERSAO);        
    }
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		Log.d(TAG, "onUpgrade() de " + oldVersion + " para " + newVersion);
		int version = oldVersion;
        switch (version) {
            case VERSAO_LANCAMENTO:
                db.execSQL("ALTER TABLE " + Tabelas.PRODUTOS + " ADD COLUMN " + ProdutoDAO.PRODUTO_CHAVE_IMAGEM + " TEXT");
                db.execSQL("ALTER TABLE " + Tabelas.PRODUTOS + " ADD COLUMN " + ProdutoDAO.PRODUTO_CHAVE_ESTOQUE + " real not null default 0 ");
                version = VERSAO_3;
        }
		
	}
	
	interface Tabelas {
        String PRODUTOS = "produtos";
        String CLIENTES = "clientes";
	};    

}