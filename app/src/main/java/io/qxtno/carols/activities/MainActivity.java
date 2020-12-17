package io.qxtno.carols.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MotionEvent;

import com.github.appintro.AppIntro2;
import com.github.appintro.AppIntroFragment;

import io.qxtno.carols.R;

import static io.qxtno.carols.R.color.light_green;
import static io.qxtno.carols.R.color.orange;
import static io.qxtno.carols.R.color.pink;
import static io.qxtno.carols.R.string.app_name;
import static io.qxtno.carols.R.string.slide_1_desc;
import static io.qxtno.carols.R.string.slide_2_desc;
import static io.qxtno.carols.R.string.slide_3_desc;
import static io.qxtno.carols.R.string.slide_scale;
import static io.qxtno.carols.R.string.slide_text_size;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            if (sharedPreferences.getBoolean("firstUse", true)) {
                startActivity(new Intent(this, SlideActivity.class));

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("firstUse", false).apply();
            }
        }

        setUpTheme();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void setUpTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences("mode", MODE_PRIVATE);
        String mode = sharedPreferences.getString("mode", "light");

        switch (mode) {
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "system":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("test", true);
    }
}