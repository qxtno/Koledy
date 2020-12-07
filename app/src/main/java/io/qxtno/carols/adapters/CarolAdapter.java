package io.qxtno.carols.adapters;

import android.content.Context;
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
    }

    @NonNull
    @Override
    public CarolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CarolViewHolder(LayoutInflater.from(context).inflate(R.layout.carol_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CarolViewHolder holder, int position) {
        holder.title_text_view.setText(carols.get(position).getTitle());
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
}
