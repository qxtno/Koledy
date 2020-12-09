package io.qxtno.carols.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import io.qxtno.carols.activities.MainActivity;
import io.qxtno.carols.R;
import io.qxtno.carols.model.Carol;

public class CarolFragment extends Fragment {
    private Carol carol;
    private FloatingActionButton sizeFab;
    private ExtendedFloatingActionButton increaseFab;
    private ExtendedFloatingActionButton restoreFab;
    private ExtendedFloatingActionButton decreaseFab;
    private boolean toggleFloatingButtons = false;
    private float size;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        assert bundle != null;
        carol = bundle.getParcelable("carol");

        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).setTitle(carol.getTitle());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_carol, container, false);

        setHasOptionsMenu(true);

        TextView carolText = view.findViewById(R.id.carol_text);

        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putFloat("size", carolText.getTextSize());
        size = sharedPreferences.getFloat("size", 18);

        carolText.setText(carol.getCarol_text());
        carolText.setTextSize(sharedPreferences.getFloat("size", size));

        sizeFab = view.findViewById(R.id.font_size_fab);
        increaseFab = view.findViewById(R.id.increase_fab);
        restoreFab = view.findViewById(R.id.restore_fab);
        decreaseFab = view.findViewById(R.id.decrease_fab);

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

        size = sharedPreferences.getFloat("size", size);

        increaseFab.setOnClickListener(v -> {
            if (size < 50) {
                size++;
                carolText.setTextSize(size);
                editor = sharedPreferences.edit();
                editor.putFloat("size", size).apply();
            } else {
                Toast.makeText(requireContext(), R.string.toast_too_big, Toast.LENGTH_SHORT).show();
            }

        });

        restoreFab.setOnClickListener(v -> {
            size = 18;
            carolText.setTextSize(size);
            editor = sharedPreferences.edit();
            editor.putFloat("size", size).apply();
            Toast.makeText(requireContext(), R.string.toast_reset_done, Toast.LENGTH_SHORT).show();
        });

        decreaseFab.setOnClickListener(v -> {
            if (size > 14) {
                size--;
                carolText.setTextSize(size);
                editor = sharedPreferences.edit();
                editor.putFloat("size", size).apply();
            } else {
                Toast.makeText(requireContext(), R.string.toast_too_small, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void bringToFront() {
        sizeFab.bringToFront();
        increaseFab.bringToFront();
        restoreFab.bringToFront();
        decreaseFab.bringToFront();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.carol_menu, menu);
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
}
