package osborne.william.glucotrak;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "A1CTable")
public class A1CRecord {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Long id;

    // Date and Time in Milliseconds
    @ColumnInfo(name = "date")
    public Long date;

    @ColumnInfo(name = "a1cReading")
    public double a1cReading;

    @ColumnInfo(name = "notes")
    String notes;

    // Standard cunstructor for new object
    public A1CRecord(Long date, double a1cReading, String notes) {
        this.date = date;
        this.a1cReading = a1cReading;
        this.notes = notes;
    }

    // Constructor for over-writing an entry
    @Ignore
    public A1CRecord(Long id, Long date, double a1cReading, String notes) {
        this.id = id;
        this.date = date;
        this.a1cReading = a1cReading;
        this.notes = notes;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public double getA1cReading() {
        return a1cReading;
    }

    public void setA1cReading(double a1cReading) {
        this.a1cReading = a1cReading;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "A1CRecord{" +
                "id=" + id +
                ", date=" + date +
                ", a1cReading=" + a1cReading +
                ", notes='" + notes + '\'' +
                '}';
    }
}
