package br.com.appestoque.ui;

import java.io.File;

import br.com.appestoque.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	int mGalleryItemBackground;
    private Context mContext;

    private Integer[] mImageIds = {
            R.drawable.sample_1,
            R.drawable.sample_2,
            R.drawable.sample_3,
            R.drawable.sample_4,
            R.drawable.sample_5,
            R.drawable.sample_6,
            R.drawable.sample_7
    };
    
    private String[] imagens;

    public ImageAdapter(Context c) {
        mContext = c;
        TypedArray attr = mContext.obtainStyledAttributes(R.styleable.HelloGallery);
        mGalleryItemBackground = attr.getResourceId(R.styleable.HelloGallery_android_galleryItemBackground,1);
        attr.recycle();
    }
    
    public ImageAdapter(Context c, String[] imagens) {
    	this.imagens = imagens;
        mContext = c;
        TypedArray attr = mContext.obtainStyledAttributes(R.styleable.HelloGallery);
        mGalleryItemBackground = attr.getResourceId(R.styleable.HelloGallery_android_galleryItemBackground,1);
        attr.recycle();
    }

    public int getCount() {
        return mImageIds.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

//    public View getView(int position, View convertView, ViewGroup parent) {
//        ImageView imageView = new ImageView(mContext);
//        imageView.setImageResource(mImageIds[position]);
//        imageView.setLayoutParams(new Gallery.LayoutParams(100,100));
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        imageView.setBackgroundResource(mGalleryItemBackground);
//        return imageView;
//    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        if(position>=0&&position<=3){
			String imagem = imagens[position];
			File file = new  File(imagem);
			if(file.exists()){
				Bitmap bitmap = BitmapFactory.decodeFile(imagem);
				imageView.setImageBitmap(bitmap);
			}        
	        imageView.setLayoutParams(new Gallery.LayoutParams(100,100));
	        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
	        imageView.setBackgroundResource(mGalleryItemBackground);
        }
        return imageView;
    }
    
}