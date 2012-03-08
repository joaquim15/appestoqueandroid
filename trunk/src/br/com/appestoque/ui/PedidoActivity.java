package br.com.appestoque.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import br.com.appestoque.Constantes;
import br.com.appestoque.HttpCliente;
import br.com.appestoque.R;
import br.com.appestoque.Util;
import br.com.appestoque.dao.cadastro.ClienteDAO;
import br.com.appestoque.dao.faturamento.PedidoDAO;
import br.com.appestoque.dominio.faturamento.Pedido;

@SuppressWarnings("unused")
public class PedidoActivity extends BaseListaAtividade{

	private PedidoDAO pedidoDAO;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();		
		setContentView(R.layout.pedido_activity);
		pedidoDAO = new PedidoDAO(this);
		Cursor cursor = null;
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	        //String query = intent.getStringExtra(SearchManager.QUERY);
	        //cursor = pedidoDAO.pesquisar(query);
	    }else{
	    	cursor = pedidoDAO.listar();
	    }
	    startManagingCursor(cursor);	    
		setListAdapter(new PedidoAdapter(this,cursor));
		registerForContextMenu(getListView());
	}

	private class PedidoAdapter extends CursorAdapter {

		public PedidoAdapter(Context context, Cursor cursor) {
			super(context, cursor);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			final TextView data = (TextView) view.findViewById(R.id.data);
            final TextView cliente = (TextView) view.findViewById(R.id.cliente);
            data.setText(Util.millisegundosDate(cursor.getLong(2)));
            ClienteDAO clienteDAO = new ClienteDAO(getApplicationContext());
            cliente.setText(clienteDAO.pesquisar(cursor.getLong(4)).getNome());
            final View iconView = view.findViewById(android.R.id.icon1);
            LayerDrawable iconDrawable = (LayerDrawable) iconView.getBackground();
            iconDrawable.getDrawable(0).setColorFilter(cursor.getLong(5)==0?Constantes.COR_VERMELHO_1:Constantes.COR_AZUL_1, PorterDuff.Mode.SRC_ATOP);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return getLayoutInflater().inflate(R.layout.pedido_activity_lista, parent, false);
		}
		
	}
	
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.pedido_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
			case R.id.item_menu_sincronizar:
				pedidoDAO = new PedidoDAO(this);
				Pedido pedido = pedidoDAO.pesquisar(info.id);
				JSONObject pedidoJSON = new JSONObject();
				try {
					pedidoJSON.put("numero",pedido.getNumero());
					pedidoJSON.put("data",pedido.getData().getTime());
					pedidoJSON.put("idCliente",pedido.getIdCliente());
					pedidoJSON.put("obs",pedido.getObs());
					
					JSONObject parametroJSON = new JSONObject();
					String os = Util.serial(PedidoActivity.this);
					os = "9774d56d682e549c";
					//os = "6d682e549c";
					parametroJSON.put("os", os);
					pedidoJSON.put("parametro",parametroJSON);
				} catch (JSONException e) {
					Log.e(Constantes.TAG, e.getMessage());
				}	
				JSONObject jsonObjRecv = HttpCliente.SendHttpPost(Constantes.SERVIDOR + Constantes.RESTFUL_PEDIDO,pedidoJSON);
				long retorno = pedidoDAO.atualizar(pedido);
				return true;
			case R.id.item_menu_visualizar:
				Toast.makeText(getApplicationContext(), "Visualizar", Toast.LENGTH_SHORT).show();
				return true;
			default:
				return super.onContextItemSelected(item);
		}
	}

    @Override
    protected void onDestroy(){
    	super.onDestroy();
    	if(pedidoDAO!=null){
    		pedidoDAO.fechar();
    	}
    }
	
}