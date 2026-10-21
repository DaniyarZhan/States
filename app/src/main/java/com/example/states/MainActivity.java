package com.example.states;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_USER   = "user_key";

    private UserViewModel viewModel;
    private EditText nameBox, yearBox;
    private TextView dataView;
    private SharedPreferences prefs;
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        nameBox   = findViewById(R.id.nameBox);
        yearBox   = findViewById(R.id.yearBox);
        dataView = findViewById(R.id.dataView);
        prefs    = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        loadFromPrefs();
    }

    private void loadFromPrefs() {
        String json = prefs.getString(KEY_USER, null);
        if (json != null) {
            User saved = gson.fromJson(json, User.class);
            viewModel.setUser(saved);

            nameBox.setText(saved.getName());
            yearBox.setText(String.valueOf(saved.getAge()));
        }
    }

    public void saveData(View view) {
        String name = nameBox.getText().toString();
        int age = 0;
        try {
            age = Integer.parseInt(yearBox.getText().toString());
        } catch (NumberFormatException ignored) { }

        User user = new User(name, age);
        viewModel.setUser(user);

        String json = gson.toJson(user);
        prefs.edit().putString(KEY_USER, json).apply();
    }

    public void getData(View view) {
        User user = viewModel.getUser();
        dataView.setText("Name: " + user.getName() + " Age: " + user.getAge());
    }
}