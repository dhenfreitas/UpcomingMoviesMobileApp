package dhenfreitas.com.upcomingmovies.tasks;

import android.app.Activity;
import android.os.AsyncTask;

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

public class MoviesTask extends AsyncTask<String, Void, Void> {

    private Activity activity;
    private int currentPage;

    public MoviesTask(Activity activity, int currentPage) {
        this.activity = activity;
        this.currentPage = currentPage;
    }

    @Override
    protected Void doInBackground(String... params) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TheMoviesDatabaseAPIs.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TheMoviesDatabaseAPIs theMoviesDatabaseAPIs = retrofit.create(TheMoviesDatabaseAPIs.class);
        Call<Object> call = theMoviesDatabaseAPIs.getPlayingMovies("1f54bd990f1cdfb230adb312546d765d", "en-US", currentPage);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                if(response.code() != 200) {
                    // Error handler


                } else {
                    Object object = response.body();

                    if(object != null ) {

                        ((MainActivity) activity).addNewMovies(object.getResults());
                    }

                    if(currentPage < object.getTotalPages()) {
                        MoviesTask moviesTask = new MoviesTask(activity, currentPage+1);
                        moviesTask.execute();
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
