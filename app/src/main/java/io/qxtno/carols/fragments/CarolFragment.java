package io.qxtno.carols.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import io.qxtno.carols.activities.MainActivity;
import io.qxtno.carols.R;
import io.qxtno.carols.model.Carol;

public class CarolFragment extends Fragment {
    private FloatingActionButton sizeFab;
    private ExtendedFloatingActionButton increaseFab;
    private ExtendedFloatingActionButton restoreFab;
    private ExtendedFloatingActionButton decreaseFab;
    private boolean toggleFloatingButtons = false;
    private float size;
    private float defaultSize;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private TextView carolText;
    private ScrollView scrollView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_carol, container, false);

        setHasOptionsMenu(true);

        carolText = view.findViewById(R.id.carol_text);
        scrollView = view.findViewById(R.id.scroll_view);

        Bundle bundle = this.getArguments();
        assert bundle != null;
        Carol carol = bundle.getParcelable("carol");

        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).setTitle(carol.getTitle());
        carolText.setText(carol.getCarol_text());

        sharedPreferences = requireActivity().getSharedPreferences("mode", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean("scaleChanged", false)) {
            size = sharedPreferences.getFloat("scaleSize", 18);
        } else {
            size = sharedPreferences.getFloat("size", 18);
        }
        carolText.setTextSize(size);

        sizeFab = view.findViewById(R.id.font_size_fab);
        increaseFab = view.findViewById(R.id.increase_fab);
        restoreFab = view.findViewById(R.id.restore_fab);
        decreaseFab = view.findViewById(R.id.decrease_fab);

        sizeChanger();

        bringToFront();

        sizeFab.setOnClickListener(v -> {
            if (!toggleFloatingButtons) {
                toggleFloatingButtons = true;
                sizeFab.setImageResource(R.drawable.ic_done);
                sizeFab.setContentDescription(getResources().getString(R.string.size));
                increaseFab.setVisibility(View.VISIBLE);
                restoreFab.setVisibility(View.VISIBLE);
                decreaseFab.setVisibility(View.VISIBLE);
                animationOut();
                setClickable(true);
            } else {
                toggleFloatingButtons = false;
                sizeFab.setImageResource(R.drawable.ic_size);
                sizeFab.setContentDescription(getResources().getString(R.string.close));
                increaseFab.setVisibility(View.GONE);
                restoreFab.setVisibility(View.GONE);
                decreaseFab.setVisibility(View.GONE);
                animationIn();
                setClickable(false);
            }
        });

        increaseFab.setOnClickListener(v -> {
            size = sharedPreferences.getFloat("size", size);
            if (size < 50) {
                size++;
                carolText.setTextSize(size);
                editor = sharedPreferences.edit();
                editor.putFloat("size", size).apply();
            } else {
                Toast.makeText(requireContext(), R.string.toast_too_big, Toast.LENGTH_SHORT).show();
            }
            editor.putBoolean("scaleChanged", false).apply();
        });

        restoreFab.setOnClickListener(v -> {
            size = sharedPreferences.getFloat("size", size);
            if (sharedPreferences.getBoolean("scaleLarge", false)) {
                defaultSize = 25;
            } else {
                defaultSize = 18;
            }
            if (size != defaultSize) {
                carolText.setTextSize(defaultSize);
                editor = sharedPreferences.edit();
                editor.putFloat("size", defaultSize).apply();
                Toast.makeText(requireContext(), R.string.toast_reset_done, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), R.string.toast_size_unchanged, Toast.LENGTH_SHORT).show();
            }

        });

        decreaseFab.setOnClickListener(v -> {
            size = sharedPreferences.getFloat("size", size);
            if (size > 14) {
                size--;
                carolText.setTextSize(size);
                editor = sharedPreferences.edit();
                editor.putFloat("size", size).apply();
            } else {
                Toast.makeText(requireContext(), R.string.toast_too_small, Toast.LENGTH_SHORT).show();
            }
            editor.putBoolean("scaleChanged", false).apply();
        });

        carolText.setOnClickListener(v -> hideButtons());

        scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            if (scrollView.getScrollY() > 0) {
                hideAllButtons();
            } else {
                showButtons();
            }
        });

        return view;
    }

    private void hideAllButtons() {
        hideButtons();
        sizeFab.setVisibility(View.GONE);
        sizeFab.setClickable(false);
    }

    private void showButtons() {
        sizeFab.setVisibility(View.VISIBLE);
        sizeFab.setClickable(true);
    }

    private void hideButtons() {
        if (toggleFloatingButtons) {
            toggleFloatingButtons = false;
            sizeFab.setImageResource(R.drawable.ic_size);
            sizeFab.setContentDescription(getResources().getString(R.string.close));
            increaseFab.setVisibility(View.GONE);
            restoreFab.setVisibility(View.GONE);
            decreaseFab.setVisibility(View.GONE);
            animationIn();
            setClickable(false);
        }
    }

    private void bringToFront() {
        sizeFab.bringToFront();
        increaseFab.bringToFront();
        restoreFab.bringToFront();
        decreaseFab.bringToFront();
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);

        menu.findItem(R.id.menu_search).setVisible(false).collapseActionView();
        menu.findItem(R.id.menu_settings).setOnMenuItemClickListener(item -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.nav_settings);
            hideButtons();

            return true;
        });
    }

    private void animationOut() {
        Animation animationOut = AnimationUtils.loadAnimation(requireContext(), R.anim.anim_out);
        increaseFab.startAnimation(animationOut);
        restoreFab.startAnimation(animationOut);
        decreaseFab.startAnimation(animationOut);
    }

    private void animationIn() {
        Animation animationIn = AnimationUtils.loadAnimation(requireContext(), R.anim.anim_in);
        increaseFab.startAnimation(animationIn);
        restoreFab.startAnimation(animationIn);
        decreaseFab.startAnimation(animationIn);
    }

    private void setClickable(boolean clickable) {
        if (clickable) {
            increaseFab.setClickable(true);
            restoreFab.setClickable(true);
            decreaseFab.setClickable(true);
        } else {
            increaseFab.setClickable(false);
            restoreFab.setClickable(false);
            decreaseFab.setClickable(false);
        }
    }

    private void sizeChanger() {
        if (sharedPreferences.getBoolean("scaleChanged", false)) {
            carolText.setTextSize(sharedPreferences.getFloat("scaleSize", 18));
        }

        float size;
        if (sharedPreferences.getBoolean("scaleLarge", false)) {
            size = 20;
        } else {
            size = 15;
        }
        increaseFab.setTextSize(size);
        restoreFab.setTextSize(size);
        decreaseFab.setTextSize(size);
    }
}
