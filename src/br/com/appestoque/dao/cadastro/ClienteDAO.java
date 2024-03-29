package br.com.appestoque.dao.cadastro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.appestoque.Constantes;
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
	public static final String CLIENTE_CHAVE_SINCRONIZADO = "sincronizado";
	
	final String CLIENTE_SQL_CONTAR = "select count(*) from " + TABELA;  

	public static final String TABELA = "clientes";
    
	private DatabaseHelper databaseHelper;
	
	private SQLiteDatabase db;
	private Cursor cursor; 
	
	public ClienteDAO(Context context) {
		this.databaseHelper = new DatabaseHelper(context);
    }
	
	@Override
	public Cursor listar() {
		cursor = null;
		cursor = db.query(TABELA, new String[] { CLIENTE_CHAVE_ID,
				CLIENTE_CHAVE_NOME, CLIENTE_CHAVE_CNPJ, CLIENTE_CHAVE_ENDERECO,
				CLIENTE_CHAVE_NUMERO, CLIENTE_CHAVE_CEP,
				CLIENTE_CHAVE_COMPLEMENTO, CLIENTE_CHAVE_BAIRRO,
				CLIENTE_CHAVE_CIDADE }, null, null, null, null, null);
		return cursor;
	}

	public long criar(	Long id,
						String nome, 
						String cnpj, 
						String endereco,
						Long numero, 
						String cep, 
						String complemento, 
						String bairro,
						String cidade,
						boolean sincronizado ) {
		long identificador = 0;
		ContentValues initialValues = new ContentValues();
		initialValues.put(CLIENTE_CHAVE_ID, id);
		initialValues.put(CLIENTE_CHAVE_NOME, nome);
		initialValues.put(CLIENTE_CHAVE_CNPJ, cnpj);
		initialValues.put(CLIENTE_CHAVE_ENDERECO, endereco);
		initialValues.put(CLIENTE_CHAVE_NUMERO, numero);
		initialValues.put(CLIENTE_CHAVE_CEP, cep);
		initialValues.put(CLIENTE_CHAVE_COMPLEMENTO, complemento);
		initialValues.put(CLIENTE_CHAVE_BAIRRO, bairro);
		initialValues.put(CLIENTE_CHAVE_CIDADE, cidade);
		initialValues.put(CLIENTE_CHAVE_SINCRONIZADO, (sincronizado?1:0));
		identificador = db.insert(TABELA, null, initialValues);
		return identificador;
    }
	
    public boolean atualizar(Cliente cliente) {
		ContentValues initialValues = new ContentValues();
		if(cliente.getNome()!=null){
			initialValues.put(CLIENTE_CHAVE_NOME, cliente.getNome());
		}
		if(cliente.getCnpj()!=null){
			initialValues.put(CLIENTE_CHAVE_CNPJ, cliente.getCnpj());
		}
		if(cliente.getEndereco()!=null){
			initialValues.put(CLIENTE_CHAVE_ENDERECO, cliente.getEndereco());
		}
		if(cliente.getNumero()!=null){
			initialValues.put(CLIENTE_CHAVE_NUMERO, cliente.getNumero());
		}
		if(cliente.getCep()!=null){
			initialValues.put(CLIENTE_CHAVE_CEP, cliente.getCep());
		}
		if(cliente.getComplemento()!=null){
			initialValues.put(CLIENTE_CHAVE_COMPLEMENTO, cliente.getComplemento());
		}
		if(cliente.getBairro()!=null){
			initialValues.put(CLIENTE_CHAVE_BAIRRO, cliente.getBairro());
		}
		if(cliente.getCidade()!=null){
			initialValues.put(CLIENTE_CHAVE_CIDADE, cliente.getCidade());
		}
		return db.update(TABELA, initialValues, CLIENTE_CHAVE_ID+"="+cliente.getId(), null)>0;
    }

	@Override
	public Cliente pesquisar(long id) {
		Cliente objeto = null;
		cursor = null;
		cursor = db.query(TABELA, new String[] { CLIENTE_CHAVE_ID,
				CLIENTE_CHAVE_NOME, CLIENTE_CHAVE_CNPJ, CLIENTE_CHAVE_ENDERECO,
				CLIENTE_CHAVE_NUMERO, CLIENTE_CHAVE_CEP,
				CLIENTE_CHAVE_COMPLEMENTO, CLIENTE_CHAVE_BAIRRO,
				CLIENTE_CHAVE_CIDADE, CLIENTE_CHAVE_SINCRONIZADO }, CLIENTE_CHAVE_ID + " = " + id, null,
				null, null, null);
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			objeto = new Cliente(cursor.getLong(Cliente.CLIENTE_SEQUENCIA_ID), 
							     cursor.getString(Cliente.CLIENTE_SEQUENCIA_NOME),
							     cursor.getString(Cliente.CLIENTE_SEQUENCIA_CNPJ), 
							     cursor.getString(Cliente.CLIENTE_SEQUENCIA_ENDERECO),
							     cursor.getLong(Cliente.CLIENTE_SEQUENCIA_NUMERO), 
							     cursor.getString(Cliente.CLIENTE_SEQUENCIA_CEP),
							     cursor.getString(Cliente.CLIENTE_SEQUENCIA_COMPLEMENTO), 
							     cursor.getString(Cliente.CLIENTE_SEQUENCIA_BAIRRO),
							     cursor.getString(Cliente.CLIENTE_SEQUENCIA_CIDADE),
							     cursor.getLong(Cliente.CLIENTE_SEQUENCIA_SINCRONIZADO));
			cursor.close();
			return objeto;
		}else{
			cursor.close();
			return null;
		}
	}
	
    public Cursor pesquisar(String nome) {
		cursor = null;
		cursor = db.query(TABELA, new String[] { CLIENTE_CHAVE_ID,
				CLIENTE_CHAVE_NOME, CLIENTE_CHAVE_CNPJ, CLIENTE_CHAVE_ENDERECO,
				CLIENTE_CHAVE_NUMERO, CLIENTE_CHAVE_CEP,
				CLIENTE_CHAVE_COMPLEMENTO, CLIENTE_CHAVE_BAIRRO,
				CLIENTE_CHAVE_CIDADE }, CLIENTE_CHAVE_NOME + " like '" + Constantes.CARACTER_PERCENTAGEM + nome + Constantes.CARACTER_PERCENTAGEM + "'", null, null, null, null);
		return cursor;
    }
    
    public boolean existeBairro(String nome) {
		cursor = null;
		cursor = db.query(TABELA, new String[] {CLIENTE_CHAVE_ID}, CLIENTE_CHAVE_BAIRRO + " = '" + nome + "'" , null, null, null, null);
		return (cursor.getCount()>0);
    }
    
    public boolean existeCidade(String nome) {
		cursor = null;
		cursor = db.query(TABELA, new String[] {CLIENTE_CHAVE_ID}, CLIENTE_CHAVE_CIDADE + " = '" + nome + "'" , null, null, null, null);
		return (cursor.getCount()>0);
    }
    
	public String[] listarBairros() {
		cursor = db.query(true, TABELA, new String[] {CLIENTE_CHAVE_BAIRRO}, null, null, null, null, CLIENTE_CHAVE_BAIRRO, null);
		String[] bairros = new String[cursor.getCount()];
		if (bairros.length>0) {
			int i = 0;
			while (cursor.moveToNext()) {
				bairros[i] = cursor.getString(0);
				++i;
			}
		}
		return bairros;
    }

	public String[] listarCidades() {
		cursor = db.query(true, TABELA, new String[] {CLIENTE_CHAVE_CIDADE}, null, null, null, null, CLIENTE_CHAVE_CIDADE, null);
		String[] cidades = new String[cursor.getCount()];
		if (cidades.length>0) {
			int i = 0;
			while (cursor.moveToNext()) {
				cidades[i] = cursor.getString(0);
				++i;
			}
		}
		return cidades;
    }
    
    public long contar(){
    	return db.compileStatement(CLIENTE_SQL_CONTAR).simpleQueryForLong();
    }

	@Override
	public void limpar() {
    	db.delete(TABELA, null, null);
	}
	
    public void fechar(){
    	this.databaseHelper.close();
    }
    
    public void abrir(){
    	db = databaseHelper.getReadableDatabase();
    }

}