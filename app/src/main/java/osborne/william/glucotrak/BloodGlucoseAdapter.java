package osborne.william.glucotrak;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BloodGlucoseAdapter extends RecyclerView.Adapter<BloodGlucoseAdapter.ViewHolder> {


    private List<BloodGlucoseRecord> mBGList;


    public BloodGlucoseAdapter(List<BloodGlucoseRecord> bgRecord) {
        this.mBGList = bgRecord;
    }

    @NonNull
    @Override
    public BloodGlucoseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.blood_glucose_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BloodGlucoseAdapter.ViewHolder viewHolder, int position) {

        DateFormat simple = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a");

        // Creating date from milliseconds
        // using Date() constructor
        Date result = new Date(mBGList.get(position).getDateTime());

        viewHolder.dateTextView.setText(simple.format(result).toString());
        viewHolder.relativeTimeTextView.setText(mBGList.get(position).getRelativeTime());
        viewHolder.bloodGlucoseReadingTextView.setText(String.valueOf(mBGList.get(position).getBloodSugarConcentration()));
    }

    @Override
    public int getItemCount() {
        return mBGList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView dateTextView;
        private TextView relativeTimeTextView;
        private TextView bloodGlucoseReadingTextView;

        private ViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.bgDateTextView);
            relativeTimeTextView = itemView.findViewById(R.id.bgRelativeTimeTextView);
            bloodGlucoseReadingTextView = itemView.findViewById(R.id.bgBloodGlucoseTextView);
        }
    }
}
