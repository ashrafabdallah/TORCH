package com.example.torch.activity.showWorkImage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.torch.R;
import com.squareup.picasso.Picasso;

public class WorkImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_image);
        ImageView workImage=findViewById(R.id.imageWorks);
        Intent intent = getIntent();
        String imageUrl = intent.getExtras().getString("imageUrl");
        if(imageUrl.length()>0)
        {
            Picasso.get().load(imageUrl).into(workImage);
        }else {
            workImage.setImageResource(R.drawable.logo);
        }

    }
}
