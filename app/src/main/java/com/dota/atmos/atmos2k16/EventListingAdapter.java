package com.dota.atmos.atmos2k16;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.dota.atmos.atmos2k16.Set.EventSet;
import com.dota.atmos.atmos2k16.TableManagers.EventTableManager;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
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

    EventTableManager tableManager;
    ArrayList<EventSet> events;
    RecyclerClickListener clickListener;
    Context context;
    LayoutInflater inflater;

    int defaultImage = R.drawable.atmosmono;
    int offset = 0;

    public EventListingAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        tableManager = new EventTableManager(context);

    }

    public void setDefaultImage(int defaultImage) {
        this.defaultImage = defaultImage;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.event_listing_row, parent, false));

    }

    public void setClickListener(RecyclerClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setEvents(ArrayList<EventSet> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final EventSet event = events.get(position);
        holder.name.setText(event.getName());
        if (event.isFavourite())
            holder.favourite.setFavorite(true);
        else
            holder.favourite.setFavorite(false);

        if (!event.isImage_downloaded()) {
            if (event.getImg_link() == null || event.getImg_link().isEmpty()) {
                Picasso.with(context).load(defaultImage).centerCrop();
            } else {
                Picasso.with(context).load(event.getImg_link()).centerCrop().placeholder(context.getResources().getDrawable(defaultImage))
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
            }
        } else {

            holder.image.setImageBitmap(loadImageFromStorage(event.getImg_link(), event.getName()));
        }


        Animation animation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(300);
        animation.setInterpolator(new DecelerateInterpolator(1.5f));
        animation.setFillAfter(true);
        animation.setStartOffset(offset);
        holder.itemView.startAnimation(animation);
        offset = offset + 40;

    }

    @Override
    public void onViewRecycled(MyViewHolder holder) {
        super.onViewRecycled(holder);
        offset = 0;
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
        return events.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image, share;
        TextView name;
        MaterialFavoriteButton favourite;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.main_events);
            name = (TextView) itemView.findViewById(R.id.event_name);
            favourite = (MaterialFavoriteButton) itemView.findViewById(R.id.favourite_icon);
            share = (ImageView) itemView.findViewById(R.id.share_icon);
            if (clickListener != null) {
                favourite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickListener.onClick(v, getLayoutPosition()))
                            if (events.get(getLayoutPosition()).isFavourite()) {
                                favourite.setFavorite(true, true);
                            } else {
                                favourite.setFavorite(false, true);
                            }
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, EventDetailsActivity.class);
                        intent.putExtra("event_id", events.get(getLayoutPosition()).getId());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ActivityOptionsCompat options = ActivityOptionsCompat.
                                    makeSceneTransitionAnimation((Activity) context, image, "event_image");
                            context.startActivity(intent, options.toBundle());
                        } else {
                            context.startActivity(intent);
                        }
                    }
                });
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "rotationX", 0.0f, 360f);
                        animation.setDuration(500);
                        animation.setRepeatCount(0);
                        animation.setInterpolator(new AccelerateDecelerateInterpolator());
                        animation.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                clickListener.onClick(view, getLayoutPosition());
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });
                        animation.start();

                    }
                });
            }

        }
    }
}
