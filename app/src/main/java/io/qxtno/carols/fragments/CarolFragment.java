package io.qxtno.carols.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import io.qxtno.carols.activities.MainActivity;
import io.qxtno.carols.R;
import io.qxtno.carols.model.Carol;

public class CarolFragment extends Fragment {
    private Carol carol;

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
        TextView carolText = view.findViewById(R.id.carol_text);

        carolText.setText(carol.getCarol_text());

        return view;
    }
}
