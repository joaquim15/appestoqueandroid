package br.com.appestoque.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.appestoque.dominio.Produto;
import br.com.appestoque.provider.DatabaseHelper;

public class ProdutoDAO {
	
	public static final String PRODUTO_CHAVE_ID = "_id";
	public static final String PRODUTO_CHAVE_NOME = "nome";
    public static final String PRODUTO_CHAVE_NUMERO = "numero";
    public static final String PRODUTO_CHAVE_PRECO = "preco";
    public static final String PRODUTO_CHAVE_ESTOQUE = "estoque";
    public static final String PRODUTO_CHAVE_IMAGEM = "imagem";
    
    private static final String TABELA = "produtos";
    
	private DatabaseHelper databaseHelper;	
	private SQLiteDatabase sqliteDatabase;
	
	public ProdutoDAO(Context context) {
		this.databaseHelper = new DatabaseHelper(context);
    }
	
	public Cursor listar() {
		sqliteDatabase = databaseHelper.getReadableDatabase();
    	return sqliteDatabase.query(TABELA, new String[] {PRODUTO_CHAVE_ID, 
    			PRODUTO_CHAVE_NOME, PRODUTO_CHAVE_NUMERO, PRODUTO_CHAVE_PRECO}, null, null, null, null, null);
    }
	
    public long criar(Long id, String nome, String numero, double preco) {
    	sqliteDatabase = databaseHelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(PRODUTO_CHAVE_ID, id);
        initialValues.put(PRODUTO_CHAVE_NOME, nome);
        initialValues.put(PRODUTO_CHAVE_NUMERO, numero);        
        initialValues.put(PRODUTO_CHAVE_PRECO, preco);
        return sqliteDatabase.insert(TABELA, null,initialValues);
    }
    
    public Cursor buscar(String numero) {
    	sqliteDatabase = databaseHelper.getReadableDatabase();
    	return sqliteDatabase.query(TABELA, new String[] {PRODUTO_CHAVE_ID, 
    			PRODUTO_CHAVE_NOME, PRODUTO_CHAVE_NUMERO, PRODUTO_CHAVE_PRECO}, PRODUTO_CHAVE_NUMERO + " like '" + numero + "%'" , null, null, null, null);
    }
    
    public void limpar(){
    	sqliteDatabase = databaseHelper.getWritableDatabase();
    	sqliteDatabase.delete(TABELA, null, null);
    }
    
    public Produto buscar(long id){
    	sqliteDatabase = databaseHelper.getReadableDatabase();
    	Cursor cursor =  sqliteDatabase.query(TABELA, new String[] {PRODUTO_CHAVE_ID, 
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
    	}else{
    		return null;
    	}
    }

}
