package br.com.appestoque.dao;

import android.database.Cursor;
import br.com.appestoque.dominio.Produto;

public class ClienteDAO implements IDAO<Produto,Long>{

	@Override
	public Cursor listar() {
		return null;
	}

	@Override
	public Produto pesquisar(long id) {
		return null;
	}

	@Override
	public void limpar() {
	}

	@Override
	public void fechar() {
	}

}