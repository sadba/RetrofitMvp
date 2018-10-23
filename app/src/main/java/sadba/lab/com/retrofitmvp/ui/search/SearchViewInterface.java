package sadba.lab.com.retrofitmvp.ui.search;

import sadba.lab.com.retrofitmvp.models.MovieResponse;

public interface SearchViewInterface {

    void showToast(String str);
    void displayResult(MovieResponse movieResponse);
    void displayError(String s);
}
