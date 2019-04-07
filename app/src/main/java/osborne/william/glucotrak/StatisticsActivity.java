package osborne.william.glucotrak;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;





/* NOT CURRENTLY USED  */

public class StatisticsActivity extends AppCompatActivity {

    TextView test1;

    private BloodGlucoseViewModel bloodGlucoseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        test1 = findViewById(R.id.allAvgtextView);



    }
}
