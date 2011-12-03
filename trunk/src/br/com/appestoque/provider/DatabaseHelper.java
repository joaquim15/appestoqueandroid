package br.com.appestoque.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "appestoque.db";
	private static final int DATABASE_VERSION = 2;
	
	private static final String DATABASE_CREATE =
        "create table produtos ( _id integer primary key autoincrement, "
        + "nome text not null, numero text not null);";
	
	DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(db);
	}

}
