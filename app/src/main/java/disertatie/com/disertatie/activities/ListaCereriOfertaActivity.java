package disertatie.com.disertatie.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;

import disertatie.com.disertatie.Database.DatabaseHelper;
import disertatie.com.disertatie.R;
import disertatie.com.disertatie.adapters.CerereOfertaAdapter;
import disertatie.com.disertatie.adapters.MaterialsAdapter;
import disertatie.com.disertatie.entities.CerereOferta;
import disertatie.com.disertatie.entities.Material;

public class ListaCereriOfertaActivity extends  AppCompatActivity implements  CerereOfertaAdapter.ViewHolderCallbacks{

    private Toolbar toolbar;
    private ArrayList<CerereOferta> listaCereriOferta = new ArrayList<>();
    private RecyclerView recyclerView;
    private CerereOfertaAdapter mAdapter;
    private DatabaseHelper databaseHelper;
    private FloatingActionButton fabAdaugaMaterial;


    private static int REQUEST_CODE = -1;

    private static final String TAG = "Logistica";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cereri_oferta);

        // toolbar setup
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            // use toolbar as actionbar
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
            TextView tv = (TextView) toolbar.findViewById(R.id.toolbar_title);
            tv.setText(R.string.cerere_oferta);


        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_cereri_oferta);
        fabAdaugaMaterial = (FloatingActionButton) findViewById(R.id.flAdaugaCerereOferta);


        listaCereriOferta = new ArrayList<CerereOferta>();
        databaseHelper = new DatabaseHelper(this);



        mAdapter = new CerereOfertaAdapter(listaCereriOferta, this, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);




        fabAdaugaMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListaCereriOfertaActivity.this, CerereOfertaActivity.class);
                startActivityForResult(i, REQUEST_CODE );
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            mAdapter.notifyDataSetChanged();
        }
    }



    @Override
    public void onDeleteClick(CerereOferta cerereOferta) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        try {
            listaCereriOferta = databaseHelper.selectCereriOferta();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mAdapter.updateViewMaterials(listaCereriOferta);

        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter("updateCerereOferta"));

        try {
            listaCereriOferta = databaseHelper.selectCereriOferta();

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            try {
                int codDocument = intent.getIntExtra("codDocument", -1);
                double cantitate = intent.getDoubleExtra("cantitate", -1);
                double pret = intent.getDoubleExtra("pret", -1);
                String dataLivrare = intent.getStringExtra("dataLivrare");
                //CerereOferta.Status status = CerereOferta.Status.valueOf(intent.getStringExtra("status").toString());
                CerereOferta.Status status = (CerereOferta.Status)intent.getExtras().get("status");
                if(status.equals(CerereOferta.Status.ACCEPTAT)) {
                    databaseHelper.updateCerereOferta(codDocument, status);
                    updateAdapter();
                }else if(status.equals(CerereOferta.Status.MODIFICAT)){
                    databaseHelper.updateCerereOferta(codDocument, cantitate, pret, dataLivrare, status);
                    updateAdapter();
                }else{
                    Log.e(TAG, "Message Receiver Error Invalid Status="+status);
                }

            }catch (NullPointerException ex){
                Log.e(TAG, "Message Receiver Error="+ex.getMessage());
            }

            Log.d(TAG,"mMessageReceiver-bam-worked");



            //tvStatus.setText(message);
            // Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    };

    public void updateAdapter(){
        try {
            listaCereriOferta = databaseHelper.selectCereriOferta();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mAdapter.updateViewMaterials(listaCereriOferta);

    }
}
