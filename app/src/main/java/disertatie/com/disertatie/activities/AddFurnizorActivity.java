package disertatie.com.disertatie.activities;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import disertatie.com.disertatie.Database.DatabaseHelper;
import disertatie.com.disertatie.R;
import disertatie.com.disertatie.entities.Furnizor;
import disertatie.com.disertatie.entities.Material;

public class AddFurnizorActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etDenumireFurnizor;
    private EditText etNrInregRC;
    private EditText etAdresa;
    private EditText etEmailFurnizor;
    private Spinner spinnerRating;
    private Button btnAdaugaFurnizor;
    private Button btnModificaFurnizor;

    DatabaseHelper databaseHelper;
    private static String FURNIZOR = "FURNIZOR";
    private static int cod_furnizor = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_furnizor);
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
            tv.setText(R.string.adauga_furnizor);
        }

        etDenumireFurnizor = (EditText) findViewById(R.id.etDenumireFurnizor);
        etNrInregRC = (EditText) findViewById(R.id.etNrInregRCFurnizor);
        etAdresa = (EditText) findViewById(R.id.etAdresaFurnizor);
        etEmailFurnizor = (EditText) findViewById(R.id.etEmailFurnizor);
        btnAdaugaFurnizor = (Button) findViewById(R.id.btnAdaugaFurnizor);
        btnModificaFurnizor = (Button) findViewById(R.id.btnModificaFurnizor) ;
        spinnerRating = (Spinner) findViewById(R.id.spinnerRating);


        ArrayAdapter<String> spinnerRatingArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ratings));
        spinnerRating.setAdapter(spinnerRatingArrayAdapter);

        databaseHelper = new DatabaseHelper(this);

        btnAdaugaFurnizor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.insertFurnizor(etDenumireFurnizor.getText().toString(),
                        etNrInregRC.getText().toString(),
                        Integer.parseInt(etAdresa.getText().toString()),
                        Integer.parseInt(spinnerRating.getSelectedItem().toString()), etEmailFurnizor.getText().toString());

                etDenumireFurnizor.setText("");
                etNrInregRC.setText("");
                etAdresa.setText("");
                finish();
            }
        });

        if(getIntent().getExtras()!=null) {
            btnAdaugaFurnizor.setVisibility(View.GONE);
            btnModificaFurnizor.setVisibility(View.VISIBLE);
            Furnizor furnizor = (Furnizor) getIntent().getExtras().getSerializable(FURNIZOR);
            etDenumireFurnizor.setText(furnizor.getDenumire_furnizor());
            etNrInregRC.setText(furnizor.getNr_inregistrare_RC() + "");
            etAdresa.setText(furnizor.getCod_adresa() + "");
            etEmailFurnizor.setText(furnizor.getEmail()+"");
            cod_furnizor = furnizor.getCod_furnizor();
        }

        btnModificaFurnizor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.updateFurnizor(cod_furnizor, etDenumireFurnizor.getText().toString(),
                        etNrInregRC.getText().toString(),
                        Integer.parseInt(etAdresa.getText().toString()), Integer.parseInt(spinnerRating.getSelectedItem().toString()),
                        etEmailFurnizor.getText().toString());
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
