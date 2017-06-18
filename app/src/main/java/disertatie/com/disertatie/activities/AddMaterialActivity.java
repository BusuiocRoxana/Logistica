package disertatie.com.disertatie.activities;

import android.content.Context;
import android.content.Intent;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import disertatie.com.disertatie.Database.DatabaseHelper;
import disertatie.com.disertatie.R;
import disertatie.com.disertatie.entities.Material;

public class AddMaterialActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etDenumireMaterial;
    private EditText etStocCurent;
    private EditText etStocMinim;
    private Button btnAdaugaMaterial;
    private Button btnModificaMaterial;
    private Material material = new Material();
    private Context context;

    DatabaseHelper databaseHelper;
    private static String MATERIAL = "MATERIAL";
    private static int cod_material = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material);
        context = this;

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
            tv.setText(R.string.adauga_material);
        }

        etDenumireMaterial = (EditText) findViewById(R.id.etDenumireMaterial);
        etStocCurent = (EditText) findViewById(R.id.etStocCurent);
        etStocMinim = (EditText) findViewById(R.id.etStocMinim);
        btnAdaugaMaterial = (Button) findViewById(R.id.btnAdaugaMaterial);
        btnModificaMaterial = (Button) findViewById(R.id.btnModificaMaterial) ;


        databaseHelper = new DatabaseHelper(this);

        btnAdaugaMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String collectedErrors = "";
                if(etDenumireMaterial.getText().length() >0){
                    material.setDenumire_material(etDenumireMaterial.getText().toString());
                }else{
                    collectedErrors += "Adaugati denumire material\n";
                }
                if(etStocCurent.getText().length() > 0){
                    material.setStoc_curent(Double.parseDouble(etStocCurent.getText().toString()));
                }else{
                    collectedErrors += "Adaugati stoc curent\n";
                }
                if(etStocMinim.getText().length() >0){
                    material.setStoc_minim(Double.parseDouble(etStocMinim.getText().toString()));
                }else{
                    collectedErrors += "Adaugati stoc minim\n";
                }
                if(collectedErrors.length() > 0){
                    Toast.makeText(context, collectedErrors,Toast.LENGTH_SHORT).show();
                }else {
                    databaseHelper.insertMaterial(material);
                    etDenumireMaterial.setText("");
                    etStocMinim.setText("");
                    etStocCurent.setText("");
                    finish();
                    Toast.makeText(context, "Material inserat cu succes",Toast.LENGTH_SHORT).show();
                }


            }
        });

        if(getIntent().getExtras()!=null) {
            btnAdaugaMaterial.setVisibility(View.GONE);
            btnModificaMaterial.setVisibility(View.VISIBLE);
            Material material = (Material) getIntent().getExtras().getSerializable(MATERIAL);
            etDenumireMaterial.setText(material.getDenumire_material());
            etStocCurent.setText(material.getStoc_curent() + "");
            etStocMinim.setText(material.getStoc_minim() + "");
            cod_material = material.getCod_material();
        }

        btnModificaMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.updateMaterial(cod_material, etDenumireMaterial.getText().toString(), Double.parseDouble(etStocCurent.getText().toString()),
                        Double.parseDouble(etStocMinim.getText().toString()));
                finish();
            }
        });
    }
    @Override
    public void finish() {
        Log.d("Result","Result-ok="+RESULT_OK);
        setResult(RESULT_OK);
        super.finish();
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
}
