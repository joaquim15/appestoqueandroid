package br.com.appestoque.ui;

import br.com.appestoque.R;
import br.com.appestoque.dao.faturamento.ItemDAO;
import br.com.appestoque.dao.faturamento.PedidoDAO;
import br.com.appestoque.dao.suprimento.ProdutoDAO;
import br.com.appestoque.dominio.suprimento.Produto;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class ItemActivity extends BaseListaAtividade{
	
	private ItemDAO itemDAO;
	private ProdutoDAO produtoDAO;
	
	private class ItensAdapter extends CursorAdapter {

		public ItensAdapter(Context context, Cursor cursor) {
			super(context, cursor);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			final TextView produto = (TextView) view.findViewById(R.id.produto);
            final TextView quantidade = (TextView) view.findViewById(R.id.quantidade);
            final TextView valor = (TextView) view.findViewById(R.id.valor);
            quantidade.setText(cursor.getString(1));
            valor.setText(cursor.getString(2));
            Produto prd = produtoDAO.pesquisar(cursor.getLong(3));
            produto.setText(prd.getNome());
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return getLayoutInflater().inflate(R.layout.item_activity_lista, parent, false);
		}
		
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_activity);
		itemDAO = new ItemDAO(this);
		produtoDAO = new ProdutoDAO(this);
		Cursor cursor = null;
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
	        cursor = itemDAO.listar(extras.getLong(PedidoDAO.PEDIDO_CHAVE_ID));
	        ((TextView) findViewById(R.id.edtId)).setText(extras.getString(PedidoDAO.PEDIDO_CHAVE_ID));
	    }
	    startManagingCursor(cursor);	    
		setListAdapter(new ItensAdapter(this,cursor));
		registerForContextMenu(getListView());
	}
	
//	public void onAdicionarClick(Context context) {
//		final Intent intent = new Intent(context, IniciarAtividade.class);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		context.startActivity(intent);
//	}
	
	public void onAdicionarClick(View v) {
		Intent intent = new Intent(this, ItemEditarActivity.class);
		intent.putExtras(getIntent().getExtras());
    	startActivity(intent);
    }
    
    @Override
    protected void onDestroy(){
    	super.onDestroy();
    	itemDAO.fechar();
    }
	
}