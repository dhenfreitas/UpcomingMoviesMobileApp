package dhenfreitas.com.upcomingmovies.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Diego on 06/12/2017.
 */

public class Genre {
    @SerializedName("id")
    @Expose
    private int genreID;

    @SerializedName("name")
    @Expose
    private String name;

    public int getGenreID() {
        return genreID;
    }

    public void setGenreID(int genreID) {
        this.genreID = genreID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
