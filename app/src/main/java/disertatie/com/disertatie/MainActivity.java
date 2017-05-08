package disertatie.com.disertatie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private LinearLayout llDateInterne;
    private LinearLayout llActivitati;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
