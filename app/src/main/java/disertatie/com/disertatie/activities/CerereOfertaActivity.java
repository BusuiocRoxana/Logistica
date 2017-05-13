package disertatie.com.disertatie.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import disertatie.com.disertatie.Database.DatabaseHelper;
import disertatie.com.disertatie.R;
import disertatie.com.disertatie.Utils.DateConvertor;
import disertatie.com.disertatie.entities.CerereOferta;
import disertatie.com.disertatie.entities.Furnizor;
import disertatie.com.disertatie.entities.Material;

public class CerereOfertaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout llTermenRaspuns;
    private LinearLayout llDataLivrare;
    private TextView tvTermenRaspuns;
    private TextView tvDataLivrare;
    private TextView tvValoare;
    private Calendar calendar;
    private EditText etCantitate;
    private EditText etPret;
    private Spinner spinnerMaterial;
    private Spinner spinnerFurnizor;

    private int year, month, day;
    private Context context;
    private CerereOferta cerereOferta = new CerereOferta();
    private DatabaseHelper databaseHelper;
    private ArrayList<Material> listaMateriale = new ArrayList<>();
    private ArrayList<Furnizor> listaFurnizori = new ArrayList<>();

    private boolean isConfirmed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerere_oferta);

        context = this;
        databaseHelper = new DatabaseHelper(context);
        try {
            listaMateriale = databaseHelper.getAllMaterials();
            listaFurnizori = databaseHelper.getAllFurnizori();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // toolbar setup
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
            TextView tv = (TextView) toolbar.findViewById(R.id.toolbar_title);
            tv.setText(R.string.cerere_oferta);
        }
        llTermenRaspuns = (LinearLayout)findViewById(R.id.llTermenRaspuns);
        llDataLivrare = (LinearLayout) findViewById(R.id.llDataLivrare);


        tvTermenRaspuns = (TextView) findViewById(R.id.tvTermenRaspuns);
        tvDataLivrare = (TextView)findViewById(R.id.tvDataLivrare);
        tvValoare = (TextView) findViewById(R.id.tvValoare);
        etCantitate = (EditText) findViewById(R.id.etCantitate);
        etPret = (EditText) findViewById(R.id.etPret);
        spinnerFurnizor = (Spinner)findViewById(R.id.spinnerFurnizor);
        spinnerMaterial = (Spinner)findViewById(R.id.spinnerMaterial);


        ArrayAdapter<Material> spinnerMaterialeArrayAdapter = new ArrayAdapter<Material>(context,
                android.R.layout.simple_spinner_dropdown_item,listaMateriale);

        ArrayAdapter<Furnizor> spinnerFurnizoriArrayAdapter = new ArrayAdapter<Furnizor>(context,
                android.R.layout.simple_spinner_dropdown_item,listaFurnizori);

        spinnerMaterial.setAdapter(spinnerMaterialeArrayAdapter);
        spinnerFurnizor.setAdapter(spinnerFurnizoriArrayAdapter);


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


       tvDataLivrare.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                       new DatePickerDialog.OnDateSetListener() {

                           @Override
                           public void onDateSet(DatePicker view, int year,
                                                 int monthOfYear, int dayOfMonth) {

                               tvDataLivrare.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                           }
                       }, year, month, day);
               datePickerDialog.show();

           }
       });
        tvTermenRaspuns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                tvTermenRaspuns.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, day);
                datePickerDialog.show();

            }
        });




        etPret.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                cerereOferta = new CerereOferta((Material)spinnerMaterial.getSelectedItem(), Double.parseDouble(etCantitate.getText().toString()),
                        DateConvertor.textToDate(tvTermenRaspuns.getText().toString()),
                        (Furnizor)spinnerFurnizor.getSelectedItem(), isConfirmed, Double.parseDouble(etPret.getText().toString()),
                        DateConvertor.textToDate(tvDataLivrare.getText().toString()));
                double valoare = cerereOferta.calculeazaValoare(cerereOferta.getPret(),cerereOferta.getCantitate());
                tvValoare.setText(valoare+"");
            }
        });

    }
}
