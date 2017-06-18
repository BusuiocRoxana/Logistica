package disertatie.com.disertatie.activities;

import android.content.Context;
import android.content.res.Resources;
import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Choreographer;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import disertatie.com.disertatie.Database.DatabaseHelper;
import disertatie.com.disertatie.R;
import disertatie.com.disertatie.entities.Adresa;
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

    private RelativeLayout rlAdresa;
    private EditText etNumar;
    private EditText etStrada;
    private EditText etLocalitate;
    private EditText etJudetSector;
    private EditText etTara;
    private EditText etTelefon;
    private RatingBar ratingBar;
    private TextView tvRating;

    DatabaseHelper databaseHelper;
    private static String FURNIZOR = "FURNIZOR";
    private static int cod_furnizor = -1;
    private static int cod_adresa = -1;

    Integer[] ratings = new Integer[]{1, 2, 3, 4, 5};


    private Furnizor furnizor =  new Furnizor();
    private Adresa adresa = new Adresa();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_furnizor);

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
            tv.setText(R.string.adauga_furnizor);
        }

        etDenumireFurnizor = (EditText) findViewById(R.id.etDenumireFurnizor);
        etNrInregRC = (EditText) findViewById(R.id.etNrInregRCFurnizor);
        etAdresa = (EditText) findViewById(R.id.etAdresaFurnizor);
        etEmailFurnizor = (EditText) findViewById(R.id.etEmailFurnizor);
        btnAdaugaFurnizor = (Button) findViewById(R.id.btnAdaugaFurnizor);
        btnModificaFurnizor = (Button) findViewById(R.id.btnModificaFurnizor) ;
        spinnerRating = (Spinner) findViewById(R.id.spinnerRating);

        rlAdresa = (RelativeLayout)findViewById(R.id.adresaFurnizor);
        etNumar = (EditText) rlAdresa.findViewById(R.id.etNumar);
        etStrada = (EditText) rlAdresa.findViewById(R.id.etStrada);
        etJudetSector = (EditText) rlAdresa.findViewById(R.id.etJudetSector);
        etLocalitate = (EditText) rlAdresa.findViewById(R.id.etLocalitate);
        etTara = (EditText) rlAdresa.findViewById(R.id.etTara);
        etTelefon = (EditText) rlAdresa.findViewById(R.id.etTelefonCompanie);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        tvRating = (TextView)findViewById(R.id.tvRating);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                tvRating.setText(String.valueOf(rating));


            }
        });


        ArrayAdapter<Integer> spinnerRatingArrayAdapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_dropdown_item,ratings);
        spinnerRating.setAdapter(spinnerRatingArrayAdapter);

        databaseHelper = new DatabaseHelper(this);

        btnAdaugaFurnizor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String collectedErrors = "";
                if(etDenumireFurnizor.getText().length()>0){
                    furnizor.setDenumire_furnizor(etDenumireFurnizor.getText().toString());
                }else{
                    collectedErrors += "Introduceti denumire furnizor\n";
                }
                if(etNrInregRC.getText().length()>0){
                    furnizor.setNr_inregistrare_RC(etNrInregRC.getText().toString());
                }else{
                    collectedErrors += "Introduceti Nr. Inregistrare Registrul Comertului\n";
                }
                if(etEmailFurnizor.getText().length()>0){
                    furnizor.setEmail(etEmailFurnizor.getText().toString());
                }else{
                    collectedErrors += "Introduceti email furnizor\n";
                }if(spinnerRating.getSelectedItem() != null){
                   furnizor.setRating(Integer.parseInt(spinnerRating.getSelectedItem().toString()));
                }else{
                    collectedErrors += "Selectati rating\n";
                }

                if(etTelefon.getText().length()>0){
                   adresa.setTelefon(etTelefon.getText().toString());
                }else{
                    collectedErrors += "Introduceti numar de telefon\n";
                }
                if(etNumar.getText().length()>0){
                    adresa.setNumar(Integer.parseInt(etNumar.getText().toString()));
                }else{
                    collectedErrors += "Introduceti numar adresa\n";
                } if(etStrada.getText().length()>0){
                    adresa.setStrada(etStrada.getText().toString());
                }else{
                    collectedErrors += "Introduceti strada\n";
                } if(etJudetSector.getText().length()>0){
                    adresa.setJudet_sector(etJudetSector.getText().toString());
                }else{
                    collectedErrors += "Introduceti judet/sector\n";
                } if(etLocalitate.getText().length()>0){
                    adresa.setLocalitate(etLocalitate.getText().toString());
                }else{
                    collectedErrors += "Introduceti localitate\n";
                }if(etTara.getText().length()>0){
                    adresa.setTara(etTara.getText().toString());
                }else{
                    collectedErrors += "Introduceti tara\n";
                }

              // boolean isAddressInserted = databaseHelper.insertAdresa(Integer.parseInt(etNumar.getText().toString()), etStrada.getText().toString(),
             //           etLocalitate.getText().toString(), etJudetSector.getText().toString(), etTara.getText().toString(), etTelefon.getText().toString());
                collectedErrors =  collectedErrors.trim();
                if(collectedErrors.length()>0){
                    Toast.makeText(context, collectedErrors,Toast.LENGTH_SHORT).show();
                }else {
                    long lastInsertedAddress = databaseHelper.insertAdresa(adresa);

                    if (lastInsertedAddress != -1 ) {
                        // int cod_adresa = databaseHelper.printAutoIncrements();
                       // int cod_adresa = databaseHelper.getMaxIdAdresa();
                        Log.d("TEST_ADRESA", "lastInsertedAddress=" + lastInsertedAddress);
                        int cod_adresa = databaseHelper.getPrimaryKeyAddress(lastInsertedAddress);
                        Log.d("TEST_ADRESA", "cod_adresa=" + cod_adresa);
                        databaseHelper.insertFurnizor(furnizor, cod_adresa);
                    }

                    etNumar.setText("");
                    etStrada.setText("");
                    etLocalitate.setText("");
                    etJudetSector.setText("");
                    etTara.setText("");

                    etDenumireFurnizor.setText("");
                    etNrInregRC.setText("");
                    etAdresa.setText("");
                    etEmailFurnizor.setText("");
                    Toast.makeText(context, "Furnizor inserat cu succes",Toast.LENGTH_SHORT).show();
                    finish();
                }
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
            spinnerRating.setSelection(spinnerRatingArrayAdapter.getPosition(furnizor.getRating()));
            etNumar.setText(furnizor.getAdresa().getNumar()+"");
            etStrada.setText(furnizor.getAdresa().getStrada());
            etLocalitate.setText(furnizor.getAdresa().getLocalitate());
            etJudetSector.setText(furnizor.getAdresa().getJudet_sector());
            etTara.setText(furnizor.getAdresa().getTara());

            cod_furnizor = furnizor.getCod_furnizor();
            cod_adresa = furnizor.getAdresa().getCod_adresa();
        }

        btnModificaFurnizor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Adresa adresa = new Adresa(cod_adresa,Integer.parseInt(etNumar.getText().toString()), etStrada.getText().toString(),
                        etLocalitate.getText().toString(), etJudetSector.getText().toString(),
                        etTara.getText().toString(),etTelefon.getText().toString());
                databaseHelper.updateFurnizor(cod_furnizor, etDenumireFurnizor.getText().toString(),
                        etNrInregRC.getText().toString(),adresa,
                        Integer.parseInt(spinnerRating.getSelectedItem().toString()),
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
