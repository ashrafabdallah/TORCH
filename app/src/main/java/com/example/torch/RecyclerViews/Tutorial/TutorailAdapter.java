package com.example.torch.RecyclerViews.Tutorial;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torch.R;
import com.example.torch.model.model_Tutorial.Tutorial;
import com.squareup.picasso.Picasso;

import java.util.List;


public class TutorailAdapter extends RecyclerView.Adapter<TutorailAdapter.MyTutorialHolder>{
    private Context context;
private List<Tutorial>tutorialList;
    public TutorailAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyTutorialHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_works, parent, false);
        return new MyTutorialHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTutorialHolder holder, int position) {
        holder.imageYoutubeLogo.setVisibility(View.VISIBLE);
        Tutorial tutorial = tutorialList.get(position);
        String image = tutorial.getImage();
        if(image.length()>0)
        {
            Picasso.get().load(image).into(holder.imageWorks);
        }else {
            holder.imageWorks.setImageResource(R.drawable.logo);
        }
        final String Videourl = tutorial.getName();
        holder.contenair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(Videourl));
                i.setPackage("com.google.android.youtube");
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tutorialList!=null?tutorialList.size():0;
    }
    public void setData(List<Tutorial>tutorialList)
    {
        this.tutorialList=tutorialList;
    }

    class MyTutorialHolder extends RecyclerView.ViewHolder {
        private ImageView imageWorks, imageYoutubeLogo;
        private CardView contenair;

        public MyTutorialHolder(@NonNull View itemView) {
            super(itemView);
            imageWorks = itemView.findViewById(R.id.imageWorks);
            imageYoutubeLogo = itemView.findViewById(R.id.imageYoutubeLogo);
            contenair = itemView.findViewById(R.id.Container);
        }
    }

 
}
