package br.com.mbecker.jagastei.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.mbecker.jagastei.R;
import br.com.mbecker.jagastei.Util;
import br.com.mbecker.jagastei.db.GastoModel;

public class GastoAdapter extends RecyclerView.Adapter<GastoAdapter.ViewHolder> {
    private final List<GastoModel> mDataset;

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mValor;
        final TextView mData;
        final TextView mDesc;

        ViewHolder(View v) {
            super(v);
            mValor = v.findViewById(R.id.tvValor);
            mData = v.findViewById(R.id.tvData);
            mDesc = v.findViewById(R.id.tvDesc);
        }
    }

    public GastoAdapter(List<GastoModel> myDataset) {
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gasto_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GastoModel g = mDataset.get(position);
        holder.mValor.setText(Util.frmValor(g.getValor()));
        holder.mData.setText(Util.frmData(g.getQuando()));
        holder.mDesc.setText(g.getObs());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}