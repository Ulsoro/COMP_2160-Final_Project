package osborne.william.glucotrak;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class BPAdapter extends RecyclerView.Adapter<BPAdapter.BPViewHolder> {



    class BPViewHolder extends RecyclerView.ViewHolder {

        private TextView bprowDate;
        private TextView bprowNote;
        private TextView bprowSystolic;
        private TextView bprowDiastolic;

        private BPViewHolder(View itemView) {
            super(itemView);
            bprowDate = itemView.findViewById(R.id.bprowDate);
            bprowNote = itemView.findViewById(R.id.bpRowNotes);
            bprowSystolic = itemView.findViewById(R.id.bprowSystolic);
            bprowDiastolic = itemView.findViewById(R.id.bprowDiastolic);
        }

    }

    private final LayoutInflater mInflater;
    private List<BPRecord> mBPList; // Cached copy of Blood Glucose Readings

    BPAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public BPViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.blood_pressure_row, parent, false);
        return new BPViewHolder(itemView);
    }







    @Override
    public void onBindViewHolder(final BPViewHolder holder, int position) {

        final BPRecord current = mBPList.get(position);

        if (mBPList != null) {

            holder.bprowDate.setText(DateFormat.format("dd-MMM-yyyy h:mm a", current.getDate()).toString());
            holder.bprowSystolic.setText(String.valueOf(current.getSystolic()));
            holder.bprowDiastolic.setText(String.valueOf(current.getDiastolic()));
            holder.bprowNote.setText(current.getNotes());
        } else {
            // Covers the case of data not being ready yet.
            holder.bprowDate.setText("No Date");
            holder.bprowSystolic.setText("No Systolic");
            holder.bprowDiastolic.setText("No Diastolic");
            holder.bprowNote.setText("No Notes");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, EditBloodGlucoseActivity.class);
                intent.putExtra("existing", true);
                intent.putExtra("id", current.getId());
                intent.putExtra("date", current.getDate());
                intent.putExtra("notes", current.getNotes());
                intent.putExtra("systolic", current.getSystolic());
                intent.putExtra("diastolic", current.getDiastolic());

                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                MainActivity.removeRecord(current);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Delete Record?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();


                return false;
            }
        });
    }

    void setBP(List<BPRecord> bgList){
        mBPList = bgList;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mBPList != null)
            return mBPList.size();
        else return 0;
    }

}


