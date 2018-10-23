package sadba.lab.com.retrofitmvp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import sadba.lab.com.retrofitmvp.R;
import sadba.lab.com.retrofitmvp.adapters.MoviesAdapter;
import sadba.lab.com.retrofitmvp.models.MovieResponse;
import sadba.lab.com.retrofitmvp.ui.search.SearchActivity;

public class MainActivity extends AppCompatActivity implements MainViewInterface {

    @BindView(R.id.rvMovies)
    RecyclerView rvMovies;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ProgressBar progressBar;
    private String TAG = "MainActivity";

    MainPresenter mainPresenter;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initProgressBar();

        setupMVP();
        setupViews();
        getMovieList();
    }

    private void getMovieList() {
        mainPresenter.getMovies();
    }

    private void setupViews() {
        showProgress();
        setSupportActionBar(toolbar);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupMVP() {
        mainPresenter = new MainPresenter(this);
    }

    @Override
    public void showToast(String s) {

        Toast.makeText(MainActivity.this,s, Toast.LENGTH_LONG).show();

    }

    @Override
    public void displayMovies(MovieResponse movieResponse) {
        if(movieResponse!=null) {
            Log.d(TAG,movieResponse.getResults().get(1).getTitle());
            adapter = new MoviesAdapter(movieResponse.getResults(), MainActivity.this);
            rvMovies.setAdapter(adapter);
            hideProgress();
        }else{
            Log.d(TAG,"Movies response null");
            hideProgress();
        }

    }

    /**
     * Initializing progressbar programmatically
     * */
    private void initProgressBar() {
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.addView(progressBar);

        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        progressBar.setVisibility(View.INVISIBLE);

        this.addContentView(relativeLayout, params);
    }

    @Override
    public void displayError(String e) {
        showToast(e);

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    //Added in Part 2 of the series
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.search){
            showToast("Search Clicked");
            Intent i = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
