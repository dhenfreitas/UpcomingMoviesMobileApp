package dhenfreitas.com.upcomingmovies.tasks;

import android.app.Activity;
import android.os.AsyncTask;

import java.util.HashMap;
import java.util.List;

import dhenfreitas.com.upcomingmovies.activities.MainActivity;
import dhenfreitas.com.upcomingmovies.interfaces.TheMoviesDatabaseAPIs;
import dhenfreitas.com.upcomingmovies.models.Genre;
import dhenfreitas.com.upcomingmovies.models.Object;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Diego on 06/12/2017.
 */

public class GenreTask extends AsyncTask<String, Void, Void> {

    private Activity activity;

    public GenreTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(String... params) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TheMoviesDatabaseAPIs.GENRE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TheMoviesDatabaseAPIs theMoviesDatabaseAPIs = retrofit.create(TheMoviesDatabaseAPIs.class);
        Call<Object> call = theMoviesDatabaseAPIs.getGenres("1f54bd990f1cdfb230adb312546d765d");
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                if(response.code() != 200) {
                    // Error handler


                } else {
                    Object object = response.body();

                    if(object != null ) {

                        List<Genre> genres = object.getGenres();

                        HashMap<Integer, String> genreHashMap = new HashMap<>();;

                        for(int i = 0; i<genres.size(); i++) {

                            genreHashMap.put(genres.get(i).getGenreID(), genres.get(i).getName());
                        }

                        ((MainActivity) activity).setGenreHashMap(genreHashMap);
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });

        return null;
    }
}
