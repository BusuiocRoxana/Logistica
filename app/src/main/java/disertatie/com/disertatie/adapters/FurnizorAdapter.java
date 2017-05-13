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
import java.util.List;
import disertatie.com.disertatie.R;
import disertatie.com.disertatie.activities.AddFurnizorActivity;
import disertatie.com.disertatie.activities.AddMaterialActivity;
import disertatie.com.disertatie.entities.Furnizor;
import disertatie.com.disertatie.entities.Material;

/**
 * Created by Roxana on 5/9/2017.
 */

public class FurnizorAdapter extends RecyclerView.Adapter<FurnizorAdapter.MyViewHolder> {

    private List<Furnizor> furnizoriList;
    private Context context;
    private ViewHolderCallbacks callbacks;
    private static String FURNIZOR = "FURNIZOR";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDenumireFurnizor;
        public TextView tvNrInregRC;
        public TextView tvAdresaFurnizor;
        public TextView tvRating;
        public TextView tvEmail;
        private ImageView ivEdit;
        private ImageView ivDelete;

        public MyViewHolder(View view) {
            super(view);
            tvDenumireFurnizor = (TextView) view.findViewById(R.id.tvFurnizor);
            tvNrInregRC = (TextView) view.findViewById(R.id.tvNrInregRC);
            tvAdresaFurnizor = (TextView) view.findViewById(R.id.tvAdresaFurnizor);
            tvRating = (TextView) view.findViewById(R.id.tvRating);
            tvEmail  = (TextView) view.findViewById(R.id.tvEmailFurnizor);
            ivDelete = (ImageView)view.findViewById(R.id.ivDeleteFurnizor);
            ivEdit = (ImageView)view.findViewById(R.id.ivEditFurnizor);
        }
    }


    public FurnizorAdapter(List<Furnizor> furnizoriList, Context context, ViewHolderCallbacks callbacks) {
        this.furnizoriList = furnizoriList;
        this.context =  context;
        this.callbacks = callbacks;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_furnizor, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FurnizorAdapter.MyViewHolder holder, final int position) {
        final Furnizor furnizor =  furnizoriList.get(position);
        holder.tvDenumireFurnizor.setText(furnizor.getDenumire_furnizor());
        holder.tvNrInregRC.setText(furnizor.getNr_inregistrare_RC());
        holder.tvAdresaFurnizor.setText(String.valueOf(furnizor.getAdresa()));
        holder.tvRating.setText(String.valueOf(furnizor.getRating()));
        holder.tvEmail.setText(furnizor.getEmail());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(context, AddFurnizorActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(FURNIZOR, furnizoriList.get(position));
                i.putExtras(bundle);
                context.startActivity(i);

            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle("Stergere furnizor")
                        .setMessage("Sunteti sigur ca vreti sa stergeti acest furnizor?")
                        .setPositiveButton(R.string.da, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                furnizoriList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, furnizoriList.size());
                                callbacks.onDeleteClick(furnizor);
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
        return furnizoriList.size();
    }
    public interface ViewHolderCallbacks {
        public void onDeleteClick(Furnizor furnizor);
    }

    public void updateViewFurnizori(ArrayList<Furnizor> results) {
        furnizoriList = results;
        notifyDataSetChanged();
    }
}