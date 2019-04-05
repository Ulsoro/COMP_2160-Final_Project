package osborne.william.glucotrak;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditBloodGlucoseActivity extends AppCompatActivity {

    ImageButton cancelButton;
    ImageButton acceptButton;

    EditText bgConcentration;
    Spinner bgRelTime;
    TextView bgDate;
    TextView bgTime;
    EditText bgNote;

    private BloodGlucoseViewModel bloodGlucoseViewModel;

    Intent intent;

    boolean editExisting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_blood_glucose);

        cancelButton = (ImageButton) findViewById(R.id.bgCancelImageButton);
        acceptButton = (ImageButton) findViewById(R.id.bgAcceptImageButton);

        bgConcentration = (EditText) findViewById(R.id.bgConcentrationEditText);
        bgRelTime = (Spinner) findViewById(R.id.bgRelativeTimeMeasuredSpinner);
        bgDate = (TextView) findViewById(R.id.bgDateTextView);
        bgTime = (TextView) findViewById(R.id.bgTimeTextView);
        bgNote = (EditText) findViewById(R.id.bgNotesEditText);

        // View model gives access to Database
        bloodGlucoseViewModel = ViewModelProviders.of(this).get(BloodGlucoseViewModel.class);

        intent = getIntent();

        editExisting = intent.getBooleanExtra("existing", false);

        if (editExisting) {
            bgConcentration.setText(String.valueOf(intent.getDoubleExtra("bgcon", 0)));
            bgRelTime.setSelection(0);  // TODO FIX THIS
            bgDate.setText(intent.getStringExtra("date"));
            bgNote.setText(intent.getStringExtra("notes"));
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double bgCon = Double.parseDouble(bgConcentration.getText().toString());
                String bgRTime = bgRelTime.getSelectedItem().toString();
                long time = System.currentTimeMillis();
                String note = bgNote.getText().toString();

                if (editExisting){
                    long id = intent.getLongExtra("id", System.currentTimeMillis());
                    bloodGlucoseViewModel.insert(new BloodGlucoseRecord(id, time, bgCon, bgRTime, note));
                }
                else {
                    bloodGlucoseViewModel.insert(new BloodGlucoseRecord(time, bgCon, bgRTime, note));
                }

                finish();
            }
        });
    }
}
