package sadba.lab.com.retrofitmvp.ui;

import sadba.lab.com.retrofitmvp.models.MovieResponse;

public interface MainViewInterface {

    void showToast(String s);
    void displayMovies(MovieResponse movieResponse);
    void displayError(String s);
    void showProgress();

    void hideProgress();
}
