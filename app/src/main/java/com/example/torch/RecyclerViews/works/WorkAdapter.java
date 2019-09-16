package com.example.torch.RecyclerViews.works;

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
import com.example.torch.activity.showWorkImage.WorkImageActivity;
import com.example.torch.model.works_model.Work;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.MyWorkHolder>{
    private List<Work>workList;
    private Context context;

    public WorkAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyWorkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_works, parent, false);

        return new MyWorkHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyWorkHolder holder, int position) {
        Work work = workList.get(position);
        final String type = work.getType();
        final String name = work.getName();
        final String videoUrl = work.getVideoUrl();
//        if(type=="image")
//        {
//            holder.imageYoutubeLogo.setVisibility(View.GONE);
//        }else
//         {
//             holder.imageYoutubeLogo.setVisibility(View.VISIBLE);
//
//        }
        switch (type)
        {
            case "image":
                holder.imageYoutubeLogo.setVisibility(View.GONE);
                break;
            case "video" :
                    holder.imageYoutubeLogo.setVisibility(View.VISIBLE);
                    break;
        }
        if(name.length()>0)
        {
            Picasso.get().load(name).into(holder.imageWorks);
        }else {
            holder.imageWorks.setImageResource(R.drawable.logo);
        }
        holder.contenair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(type=="image"&&name.length()>0)
//                {
//                    Intent i=new Intent(context, WorkImageActivity.class);
//                    i.putExtra("imageUrl",name);
//                    context.startActivity(i);
//                }else {
//
//                    Intent i=new Intent(Intent.ACTION_VIEW);
//                    i.setData(Uri.parse(videoUrl));
//                    i.setPackage("com.google.android.youtube");
//                    context.startActivity(i);
//                }
                switch (type)
                {
                    case "image":
                        Intent i=new Intent(context, WorkImageActivity.class);
                    i.putExtra("imageUrl",name);
                    context.startActivity(i);
                    break;
                    case "video" :
                        Intent intentVideo=new Intent(Intent.ACTION_VIEW);
                        intentVideo.setData(Uri.parse(videoUrl));
                        intentVideo.setPackage("com.google.android.youtube");
                    context.startActivity(intentVideo);
                    break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return workList!=null?workList.size():0;
    }
    public void SetDataToRecyclerView(List<Work>workList)
    {
        this.workList=workList;
    }

    class MyWorkHolder extends RecyclerView.ViewHolder {
private ImageView imageWorks,imageYoutubeLogo;
private CardView contenair;
        public MyWorkHolder(@NonNull View itemView) {
            super(itemView);
            imageWorks=itemView.findViewById(R.id.imageWorks);
            imageYoutubeLogo=itemView.findViewById(R.id.imageYoutubeLogo);
            contenair=itemView.findViewById(R.id.Container);
        }
    }
}
