package disertatie.com.disertatie.activities;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;

import disertatie.com.disertatie.Database.DatabaseHelper;
import disertatie.com.disertatie.R;
import disertatie.com.disertatie.adapters.CerereOfertaAdapter;
import disertatie.com.disertatie.adapters.ComandaAdapter;
import disertatie.com.disertatie.entities.CerereOferta;
import disertatie.com.disertatie.entities.Comanda;

public class ListaComenziActivity extends AppCompatActivity implements  ComandaAdapter.ViewHolderCallbacks{


    private Toolbar toolbar;
    private ArrayList<Comanda> listaComenzi = new ArrayList<>();
    private RecyclerView recyclerView;
    private ComandaAdapter mAdapter;
    private DatabaseHelper databaseHelper;


    private static int REQUEST_CODE = -1;

    private static final String TAG = "Logistica";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_comenzi);


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
            tv.setText(R.string.comenzi);


        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_comenzi);


        listaComenzi = new ArrayList<Comanda>();
        databaseHelper = new DatabaseHelper(this);


        try {
            listaComenzi = databaseHelper.selectComenzi();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        mAdapter = new ComandaAdapter(listaComenzi, this, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            mAdapter.notifyDataSetChanged();
        }
    }



    @Override
    public void onDeleteClick(Comanda comanda) {

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
            listaComenzi = databaseHelper.selectComenzi();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mAdapter.updateViewComenzi(listaComenzi);


    }
}
