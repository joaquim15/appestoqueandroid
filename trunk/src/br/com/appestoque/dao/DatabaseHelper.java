package br.com.appestoque.dao;

import br.com.appestoque.dao.suprimento.ProdutoDAO;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{

	private static final String TAG = "Appestoque";
	
	public static final String BASEDEDADOS_ARQUIVO = "appestoque.db";	
    private static final int BASEDEDADOS_VERSAO = 0;
	
	public DatabaseHelper(Context context) {
        super(context, BASEDEDADOS_ARQUIVO, null, BASEDEDADOS_VERSAO);        
    }
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(" create table "+ Tabelas.PRODUTOS 	 + " ( "
					+ ProdutoDAO.PRODUTO_CHAVE_ID        + " integer primary key,  	"
			        + ProdutoDAO.PRODUTO_CHAVE_NOME      + " text not null, 	 	" 
			        + ProdutoDAO.PRODUTO_CHAVE_NUMERO    + " text not null,	 	 	"
			        + ProdutoDAO.PRODUTO_CHAVE_PRECO     + " real not null	 	 	"
			        + "  );");
		
		db.execSQL(" create table "+ Tabelas.CLIENTES 	 + " ( "
				+ ProdutoDAO.PRODUTO_CHAVE_ID        + " integer primary key,  	"
		        + ProdutoDAO.PRODUTO_CHAVE_NOME      + " text not null, 	 	" 
		        + ProdutoDAO.PRODUTO_CHAVE_NUMERO    + " text not null,	 	 	"
		        + ProdutoDAO.PRODUTO_CHAVE_PRECO     + " real not null	 	 	"
		        + "  );");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		switch (newVersion) {
			case 0: db.execSQL("DROP TABLE IF EXISTS " + Tabelas.PRODUTOS);
			db.execSQL("DROP TABLE IF EXISTS " + Tabelas.PRODUTOS);
		}
		
		Log.d( TAG , "onUpgrade() de " + oldVersion + " para " + newVersion );
		
//		int version = oldVersion;
//        switch (version) {
//            case VERSAO_LANCAMENTO:
//                db.execSQL("ALTER TABLE " + Tabelas.PRODUTOS + " ADD COLUMN " + ProdutoDAO.PRODUTO_CHAVE_ESTOQUE + " real not null default 0 ");
//                version = VERSAO_3;
//        }
		
	}
	
	interface Tabelas {
        String PRODUTOS = "produtos";
        String CLIENTES = "clientes";
	};    

}