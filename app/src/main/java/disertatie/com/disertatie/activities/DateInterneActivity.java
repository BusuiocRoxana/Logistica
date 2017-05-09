package disertatie.com.disertatie.activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import disertatie.com.disertatie.Database.DatabaseHelper;
import disertatie.com.disertatie.R;

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

        databaseHelper = new DatabaseHelper(this);

        btnSalveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.insertCompanie(etDenumireCompanie.getText().toString(), etNrInregRC.getText().toString(),
                        etEmailCompanie.getText().toString(), Integer.parseInt(etCodAdresaCompanie.getText().toString()),
                        etTelefonCompanie.getText().toString());
            }
        });

        // Bundle extras = getIntent().getExtras();
        // if (extras != null) {
        // int Value = extras.getInt("id");

        //if (Value > 0) {

        Cursor rs = databaseHelper.getData();
        //id_To_Update = Value;
        rs.moveToFirst();
        if (rs.getCount() != 0) {
            String denumire = rs.getString(rs.getColumnIndex(DatabaseHelper.COLUMN_DENUMIRE_COMPANIE));
            String nrInregRC = rs.getString(rs.getColumnIndex(DatabaseHelper.COLUMN_NR_INREG_RC));
            String adresa = rs.getString(rs.getColumnIndex(DatabaseHelper.COLUMN_COD_ADRESA));
            String telefon = rs.getString(rs.getColumnIndex(DatabaseHelper.COLUMN_TELEFON_COMPANIE));
            String email = rs.getString(rs.getColumnIndex(DatabaseHelper.COLUMN_EMAIL_COMPANIE));

            if (!rs.isClosed()) {
                rs.close();
            }

            etDenumireCompanie.setText((CharSequence) denumire);
            etDenumireCompanie.setFocusable(false);
            etDenumireCompanie.setClickable(false);

            etNrInregRC.setText((CharSequence) nrInregRC);
            etNrInregRC.setFocusable(false);
            etNrInregRC.setClickable(false);

            etTelefonCompanie.setText((CharSequence) telefon);
            etTelefonCompanie.setFocusable(false);
            etTelefonCompanie.setClickable(false);

            etEmailCompanie.setText((CharSequence) email);
            etEmailCompanie.setFocusable(false);
            etEmailCompanie.setClickable(false);

            etCodAdresaCompanie.setText((CharSequence) adresa);
            etCodAdresaCompanie.setFocusable(false);
            etCodAdresaCompanie.setClickable(false);

            etTelefonCompanie.setText((CharSequence) telefon);
            etTelefonCompanie.setFocusable(false);
            etTelefonCompanie.setClickable(false);
        }
    }
        }
    //}
//}