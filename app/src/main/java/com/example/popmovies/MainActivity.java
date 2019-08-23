package com.example.popmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.popmovies.database.AppDatabase;
import com.example.popmovies.network.APICallFactory;
import com.example.popmovies.network.response.GenreResponse;
import com.example.popmovies.network.response.MovieResponse;
import com.example.popmovies.network.response.ReviewResponse;
import com.example.popmovies.network.response.TrailerResponse;
import com.example.popmovies.ui.pages.moviedetails.MovieDetailsFragment;
import com.example.popmovies.ui.pages.movies.MoviesFragment;
import com.example.popmovies.ui.pages.movies.MoviesViewModel;
import com.example.popmovies.utilities.DataFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String MOVIES_BUNDLE_KEY = "movies";
    public static final String SORT_TYPE_KEY = "sort_type";

    private ProgressBar mProgressBar;
    private TextView mErrorMessage;
    private static String[] mSortTypes;
    private String mCurrentSort;
    private int mMovieId = -1;
    private AppDatabase mAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mErrorMessage = (TextView) findViewById(R.id.error_message);

        mSortTypes = getResources().getStringArray(R.array.spinner_array);

        mCurrentSort = mSortTypes[0];

        APICallFactory.getConfiguration();

        mAppDatabase = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null) {
            mCurrentSort = savedInstanceState.getString(SORT_TYPE_KEY);
        } else {
            mCurrentSort = mSortTypes[0];
            retrieveMovies();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(SORT_TYPE_KEY, mCurrentSort);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentSort = savedInstanceState.getString(SORT_TYPE_KEY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_spinner);
        Spinner spinner = (Spinner) item.getActionView();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_drop_down_item);

        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getPosition(mCurrentSort));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!mCurrentSort.equals(mSortTypes[position])) {
                    mCurrentSort = mSortTypes[position];
                    if (position == 2) {
                        setupMoviesViewModel();
                    } else {
                        retrieveMovies();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_spinner) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    //----- Protected methods -----//

    public void loadMovieDetails(int movieId) {
        mMovieId = movieId;

        mProgressBar.setVisibility(View.VISIBLE);

        showProgressBar(true);

        retrieveGenres();
    }


    //----- Private methods -----//

    private void showMovies() {
        MoviesFragment moviesFragment = new MoviesFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(MOVIES_BUNDLE_KEY, DataFactory.getMovieList());
        moviesFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(MoviesFragment.class.getSimpleName())
                .replace(R.id.fragment_container, moviesFragment)
                .commit();
    }

    private void showMovieDetails() {
        if (mMovieId != -1) {
            MovieDetailsFragment detailsFragment = MovieDetailsFragment.newInstance(mMovieId);
            detailsFragment.setAppDatabase(mAppDatabase);

            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(MovieDetailsFragment.class.getSimpleName())
                    .replace(R.id.fragment_container, detailsFragment)
                    .commit();
        }
    }

    //----------------------------
    //----- retrieve movies ------
    //----------------------------

    private void retrieveMovies() {
        showProgressBar(true);
        if (mCurrentSort.equals(SortType.POPULAR.getSortType())) {
            APICallFactory.getMovieService()
                    .getMostPopularMovies(BuildConfig.API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onMoviesLoaded, this::onErrorOccurred);
        } else if (mCurrentSort.equals(SortType.TOP_RATED.getSortType())) {
            APICallFactory.getMovieService()
                    .getTopRatedMovies(BuildConfig.API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onMoviesLoaded, this::onErrorOccurred);
        }
    }

    private void onMoviesLoaded(MovieResponse response) {
        showProgressBar(false);
        if (response.movies != null) {
            DataFactory.setMovies(response.movies);
            showMovies();
        } else {
            showErrorMessage();
        }
    }

    //----------------------------
    //----- retrieve genres ------
    //----------------------------

    private void retrieveGenres() {
        APICallFactory.getGenreService()
                .getGenres(BuildConfig.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGenresLoaded, this::onErrorOccurred);
    }

    private void onGenresLoaded(GenreResponse response) {
        if (response.genres != null) {
            DataFactory.setGenres(response.genres);
            retrieveReviews();
        } else {
            showErrorMessage();
            showProgressBar(false);
        }
    }

    //----------------------------
    //----- retrieve reviews ------
    //----------------------------

    private void retrieveReviews() {
        APICallFactory.getReviewService()
                .getReviews(mMovieId, BuildConfig.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onReviewsLoaded, this::onErrorOccurred);
    }

    private void onReviewsLoaded(ReviewResponse response) {
        showProgressBar(false);
        if (response.reviews != null) {
            DataFactory.setReviews(response.reviews);
            retrieveTrailers();
        } else {
            showErrorMessage();
        }
    }

    //----------------------------
    //----- retrieve trailers ------
    //----------------------------

    private void retrieveTrailers() {
        APICallFactory.getTrailerService()
                .getTrailers(mMovieId, BuildConfig.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTrailersLoaded, this::onErrorOccurred);
    }

    private void onTrailersLoaded(TrailerResponse response) {
        if (response.trailers != null) {
            DataFactory.setTrailers(response.trailers);
            showMovieDetails();
        } else {
            showErrorMessage();
            showProgressBar(false);
        }
    }

    private void onErrorOccurred(Throwable e) {
        showErrorMessage();
    }

    private void showErrorMessage() {
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    private void showProgressBar(boolean visible) {
        mProgressBar.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void setupMoviesViewModel() {
        MoviesViewModel viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        viewModel.getFavorites().observe(this, favorites -> {
            Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
            DataFactory.setMovies(favorites);
            showMovies();
        });
    }

    //----- Enum -----//

    private enum SortType {
        POPULAR {
            @Override
            public String getSortType() {
                return mSortTypes[0];
            }
        },
        TOP_RATED {
            @Override
            public String getSortType() {
                return mSortTypes[1];
            }
        },
        FAVORITES {
            @Override
            public String getSortType() {
                return mSortTypes[2];
            }
        };

        public abstract String getSortType();
    }
}
