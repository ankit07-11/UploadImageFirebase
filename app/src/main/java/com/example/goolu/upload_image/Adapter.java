package com.example.goolu.upload_image;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ImageViewHolder> {
    @NonNull
    private Context mcontext;
    private List<upload> mupload;

    public Adapter(@NonNull Context mcontext,List<upload> uploads) {
        this.mcontext = mcontext;
        mupload=uploads;
    }

    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(mcontext).inflate(R.layout.item,viewGroup,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {
           upload uploadCurrent=mupload.get(i);
        imageViewHolder.textViewName.setText(uploadCurrent.getmName());
        System.out.println("URL"+uploadCurrent.getmImageUrl());
        //imageViewHolder.imageView.setImageURI(Uri.parse(uploadCurrent.getmImageUrl()));
        //Picasso.with(mcontext).load(uploadCurrent.getmImageUrl()).fit().centerCrop().into(imageViewHolder.imageView);
        Picasso.get().load(uploadCurrent.getmImageUrl()).into(imageViewHolder.imageView);
        System.out.println("URL"+uploadCurrent.getmImageUrl());
    }

    @Override
    public int getItemCount() {
        return mupload.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName;
        public ImageView imageView,image2;
        public ImageViewHolder(View itemView){
            super(itemView);
            textViewName =itemView.findViewById(R.id.textView2);
            imageView=itemView.findViewById(R.id.imageView2);
        }
    }
}
