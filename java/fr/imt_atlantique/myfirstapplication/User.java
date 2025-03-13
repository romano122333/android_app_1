package fr.imt_atlantique.myfirstapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String prenom, nom, ville_naissance, tel_1, tel_2, tel_3;
    private int departement_naissance, birthYear, birthMonth, birthDay;
    protected User(Parcel in) {
        prenom = in.readString();
        nom = in.readString();
        departement_naissance = in.readInt();
        ville_naissance = in.readString();
        birthYear = in.readInt();
        birthMonth = in.readInt();
        birthDay = in.readInt();
        tel_1 = in.readString();
        tel_2 = in.readString();
        tel_3 = in.readString();
    }

    public User(String prenom, String nom, int departement_naissance, String ville_naissance,
                int birthYear, int birthMonth, int birthDay, String tel_1, String tel_2, String tel_3) {
        this.prenom = prenom;
        this.nom = nom;
        this.departement_naissance = departement_naissance;
        this.ville_naissance = ville_naissance;
        this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
        this.tel_1 = tel_1;
        this.tel_2 = tel_2;
        this.tel_3 = tel_3;
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

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public int getDepartementNaissance() {
        return departement_naissance;
    }

    public int getBirthYear(){
        return birthYear;
    }

    public int getBirthMonth(){
        return birthMonth;
    }

    public int getBirthDay(){
        return birthDay;
    }

    public String getTel_1(){
        return tel_1;
    }

    public String getTel_2(){
        return tel_2;
    }

    public String getTel_3(){
        return tel_3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getVille_naissance() {
        return ville_naissance;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(prenom);
        parcel.writeString(nom);
        parcel.writeInt(departement_naissance);
        parcel.writeString(ville_naissance);
        parcel.writeInt(birthYear);
        parcel.writeInt(birthMonth);
        parcel.writeInt(birthDay);
        parcel.writeString(tel_1);
        parcel.writeString(tel_2);
        parcel.writeString(tel_3);
    }

}
