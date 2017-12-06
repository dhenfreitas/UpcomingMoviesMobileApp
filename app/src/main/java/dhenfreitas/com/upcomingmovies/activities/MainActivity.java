package dhenfreitas.com.upcomingmovies.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dhenfreitas.com.upcomingmovies.R;
import dhenfreitas.com.upcomingmovies.adapter.MovieListAdapter;
import dhenfreitas.com.upcomingmovies.models.Object;
import dhenfreitas.com.upcomingmovies.models.Result;
import dhenfreitas.com.upcomingmovies.tasks.GenreTask;
import dhenfreitas.com.upcomingmovies.utils.FindMovies;

public class MainActivity extends FragmentActivity {

    private Object hashMapMovieObjects;
    private HashMap<Integer, String> genreHashMap;
    private int MoviesDBTotalPages;
    InputMethodManager imm;

    // The data to show
    List<Result> moviesList = new ArrayList<>();
    MovieListAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String movieID = "";

        GenreTask genreTask = new GenreTask(this);
        genreTask.execute();

        FindMovies findMovies = new FindMovies();
        findMovies.buildMoviesList(this, movieID);

        // We get the ListView component from the layout
        ListView lv = (ListView) findViewById(R.id.listView);

        // This adapter will receive the first list of movies
        // Newly retrieved movies will be added continuously
        moviesAdapter = new MovieListAdapter(moviesList, this);
        lv.setAdapter(moviesAdapter);

        // React to user clicks on item
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
                                    long id) {

                imm = (InputMethodManager)getSystemService(Context.
                        INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                // The view is a Relative Layout whose children are:
                // [0] poster, [1] title, [2] genre, [3] overview, [4] release data
                TextView titleName = (TextView) ((RelativeLayout) view).getChildAt(1);
                TextView genreName = (TextView) ((RelativeLayout) view).getChildAt(2);

                Toast.makeText(MainActivity.this, "Movie ["+titleName.getText()+"] - \nGenres ["+genreName.getText()+"]", Toast.LENGTH_SHORT).show();

            }
        });

        // we register for the contextmneu
        registerForContextMenu(lv);

        // TextFilter
        lv.setTextFilterEnabled(true);
        EditText editTxt = (EditText) findViewById(R.id.editTxt);

        editTxt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Text ["+s+"] - Start ["+start+"] - Before ["+before+"] - Count ["+count+"]");
                if (count < before) {
                    // We're deleting char so we need to reset the adapter data
                    moviesAdapter.resetData();
                }

                moviesAdapter.getFilter().filter(s.toString());

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        return true;
    }


    // A context Menu appear when the user long click on an item
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo aInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;

        Result movie =  moviesAdapter.getItem(aInfo.position);

        menu.setHeaderTitle("Options for " + movie.getTitle());
        menu.add(1, 1, 1, "Details");
        menu.add(1, 2, 2, "Delete");

    }

    // This method is called when user selects an Item in the Context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case 1:
                break;

            case 2:
                AdapterView.AdapterContextMenuInfo aInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                moviesList.remove(aInfo.position);
                moviesAdapter.notifyDataSetChanged();
                break;
        }
        return true;
    }


    public void addNewMovies(List<Result> results) {

        // Incrementing the list of movies
        for(int i = 0; i < results.size(); i++) {
            MainActivity.this.moviesList.add(results.get(i));
            MainActivity.this.moviesAdapter.notifyDataSetChanged(); // notify when the data model is changed
        }
    }

    public HashMap<Integer, String> getGenreHashMap() {
        return genreHashMap;
    }

    public void setGenreHashMap(HashMap<Integer, String> genreHashMap) {
        this.genreHashMap = genreHashMap;
    }

    public int getMoviesDBTotalPages() {
        return MoviesDBTotalPages;
    }

    public void setMoviesDBTotalPages(int moviesDBTotalPages) {
        MoviesDBTotalPages = moviesDBTotalPages;
    }
}
