package disertatie.com.disertatie.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
import java.util.List;

import disertatie.com.disertatie.Database.DatabaseHelper;
import disertatie.com.disertatie.R;
import disertatie.com.disertatie.adapters.FurnizorAdapter;
import disertatie.com.disertatie.adapters.MaterialsAdapter;
import disertatie.com.disertatie.entities.Furnizor;
import disertatie.com.disertatie.entities.Material;

public class FurnizoriActivity extends AppCompatActivity implements  FurnizorAdapter.ViewHolderCallbacks{

    private static final String TAG = "Logistica";
    private Toolbar toolbar;
    private ArrayList<Furnizor> furnizoriList;
    private RecyclerView recyclerView;
    private FurnizorAdapter mAdapter;
    private DatabaseHelper databaseHelper;
    private FloatingActionButton fabAdaugaFurnizor;
    private static int REQUEST_CODE = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furnizori);

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
            tv.setText(R.string.furnizori);
        }
        furnizoriList = new ArrayList<Furnizor>();
        databaseHelper = new DatabaseHelper(this);
        try {
            furnizoriList = databaseHelper.getAllFurnizori();


        } catch (ParseException e) {
            e.printStackTrace();
        }
        for(int i=0;i<furnizoriList.size();i++){
            Log.d(TAG, "FURNIZORI="+furnizoriList.get(i).getDenumire_furnizor()+"-"+furnizoriList.get(i).getCod_adresa());

        }

        fabAdaugaFurnizor = (FloatingActionButton) findViewById(R.id.flAdaugaFurnizor);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_furnizori);
        if(furnizoriList.size() > 0) {
            mAdapter = new FurnizorAdapter(furnizoriList, this, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }

        fabAdaugaFurnizor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FurnizoriActivity.this, AddFurnizorActivity.class);
                startActivityForResult(i, REQUEST_CODE );
            }
        });
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
    public void onDeleteClick(Furnizor furnizor) {
        databaseHelper.deleteFurnizor(furnizor.getCod_furnizor());
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            furnizoriList = databaseHelper.getAllFurnizori();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(mAdapter == null) {
            mAdapter = new FurnizorAdapter(furnizoriList, this, this);
        }
        mAdapter.updateViewFurnizori(furnizoriList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            mAdapter.notifyDataSetChanged();
        }
    }

}