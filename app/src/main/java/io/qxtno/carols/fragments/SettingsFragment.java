package io.qxtno.carols.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;

import io.qxtno.carols.R;

public class SettingsFragment extends Fragment {
    private RadioGroup radioGroup;
    private RadioButton lightRadio;
    private RadioButton darkRadio;
    private RadioButton systemRadio;
    private SwitchMaterial scaleSwitch;
    private TextView themeText;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        radioGroup = view.findViewById(R.id.radio_theme);
        lightRadio = view.findViewById(R.id.radio_light);
        darkRadio = view.findViewById(R.id.radio_dark);
        systemRadio = view.findViewById(R.id.radio_system);
        scaleSwitch = view.findViewById(R.id.scale_switch);
        themeText = view.findViewById(R.id.theme_text);

        sharedPreferences = requireActivity().getSharedPreferences("mode", Context.MODE_PRIVATE);

        themeHandler();
        scaleHandler();
        sizeChanger();

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_search).setVisible(false).collapseActionView();
        menu.findItem(R.id.menu_settings).setVisible(false);
    }

    @SuppressLint("NonConstantResourceId")
    private void themeHandler() {

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            systemRadio.setVisibility(View.GONE);
        } else {
            systemRadio.setVisibility(View.VISIBLE);
        }

        radioGroup.clearCheck();
        String mode = sharedPreferences.getString("mode", "light");

        switch (mode) {
            case "light":
                lightRadio.setChecked(true);
                break;
            case "dark":
                darkRadio.setChecked(true);
                break;
            case "system":
                systemRadio.setChecked(true);
                break;
        }

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            editor = sharedPreferences.edit();
            switch (checkedId) {
                case R.id.radio_light:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putString("mode", "light").apply();
                    break;
                case R.id.radio_dark:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putString("mode", "dark").apply();
                    break;
                case R.id.radio_system:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putString("mode", "system").apply();
                    break;
            }
        });
    }

    private void scaleHandler() {
        boolean large = sharedPreferences.getBoolean("scaleLarge", false);
        if (large) {
            scaleSwitch.setChecked(true);
            scaleSwitch.setText(R.string.scale_normal);
        }
        scaleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor = sharedPreferences.edit();
            if (isChecked) {
                editor.putFloat("scaleSize", 25)
                        .putBoolean("scaleLarge", true)
                        .putFloat("size", 25)
                        .apply();
                scaleSwitch.setText(R.string.scale_normal);
            } else {
                editor.putFloat("scaleSize", 18)
                        .putBoolean("scaleLarge", false)
                        .putFloat("size", 25)
                        .apply();
                scaleSwitch.setText(R.string.scale_large);
            }
            sizeChanger();
            editor.putBoolean("scaleChanged", true).apply();
        });
    }

    private void sizeChanger() {
        float size;
        if (sharedPreferences.getBoolean("scaleLarge", false)) {
            size = 20;
        } else {
            size = 15;
        }
        themeText.setTextSize(size);
        lightRadio.setTextSize(size);
        darkRadio.setTextSize(size);
        systemRadio.setTextSize(size);
        scaleSwitch.setTextSize(size);
    }
}
