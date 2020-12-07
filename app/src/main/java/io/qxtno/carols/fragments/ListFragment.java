package io.qxtno.carols.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import io.qxtno.carols.R;
import io.qxtno.carols.adapters.CarolAdapter;
import io.qxtno.carols.helpers.DatabaseOpenHelper;
import io.qxtno.carols.model.Carol;

public class ListFragment extends Fragment implements CarolAdapter.OnItemClickListener {
    private ArrayList<Carol> carols;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(requireActivity());
        carols = databaseOpenHelper.getCarols();

        sort(carols);

        RecyclerView recyclerView = view.findViewById(R.id.list_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        CarolAdapter adapter = new CarolAdapter(requireActivity(), carols);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("carol", carols.get(position));
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.nav_carol, bundle);

    }

    private void sort(ArrayList<Carol> arrayList) {
        Collections.sort(arrayList, new Comparator<Carol>() {
            final Collator collator = Collator.getInstance(new Locale("pl", "PL"));

            @Override
            public int compare(final Carol carol1, Carol carol2) {
                return collator.compare(carol1.getTitle(), carol2.getTitle());
            }
        });
    }
}
