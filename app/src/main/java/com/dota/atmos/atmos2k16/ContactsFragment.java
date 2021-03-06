package com.dota.atmos.atmos2k16;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dota.atmos.atmos2k16.Adapter.ContactAdapter;
import com.dota.atmos.atmos2k16.Set.Contacts;

import java.util.ArrayList;

import Helper.RecyclerClickListener;

/**
 * Created by SHREEDA on 24-09-2016.
 */

public class ContactsFragment extends android.support.v4.app.Fragment implements RecyclerClickListener {
    ContactAdapter contactAdapter;
    RecyclerView recyclerView;
    ArrayList<Contacts> data;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        data = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.contacts_recycler);

        contactAdapter = new ContactAdapter(getActivity());
        contactAdapter.setClickListener(this);
        recyclerView.setAdapter(contactAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2 ));
        contactAdapter.setArrayList(data);
        feedData();

    }

    private void feedData() {
        Contacts temp1 = new Contacts("Keshav Kedarnath", "President", "+917997030001", "president@hyderabad.bits-pilani.ac.in", R.drawable.keshav);
        Contacts temp2 = new Contacts("Sampath Balineni", "General Secretary", "+91 7997030002", "gensec@hyderabad.bits-pilani.ac.in", R.drawable.sampath);
        Contacts temp3 = new Contacts("Prashant Balana", "Sponsorship and Marketing", "+919912248413","prashant.balana@gmail.com", R.drawable.balana);
        Contacts temp4 = new Contacts("Rahul Somu", "Publicity and Social Media Relations", "+918499966778","rahulsomu9@gmail.com", R.drawable.somu);
        Contacts temp5 = new Contacts("Rajat Bansal", "Technical Convener", "+919912283346","rajatbansal427@gmail.com", R.drawable.rajat);
        Contacts temp6 = new Contacts("Prudthviraj", "Logistics & Operations", "+918096765563","prudthviraj666@gmail.com", R.drawable.pruthvi);
        Contacts temp7 = new Contacts("Akshay Borude", "Hospitality & Accom", "+919912366272","akshaykborude@gmail.comgmail.com", R.drawable.akshay);
        Contacts temp8 = new Contacts("Monil Shah", "Website & Creatives", "+91 9553305670","shahmonil1996@gmail.com", R.drawable.monil);
        Contacts temp9 = new Contacts("Ayush Beria", "Visual Effects", "+91 9553324287","ayush.beria@gmail.com", R.drawable.ayush);
        Contacts temp10 = new Contacts("Sajal Bansal", "Photography", "+91 9912293891","sajaldineshbansal@gmail.com", R.drawable.sajal);

        data.add(temp1);
        data.add(temp2);
        data.add(temp3);
        data.add(temp4);
        data.add(temp5);
        data.add(temp6);
        data.add(temp7);
        data.add(temp8);
        data.add(temp9);
        data.add(temp10);
        contactAdapter.notifyItemRangeInserted(0, data.size() - 1);
    }


    @Override
    public boolean onClick(View view, int position) {
        if (view.getId() == R.id.call) {
            Uri number = Uri.parse("tel:" + data.get(position).getMobile());
            startActivity(new Intent(Intent.ACTION_DIAL, number));


        } else if (view.getId() == R.id.email) {

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/html").putExtra(Intent.EXTRA_EMAIL, new String[]{data.get(position).getEmail()});
            startActivity(emailIntent);


        }
        return true;
    }
}
