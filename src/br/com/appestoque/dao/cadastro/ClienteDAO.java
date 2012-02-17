package br.com.appestoque.dao.cadastro;

import android.database.Cursor;
import br.com.appestoque.dao.IDAO;
import br.com.appestoque.dominio.cadastro.Cliente;

public class ClienteDAO implements IDAO<Cliente,Long>{

	@Override
	public Cursor listar() {
		return null;
	}

	@Override
	public Cliente pesquisar(long id) {
		return null;
	}

	@Override
	public void limpar() {
	}

	@Override
	public void fechar() {
	}
	
}