package br.com.appestoque.ui;

import java.io.File;

import br.com.appestoque.R;
import br.com.appestoque.Util;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class ProdutoImagemActivity extends BaseAtividade {

	String[] imagens;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.produto_imagem_activity);
		
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			imagens = extras.getStringArray("imagens"); 
		}

		Gallery gallery = (Gallery) findViewById(R.id.gallery);
		gallery.setAdapter(new Adaptador(this, imagens));

		gallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				ImageView imageView = (ImageView) findViewById(R.id.imagemCentral);			
		    	Bitmap bitmap = BitmapFactory.decodeFile(imagens[arg2]);
		    	imageView.setImageBitmap(bitmap);
			}
		});
		
	}
	
    private class Adaptador extends BaseAdapter{

    	int mGalleryItemBackground;
        private Context context;
        private String[] imagens;
        
        public Adaptador(Context context, String[] imagens) {
        	this.imagens = imagens;
            this.context = context;
            TypedArray attr = context.obtainStyledAttributes(R.styleable.HelloGallery);
            mGalleryItemBackground = attr.getResourceId(R.styleable.HelloGallery_android_galleryItemBackground,1);
            attr.recycle();
        }

    	
		@Override
		public int getCount() {
			return imagens.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(context);
			String imagem = imagens[position];
			File file = new  File(imagem);
			if(file.exists()){
				Bitmap bitmap = BitmapFactory.decodeFile(imagem);
				imageView.setImageBitmap(bitmap);
			}        
		    imageView.setLayoutParams(new Gallery.LayoutParams(100,100));
		    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		    imageView.setBackgroundResource(mGalleryItemBackground);
	        return imageView;
		}
    	
    }
	
}