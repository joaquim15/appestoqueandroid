package br.com.appestoque.ui;

import br.com.appestoque.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class ProdutoImagemActivity extends BaseAtividade {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.produto_imagem_activity);
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			ImageView imageView = (ImageView) findViewById(R.id.imagemCentral);			
	    	Bitmap bitmap = BitmapFactory.decodeFile(extras.getString("imagemCentral"));
	    	imageView.setImageBitmap(bitmap);
		}
	}
	
}