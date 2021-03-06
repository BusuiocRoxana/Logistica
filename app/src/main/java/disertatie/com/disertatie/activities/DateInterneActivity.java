package disertatie.com.disertatie.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import disertatie.com.disertatie.Database.DatabaseHelper;
import disertatie.com.disertatie.R;
import disertatie.com.disertatie.entities.Adresa;
import disertatie.com.disertatie.entities.Companie;

public class DateInterneActivity extends AppCompatActivity {

    private static final String TAG = "Logistica";
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

    private LinearLayout rlAdresa;
    private EditText etNumar;
    private EditText etStrada;
    private EditText etLocalitate;
    private EditText etJudetSector;
    private EditText etTara;
    private int cod_adresa;
    private int cod_companie;
    private Adresa adresa = new Adresa();
    private Companie companie  = new Companie();

    private Context context;

    private boolean isActionPressed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_interne);
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

        rlAdresa = (LinearLayout)findViewById(R.id.adresaFurnizor);
        etNumar = (EditText) rlAdresa.findViewById(R.id.etNumar);
        etStrada = (EditText) rlAdresa.findViewById(R.id.etStrada);
        etJudetSector = (EditText) rlAdresa.findViewById(R.id.etJudetSector);
        etLocalitate = (EditText) rlAdresa.findViewById(R.id.etLocalitate);
        etTara = (EditText) rlAdresa.findViewById(R.id.etTara);


        databaseHelper = new DatabaseHelper(this);


        etNrInregRC.addTextChangedListener(new TextWatcher() {
            boolean isEdiging;
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }


            @Override
            public void afterTextChanged(Editable s) {
                if(isEdiging || isActionPressed) return;
                isEdiging = true;
                // removing old dashes
                StringBuilder sb = new StringBuilder();
                sb.append(s.toString().replace("/", ""));

                if (sb.length()> 3)
                    sb.insert(3, "/");
                if (sb.length()> 6)
                    sb.insert(6, "/");
                if (sb.length()> 9)
                    sb.insert(9, "/");
                if (sb.length()> 12)
                    sb.insert(12, "/");
                if(sb.length()> 17)
                    sb.delete(17, sb.length());

                s.replace(0, s.length(), sb.toString());
                isEdiging = false;
            }
        });

        etNrInregRC.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if(etNrInregRC.getText().length()<17){
                        Toast.makeText(context, "Numar Registrul Comertului incomplet",Toast.LENGTH_SHORT).show();
                        return true;
                    }


                }

                return false;
            }
        });

        /*etNrInregRC.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {


                    if(etNrInregRC.getText().length() > 0 && etNrInregRC.getText().length()==17) {
                        isActionPressed = true;
                        String nr_series = etNrInregRC.getText().toString().substring(0, 7);
                        String nr_date = etNrInregRC.getText().toString().substring(7, 17);
                        String[] date_elem =  nr_date.split("/");
                        if(date_elem.length > 0) {
                            String day = date_elem[0].toString();
                            String month = date_elem[1].toString();
                            String year = date_elem[2].toString();
                            String data_elem_final = day + "." + month + "." + year;
                            StringBuilder nr_all = new StringBuilder(nr_series.concat(data_elem_final));
                            etNrInregRC.setText("");
                            etNrInregRC.setText(nr_all.toString());

                            Log.d(TAG, "nr_series=" + nr_series);
                            Log.d(TAG, "nr_date=" + nr_date);
                            Log.d(TAG, "nr_all=" + nr_all);
                            Log.d(TAG, "date=" + date_elem.toString());
                            etNrInregRC.clearFocus();
                            etTelefonCompanie.requestFocus();
                            return true;
                        }

                    }else{
                        Toast.makeText(context, "Numar Registrul Comertului incomplet",Toast.LENGTH_SHORT).show();
                        return false;
                    }

                }


                return false;
            }
        });*/


        if(databaseHelper.numberOfRowsCompanie() != 0) {
            Companie companie = databaseHelper.getCompany();
            Log.d(TAG, "companie:"+companie.toString());
            cod_companie = companie.getCod_companie();
            cod_adresa = companie.getAdresa().getCod_adresa();
            etDenumireCompanie.setText(companie.getDenumire_companie());
            etNrInregRC.setText(companie.getNr_inreg_RC());
            etEmailCompanie.setText(companie.getEmail());
            etNumar.setText(companie.getAdresa().getNumar() + "");
            etStrada.setText(companie.getAdresa().getStrada());
            etLocalitate.setText(companie.getAdresa().getLocalitate());
            etJudetSector.setText(companie.getAdresa().getJudet_sector());
            etTara.setText(companie.getAdresa().getTara());
            etTelefonCompanie.setText(companie.getAdresa().getTelefon());


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
              /*  String collectedErrors = "";
                if(etDenumireCompanie.getText().length()>0){
                    companie.setDenumire_companie(etDenumireCompanie.getText().toString());
                }else{
                    collectedErrors += "Introduceti denumire companie\n";
                }
                if(etNrInregRC.getText().length() == 17){
                    companie.setNr_inreg_RC(etNrInregRC.getText().toString());
                }else{
                    collectedErrors += "Nr. Inregistrare Registrul Comertului incomplet\n";
                }
                if(etEmailCompanie.getText().length()>0 && isValidEmail(etEmailCompanie.getText().toString())){
                    companie.setEmail(etEmailCompanie.getText().toString());
                }else{
                    collectedErrors += "Email companie invalid\n";
                }
                if(etTelefonCompanie.getText().length()>0){
                    adresa.setTelefon(etTelefonCompanie.getText().toString());
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
                collectedErrors =  collectedErrors.trim();
                if(collectedErrors.length() > 0){
                    Toast.makeText(context, collectedErrors,Toast.LENGTH_SHORT).show();
                }*/
              if(checkInput()){
                    long lastInsertedAddress = databaseHelper.insertAdresa(adresa);
                    if (lastInsertedAddress != -1 ) {
                        Log.d("TEST_ADRESA", "lastInsertedAddress=" + lastInsertedAddress);
                        int cod_adresa = databaseHelper.getPrimaryKeyAddress(lastInsertedAddress);
                        Log.d("TEST_ADRESA", "cod_adresa=" + cod_adresa);
                        databaseHelper.insertCompanie(etDenumireCompanie.getText().toString(), etNrInregRC.getText().toString(),
                                etEmailCompanie.getText().toString(), cod_adresa);
                        Toast.makeText(context, "Date inserate cu succes",Toast.LENGTH_SHORT).show();

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
                        etTelefonCompanie.setEnabled(false);
                        rlAdresa.setEnabled(false);
                    }

                }



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
                etTelefonCompanie.setEnabled(true);
                rlAdresa.setEnabled(true);


            }
        });

        btnSalveazaModificari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()) {
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
                    etTelefonCompanie.setEnabled(false);
                    rlAdresa.setEnabled(false);
                    Adresa adresa = new Adresa(cod_adresa, Integer.parseInt(etNumar.getText().toString()), etStrada.getText().toString(),
                            etLocalitate.getText().toString(), etJudetSector.getText().toString(), etTara.getText().toString(), etTelefonCompanie.getText().toString());

                    databaseHelper.updateCompanie(cod_companie, etDenumireCompanie.getText().toString(), etNrInregRC.getText().toString(),
                            etEmailCompanie.getText().toString(),
                            adresa);
                }
            }
        });

        btnTaxe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DateInterneActivity.this, AdaugaTaxeActivity.class);
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

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public boolean checkInput(){
        String collectedErrors = "";
        if(etDenumireCompanie.getText().length()>0){
            companie.setDenumire_companie(etDenumireCompanie.getText().toString());
        }else{
            collectedErrors += "Introduceti denumire companie\n";
        }
        if(etNrInregRC.getText().length() == 17){
            companie.setNr_inreg_RC(etNrInregRC.getText().toString());
        }else{
            collectedErrors += "Nr. Inregistrare Registrul Comertului incomplet\n";
        }
        if(etEmailCompanie.getText().length()>0 && isValidEmail(etEmailCompanie.getText().toString())){
            companie.setEmail(etEmailCompanie.getText().toString());
        }else{
            collectedErrors += "Email companie invalid\n";
        }
        if(etTelefonCompanie.getText().length()>0){
            adresa.setTelefon(etTelefonCompanie.getText().toString());
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
        collectedErrors =  collectedErrors.trim();
        if(collectedErrors.length() > 0){
            Toast.makeText(context, collectedErrors,Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }
}
