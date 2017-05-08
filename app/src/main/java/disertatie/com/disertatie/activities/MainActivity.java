package disertatie.com.disertatie.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import disertatie.com.disertatie.R;

public class MainActivity extends AppCompatActivity {
    private LinearLayout llDateInterne;
    private LinearLayout llActivitati;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // toolbar setup
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            // use toolbar as actionbar
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
            TextView tv = (TextView) toolbar.findViewById(R.id.toolbar_title);
            tv.setText(R.string.pagina_principala);
        }

        llDateInterne=(LinearLayout)findViewById(R.id.llDateInterne);
        llActivitati=(LinearLayout)findViewById(R.id.llActivitati);

        llDateInterne.setOnClickListener(clickListener);
        llActivitati.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i;
            switch (v.getId())
            {
                case R.id.llDateInterne:
                    i=new Intent(MainActivity.this,DateInterneActivity.class);
                    startActivity(i);
                    break;
                case R.id.llActivitati:
                    break;
            }
        }
    };
}
