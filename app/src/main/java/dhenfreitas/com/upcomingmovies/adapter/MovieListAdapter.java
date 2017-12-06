package dhenfreitas.com.upcomingmovies.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dhenfreitas.com.upcomingmovies.R;
import dhenfreitas.com.upcomingmovies.activities.MainActivity;
import dhenfreitas.com.upcomingmovies.models.Result;
import dhenfreitas.com.upcomingmovies.utils.Util;
import dhenfreitas.com.upcomingmovies.utils.Verbose;

/**
 * Created by Diego on 06/12/2017.
 */

public class MovieListAdapter extends ArrayAdapter<Result> implements Filterable {

    private List<Result> resultList;
    private Activity activity;
    private Filter resultFilter;
    private List<Result> origResultList;

    public MovieListAdapter(List<Result> resultList, Activity activity) {
        super(activity, R.layout.row_list_layout, resultList);
        this.resultList = resultList;
        this.activity = activity;
        this.origResultList = resultList;
    }

    public int getCount() {
        return resultList.size();
    }

    public Result getItem(int position) {
        return resultList.get(position);
    }

    public long getItemId(int position) {
        return resultList.get(position).hashCode();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        MoviesHolder holder = new MoviesHolder();

        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.row_list_layout, null);

            // Now we can fill the layout with the right values
            ImageView moviePosterView = (ImageView) v.findViewById(R.id.poster);
            TextView titleMovieView = (TextView) v.findViewById(R.id.name);
            TextView genresView = (TextView) v.findViewById(R.id.genres);
            TextView releaseDataView = (TextView) v.findViewById(R.id.releaseData);
            TextView overviewView = (TextView) v.findViewById(R.id.overview);

            holder.moviePosterView = moviePosterView;
            holder.movieTitleView = titleMovieView;
            holder.genreView = genresView;
            holder.releaseDateView = releaseDataView;
            holder.overviewView = overviewView;


            v.setTag(holder);
        }
        else
            holder = (MoviesHolder) v.getTag();

        Result r = resultList.get(position);

        String imageURL = Verbose.MOVIE_DB_IMAGE_BASE_URL + r.getPosterPath();

        Util.setImage(activity, holder.moviePosterView, imageURL);

        holder.movieTitleView.setText(r.getTitle());

        String fullGenres = "";

        for(int i = 0; i < r.getGenreIDs().size(); i++) {
            fullGenres = fullGenres + ((MainActivity) activity).getGenreHashMap().get(r.getGenreIDs().get(i)) + " ";
        }

        holder.genreView.setText(fullGenres);

        holder.releaseDateView.setText(r.getReleaseDate());

        holder.overviewView.setText(r.getOverview() + "\n");

        return v;
    }

    public void resetData() {
        resultList = origResultList;
    }

    // Holder for faster viewing and protect the component
    private static class MoviesHolder {
        public ImageView moviePosterView;
        public TextView movieTitleView;
        public TextView genreView;
        public TextView overviewView;
        public TextView releaseDateView;
    }

	// For filtering
    @Override
    public Filter getFilter() {
        if (resultFilter == null)
            resultFilter = new MoviesFilter();

        return resultFilter;
    }

    private class MoviesFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = origResultList;
                results.count = origResultList.size();
            }
            else {
                // We perform filtering operation
                List<Result> nMoviesList = new ArrayList<Result>();

                for (Result p : resultList) {
                    if (p.getTitle().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        nMoviesList.add(p);
                }

                results.values = nMoviesList;
                results.count = nMoviesList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                resultList = (List<Result>) results.values;
                notifyDataSetChanged();
            }

        }
    }
}
