package com.ensak911.emplois_annonces.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hp on 1/8/2018.
 */
public class EmploisList {
    @SerializedName("emplois")
    private ArrayList<Emploi> emploisList;

    public ArrayList<Emploi> getEmploisList() {
        return emploisList;
    }

    public void setEmploisList(ArrayList<Emploi> emploisList) {
        this.emploisList = emploisList;
    }
}
