package com.ensak911.emplois_annonces.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by hp on 1/7/2018.
 */
public class Emploi {
    @SerializedName("id")
    private int id;
    @SerializedName("date")
    private Date date;
    @SerializedName("contenu")
    private String contenu;
    @SerializedName("attachement")
    private String attachement;
    private String filliere;
    private String option;
    private String annee;

    public Emploi(int id, String attachement, String contenu, Date date) {
        this.id = id;
        this.attachement = attachement;
        this.contenu = contenu;
        this.date = date;
    }

    public Emploi( String contenu,Date date) {
        this.date = date;
        this.contenu = contenu;
    }
    public Emploi( Date date,  String attachement) {
        this.date = date;
        this.attachement = attachement;
    }
    public Emploi(int id, Date date, String contenu, String attachement, String filliere, String option, String annee) {
        this.id = id;
        this.date = date;
        this.contenu = contenu;
        this.attachement = attachement;
        this.filliere = filliere;
        this.option = option;
        this.annee = annee;
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

    public String getFilliere() {
        return filliere;
    }

    public void setFilliere(String filliere) {
        this.filliere = filliere;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }
}
