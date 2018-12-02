package com.example.android.popularmoviesstage1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import com.example.android.popularmoviesstage1.adapter.MovieImageAdaptor;
import com.example.android.popularmoviesstage1.utilities.JsonUtils;
import com.example.android.popularmoviesstage1.utilities.NetworkUtils;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    Logger logger = Logger.getLogger("MainActivity");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.sort_menu);
        Spinner spinner = (Spinner) item.getActionView(); // get the spinner
        ArrayAdapter<CharSequence> sort_menu_adaptor = ArrayAdapter.createFromResource(this,
                R.array.sort_array,android.R.layout.simple_spinner_item);
        sort_menu_adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sort_menu_adaptor);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        MainActivityFragment.setIsPopularMoviesSelected(true);
                        break;
                    case 1:
                        MainActivityFragment.setIsPopularMoviesSelected(false);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // sometimes you need nothing here
            }
        });

        return true;
    }
}