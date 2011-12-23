package br.com.appestoque;

import android.content.Context;
import android.provider.Settings;
import android.widget.Toast;

public class Util {

	public static void dialogo(Context context, String msg){		
		Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
	}
	
	public static String serial(Context context){
		return Settings.System.getString(context.getContentResolver(),Settings.System.ANDROID_ID);
	}
	
}
