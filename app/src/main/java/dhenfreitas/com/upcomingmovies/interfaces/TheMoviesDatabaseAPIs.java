package dhenfreitas.com.upcomingmovies.interfaces;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import dhenfreitas.com.upcomingmovies.models.Object;
import dhenfreitas.com.upcomingmovies.models.Result;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Diego on 05/12/2017.
 */

public interface TheMoviesDatabaseAPIs {

    String BASE_URL = "https://api.themoviedb.org/3/movie/";
    String GENRE_URL = "http://api.themoviedb.org/3/genre/movie/";

    @Headers("Content-Type: application/json")
    @GET("list?")
    Call<Object> getGenres(@Query("api_key") String key);

    @Headers("Content-Type: application/json")
    @GET("upcoming?")
    Call<Object> getUpcomingMovies(@Query("api_key") String key, @Query("language") String language, @Query("page") int page);

    @Headers("Content-Type: application/json")
    @GET("now_playing?")
    Call<Object> getPlayingMovies(@Query("api_key") String key, @Query("language") String language, @Query("page") int page);

}
