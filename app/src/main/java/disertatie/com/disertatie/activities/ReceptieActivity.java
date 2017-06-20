package disertatie.com.disertatie.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import disertatie.com.disertatie.Database.DatabaseHelper;
import disertatie.com.disertatie.R;
import disertatie.com.disertatie.Utils.DateConvertor;
import disertatie.com.disertatie.entities.Comanda;
import disertatie.com.disertatie.entities.Furnizor;
import disertatie.com.disertatie.entities.Material;
import disertatie.com.disertatie.entities.Receptie;

public class ReceptieActivity extends AppCompatActivity {

    private Button btnSalveazaReceptie;
    private TextView tvDataReceptie;
    private TextView tvDiferenta;
    private EditText etMaterial;
    private EditText etCantitateComandata;
    private EditText etCantitateReceptionata;
    private Spinner spinnerComenzi;
    private int year, month, day;
    private Context context;
    private ArrayList<Integer> listaReferinteComenzi = new ArrayList<>();
    private ArrayList<Comanda> listaComenzi = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private Comanda comanda = new Comanda();
    private int idComandaSelectata = -1;
    private Receptie receptie =  new Receptie();
    private static String TAG = "Logistica";
    private Calendar calendar;
    private Toolbar toolbar;
    private LinearLayout llCantReceptAnt;
    private TextView tvCantReceptAnt;
    private boolean isFirstDisplayed = false;
    private boolean userIsInteracting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receptie);

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
            tv.setText(R.string.receptie_material);
        }


        context = this;
        tvDataReceptie = (TextView) findViewById(R.id.tvDataReceptie);
        tvDiferenta = (TextView) findViewById(R.id.tvDiferenta);
        tvDataReceptie.setText(DateConvertor.dateToString(new Date()));

        spinnerComenzi = (Spinner) findViewById(R.id.spinnerReferintaReceptie);
        etMaterial = (EditText) findViewById(R.id.etDenumireMat);
        etCantitateComandata = (EditText) findViewById(R.id.etCantComandata);
        etCantitateReceptionata = (EditText) findViewById(R.id.etCantReceptionata);
        btnSalveazaReceptie = (Button)findViewById(R.id.btnSalveazaReceptie);
        llCantReceptAnt = (LinearLayout)findViewById(R.id.llCantReceptAnt);
        tvCantReceptAnt = (TextView) findViewById(R.id.tvCantReceptionataAnt);


        databaseHelper = new DatabaseHelper(context);
        try {
            listaComenzi = databaseHelper.selectComenzi();
            Log.d(TAG,"VERIFICA-ID:"+listaComenzi.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < listaComenzi.size(); i++) {
            listaReferinteComenzi.add(listaComenzi.get(i).getCod_comanda());
            Log.d(TAG,"VERIFICA-ID-int:"+listaReferinteComenzi.toString());
        }

        ArrayAdapter<Integer> spinnerComenziArrayAdapter = new ArrayAdapter<Integer>(context,
                android.R.layout.simple_spinner_dropdown_item, listaReferinteComenzi);


        if(getIntent().getExtras()!=null) {
         isFirstDisplayed = getIntent().getExtras().getBoolean("IS_FIRST_DISPLAYED");
        }
        spinnerComenzi.setAdapter(spinnerComenziArrayAdapter);
        //spinnerComenzi.setSelection(Adapter.NO_SELECTION, false);
        spinnerComenzi.clearFocus();


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        tvDataReceptie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                tvDataReceptie.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, day);
                datePickerDialog.show();

            }
        });

        spinnerComenzi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idComandaSelectata = listaReferinteComenzi.get(position);
                comanda =  selectComanda(idComandaSelectata, listaComenzi);
                double cantReceptAnt = databaseHelper.checkCantitateComanda(comanda.getCod_comanda());
                if(cantReceptAnt > 0 ){
                    llCantReceptAnt.setVisibility(View.VISIBLE);
                    tvCantReceptAnt.setText(cantReceptAnt+"");
                    if(cantReceptAnt ==  comanda.getCerereOferta().getCantitate() && userIsInteracting) {
                        new AlertDialog.Builder(context)
                                .setTitle("Receptie completa")
                                .setMessage("Cantitatea din comanda #" + comanda.getCod_comanda() + " a fost receptionata complet.\n" +
                                        "Doriti sa receptionati alta comanda?")
                                .setPositiveButton(R.string.da, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                .setNegativeButton(R.string.nu, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(ReceptieActivity.this, MainActivity.class);
                                        startActivity(i);
                                        dialog.cancel();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }

                }else{
                    llCantReceptAnt.setVisibility(View.GONE);
                }
                Log.d(TAG, "test comanda="+comanda.toString());
                if(comanda != null) {
                    etCantitateReceptionata.setText("");
                    etMaterial.setText(comanda.getCerereOferta().getMaterial() + "");
                    etCantitateComandata.setText(comanda.getCerereOferta().getCantitate() + "");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etCantitateReceptionata.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && etCantitateReceptionata.getText().length() >0) {
                    double diferenta = Double.parseDouble(etCantitateComandata.getText().toString()) -
                            Double.parseDouble(etCantitateReceptionata.getText().toString());
                    tvDiferenta.setText(diferenta + "");
                }
            }
        });


        btnSalveazaReceptie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etCantitateReceptionata.getText().length()>0) {
                    double cantReceptionata = Double.parseDouble(etCantitateReceptionata.getText().toString());
                    tvDiferenta.setText(comanda.getCerereOferta().getCantitate() - cantReceptionata + "");
                    receptie.setCantitate_receptionata(cantReceptionata);
                    receptie.setData_receptie(tvDataReceptie.getText().toString());
                    receptie.setComanda(comanda);

                    long row_id = databaseHelper.insertReceptie(receptie);
                    if (row_id != -1) {
                        int cod_receptie = databaseHelper.getPrimaryKeyByRowId(row_id, DatabaseHelper.TABLE_RECEPTII, DatabaseHelper.COLUMN_COD_RECEPTIE);
                        Log.d(TAG, "test-receptie" + receptie.toString());
                        Log.e(TAG, "Sending email");
                        Log.i("Send email", "");
                        String[] TO = {receptie.getComanda().getCerereOferta().getFurnizor().getEmail()};
                        String[] CC = {""};
                        String textReceptie = "Receptie cu referinta la Comanda Nr.#" + receptie.getComanda().getCod_comanda() + "\n\n"
                                + "Data Receptie\t" + receptie.getData_receptie() + "\n"
                                + "Material " + receptie.getComanda().getCerereOferta().getMaterial().getDenumire_material().toUpperCase() + "\n"
                                + "Cantitate comandata\t" + receptie.getComanda().getCerereOferta().getCantitate() + "\tbucati\n"
                                + "Cantitate receptionata\t" + receptie.getCantitate_receptionata() + "\n"
                                + "Diferenta \t" + tvDiferenta.getText();

                        // +"Adresa Livrare\t"+"+"";

                        //de luat adresa companie si introdus la livrare

                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                        emailIntent.putExtra(Intent.EXTRA_CC, CC);
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Receptie Nr.#" + cod_receptie);
                        emailIntent.putExtra(Intent.EXTRA_TEXT, textReceptie);
                        startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
                        finish();
                    }
                }else{
                    Toast.makeText(context, "Introduceti cantitate receptionata", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public Comanda selectComanda(int id, ArrayList<Comanda> comenzi) {
        Comanda com = new Comanda();
        for (int i = 0; i < comenzi.size(); i++) {
         try {
             if (comenzi.get(i).getCod_comanda() == id) {
                 com = comenzi.get(i);
             }
         }catch (Exception ex){
         }
        }
        return com;
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
    public void onUserInteraction() {
        super.onUserInteraction();
        userIsInteracting = true;
    }

}
