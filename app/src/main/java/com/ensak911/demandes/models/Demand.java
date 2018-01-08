package com.ensak911.demandes.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by P on 16/11/2017.
 */

public class Demand {
    @SerializedName("id")
    private String id;
    @SerializedName("titre")
    private String titre;
    @SerializedName("type")
    private String type;
    @SerializedName("description")
    private String desciption;
    @SerializedName("dateCreation")
    private Date dateCreation;
    @SerializedName("dateLivraison")
    private Date dateLivraison;
    @SerializedName("accuseReception")
    private Date accuseReception;
    @SerializedName("nAppogee")
    private String nAppogee;

    public Demand(String titre, Date dateCreation) {
        this.titre = titre;
        this.dateCreation = dateCreation;
    }
    public Demand(String titre, Date dateCreation, Date accuseReception) {
        this.titre = titre;
        this.dateCreation = dateCreation;
        this.accuseReception=accuseReception;
    }

    public Demand(String id, String titre, String type, String desciption, Date dateCreation, Date dateLivraison, String nAppogee) {
        this.id = id;
        this.titre = titre;
        this.type = type;
        this.desciption = desciption;
        this.dateCreation = dateCreation;
        this.dateLivraison = dateLivraison;
        this.nAppogee=nAppogee;
    }

    public Demand(String id, String titre, String type, String desciption, Date dateCreation, Date dateLivraison, Date accuseReception, String nAppogee) {
        this.id = id;
        this.titre = titre;
        this.type = type;
        this.desciption = desciption;
        this.dateCreation = dateCreation;
        this.dateLivraison = dateLivraison;
        this.accuseReception = accuseReception;
        this.nAppogee = nAppogee;
    }

    public Date getAccuseReception() {
        return accuseReception;
    }

    public void setAccuseReception(Date accuseReception) {
        this.accuseReception = accuseReception;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public String getnAppogee() {
        return nAppogee;
    }

    public void setnAppogee(String nAppogee) {
        this.nAppogee = nAppogee;
    }
}
