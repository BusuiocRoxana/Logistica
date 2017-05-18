package disertatie.com.disertatie.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import disertatie.com.disertatie.Constants.HtmlClass;
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
    private static String TAG = "log-async";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerere_oferta);

        context = this;

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
                if(isStoragePermissionGranted()) {
                    new AsyncFileMaker().execute("cerere_oferta.html");
                }

                    /*String textCerereOferta = "";


                    Log.i("Send email", "");
                    String[] TO = {"busuioc.roxana@gmail.com"};
                    String[] CC = {""};
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);

                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/html");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                    emailIntent.putExtra(Intent.EXTRA_CC, CC);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Cerere de Oferta");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("<html><body><p>Companie: Denumire</p>\n" +
                            "<p>Nr. Inreg. Registrul Comertului: J44/40/12.12.2016</p>\n" +
                            "<p>Doc. Nr. #123</p>\n" +"<span></span>"+"bla"+
                            "<p>Data: 01.01.2017</p>\n" +
                            "<table>\n"+
                            "<tr><th>Material</th><th>Cantitate</th><th>Pret</th><th>Valoare</th></tr>\n"+
                            "<tr>\n"+
                            "<td>Apa plata</td>\n"+
                            "<td>"+etCantitate.getText().toString()+"</td>\n"+
                            "<td>"+etPret.getText().toString()+" LEI</td>\n"+
                            "<td>"+tvValoare.getText().toString()+"LEI</td>\n"+
                            "</tr>\n"+
                            "</table>\n"+
                            "<p>Termen de Raspuns: "+tvTermenRaspuns.getText().toString()+"</p>\n" +
                            "<p>Data Estimativa Livrare: "+tvDataLivrare.getText().toString()+"</p></body></html>"));
              //  emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("<a href=\"file://///C:/Users/i335495/AndroidStudioProjects/Logistica/raspuns_cerere_oferta.html\">right click </a>"));

             

                    try {
                        startActivity(Intent.createChooser(emailIntent, "Trimite email..."));
                        finish();
                        Log.i("Finished sending email", "");
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(CerereOfertaActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                    }*/
                }

        });
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    private class AsyncFileMaker extends AsyncTask<String,Void,Void>
    {
        String fileName;
        File f;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            fileName=params[0];
            Log.e(TAG,"Creating file");
            try {
                f = new File(getExternalFilesDir(null), fileName);
                FileOutputStream os = new FileOutputStream(f);
               /* String text = "<!DOCTYPE html>" +
                        "<html> " +
                        "<head> " +
                        "<style> " +
                        "table, th, td { " +
                        "    border: 1px solid black; " +
                        "} " +
                        "</style> " +
                        "</head> " +
                        "<body> " +
                        " " +
                        "<table> " +
                        "  <tr> " +
                        "    <th>Month</th> " +
                        "    <th>Savings</th> " +
                        "  </tr> " +
                        "  <tr> " +
                        "    <td>January</td> " +
                        "    <td>$100</td> " +
                        "  </tr> " +
                        "  <tr> " +
                        "    <td>February</td> " +
                        "    <td>$80</td> " +
                        "  </tr> " +
                        "</table> " +
                        " " +
                        "</body> " +
                        "</html> ";*/
               /*String text = "<!DOCTYPE html>\n" +
                       "<html>\n" +
                       "<body>\n" +
                       "\n" +
                       "<p>Click the button to trigger a function that will output \"Hello World\" in a p element with id=\"demo\".</p>\n" +
                       "\n" +
                       "<button onclick=\"foo()\">Click me</button>\n" +
                       "\n" +
                       "<p id=\"demo\"></p>\n" +
                       "\n" +
                       "<script>\n" +
                       "function myFunction() {\n" +
                       "    document.getElementById(\"demo\").innerHTML = \"Hello World\";\n" +
                       "}\n" +
                       "function foo(){\n" +
                       "document.getElementById(\"demo\").innerHTML = \"Start\";\n" +
                       "var request = new XMLHttpRequest();\n" +
                       "request.open('POST', \"https://fcm.googleapis.com/fcm/send\", true);\n" +
                       "request.setRequestHeader('Content-Type', 'application/json');\n" +
                       "request.setRequestHeader('Authorization', 'key=AAAAVNU4yx8:APA91bFPqjAAqw9GEEb_RAnDsujxTR-sE-cQ8zxFQAU1t13Z3XNrR8NwK8gIBoSreVVte5nShz13qW21pt4PqCh__YZmG64Y9kE0iRWoc7aFr9eaW6IFlKoR4UVup2nOvPba7NCJXXGH');\n" +
                       "\n" +
                       "document.getElementById(\"demo\").innerHTML = \"Start 2\";\n" +
                       "request.onreadystatechange = function () {\n" +
                       "    if (request.readyState === 4) {\n" +
                       "       alert(request.responseText);\n" +
                       "    }\n" +
                       "    document.getElementById(\"demo\").innerHTML = \"Callback finished\";\n" +
                       "}\n" +
                       "request.send('{'+\n" +
                       "  '\"to\" : \"eFGq6m0IBpw:APA91bHTsc6eARAzFJ1jILaGJybiN0ifp--koTj3MaL1KaJJhCgkY3pcVtGCbywe5ctPwmx7VrO41YavcLzW0kPk04pVdO6GtCTnlzSxf785q6v94QDo6DS3PrWyh6IgwEnTxSa94Cj3\",'+\n" +
                       "  '\"notification\" : { \"title\" : \"From HTML\" , \"body\" : \"123456\" }'+\n" +
                       "'}');\n" +
                       "\n" +
                       "document.getElementById(\"demo\").innerHTML = \"Attempting send\";\n" +
                       "}\n" +
                       "</script>\n" +
                       "\n" +
                       "</body>\n" +
                       "</html>\n" +
                       "\n";*/

                String text = HtmlClass.invoice;
                os.write(text.getBytes());
                os.close();
            }catch (FileNotFoundException e){
                Log.e(TAG,"Could not find file : "+f.getAbsolutePath());
                cancel(false);
                return null;
            }catch (IOException e){
                Log.e(TAG,"Could not write file : "+f.getAbsolutePath());
                cancel(false);
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.e(TAG,"Sending email");
            Log.i("Send email", "");
            String[] TO = {"busuioc.roxana@gmail.com"};
            String[] CC = {""};
            Intent emailIntent = new Intent(Intent.ACTION_SEND);

            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/html");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_CC, CC);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Cerere de Oferta");
           /* emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("<p><b>Companie:</b> Denumire</p>\n" +
                    "<p><b>Nr. Inreg. Registrul Comertului:</b> J44/40/12.12.2016</p>\n" +
                    "<p><b>Doc. Nr.</b> #123</p>\n" +
                    "<p><b>Data:</b> 01.01.2017</p>\n" +
                    "<p><b>Material:</b> Apa plata</p>\n" +
                    "<p><b>Cantitate: 123</p>\n" +
                    "<p><b>Pret:</b> 123 LEI</p>\n" +
                    "<p><b>Valoare:</b> 123 LEI<p/>\n" +
                    "<p><b>Termen de Raspuns:</b> 1</p>\n" +
                    "<p><b>Data Estimativa Livrare:</b> 1.1.2017</p>"
            ));*/
            emailIntent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(f));
            startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
        }


    }
}
