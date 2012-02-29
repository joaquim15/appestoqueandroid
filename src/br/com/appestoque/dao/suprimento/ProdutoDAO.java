package br.com.appestoque.dao.suprimento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.appestoque.dao.DatabaseHelper;
import br.com.appestoque.dao.IDAO;
import br.com.appestoque.dominio.suprimento.Produto;

public class ProdutoDAO implements IDAO<Produto,Long> {
	
	public static final String PRODUTO_CHAVE_ID = "_id";
	public static final String PRODUTO_CHAVE_NOME = "nome";
    public static final String PRODUTO_CHAVE_NUMERO = "numero";
    public static final String PRODUTO_CHAVE_PRECO = "preco";
    
    public static final String TABELA = "produtos";
    
	private DatabaseHelper databaseHelper;
	
	public ProdutoDAO(Context context) {
		this.databaseHelper = new DatabaseHelper(context);
    }
	
	public Cursor listar() {		
		SQLiteDatabase db = databaseHelper.getReadableDatabase(); 
		Cursor cursor = db.query(TABELA, new String[] {	PRODUTO_CHAVE_ID, 
    													PRODUTO_CHAVE_NOME, 
    													PRODUTO_CHAVE_NUMERO, 
    													PRODUTO_CHAVE_PRECO}, 
    													null, null, null, null, null);
    	return cursor;
    }
	
    public long criar(Long id, String nome, String numero, double preco) {
    	SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(PRODUTO_CHAVE_ID, id);
        initialValues.put(PRODUTO_CHAVE_NOME, nome);
        initialValues.put(PRODUTO_CHAVE_NUMERO, numero);        
        initialValues.put(PRODUTO_CHAVE_PRECO, preco);
        long ret = db.insert(TABELA, null,initialValues);
        return ret;
    }
    
    public Cursor pesquisar(String numero) {
    	SQLiteDatabase db = databaseHelper.getReadableDatabase(); 
    	Cursor cursor = db.query(TABELA, new String[] {PRODUTO_CHAVE_ID, 
    			PRODUTO_CHAVE_NOME, PRODUTO_CHAVE_NUMERO, PRODUTO_CHAVE_PRECO}, PRODUTO_CHAVE_NUMERO + " like '" + numero + "%'" , null, null, null, null);
    	return cursor;
    }
    
    public void limpar(){
    	SQLiteDatabase db = databaseHelper.getWritableDatabase();
    	db.delete(TABELA, null, null);
    }
    
    public Produto pesquisar(long id){
    	SQLiteDatabase db = databaseHelper.getReadableDatabase(); 
    	Cursor cursor =  db.query(TABELA, new String[] {PRODUTO_CHAVE_ID, 
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
    
    public void fechar(){
    	if(databaseHelper!=null){
    		databaseHelper.close();
    	}
    }

}