package disertatie.com.disertatie.activities;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import disertatie.com.disertatie.Database.DatabaseHelper;
import disertatie.com.disertatie.R;
import disertatie.com.disertatie.adapters.MaterialsAdapter;
import disertatie.com.disertatie.entities.Material;

public class MaterialsActivity extends AppCompatActivity implements  MaterialsAdapter.ViewHolderCallbacks{

    private Toolbar toolbar;
    private ArrayList<Material> materialList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MaterialsAdapter mAdapter;
    private DatabaseHelper databaseHelper;
    private FloatingActionButton fabAdaugaMaterial;


    private static int REQUEST_CODE = -1;
    private static String COD_MATERIAL = "COD_MATERIAL";
    private static String DENUMIRE_MATERIAL = "DENUMIRE_MATERIAL";
    private static String STOC_CURENT = "STOC_CURENT";
    private static String STOC_MINIM = "STOC_MINIM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);setContentView(R.layout.activity_materials);
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
            tv.setText(R.string.materiale);


        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_materials);
        fabAdaugaMaterial = (FloatingActionButton) findViewById(R.id.flAdaugaMaterial);


        materialList = new ArrayList<Material>();
        databaseHelper = new DatabaseHelper(this);
        try {
            materialList = databaseHelper.getAllMaterials();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        mAdapter = new MaterialsAdapter(materialList, this, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);




        fabAdaugaMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MaterialsActivity.this, AddMaterialActivity.class);
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
    public void onDeleteClick(Material material) {
        databaseHelper.deleteMaterial(material.getCod_material());
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
            materialList = databaseHelper.getAllMaterials();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mAdapter.updateViewMaterials(materialList);


    }
}