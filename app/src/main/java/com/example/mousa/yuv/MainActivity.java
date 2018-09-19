package com.example.mousa.yuv;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
    ImageView srcimg;
    ImageView yimg;
    ImageView uimg;
    ImageView vimg;
    ImageView yuv;
    Button Gal;
    Bitmap im;
    TextView tx;
    int r;
    int g;
    int b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        srcimg = (ImageView) findViewById(R.id.img);
        yimg = (ImageView) findViewById(R.id.imY);
        uimg = (ImageView) findViewById(R.id.imU);
        vimg = (ImageView) findViewById(R.id.ImV);
        Gal = (Button) findViewById(R.id.gallary);
        yuv=(ImageView)findViewById(R.id.imYuv);

    }


    public void Pick_photo(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("return-data", "true");
        startActivityForResult(intent, 100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 & resultCode == RESULT_OK) {
            srcimg.setImageBitmap((Bitmap) data.getExtras().get("data"));
            im = (Bitmap) data.getExtras().get("data");
             Bitmap ybitmap =Bitmap.createBitmap(im);
            Bitmap ubitmap =Bitmap.createBitmap(im);
            Bitmap vbitmap =Bitmap.createBitmap(im);
            Bitmap yuvbitmap =Bitmap.createBitmap(im);
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    int color = im.getPixel(i, j);
                    r = Color.red(color);
                    g = Color.green(color);
                    b = Color.blue(color);
                    int y=(int)(0.299*r+0.587*g+0.114*b);
                    ybitmap.setPixel(i,j,Color.argb(255,y,y,y));
                    int u=(int)((0.436*(b-y))/(1-0.114));
                    ubitmap.setPixel(i,j,Color.argb(255,0,0,u));
                    int v=(int)((0.615*((r-y))/(1-0.299)));
                    vbitmap.setPixel(i,j,Color.argb(255,v,0,0));

                    if(j%2==0)
                        yuvbitmap.setPixel(i,j,Color.argb(255,v,y,u));

                    else
                        yuvbitmap.setPixel(i,j,Color.argb(255,255,255,255));
                }
            }
            yimg.setImageBitmap(ybitmap);
            uimg.setImageBitmap(ubitmap);
            vimg.setImageBitmap(vbitmap);
            yuv.setImageBitmap(yuvbitmap);

        }
    }
}