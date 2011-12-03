package br.com.appestoque;

import android.content.Context;
import android.widget.Toast;

public class Util {

	public static void dialogo(Context context, String msg){
		Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
	}
	
}
