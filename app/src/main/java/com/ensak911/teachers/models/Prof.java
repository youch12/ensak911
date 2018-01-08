package com.ensak911.teachers.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hp on 1/7/2018.
 */
public class Prof {
    @SerializedName("id")
    private String id;
    @SerializedName("nom")
    private String nom;
    @SerializedName("prenom")
    private String prenom;
    @SerializedName("email")
    private String email;
    @SerializedName("tel")
    private String tel;
    private String title;
    private String image;

    public Prof(String id, String email, String nom, String prenom, String tel, String title) {
        this.id = id;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.title = title;
    }

    public Prof(String id, String nom, String prenom, String email, String tel, String title, String image) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.tel = tel;
        this.title = title;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
