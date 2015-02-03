package lifemonitor.application.controller.service.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.Collection;
import java.util.LinkedList;

import lifemonitor.application.model.medicalRecord.Doctor;

/**
 * @author Romain Philippon
 */
public class DoctorAdapter extends ArrayAdapter<String> {
    private LinkedList<Doctor> doctors;
    private LayoutInflater inflater;
    private View itemLayout;

    public DoctorAdapter(Context context, int resourceLayout) {
        super(context, resourceLayout);
        this.inflater = LayoutInflater.from(context);
        this.itemLayout = this.inflater.inflate(resourceLayout, null);
        this.doctors = new LinkedList<Doctor>();
    }

    public boolean addDoctor(Doctor item) {
        this.add(item.getName());
        return this.doctors.add(item);
    }

    public boolean addAllDoctors(Collection<Doctor> items) {
        for(Doctor doc : items) {
            this.add(doc.getName());
        }

        return this.doctors.addAll(items);
    }

    @Override
    public void clear() {
        super.clear();
        this.doctors.clear();
    }

    public Doctor getDoctor(int position) {
        return this.doctors.get(position);
    }
}
