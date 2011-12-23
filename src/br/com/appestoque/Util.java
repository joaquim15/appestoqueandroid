package br.com.appestoque;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

public class Util {

	public static void dialogo(Context context, String msg){		
		Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
	}
	
	public static String serial(Context context){
		return Settings.System.getString(context.getContentResolver(),Settings.System.ANDROID_ID);
	}
	
	public static void notificar(Context context, CharSequence mensagemBarraStatus, CharSequence titulo,CharSequence mensagem, Class<?> activity) {
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.icon, mensagemBarraStatus, System.currentTimeMillis());
		PendingIntent p = PendingIntent.getActivity(context, 0, new Intent(), 0);
		notification.setLatestEventInfo(context, titulo, mensagem, p);
		notification.vibrate = new long[] { 100, 250, 100, 500 };
		nm.notify(0, notification);
	}

	
}
