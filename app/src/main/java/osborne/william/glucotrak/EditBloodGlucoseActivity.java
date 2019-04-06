package osborne.william.glucotrak;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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

    boolean editExisting; // Flag for editing an existing record

    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_blood_glucose);

        cancelButton = (ImageButton) findViewById(R.id.bgCancelImageButton);
        acceptButton = (ImageButton) findViewById(R.id.bgAcceptImageButton);

        bgConcentration = (EditText) findViewById(R.id.bgConcentrationEditText);
        bgRelTime = (Spinner) findViewById(R.id.bgRelativeTimeMeasuredSpinner);
        bgDate = (TextView) findViewById(R.id.bgRowDateEditText);
        bgTime = (TextView) findViewById(R.id.bgTimeEditText);
        bgNote = (EditText) findViewById(R.id.bgNotesEditText);

        // View model gives access to Database
        bloodGlucoseViewModel = ViewModelProviders.of(this).get(BloodGlucoseViewModel.class);

        intent = getIntent();

        myCalendar = Calendar.getInstance();

        editExisting = intent.getBooleanExtra("existing", false);

        if (editExisting) {

            // Load the saved time into the calendar
            myCalendar.setTimeInMillis(intent.getLongExtra("date", 0));

            bgConcentration.setText(String.valueOf(intent.getDoubleExtra("bgcon", 0)));

            bgNote.setText(intent.getStringExtra("notes"));

            // Determine Relative Date
            int spinnerIndex = 0;
            String spinnerCompare = intent.getStringExtra("relativeTime");
            for (int i=0;i<bgRelTime.getCount();i++){
                if (bgRelTime.getItemAtPosition(i).toString().equalsIgnoreCase(spinnerCompare)){
                    spinnerIndex = i;
                }
            }
            bgRelTime.setSelection(spinnerIndex);
        }
        updateLabel();

        // Cancels edits and returns to Main Activity
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Accepts the Edits entered and inserts into the Database
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double bgCon = Double.parseDouble(bgConcentration.getText().toString());
                String bgRTime = bgRelTime.getSelectedItem().toString();
                long time = myCalendar.getTimeInMillis();
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


        // Updates the Calendar when the DatePicked selects a date
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        bgDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditBloodGlucoseActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        // Updates the Time when the TimePicker selects a time
        bgTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                int minute = myCalendar.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EditBloodGlucoseActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        myCalendar.set(Calendar.HOUR, selectedHour);
                        myCalendar.set(Calendar.MINUTE, selectedMinute);
                        updateLabel();
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    // Updates the Date and Time with current values from the Calendar Object
    private void updateLabel() {
        String dateFormat = "dd-MMM-yyyy"; // Format for Date
        String timeFormat = "h:mm aa";
        SimpleDateFormat sdfDate = new SimpleDateFormat(dateFormat, Locale.US);
        SimpleDateFormat sdfTime = new SimpleDateFormat(timeFormat, Locale.US);

        bgDate.setText(sdfDate.format(myCalendar.getTime()));
        bgTime.setText(sdfTime.format(myCalendar.getTime()));
    }

}
