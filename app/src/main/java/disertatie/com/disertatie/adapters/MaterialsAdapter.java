package disertatie.com.disertatie.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import disertatie.com.disertatie.R;
import disertatie.com.disertatie.activities.AddMaterialActivity;
import disertatie.com.disertatie.activities.MaterialsActivity;
import disertatie.com.disertatie.entities.Material;

/**
 * Created by I335495 on 5/9/2017.
 */

public class MaterialsAdapter extends RecyclerView.Adapter<MaterialsAdapter.MyViewHolder> {
    private List<Material> materialList;
    private Context context;
    private static String MATERIAL = "MATERIAL";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDenumireMaterial;
        public TextView tvStocMinim;
        public TextView tvStocCurent;
        private ImageView ivEdit;
        private ImageView ivDelete;


        public MyViewHolder(View view) {
            super(view);
            tvDenumireMaterial = (TextView) view.findViewById(R.id.tvMaterial);
            tvStocMinim = (TextView) view.findViewById(R.id.tvStocMinim);
            tvStocCurent = (TextView) view.findViewById(R.id.tvStocCurent);

            ivDelete = (ImageView)view.findViewById(R.id.ivDeleteMaterial);
            ivEdit = (ImageView)view.findViewById(R.id.ivEditMaterial);
        }
    }


    public MaterialsAdapter(List<Material> materialsList, Context context) {
        this.materialList = materialsList;
        this.context =  context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_material, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Material material =  materialList.get(position);
        holder.tvDenumireMaterial.setText(material.getDenumire_material());
        holder.tvStocCurent.setText(material.getStoc_curent()+"");
        holder.tvStocMinim.setText(material.getStoc_minim()+"");

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(context, AddMaterialActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(MATERIAL,materialList.get(position));
                i.putExtras(bundle);
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return materialList.size();
    }


}
