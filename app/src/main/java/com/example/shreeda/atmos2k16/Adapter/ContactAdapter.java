package com.example.shreeda.atmos2k16.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.shreeda.atmos2k16.R;
import com.example.shreeda.atmos2k16.Set.Contacts;

import java.util.ArrayList;
import java.util.zip.Inflater;

import Helper.RecyclerClickListener;

import static android.R.attr.data;
import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by SHREEDA on 23-09-2016.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {
    ArrayList<Contacts> arrayList;
    Context context;
    LayoutInflater inflater;

    RecyclerClickListener clickListener;

    public void setClickListener(RecyclerClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public ContactAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        arrayList = new ArrayList<>();
        this.context = context;
    }

    public void setArrayList(ArrayList<Contacts> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(inflater.inflate(R.layout.contact_row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {
        myViewHolder.name.setText(arrayList.get(i).getName());
        myViewHolder.designation.setText((arrayList.get(i).getDesignation()));
        myViewHolder.imageView.setImageResource(arrayList.get(i).getImage());

    }

    private void showpopupmenu(View view, final int pos) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_contacts, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.phone:
                        Uri number = Uri.parse("tel:" + arrayList.get(pos).getMobile());
                        Intent intent = new Intent(Intent.ACTION_DIAL, number);
                        context.startActivity(intent);



                    case  R.id.email:
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setType("text/html").putExtra(Intent.EXTRA_EMAIL, new String[]{arrayList.get(pos).getEmail()});
                        context.startActivity(emailIntent);

                }
                return false;
            }
        });
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name,designation,numberTV,emailTV;
        ImageButton more;
//        RelativeLayout email,mobile;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.pic_left);
            name=(TextView) itemView.findViewById(R.id.name_left);
            designation=(TextView)itemView.findViewById(R.id.post_left);
            more = (ImageButton) itemView.findViewById(R.id.menu_left);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showpopupmenu(more, getLayoutPosition());
                }
            });
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showpopupmenu(more, getLayoutPosition());
                }
            });
//            mobile=(RelativeLayout)itemView.findViewById(R.id.call);
//            email=(RelativeLayout)itemView.findViewById(R.id.email);
//            numberTV=(TextView) itemView.findViewById(R.id.phoneno);
//            emailTV=(TextView) itemView.findViewById(R.id.emailTV);
////            if(clickListener!=null)
//            {
//                mobile.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        clickListener.onClick(v, getAdapterPosition());
//                    }
//                });
//                email.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        clickListener.onClick(v, getAdapterPosition());
//                    }
//                });
            }
        }
    }

