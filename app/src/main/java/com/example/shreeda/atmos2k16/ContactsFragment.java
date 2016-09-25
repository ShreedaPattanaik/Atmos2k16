package com.example.shreeda.atmos2k16;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shreeda.atmos2k16.Adapter.ContactAdapter;
import com.example.shreeda.atmos2k16.Set.Contacts;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        contactAdapter.setArrayList(data);
        feedData();

    }

    private void feedData() {
        Contacts temp1 = new Contacts("Keshav Kedarnath", "President", "+917997030001", "president@hyderabad.bits-pilani.ac.in", R.drawable.ic_insert_emoticon_black_24dp);
        Contacts temp2 = new Contacts("Sampath Balineni", "General Secretary", "+91 7997030002", "gensec@hyderabad.bits-pilani.ac.in", R.drawable.ic_insert_emoticon_black_24dp);
        Contacts temp3 = new Contacts("Prashant Balana", "Sponsorship and Marketing", "+919912248413","prashant.balana@gmail.com", R.drawable.ic_insert_emoticon_black_24dp);
        Contacts temp4 = new Contacts("Rahul Somu", "Publicity and Social Media Relations", "+918499966778","rahulsomu9@gmail.com", R.drawable.ic_insert_emoticon_black_24dp);
        Contacts temp5 = new Contacts("Rajat Bansal", "Technical Convener", "+919912283346","rajatbansal427@gmail.com", R.drawable.ic_insert_emoticon_black_24dp);
        Contacts temp6 = new Contacts("Prudthviraj Vanaparthi", "Logistics & Operations", "+918096765563","prudthviraj666@gmail.com", R.drawable.ic_insert_emoticon_black_24dp);
        Contacts temp7 = new Contacts("Akshay Borude", "Hospitality & Accommodation", "+919912366272","akshaykborude@gmail.comgmail.com", R.drawable.ic_insert_emoticon_black_24dp);
        Contacts temp8 = new Contacts("Monil Shah", "Website & Creatives", "+91 9553305670","shahmonil1996@gmail.com", R.drawable.ic_insert_emoticon_black_24dp);
        data.add(temp1);
        data.add(temp2);
        data.add(temp3);
        data.add(temp4);
        data.add(temp5);
        data.add(temp6);
        data.add(temp7);
        data.add(temp8);
        contactAdapter.notifyItemRangeInserted(0, data.size() - 1);
    }


    @Override
    public void onClick(View view, int position) {
        if (view.getId() == R.id.call) {
            Uri number = Uri.parse("tel:" + data.get(position).getMobile());
            startActivity(new Intent(Intent.ACTION_DIAL, number));


        } else if (view.getId() == R.id.email) {

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/html").putExtra(Intent.EXTRA_EMAIL, new String[]{data.get(position).getEmail()});
            startActivity(emailIntent);


        }
    }
}
