package com.dota.atmos.atmos2k16.Set;

import android.database.Cursor;
import android.util.Log;

import com.dota.atmos.atmos2k16.TableManagers.EventTableManager;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by SHREEDA on 26-09-2016.
 */

public class EventSet {
    private int id;
    private String name, description, fbUrl, contacts, pdfLink, prize, probSt, tab;
    private int type;
    private String img_link;
    private boolean image_downloaded;
    private boolean favourite;


    /**
     * Event set Constructor
     *
     * @param c Cursor from table
     * @param b true if all data need to be added , else false;
     */
    public EventSet(Cursor c, boolean b) {
        this.id = c.getInt(c.getColumnIndex(EventTableManager.KEY_EVENT_ID));
        this.name = c.getString(c.getColumnIndex(EventTableManager.KEY_NAME));
        this.fbUrl = c.getString(c.getColumnIndex(EventTableManager.KEY_FBURL));
        this.tab = c.getString(c.getColumnIndex(EventTableManager.KEY_TAB));
        this.type = c.getInt(c.getColumnIndex(EventTableManager.KEY_TYPE));
        this.img_link = c.getString(c.getColumnIndex(EventTableManager.KEY_IMAGE_LINK));
        this.image_downloaded = c.getInt(c.getColumnIndex(EventTableManager.KEY_IMAGE_DOWNLOAD)) == 1;
        this.favourite = c.getInt(c.getColumnIndex(EventTableManager.KEY_FAVOURITE)) == 1;

        if (b) {
            this.description = c.getString(c.getColumnIndex(EventTableManager.KEY_DESCRIPTION));
            this.contacts = c.getString(c.getColumnIndex(EventTableManager.KEY_CONTACTS));
            Log.e("eventset", c.getString(c.getColumnIndex(EventTableManager.KEY_CONTACTS)));
            this.pdfLink = c.getString(c.getColumnIndex(EventTableManager.KEY_PDFLINK));
            this.prize = c.getString(c.getColumnIndex(EventTableManager.KEY_PRIZE));
            this.probSt = c.getString(c.getColumnIndex(EventTableManager.KEY_PROBST));
        } else {
            this.description = "";
            this.contacts = "";
            this.pdfLink = "";
            this.prize = "";
            this.probSt = "";
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFbUrl() {
        return fbUrl;
    }

    public JSONArray getContacts() {

        try {
            JSONArray array = new JSONArray(contacts);
            Log.e("Evenset",array.toString());
            return array;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("eventSetError",e.toString());

        }
        return null;
    }

    public String getPdfLink() {
        return pdfLink;
    }

    public String getPrize() {
        return prize;
    }

    public String getProbSt() {
        return probSt;
    }

    public String getTab() {
        return tab;
    }

    public int getType() {
        return type;
    }

    public String getImg_link() {
        return img_link;
    }

    public boolean isImage_downloaded() {
        return image_downloaded;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFav(boolean fav) {
        this.favourite = fav;
    }
}
