package com.ensak911.emplois_annonces.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by hp on 1/7/2018.
 */
public class Annonce{
    @SerializedName("id")
    private int id;
    @SerializedName("date")
    private Date date;
    @SerializedName("contenu")
    private String contenu;
    @SerializedName("attachement")
    private String attachement;

    public Annonce( String contenu,Date date) {
        this.date = date;
        this.contenu = contenu;
    }
    public Annonce( Date date,  String attachement) {
        this.date = date;
        this.attachement = attachement;
    }

    public Annonce(int id, Date date, String contenu, String attachement) {
        this.id = id;
        this.date = date;
        this.contenu = contenu;
        this.attachement = attachement;
    }

    public Annonce() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getAttachement() {
        return attachement;
    }

    public void setAttachement(String attachement) {
        this.attachement = attachement;
    }
}
