package dhenfreitas.com.upcomingmovies.fragment;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import dhenfreitas.com.upcomingmovies.R;
import dhenfreitas.com.upcomingmovies.activities.MainActivity;
import dhenfreitas.com.upcomingmovies.models.Result;
import dhenfreitas.com.upcomingmovies.utils.Util;
import dhenfreitas.com.upcomingmovies.utils.Verbose;

/**
 * Created by Diego on 06/12/2017.
 */

public class SingleFlipcard extends Fragment {
    private ImageView flipcardPhotoPreview;

    private TextView movieName;
    private TextView movieGenre;
    private TextView movieReleaseData;
    private TextView movieOverview;

    Bitmap decodedByte;

    Typeface light;
    Typeface black;

    public static SingleFlipcard newInstance() {
        SingleFlipcard fragment = new SingleFlipcard();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View flipcardView = inflater.inflate(R.layout.fragment_single_flipcard, container, false);

        light = Typeface.createFromAsset(getContext().getAssets(),"Lato-Light.ttf");
        black = Typeface.createFromAsset(getContext().getAssets(),"Lato-Black.ttf");

        flipcardPhotoPreview = (ImageView) flipcardView.findViewById(R.id.flipcardPhotoPreview);

        movieName = (TextView) flipcardView.findViewById(R.id.movieName);
        movieGenre = (TextView) flipcardView.findViewById(R.id.movieGenre);
        movieReleaseData = (TextView) flipcardView.findViewById(R.id.movieReleaseData);
        movieOverview = (TextView) flipcardView.findViewById(R.id.movieOverview);

        LinearLayout wholeFlipcard =  (LinearLayout) flipcardView.findViewById(R.id.wholeFlipcard);

        wholeFlipcard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        Bundle bundle = getArguments();

        Result movieInfo = (Result) bundle.getSerializable(Verbose.SINGLE_MOVIE);

        movieName.setTypeface(black);
        movieGenre.setTypeface(light);
        movieReleaseData.setTypeface(light);
        movieOverview.setTypeface(light);

        movieName.setText(movieInfo.getTitle());

        String fullGenres = "";

        for(int i = 0; i < movieInfo.getGenreIDs().size(); i++) {
            fullGenres = fullGenres + ((MainActivity) getActivity()).getGenreHashMap().get(movieInfo.getGenreIDs().get(i)) + " ";
        }

        movieGenre.setText(fullGenres);

        movieReleaseData.setText(movieInfo.getReleaseDate());

        movieOverview.setText(movieInfo.getOverview() + "\n");

        String imageURL = Verbose.MOVIE_DB_IMAGE_BASE_URL + movieInfo.getPosterPath();

        Util.setImage(getActivity(), flipcardPhotoPreview, imageURL);


        View.OnClickListener cancel = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                closeFlipcard();
            }
        };

        flipcardPhotoPreview.setOnClickListener(cancel);

        flipcardView.setOnClickListener(cancel);

        TableRow flipcardPhotoField = (TableRow) flipcardView.findViewById(R.id.flipcardPhotoField);

        flipcardPhotoField.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                closeFlipcard();
                return true;
            }
        });

        return flipcardView;
    }

    private void closeFlipcard() {
        android.support.v4.app.FragmentManager manager = getFragmentManager();

        if (manager != null)
        {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            SingleFlipcard currFrag = (SingleFlipcard) manager.findFragmentByTag(Verbose.FLIP_CARD);

            transaction.remove(currFrag).commit();

        }
    }

}
