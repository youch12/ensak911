package com.ensak911.emplois_annonces.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hp on 1/8/2018.
 */
public class AnnoncesList {
    @SerializedName("annonces")
    private ArrayList<Annonce> annoncesList;

    public ArrayList<Annonce> getAnnoncesList() {
        return annoncesList;
    }

    public void setAnnoncesList(ArrayList<Annonce> annoncesList) {
        this.annoncesList = annoncesList;
    }
}
