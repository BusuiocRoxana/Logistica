package disertatie.com.disertatie.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
        try {
            listaCereriOferta = databaseHelper.selectCereriOferta();

        } catch (ParseException e) {
            e.printStackTrace();
        }

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


    }
}