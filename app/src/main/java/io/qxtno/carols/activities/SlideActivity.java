package io.qxtno.carols.activities;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.github.appintro.AppIntro2;
import com.github.appintro.AppIntroFragment;

import io.qxtno.carols.R;

import static io.qxtno.carols.R.color.*;
import static io.qxtno.carols.R.string.*;

public class SlideActivity extends AppIntro2 {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources res = getResources();

        addSlide(AppIntroFragment.newInstance(res.getString(app_name),
                res.getString(slide_1_desc),
                R.drawable.ic_music_note_slide, res.getColor(pink)));
        addSlide(AppIntroFragment.newInstance(res.getString(slide_text_size),
                res.getString(slide_2_desc),
                R.drawable.ic_size_slide, res.getColor(light_green)));
        addSlide(AppIntroFragment.newInstance(res.getString(slide_scale),
                res.getString(slide_3_desc),
                R.drawable.ic_zoom_slide, res.getColor(orange)));
    }

    @Override
    protected void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    protected void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }
}
