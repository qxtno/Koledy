package io.qxtno.carols.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.qxtno.carols.R;
import io.qxtno.carols.model.Carol;

public class CarolAdapter extends RecyclerView.Adapter<CarolAdapter.CarolViewHolder> {

    private final Context context;
    private final ArrayList<Carol> carols;
    private final ArrayList<Carol> carolsBackup;
    private final ArrayList<Carol> filteredList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public CarolAdapter(Context context, ArrayList<Carol> carols) {
        this.context = context;
        this.carols = carols;
        carolsBackup = new ArrayList<>(carols);
    }

    @NonNull
    @Override
    public CarolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CarolViewHolder(LayoutInflater.from(context).inflate(R.layout.carol_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CarolViewHolder holder, int position) {
        holder.title_text_view.setText(carols.get(position).getTitle());
        sizeChanger(holder);
    }

    private void sizeChanger(CarolViewHolder holder) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mode", Context.MODE_PRIVATE);
        float size = sharedPreferences.getFloat("scaleSize", 18);
        holder.title_text_view.setTextSize(size);
    }

    @Override
    public int getItemCount() {
        return carols.size();
    }

    public class CarolViewHolder extends RecyclerView.ViewHolder {
        TextView title_text_view;

        public CarolViewHolder(@NonNull View itemView) {
            super(itemView);
            title_text_view = itemView.findViewById(R.id.title_text_view);

            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
        }
    }

    public void filter(String text) {
        filteredList.clear();
        if (text.isEmpty()) {
            filteredList.addAll(carolsBackup);
        } else {
            text = text.toLowerCase();
            for (Carol carol : carolsBackup) {
                if (carol.getTitle().toLowerCase().contains(text)) {
                    filteredList.add(carol);
                }
            }
        }
        carols.clear();
        carols.addAll(filteredList);
        notifyDataSetChanged();
    }
}
