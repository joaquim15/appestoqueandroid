package br.com.appestoque.dao;

import br.com.appestoque.dao.cadastro.ClienteDAO;
import br.com.appestoque.dao.suprimento.ProdutoDAO;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

	public static final String BASEDEDADOS_ARQUIVO = "appestoque.db";	
    
	private static final int BASEDEDADOS_VERSAO = 1;
	private static final int VERSAO_LANCAMENTO 	= 1;
	
	public DatabaseHelper(Context context) {
        super(context, BASEDEDADOS_ARQUIVO, null, BASEDEDADOS_VERSAO);        
    }
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(" create table "+ Tabelas.PRODUTOS 	+ " ( "
					+ ProdutoDAO.PRODUTO_CHAVE_ID       + " integer primary key,  	"
			        + ProdutoDAO.PRODUTO_CHAVE_NOME     + " text not null, 	 		" 
			        + ProdutoDAO.PRODUTO_CHAVE_NUMERO   + " text not null,	 	 	"
			        + ProdutoDAO.PRODUTO_CHAVE_PRECO    + " real not null	 	 	"
			        + "  ); ");
		
		db.execSQL(" create table "+ Tabelas.CLIENTES 	+ " ( "
				+ ClienteDAO.CLIENTE_CHAVE_ID        	+ " integer primary key,  	"
		        + ClienteDAO.CLIENTE_CHAVE_NOME      	+ " text not null, 	 		" 
		        + ClienteDAO.CLIENTE_CHAVE_CNPJ      	+ " text not null,	 	 	"
		        + ClienteDAO.CLIENTE_CHAVE_ENDERECO  	+ " text not null,	 	 	"
		        + ClienteDAO.CLIENTE_CHAVE_NUMERO    	+ " INTEGER not null,	 	"
		        + ClienteDAO.CLIENTE_CHAVE_CEP       	+ " text not null,	 	 	"
		        + ClienteDAO.CLIENTE_CHAVE_COMPLEMENTO  + " text not null,	 	 	"
		        + ClienteDAO.CLIENTE_CHAVE_BAIRRO  		+ " text not null,	 	 	"
		        + ClienteDAO.CLIENTE_CHAVE_CIDADE  		+ " text not null	 	 	"
		        + "  ); ");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		switch (newVersion) {
			case VERSAO_LANCAMENTO: 
				onCreate(db);
		}
	}
	
	interface Tabelas {
        String PRODUTOS = "produtos";
        String CLIENTES = "clientes";
	};    

}