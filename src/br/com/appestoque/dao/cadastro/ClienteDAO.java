package br.com.appestoque.dao.cadastro;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.appestoque.dao.DatabaseHelper;
import br.com.appestoque.dao.IDAO;
import br.com.appestoque.dominio.cadastro.Cliente;

public class ClienteDAO implements IDAO<Cliente,Long>{

	public static final String CLIENTE_CHAVE_ID = "_id";
	public static final String CLIENTE_CHAVE_NOME = "nome";
	public static final String CLIENTE_CHAVE_CNPJ = "cnpj";
	public static final String CLIENTE_CHAVE_ENDERECO = "endereco";
	public static final String CLIENTE_CHAVE_NUMERO = "numero";
	public static final String CLIENTE_CHAVE_CEP = "cep";
	public static final String CLIENTE_CHAVE_COMPLEMENTO = "complemento";
	public static final String CLIENTE_CHAVE_BAIRRO = "bairro";
	public static final String CLIENTE_CHAVE_CIDADE = "cidade";

	private static final String TABELA = "cliente";
    
	private DatabaseHelper databaseHelper;
	
	public ClienteDAO(Context context) {
		this.databaseHelper = new DatabaseHelper(context);
    }
	
	
	
	@Override
	public Cursor listar() {
		SQLiteDatabase db = databaseHelper.getReadableDatabase(); 
		Cursor cursor = db.query(TABELA, new String[] {	CLIENTE_CHAVE_ID, 
														CLIENTE_CHAVE_NOME, 
														CLIENTE_CHAVE_CNPJ, 
														CLIENTE_CHAVE_ENDERECO, 
														CLIENTE_CHAVE_NUMERO,
														CLIENTE_CHAVE_CEP, 
														CLIENTE_CHAVE_COMPLEMENTO, 
														CLIENTE_CHAVE_BAIRRO, 
														CLIENTE_CHAVE_CIDADE}, 
														null, null, null, null, null);
    	return cursor;
	}

	@Override
	public Cliente pesquisar(long id) {
		return null;
	}

	@Override
	public void limpar() {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
    	db.delete(TABELA, null, null);
	}

	@Override
	public void fechar() {
		if(databaseHelper!=null){
    		databaseHelper.close();
    	}
	}
	
}