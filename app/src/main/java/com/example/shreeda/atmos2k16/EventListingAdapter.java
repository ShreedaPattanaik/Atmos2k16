package com.example.shreeda.atmos2k16;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shreeda.atmos2k16.Set.EventSet;
import com.example.shreeda.atmos2k16.TableManagers.EventTableManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import Helper.RecyclerClickListener;

/**
 * Created by SHREEDA on 05-10-2016.
 */

public class EventListingAdapter extends RecyclerView.Adapter<EventListingAdapter.MyViewHolder> {

    Context context;
    LayoutInflater inflater;

    EventTableManager tableManager;

    ArrayList<EventSet> events;
    int defaultImage;
    RecyclerClickListener clickListener;
    int previousPos;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.event_listing_row, parent, false));

    }


    public void setClickListener(RecyclerClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setEvents(ArrayList<EventSet> events) {
        this.events = events;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final EventSet event = events.get(position);
        holder.name.setText(event.getName());
        if (event.isFavourite())
            holder.favourite.setImageResource(R.drawable.ic_favorite_black_24dp);
        else
            holder.favourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);

        if (event.getImage_downloaded() == 0) {
            Picasso.with(context).load(event.getImg_link()).placeholder(context.getResources().getDrawable(defaultImage))
                    .resize(300, 200)
                    .centerCrop()

                    .into(holder.image, new Callback() {
                        @Override
                        public void onSuccess() {
                            String path = saveToInternalSorage(((BitmapDrawable) holder.image.getDrawable()).getBitmap(), event.getName());

                            tableManager.imageDownloaded(event.getId(), path);
                        }

                        @Override
                        public void onError() {

                        }
                    });

        } else {

            holder.image.setImageBitmap(loadImageFromStorage(event.getImg_link(), event.getName()));
        }

    }

    private String saveToInternalSorage(Bitmap bitmapImage, String name) {
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, name + ".jpg");

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
//            Log.e("Save unsuccessful", e.toString());
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }
    private Bitmap loadImageFromStorage(String path, String name) {

        try {
            File f = new File(path, name + ".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image, favourite, share;
        TextView name ;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.main_events);
            name = (TextView) itemView.findViewById(R.id.event_name);
            favourite = (ImageView) itemView.findViewById(R.id.favourite_icon);
            share = (ImageView) itemView.findViewById(R.id.share_icon);
            if (clickListener != null) {
                favourite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onClick(v, getAdapterPosition());
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onClick(v, getAdapterPosition());
                    }
                });
            }

        }
    }
}
