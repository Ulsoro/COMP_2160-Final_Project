package osborne.william.glucotrak;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StatisticsActivity extends AppCompatActivity {

    TextView test1;

    private BloodGlucoseViewModel bloodGlucoseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        test1 = (TextView) findViewById(R.id.allAvgtextView);



    }
}
