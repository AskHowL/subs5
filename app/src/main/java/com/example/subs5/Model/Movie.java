package com.example.subs5.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Movie implements Parcelable {

    private int id;
    private String img;
    private String title;
    private String desc;
    private String is_movie;



    public Movie(JSONObject object) {
        try {
            int id = object.getInt("id");
            String title = object.getString("title");
            String desc = object.getString("overview");
            String img = "https://image.tmdb.org/t/p/w92" + object.getString("poster_path");

            this.id = id;
            this.title = title;
            this.desc = desc;
            this.img = img;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getIs_movie() {
        return is_movie;
    }

    public void setIs_movie(String is_movie) {
        this.is_movie = is_movie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.img);
        parcel.writeString(this.title);
        parcel.writeString(this.desc);
        parcel.writeInt(this.id);
        parcel.writeString(this.is_movie);
    }

    protected Movie(Parcel in) {
        this.img = in.readString();
        this.title = in.readString();
        this.desc = in.readString();
        this.id = in.readInt();
        this.is_movie = in.readString();
    }

    public Movie() {
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
