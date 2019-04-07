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
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditBloodPressureActivity extends AppCompatActivity {

    ImageButton cancelButton;
    ImageButton acceptButton;

    EditText systolicPressureEditText;
    EditText diastolicPressureEditText;
    Spinner armSpinner;
    EditText bpDateEditText;
    EditText bpTimeEditText;
    EditText bpNotesEditText;

    Intent intent;

    boolean editExisting;

    Calendar myCalendar;

    private BPViewModel bpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_blood_pressure);

        myCalendar = Calendar.getInstance();

        // Set up elements
        cancelButton = (ImageButton) findViewById(R.id.bpCancelImageButton);
        acceptButton = (ImageButton) findViewById(R.id.bpAcceptImageButton);

        systolicPressureEditText = (EditText) findViewById(R.id.bpSystolicPressureEditText);
        diastolicPressureEditText = (EditText) findViewById(R.id.bpDiastolicPressureEditText);
        armSpinner = (Spinner) findViewById(R.id.armSpinner);
        bpDateEditText = (EditText) findViewById(R.id.bpDateEditText);
        bpTimeEditText = (EditText) findViewById(R.id.bpTimeEditText);
        bpNotesEditText = (EditText) findViewById(R.id.bpNotesEditText);

        bpViewModel = ViewModelProviders.of(this).get(BPViewModel.class);

        intent = getIntent();

        editExisting = intent.getBooleanExtra("existing", false);


        if (editExisting) {

            // Load the saved time into the calendar
            myCalendar.setTimeInMillis(intent.getLongExtra("date", 0));

            systolicPressureEditText.setText(String.valueOf(intent.getIntExtra("systolic", 0)));
            diastolicPressureEditText.setText(String.valueOf(intent.getIntExtra("diastolic", 0)));
            bpNotesEditText.setText(intent.getStringExtra("notes"));

            // Determine Relative Date
            int spinnerIndex = 0;
            String spinnerCompare = intent.getStringExtra("arm");
            for (int i=0;i<armSpinner.getCount();i++){
                if (armSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(spinnerCompare)){
                    spinnerIndex = i;
                }
            }
            armSpinner.setSelection(spinnerIndex);
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

                int systolic;
                try {
                    systolic = Integer.parseInt(systolicPressureEditText.getText().toString());
                }
                catch (NumberFormatException ex) {
                    systolic = 0;
                }


                int diastolic;
                try {
                    diastolic = Integer.parseInt(diastolicPressureEditText.getText().toString());
                }
                catch (NumberFormatException ex) {
                    diastolic = 0;
                }

                String arm = armSpinner.getSelectedItem().toString();
                long date = myCalendar.getTimeInMillis();
                String note = bpNotesEditText.getText().toString();

                if (editExisting){
                    long id = intent.getLongExtra("id", System.currentTimeMillis());
                    bpViewModel.insert(new BPRecord(id, date, systolic, diastolic, arm, note));
                }
                else {
                    bpViewModel.insert(new BPRecord(date, systolic, diastolic, arm, note));
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

        bpDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditBloodPressureActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        // Updates the Time when the TimePicker selects a time
        bpTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                int minute = myCalendar.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EditBloodPressureActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

        bpDateEditText.setText(sdfDate.format(myCalendar.getTime()));
        bpTimeEditText.setText(sdfTime.format(myCalendar.getTime()));
    }
}
