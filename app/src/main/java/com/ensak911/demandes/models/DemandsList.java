package com.ensak911.demandes.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hp on 1/8/2018.
 */
public class DemandsList {
    @SerializedName("demandes")
    private ArrayList<Demand> demandesList;

    public DemandsList(ArrayList<Demand> demandesList) {
        this.demandesList = demandesList;
    }

    public DemandsList() {
    }

    public ArrayList<Demand> getDemandesList() {
        return demandesList;
    }

    public void setDemandesList(ArrayList<Demand> demandesList) {
        this.demandesList = demandesList;
    }
}