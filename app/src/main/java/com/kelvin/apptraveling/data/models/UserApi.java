package com.kelvin.apptraveling.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserApi implements Serializable {

    @SerializedName("nombre")
    private String username;

    @SerializedName("edad")
    private Integer age;

    @SerializedName("genero")
    private String genre;
    private String userToken;
    private Integer idBdReference;

    public UserApi(String username, Integer age, String genre, String userToken, Integer idBdReference) {
        this.username = username;
        this.age = age;
        this.genre = genre;
        this.userToken = userToken;
        this.idBdReference = idBdReference;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Integer getIdBdReference() {
        return idBdReference;
    }

    public void setIdBdReference(Integer idBdReference) {
        this.idBdReference = idBdReference;
    }

    @Override
    public String toString() {
        return "UserApi{" +
                "username='" + username + '\'' +
                ", age=" + age +
                ", genre='" + genre + '\'' +
                ", userToken='" + userToken + '\'' +
                ", idBdReference=" + idBdReference +
                '}';
    }


}
