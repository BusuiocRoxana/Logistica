package disertatie.com.disertatie.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import disertatie.com.disertatie.R;
import disertatie.com.disertatie.entities.Furnizor;

/**
 * Created by Roxana on 5/9/2017.
 */

public class FurnizorAdapter extends RecyclerView.Adapter<FurnizorAdapter.MyViewHolder> {

    private List<Furnizor> furnizoriList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDenumireFurnizor;
        public TextView tvNrInregRC;
        public TextView tvAdresaFurnizor;
        public TextView tvRating;

        public MyViewHolder(View view) {
            super(view);
            tvDenumireFurnizor = (TextView) view.findViewById(R.id.tvFurnizor);
            tvNrInregRC = (TextView) view.findViewById(R.id.tvNrInregRC);
            tvAdresaFurnizor = (TextView) view.findViewById(R.id.tvAdresaFurnizor);
            tvRating = (TextView) view.findViewById(R.id.tvRating);
        }
    }


    public FurnizorAdapter(List<Furnizor> furnizoriList) {
        this.furnizoriList = furnizoriList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_furnizor, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FurnizorAdapter.MyViewHolder holder, int position) {
        Furnizor furnizor =  furnizoriList.get(position);
        holder.tvDenumireFurnizor.setText(furnizor.getDenumire_furnizor());
        holder.tvNrInregRC.setText(furnizor.getNr_inregistrare_RC());
        holder.tvAdresaFurnizor.setText(String.valueOf(furnizor.getCod_adresa()));
        holder.tvRating.setText(String.valueOf(furnizor.getRating()));


    }

    @Override
    public int getItemCount() {
        return furnizoriList.size();
    }
}