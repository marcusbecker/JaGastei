package br.com.mbecker.jagastei.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import br.com.mbecker.jagastei.R;
import br.com.mbecker.jagastei.db.GastoModel;

public class GastoAdapter extends RecyclerView.Adapter<GastoAdapter.ViewHolder> {
    private List<GastoModel> mDataset;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View v) {
            super(v);
            textView = (TextView) v;
        }
    }

    public GastoAdapter(List<GastoModel> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gasto_list_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GastoModel g = mDataset.get(position);
        holder.textView.setText(String.valueOf(g.getValor()));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}