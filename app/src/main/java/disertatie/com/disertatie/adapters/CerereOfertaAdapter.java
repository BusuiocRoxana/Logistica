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
import disertatie.com.disertatie.entities.CerereOferta;
import disertatie.com.disertatie.entities.Material;

/**
 * Created by Roxana on 5/19/2017.
 */

public class CerereOfertaAdapter extends RecyclerView.Adapter<CerereOfertaAdapter.MyViewHolder> {
    private ArrayList<CerereOferta> listaCereriOferta;
    private Context context;
    private static String CERERE_OFERTA = "CERERE_OFERTA";
    private CerereOfertaAdapter.ViewHolderCallbacks callbacks;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMaterial;
        public TextView tvFurnizor;
        public TextView tvCantitate;
        public TextView tvTermenLimita;
        private TextView tvPret;
        private TextView tvStatus;


        public MyViewHolder(View view) {
            super(view);
            tvMaterial = (TextView) view.findViewById(R.id.tvMaterial);
            tvFurnizor = (TextView) view.findViewById(R.id.tvFurnizor);
            tvCantitate = (TextView) view.findViewById(R.id.tvCantitate);
            tvTermenLimita = (TextView) view.findViewById(R.id.tvTermenLimita);
            tvPret = (TextView) view.findViewById(R.id.tvPret);
            tvStatus = (TextView) view.findViewById(R.id.tvStatus);


        }
    }


    public CerereOfertaAdapter(ArrayList<CerereOferta> listaCereriOferta, Context context, CerereOfertaAdapter.ViewHolderCallbacks callbacks) {
        this.listaCereriOferta = listaCereriOferta;
        this.context =  context;
        this.callbacks = callbacks;
    }

    @Override
    public CerereOfertaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_cerere_oferta, parent, false);

        return new CerereOfertaAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CerereOfertaAdapter.MyViewHolder holder, final int position) {
        final CerereOferta cerereOferta =  listaCereriOferta.get(position);
        holder.tvMaterial.setText(cerereOferta.getMaterial().getDenumire_material());
        holder.tvFurnizor.setText(cerereOferta.getFurnizor().getDenumire_furnizor());
        holder.tvCantitate.setText(cerereOferta.getCantitate()+"");
        holder.tvPret.setText(cerereOferta.getPret()+"");
        holder.tvTermenLimita.setText(cerereOferta.getTermen_limita_raspuns());
        holder.tvStatus.setText(cerereOferta.getStatus().toString());





    }

    @Override
    public int getItemCount() {
        return listaCereriOferta.size();
    }


    public interface ViewHolderCallbacks {
        public void onDeleteClick(CerereOferta cerereOferta);
    }

    public void updateViewMaterials(ArrayList<CerereOferta> results) {
        listaCereriOferta = results;
        notifyDataSetChanged();
    }
}
