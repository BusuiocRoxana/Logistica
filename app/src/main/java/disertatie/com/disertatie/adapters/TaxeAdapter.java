package disertatie.com.disertatie.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import disertatie.com.disertatie.R;
import disertatie.com.disertatie.activities.AddMaterialActivity;
import disertatie.com.disertatie.entities.Material;
import disertatie.com.disertatie.entities.Taxa;

/**
 * Created by I335495 on 5/23/2017.
 */

public class TaxeAdapter extends RecyclerView.Adapter<TaxeAdapter.MyViewHolder> {

        private ArrayList<Taxa> listaTaxe;
        private Context context;
        private static String TAXA = "TAXA";
        private ViewHolderCallbacks callbacks;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDenumireTaxa;
        public TextView tvProcentTaxa;
        private ImageView ivEdit;
        private ImageView ivDelete;


    public MyViewHolder(View view) {
        super(view);
        tvDenumireTaxa = (TextView) view.findViewById(R.id.tvTaxa);
        tvProcentTaxa = (TextView) view.findViewById(R.id.tvProcentTaxa);

        ivDelete = (ImageView)view.findViewById(R.id.ivDeleteMaterial);
        ivEdit = (ImageView)view.findViewById(R.id.ivEditMaterial);
    }
}


    public TaxeAdapter(ArrayList<Taxa> listaTaxa, Context context, TaxeAdapter.ViewHolderCallbacks callbacks) {
        this.listaTaxe = listaTaxa;
        this.context =  context;
        this.callbacks = callbacks;
    }

    @Override
    public TaxeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_taxa, parent, false);

        return new TaxeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaxeAdapter.MyViewHolder holder, final int position) {
        final Taxa taxa =  listaTaxe.get(position);
        holder.tvDenumireTaxa.setText(taxa.getDenumire_taxa());
        holder.tvProcentTaxa.setText(taxa.getProcent_taxa()+"");

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(context, AddMaterialActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(TAXA,listaTaxe.get(position));
                i.putExtras(bundle);
                context.startActivity(i);

            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle("Stergere taxa")
                        .setMessage("Sunteti sigur ca vreti sa stergeti acesta taxa?")
                        .setPositiveButton(R.string.da, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                listaTaxe.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position,listaTaxe.size());
                                callbacks.onDeleteClick(taxa);
                            }
                        })
                        .setNegativeButton(R.string.nu, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return listaTaxe.size();
    }


public interface ViewHolderCallbacks {
    public void onDeleteClick(Taxa taxa);
}

    public void updateViewTaxe(ArrayList<Taxa> results) {
        listaTaxe = results;
        notifyDataSetChanged();
    }
}
