package disertatie.com.disertatie.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import disertatie.com.disertatie.R;
import disertatie.com.disertatie.entities.Material;

/**
 * Created by I335495 on 5/9/2017.
 */

public class MaterialsAdapter extends RecyclerView.Adapter<MaterialsAdapter.MyViewHolder> {
    private List<Material> materialList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDenumireMaterial;
        public TextView tvStocMinim;
        public TextView tvStocCurent;


        public MyViewHolder(View view) {
            super(view);
            tvDenumireMaterial = (TextView) view.findViewById(R.id.tvMaterial);
            tvStocMinim = (TextView) view.findViewById(R.id.tvStocMinim);
            tvStocCurent = (TextView) view.findViewById(R.id.tvStocCurent);
        }
    }


    public MaterialsAdapter(List<Material> materialsList) {
        this.materialList = materialsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_material, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Material material =  materialList.get(position);
        holder.tvDenumireMaterial.setText(material.getDenumire_material());
        holder.tvStocCurent.setText(material.getStoc_curent()+"");
        holder.tvStocMinim.setText(material.getStoc_minim()+"");

    }

    @Override
    public int getItemCount() {
        return materialList.size();
    }
}
