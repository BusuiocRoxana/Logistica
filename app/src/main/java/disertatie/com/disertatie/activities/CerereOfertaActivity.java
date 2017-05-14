package disertatie.com.disertatie.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private Button btnVerificaCerere;
    private Button btnTrimiteCerere;

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
        btnVerificaCerere = (Button) findViewById(R.id.btnVerificaCerere);
        btnTrimiteCerere = (Button) findViewById(R.id.btnTrimiteCerere);




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

        btnVerificaCerere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerereOferta = new CerereOferta((Material)spinnerMaterial.getSelectedItem(), Double.parseDouble(etCantitate.getText().toString()),
                        DateConvertor.textToDate(tvTermenRaspuns.getText().toString()),
                        (Furnizor)spinnerFurnizor.getSelectedItem(), isConfirmed, Double.parseDouble(etPret.getText().toString()),
                        DateConvertor.textToDate(tvDataLivrare.getText().toString()));
                double valoare = cerereOferta.calculeazaValoare(cerereOferta.getPret(),cerereOferta.getCantitate());
                tvValoare.setText(valoare+"");
            }
        });
        btnTrimiteCerere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String textCerereOferta = "<!doctype html>\n" +
                            "<html>\n" +
                            "<head>\n" +
                            "    <meta charset=\"utf-8\">\n" +
                            "    <title>A simple, clean, and responsive HTML invoice template</title>\n" +
                            "    \n" +
                            "    <style>\n" +
                            "    .invoice-box{\n" +
                            "        max-width:800px;\n" +
                            "        margin:auto;\n" +
                            "        padding:30px;\n" +
                            "        border:1px solid #eee;\n" +
                            "        box-shadow:0 0 10px rgba(0, 0, 0, .15);\n" +
                            "        font-size:16px;\n" +
                            "        line-height:24px;\n" +
                            "        font-family:'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif;\n" +
                            "        color:#555;\n" +
                            "    }\n" +
                            "    \n" +
                            "    .invoice-box table{\n" +
                            "        width:100%;\n" +
                            "        line-height:inherit;\n" +
                            "        text-align:left;\n" +
                            "    }\n" +
                            "    \n" +
                            "    .invoice-box table td{\n" +
                            "        padding:5px;\n" +
                            "        vertical-align:top;\n" +
                            "    }\n" +
                            "    \n" +
                            "    .invoice-box table tr td:nth-child(2){\n" +
                            "        text-align:right;\n" +
                            "    }\n" +
                            "    \n" +
                            "    .invoice-box table tr.top table td{\n" +
                            "        padding-bottom:20px;\n" +
                            "    }\n" +
                            "    \n" +
                            "    .invoice-box table tr.top table td.title{\n" +
                            "        font-size:30px;\n" +
                            "        line-height:45px;\n" +
                            "        color:#333;\n" +
                            "    }\n" +
                            "    \n" +
                            "    .invoice-box table tr.information table td{\n" +
                            "        padding-bottom:40px;\n" +
                            "    }\n" +
                            "    \n" +
                            "    .invoice-box table tr.heading td{\n" +
                            "        background:#eee;\n" +
                            "        border-bottom:1px solid #ddd;\n" +
                            "        font-weight:bold;\n" +
                            "    }\n" +
                            "    \n" +
                            "    .invoice-box table tr.details td{\n" +
                            "        padding-bottom:20px;\n" +
                            "    }\n" +
                            "    \n" +
                            "    .invoice-box table tr.item td{\n" +
                            "        border-bottom:1px solid #eee;\n" +
                            "    }\n" +
                            "    \n" +
                            "    .invoice-box table tr.item.last td{\n" +
                            "        border-bottom:none;\n" +
                            "    }\n" +
                            "    \n" +
                            "    .invoice-box table tr.total td:nth-child(2){\n" +
                            "        border-top:2px solid #eee;\n" +
                            "        font-weight:bold;\n" +
                            "    }\n" +
                            "    \n" +
                            "    @media only screen and (max-width: 600px) {\n" +
                            "        .invoice-box table tr.top table td{\n" +
                            "            width:100%;\n" +
                            "            display:block;\n" +
                            "            text-align:center;\n" +
                            "        }\n" +
                            "        \n" +
                            "        .invoice-box table tr.information table td{\n" +
                            "            width:100%;\n" +
                            "            display:block;\n" +
                            "            text-align:center;\n" +
                            "        }\n" +
                            "    }\n" +
                            "    </style>\n" +
                            "</head>\n" +
                            "\n" +
                            "<body>\n" +
                            "    <div class=\"invoice-box\">\n" +
                            "        <table cellpadding=\"0\" cellspacing=\"0\">\n" +
                            "            <tr class=\"top\">\n" +
                            "                <td colspan=\"2\">\n" +
                            "                    <table>\n" +
                            "                        <tr>\n" +
                            "                            <td class=\"title\">\n" +
                            "                              Cerere de Oferta\n" +
                            "                            </td>\n" +
                            "                            \n" +
                            "                            <td>\n" +
                            "                                Numar Document #: 123<br>\n" +
                            "                                Data: January 1, 2015<br>\n" +
                            "                                Termen Raspuns: February 1, 2015<br>\n" +
                            "                              \tData Estimativa Livrare: February 1, 2015\n" +
                            "                            </td>\n" +
                            "                        </tr>\n" +
                            "                    </table>\n" +
                            "                </td>\n" +
                            "            </tr>\n" +
                            "            \n" +
                            "            <tr class=\"information\">\n" +
                            "                <td colspan=\"2\">\n" +
                            "                    <table>\n" +
                            "                        <tr>\n" +
                            "                            <td>\n" +
                            "                               Furnizor<br>\n" +
                            "                                Next Step Webs, Inc.<br>\n" +
                            "                                12345 Sunny Road<br>\n" +
                            "                                Sunnyville, TX 12345\n" +
                            "                            </td>\n" +
                            "                            \n" +
                            "                            <td>\n" +
                            "                               Companie<br>\n" +
                            "                                Acme Corp.<br>\n" +
                            "                                John Doe<br>\n" +
                            "                                john@example.com\n" +
                            "                            </td>\n" +
                            "                        </tr>\n" +
                            "                    </table>\n" +
                            "                </td>\n" +
                            "            </tr>\n" +
                            "          \n" +
                            "            \n" +
                            "            <tr class=\"heading\">\n" +
                            "                <td>\n" +
                            "                    Material\n" +
                            "                </td>\n" +
                            "                \n" +
                            "                <td>\n" +
                            "                    Pret\n" +
                            "                </td>\n" +
                            "            </tr>\n" +
                            "            \n" +
                            "            <tr class=\"item\">\n" +
                            "                <td>\n" +
                            "                    Website design\n" +
                            "                </td>\n" +
                            "                \n" +
                            "                <td>\n" +
                            "                    $300.00\n" +
                            "                </td>\n" +
                            "            </tr>\n" +
                            "            \n" +
                            "           \n" +
                            "            \n" +
                            "            \n" +
                            "            <tr class=\"total\">\n" +
                            "                <td></td>\n" +
                            "                \n" +
                            "                <td>\n" +
                            "                   Total: $385.00\n" +
                            "                </td>\n" +
                            "            </tr>\n" +
                            "        </table>\n" +
                            "    </div>\n" +
                            "</body>\n" +
                            "</html>";

                    Log.i("Send email", "");
                    String[] TO = {"busuioc.roxana@gmail.com"};
                    String[] CC = {""};
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);

                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                    emailIntent.putExtra(Intent.EXTRA_CC, CC);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Cerere de Oferta");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "" + "\n" +Html.fromHtml(textCerereOferta));

                    try {
                        startActivity(Intent.createChooser(emailIntent, "Trimite emal..."));
                        finish();
                        Log.i("Finished sending email", "");
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(CerereOfertaActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                    }
                }

        });
    }
}
