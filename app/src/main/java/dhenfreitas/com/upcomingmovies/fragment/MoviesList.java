//package dhenfreitas.com.upcomingmovies.fragment;
//
//import android.app.ActionBar;
//import android.media.Image;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.DividerItemDecoration;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.daimajia.swipe.util.Attributes;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import dhenfreitas.com.upcomingmovies.activities.MainActivity;
//import dhenfreitas.com.upcomingmovies.adapter.MovieListAdapter;
//import dhenfreitas.com.upcomingmovies.models.Object;
//import dhenfreitas.com.upcomingmovies.R;
//import dhenfreitas.com.upcomingmovies.utils.Verbose;
//
///**
// * Created by Diego on 05/12/2017.
// */
//
//public class MoviesList extends Fragment {
//
//    public static MoviesList newInstance() {
//        MoviesList fragment = new MoviesList();
//        return fragment;
//    }
//
//    private RecyclerView sicRecyclerView;
//    private RecyclerView.Adapter sicAdapter;
//
//    private String origin;
//    private Object carouselKeys;
//    private List<String> idLabelSet;
//    private ArrayList<String> fullLabelSet;
//    private ArrayList<String> brandLabelSet;
//    private ArrayList<String> colourLabelSet;
//    private ArrayList<String> compositionLabelSet;
//    private ArrayList<String> compositionPercentageLabelSet;
//    private ArrayList<String> collectionYearLabelSet;
//    private List<Image> clothImageSet;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//
//        View similarityCollectionView = inflater.inflate(R.layout.similar_list_menu, container, false);
//
//        //View trendingView = inflater.inflate(R.layout.fragment_trending, container, false);
//        sicRecyclerView = (RecyclerView) similarityCollectionView.findViewById(R.id.recycler_view);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            ActionBar actionBar = getActivity().getActionBar();
//            if (actionBar != null) {
//                actionBar.setTitle("RecyclerView");
//            }
//        }
//
//        sicRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
//
//        sicRecyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getContext(), R.drawable.divider)));
//
//        Bundle bundle = getArguments();
//
//        carouselKeys = ((MainActivity)getActivity()).getHashMapMovieObjects();
//
//        fullLabelSet = new ArrayList<String>();
//
//        brandLabelSet = new ArrayList<String>();
//
//        colourLabelSet = new ArrayList<String>();
//
//        compositionLabelSet = new ArrayList<String>();
//
//        collectionYearLabelSet = new ArrayList<String>();
//
//        clothImageSet = new ArrayList<>();
//        clothImageSet = ((MainActivity)getActivity()).getClothImageSet();
//
//        TemporaryItem carousel = carouselKeys.get(Verbose.ITEMS);
//
//        for (Object e : carouselKeys.values()) {
//            if(e == null ) {
//                fullLabelSet.add(" ");
//                brandLabelSet.add(" ");
//                colourLabelSet.add(" ");
//                compositionLabelSet.add(" ");
//                collectionYearLabelSet.add(" ");
//
//            } else {
//                fullLabelSet.add(e.getItemBrand() + e.getItemTypology()
//                        + e.getItemCollection() + e.getItemYear());
//
//                brandLabelSet.add(e.getItemBrand());
//
//                colourLabelSet.add(e.getItemColor());
//
//                compositionLabelSet.add(e.getItemComposition().get(0).getName() + e.getItemComposition().get(0).getPercent());
//                //ArrayList<ItemComposition> compositionlabel = carousel.getItemComposition();
//
//                collectionYearLabelSet.add(e.getItemCollection() + e.getItemYear());
//            }
//
//        }
//
//        sicAdapter
//                = new MovieListAdapter(this.getActivity(), carouselKeys);
//        ((MovieListAdapter) sicAdapter).setMode(Attributes.Mode.Single);
//        sicRecyclerView.setAdapter(sicAdapter);
//
//        /* Listeners */
//        sicRecyclerView.setOnScrollListener(onScrollListener);
//
//        return similarityCollectionView;
//    }
//
//    /**
//     * Substitute for our onScrollListener for RecyclerView
//     */
//    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
//        @Override
//        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//            super.onScrollStateChanged(recyclerView, newState);
//            Log.e("ListView", "onScrollStateChanged");
//        }
//
//        @Override
//        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            super.onScrolled(recyclerView, dx, dy);
//            // Could hide open views here if you wanted. //
//        }
//    };
//}