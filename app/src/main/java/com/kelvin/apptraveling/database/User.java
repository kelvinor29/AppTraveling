package com.kelvin.apptraveling.database;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String userName;
    private String userEmail;
    private String userPassword;
    private String userAge;

    // Constructor
    public User(String userName, String userEmail, String userPassword, String userAge) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userAge = userAge;
    }

    // Utilizacion del Parcelable para que pueda enviarse a travez de bundle
    protected User(Parcel in) {
        userName = in.readString();
        userEmail = in.readString();
        userPassword = in.readString();
        userAge = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(userEmail);
        dest.writeString(userPassword);
        dest.writeString(userAge);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", Email='" + userEmail + '\'' +
                ", Password='" + userPassword + '\'' +
                ", Age='" + userAge + '\'' +
                '}';
    }
}
