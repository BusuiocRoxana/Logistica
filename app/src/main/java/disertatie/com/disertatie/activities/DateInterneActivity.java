package disertatie.com.disertatie.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import disertatie.com.disertatie.Database.DatabaseHelper;
import disertatie.com.disertatie.R;
import disertatie.com.disertatie.entities.Adresa;
import disertatie.com.disertatie.entities.Companie;

public class DateInterneActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private DatabaseHelper databaseHelper;
    int id_To_Update = 0;

    private EditText etDenumireCompanie;
    private EditText etNrInregRC;
    private EditText etEmailCompanie;
    private EditText etTelefonCompanie;
    private EditText etCodAdresaCompanie;

    private Button btnSalveaza;
    private Button btnModifica;
    private Button btnSalveazaModificari;
    private Button btnTaxe;

    private RelativeLayout rlAdresa;
    private EditText etNumar;
    private EditText etStrada;
    private EditText etLocalitate;
    private EditText etJudetSector;
    private EditText etTara;
    private int cod_adresa;
    private int cod_companie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_interne);

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
            tv.setText(R.string.pagina_date_interne);
        }

        etDenumireCompanie = (EditText) findViewById(R.id.etDenumireCompanie);
        etNrInregRC = (EditText) findViewById(R.id.etNrInregistrareRC);
        etEmailCompanie = (EditText) findViewById(R.id.etEmailCompanie);
        etTelefonCompanie = (EditText) findViewById(R.id.etTelefonCompanie);
        etCodAdresaCompanie = (EditText) findViewById(R.id.etAdresaCompanie);
        btnSalveaza = (Button) findViewById(R.id.btnSalveaza);
        btnModifica = (Button)findViewById(R.id.btnModifica) ;
        btnSalveazaModificari = (Button)findViewById(R.id.btnSalveazaModificari);
        btnTaxe = (Button) findViewById(R.id.btnDefinireTaxe);

        rlAdresa = (RelativeLayout)findViewById(R.id.adresaFurnizor);
        etNumar = (EditText) rlAdresa.findViewById(R.id.etNumar);
        etStrada = (EditText) rlAdresa.findViewById(R.id.etStrada);
        etJudetSector = (EditText) rlAdresa.findViewById(R.id.etJudetSector);
        etLocalitate = (EditText) rlAdresa.findViewById(R.id.etLocalitate);
        etTara = (EditText) rlAdresa.findViewById(R.id.etTara);

        databaseHelper = new DatabaseHelper(this);


        if(databaseHelper.numberOfRowsCompanie() != 0) {
            Companie companie = databaseHelper.getCompany();
            cod_companie = companie.getCod_companie();
            cod_adresa = companie.getAdresa().getCod_adresa();
            etDenumireCompanie.setText(companie.getDenumire_companie());
            etNrInregRC.setText(companie.getNr_inreg_RC());
            etTelefonCompanie.setText(companie.getTelefon());
            etEmailCompanie.setText(companie.getEmail());
            etNumar.setText(companie.getAdresa().getNumar() + "");
            etStrada.setText(companie.getAdresa().getStrada());
            etLocalitate.setText(companie.getAdresa().getLocalitate());
            etJudetSector.setText(companie.getAdresa().getJudet_sector());
            etTara.setText(companie.getAdresa().getTara());


            etDenumireCompanie.setEnabled(false);
            etNrInregRC.setEnabled(false);
            etEmailCompanie.setEnabled(false);
            etCodAdresaCompanie.setEnabled(false);
            etTelefonCompanie.setEnabled(false);
            etNumar.setEnabled(false);
            etStrada.setEnabled(false);
            etLocalitate.setEnabled(false);
            etJudetSector.setEnabled(false);
            etTara.setEnabled(false);

            btnSalveaza.setVisibility(View.GONE);
            btnModifica.setVisibility(View.VISIBLE);
        }else{
            btnSalveaza.setVisibility(View.VISIBLE);
            btnModifica.setVisibility(View.GONE);
            etDenumireCompanie.setEnabled(true);
            etNrInregRC.setEnabled(true);
            etTelefonCompanie.setEnabled(true);
            etEmailCompanie.setEnabled(true);
            etCodAdresaCompanie.setEnabled(true);
            etTelefonCompanie.setEnabled(true);
            etNumar.setEnabled(true);
            etStrada.setEnabled(true);
            etLocalitate.setEnabled(true);
            etJudetSector.setEnabled(true);
            etTara.setEnabled(true);
        }




        btnSalveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isAddressInserted = databaseHelper.insertAdresa(
                        Integer.parseInt(etNumar.getText().toString()),
                        etStrada.getText().toString(),
                        etLocalitate.getText().toString(),
                        etJudetSector.getText().toString(),
                        etTara.getText().toString());

                if(isAddressInserted) {
                    cod_adresa = databaseHelper.printAutoIncrements();
                    Log.d("TEST_ADRESA", "cod_adresa=" + cod_adresa);
                    databaseHelper.insertCompanie(etDenumireCompanie.getText().toString(), etNrInregRC.getText().toString(),
                            etEmailCompanie.getText().toString(), cod_adresa,
                            etTelefonCompanie.getText().toString());
                }


                btnSalveaza.setVisibility(View.GONE);
                btnModifica.setVisibility(View.VISIBLE);

                etDenumireCompanie.setEnabled(false);
                etNrInregRC.setEnabled(false);
                etEmailCompanie.setEnabled(false);
                etCodAdresaCompanie.setEnabled(false);
                etTelefonCompanie.setEnabled(false);
                etNumar.setEnabled(false);
                etStrada.setEnabled(false);
                etLocalitate.setEnabled(false);
                etJudetSector.setEnabled(false);
                etTara.setEnabled(false);

            }
        });

        btnModifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnModifica.setVisibility(View.GONE);
                btnSalveazaModificari.setVisibility(View.VISIBLE);
                etDenumireCompanie.setEnabled(true);
                etNrInregRC.setEnabled(true);
                etTelefonCompanie.setEnabled(true);
                etEmailCompanie.setEnabled(true);
                etCodAdresaCompanie.setEnabled(true);
                etTelefonCompanie.setEnabled(true);
                etNumar.setEnabled(true);
                etStrada.setEnabled(true);
                etLocalitate.setEnabled(true);
                etJudetSector.setEnabled(true);
                etTara.setEnabled(true);


            }
        });

        btnSalveazaModificari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSalveazaModificari.setVisibility(View.GONE);
                btnModifica.setVisibility(View.VISIBLE);
                etDenumireCompanie.setEnabled(false);
                etNrInregRC.setEnabled(false);
                etEmailCompanie.setEnabled(false);
                etCodAdresaCompanie.setEnabled(false);
                etTelefonCompanie.setEnabled(false);
                etNumar.setEnabled(false);
                etStrada.setEnabled(false);
                etLocalitate.setEnabled(false);
                etJudetSector.setEnabled(false);
                etTara.setEnabled(false);

                Adresa adresa = new Adresa(cod_adresa,Integer.parseInt(etNumar.getText().toString()), etStrada.getText().toString(),
                        etLocalitate.getText().toString(), etJudetSector.getText().toString(), etTara.getText().toString());

                databaseHelper.updateCompanie(cod_companie,etDenumireCompanie.getText().toString(), etNrInregRC.getText().toString(),
                        etEmailCompanie.getText().toString(),
                        adresa, etTelefonCompanie.getText().toString());
            }
        });

        btnTaxe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DateInterneActivity.this, TaxeActivity.class);
                startActivity(i);
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
        }
