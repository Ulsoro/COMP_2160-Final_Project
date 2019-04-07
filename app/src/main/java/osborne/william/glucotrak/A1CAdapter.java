package osborne.william.glucotrak;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class A1CAdapter extends RecyclerView.Adapter<A1CAdapter.A1CViewHolder> {


    class A1CViewHolder extends RecyclerView.ViewHolder {

        private TextView a1cRowDate;
        private TextView a1cRowNotes;
        private TextView a1cRowValue;

        private A1CViewHolder(View itemView) {
            super(itemView);

            a1cRowDate = itemView.findViewById(R.id.a1cRowDate);
            a1cRowNotes = itemView.findViewById(R.id.a1cRowNotes);
            a1cRowValue = itemView.findViewById(R.id.a1cRowValue);
        }
    }

    private final LayoutInflater mInflater;
    private List<A1CRecord> mList; // Cached copy of A1C Readings

    A1CAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public A1CViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.a1c_row, parent, false);
        return new A1CViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final A1CViewHolder holder, int position) {

        final A1CRecord current = mList.get(position);

        if (mList != null) {

            holder.a1cRowDate.setText(DateFormat.format("dd-MMM-yyyy h:mm a", current.getDate()).toString());
            holder.a1cRowNotes.setText(current.getNotes());
            holder.a1cRowValue.setText(String.valueOf(current.getA1cReading()));

        } else {
            // Covers the case of data not being ready yet.
            holder.a1cRowDate.setText("No Date");
            holder.a1cRowValue.setText("No A1C");
            holder.a1cRowNotes.setText("No Notes");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, EditA1CActivity.class);
                intent.putExtra("existing", true);
                intent.putExtra("id", current.getId());
                intent.putExtra("date", current.getDate());
                intent.putExtra("a1c", current.getA1cReading());
                intent.putExtra("notes", current.getNotes());

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

    void setA1C(List<A1CRecord> a1cList){
        mList = a1cList;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mList != null)
            return mList.size();
        else return 0;
    }

}
