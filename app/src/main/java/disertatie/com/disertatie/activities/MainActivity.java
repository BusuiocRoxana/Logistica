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
    private LinearLayout llMateriale;
    private LinearLayout llFurnizori;

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // toolbar setup
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
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
        llMateriale = (LinearLayout)findViewById(R.id.llMateriale);
        llFurnizori = (LinearLayout)findViewById(R.id.llFurnizori);

        llDateInterne.setOnClickListener(clickListener);
        llActivitati.setOnClickListener(clickListener);
        llMateriale.setOnClickListener(clickListener);
        llFurnizori.setOnClickListener(clickListener);

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
                case R.id.llMateriale:
                    i = new Intent(MainActivity.this, MaterialsActivity.class);
                    startActivity(i);
                    break;
                case R.id.llFurnizori:
                    i = new Intent(MainActivity.this, FurnizoriActivity.class);
                    startActivity(i);
                    break;
            }
        }
    };
}
