package dhenfreitas.com.upcomingmovies.utils;

import android.app.Activity;

import dhenfreitas.com.upcomingmovies.activities.MainActivity;
import dhenfreitas.com.upcomingmovies.interfaces.TheMoviesDatabaseAPIs;
import dhenfreitas.com.upcomingmovies.models.Object;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Diego on 06/12/2017.
 */

public class FindMovies {

    Activity activity;

    public void buildMoviesList(final Activity activity, String movieID) {
        this.activity = activity;

        if(Util.isNetworkAvailable(activity)) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(TheMoviesDatabaseAPIs.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            TheMoviesDatabaseAPIs theMoviesDatabaseAPIs = retrofit.create(TheMoviesDatabaseAPIs.class);
            Call<Object> call;
//            if(movieID != null || movieID.length() > 0) {
//                call = theMoviesDatabaseAPIs.getUpcomingMovies("1f54bd990f1cdfb230adb312546d765d", "en-US", 1);
//            } else {
//                call = theMoviesDatabaseAPIs.getUpcomingMovies("1f54bd990f1cdfb230adb312546d765d", "en-US", 1);
//            }

            call = theMoviesDatabaseAPIs.getUpcomingMovies("1f54bd990f1cdfb230adb312546d765d", "en-US", 1);

            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {

                    if(response.code() != 200) {
                        // Error handler


                    } else {
                        Object object = response.body();

                        if(object != null ) {

                            ((MainActivity) activity).setMoviesDBTotalPages(object.getTotalPages());

                            ((MainActivity) activity).addNewMovies(object.getResults());
                        }
                    }

                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {

                }
            });
        } else {
            // Sem internet

        }

    }
}
