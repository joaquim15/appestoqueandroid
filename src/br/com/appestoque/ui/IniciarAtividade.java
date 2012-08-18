package br.com.appestoque.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import br.com.appestoque.Constantes;
import br.com.appestoque.R;

public class IniciarAtividade extends BaseAtividade implements LocationListener {
	
	private Double latitude; 
	private Double longitude;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.iniciar_atividade);
		getLocationManager().requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
		//getLocationManager().requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);
	}
	
	public void onClienteClick(View v) {
		startActivity(new Intent(this, ClienteActivity.class));
	}
	
	public void onProdutoClick(View v) {
		startActivity(new Intent(this, ProdutoActivity.class));
	}
	
	public void onPedidoClick(View v) {
		Intent intent = new Intent(this, PedidoActivity.class);
		intent.putExtra("latitude", this.latitude);
		intent.putExtra("longitude", this.longitude);
		startActivity(intent);
	}
	
	public void onPreferenciaClick(View v) {
		startActivity(new Intent(this, PreferenciaActivity.class));
	}
	
	@Override
    protected void onDestroy(){
    	super.onDestroy();
    	getLocationManager().removeUpdates(this);
    }
	
	
	private LocationManager getLocationManager(){
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		return locationManager;
  	}
	
	@Override
	public void onLocationChanged(Location location) {
		SharedPreferences preferencias = getSharedPreferences(Constantes.PREFERENCIAS, 0);
		SharedPreferences.Editor editor = preferencias.edit();
		editor.putString("latitude",Double.toString(location.getLatitude()));
		editor.putString("longitude",Double.toString(location.getLongitude()));
		editor.commit();
	}

	@Override
	public void onProviderDisabled(String arg0) {}

	@Override
	public void onProviderEnabled(String arg0) {}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {}
	
}