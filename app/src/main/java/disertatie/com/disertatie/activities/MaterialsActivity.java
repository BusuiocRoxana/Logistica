package disertatie.com.disertatie.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import disertatie.com.disertatie.R;
import disertatie.com.disertatie.adapters.MaterialsAdapter;
import disertatie.com.disertatie.entities.Material;

public class MaterialsActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private List<Material> materialList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MaterialsAdapter mAdapter;

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

        mAdapter = new MaterialsAdapter(materialList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareMaterialData();
    }

    private void prepareMaterialData() {
        Material material = new Material("Limonada",10, 3);
        materialList.add(material);

        material =  new Material("Apa plata", 20, 5);
        materialList.add(material);

        material =  new Material("Cafea", 30, 10);
        materialList.add(material);

        mAdapter.notifyDataSetChanged();
    }
}