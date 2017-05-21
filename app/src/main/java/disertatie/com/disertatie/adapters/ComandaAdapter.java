package disertatie.com.disertatie.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import disertatie.com.disertatie.R;
import disertatie.com.disertatie.activities.CerereOfertaActivity;
import disertatie.com.disertatie.entities.CerereOferta;
import disertatie.com.disertatie.entities.Comanda;


/**
 * Created by Roxana on 5/21/2017.
 */

public class ComandaAdapter extends RecyclerView.Adapter<ComandaAdapter.MyViewHolder> {
    private ArrayList<Comanda> listaComenzi = new ArrayList<>();
    private Context context;
    private static String COMANDA = "COMANDA";
    private ComandaAdapter.ViewHolderCallbacks callbacks;
    private static final String TAG = "Logistica";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMaterial;
        public TextView tvFurnizor;
        public TextView tvCantitate;
        public TextView tvPret;
        public TextView tvProcentTaxa;
        public TextView tvValoareTotala;
        private LinearLayout llView;


        public MyViewHolder(View view) {
            super(view);
            tvMaterial = (TextView) view.findViewById(R.id.tvMaterial);
            tvFurnizor = (TextView) view.findViewById(R.id.tvFurnizor);
            tvCantitate = (TextView) view.findViewById(R.id.tvCantitate);
            tvPret = (TextView) view.findViewById(R.id.tvPret);
            tvProcentTaxa = (TextView) view.findViewById(R.id.tvProcentTaxa);
            tvValoareTotala = (TextView) view.findViewById(R.id.tvValoareComanda);
            llView = (LinearLayout) view.findViewById(R.id.llView);

        }
    }


    public ComandaAdapter(ArrayList<Comanda> listaComenzi, Context context, ComandaAdapter.ViewHolderCallbacks callbacks) {
        this.listaComenzi= listaComenzi;
        this.context =  context;
        this.callbacks = callbacks;
    }

    @Override
    public ComandaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_comanda, parent, false);

        return new ComandaAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ComandaAdapter.MyViewHolder holder, final int position) {
        final Comanda comanda =  listaComenzi.get(position);
        Log.d(TAG, comanda.toString());
        holder.tvMaterial.setText(comanda.getCerereOferta().getMaterial().getDenumire_material());
        holder.tvFurnizor.setText(comanda.getCerereOferta().getFurnizor().getDenumire_furnizor());
        holder.tvCantitate.setText(comanda.getCerereOferta().getCantitate()+"");
        holder.tvPret.setText(comanda.getCerereOferta().getPret()+"");
        holder.tvProcentTaxa.setText(comanda.getTaxa().getProcent_taxa()+"");
        holder.tvValoareTotala.setText(comanda.getCerereOferta().calculeazaValoare()+comanda.getCerereOferta().calculeazaValoare()*comanda.getTaxa().getProcent_taxa()+"");


        holder.llView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i =  new Intent(context, CerereOfertaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(CERERE_OFERTA, listaCereriOferta.get(position));
                bundle.putString("CO_VIEW","VIEW");
                //i.putExtra("CO_VIEW","VIEW";
                i.putExtras(bundle);
                context.startActivity(i);*/
            }
        });




    }

    @Override
    public int getItemCount() {
        return listaComenzi.size();
    }


    public interface ViewHolderCallbacks {
        public void onDeleteClick(Comanda comanda);
    }

    public void updateViewComenzi(ArrayList<Comanda> results) {
        listaComenzi = results;
        notifyDataSetChanged();
    }
}
