package br.com.appestoque.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDado extends SQLiteOpenHelper {
	
	private static final String BASE_NOME = "appestoque.db";
	private static final int BASE_VERSAO = 19;

	public BaseDado(Context context) {
		super(context, BASE_NOME, null, BASE_VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE produtos( id INTEGER PRIMARY KEY AUTOINCREMENT,nome TEXT NOT NULL,	preco float NOT NULL, ) ON CONFLICT REPLACE)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
