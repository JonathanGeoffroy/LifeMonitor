package lifemonitor.application.controller.medicalRecord.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.LinkedList;
import java.util.List;

import lifemonitor.application.helper.rest.RESTHelper;
import lifemonitor.application.helper.rest.listeners.MultipleResultsRESTListener;
import lifemonitor.application.model.medicalRecord.Medicine;

/**
 * Created by jonathan on 02/01/15.
 */
public class MedicineOptionsAdapter extends ArrayAdapter<String> {
    private RESTHelper<Medicine> restHelper;
    private List<Medicine> medicines;

    public MedicineOptionsAdapter(Context context, int resource) {
        super(context, resource);
        restHelper = new RESTHelper<Medicine>(context);
    }

    public void onDataChanged(String value) {
        String request = "/medicines/search/" + value;
        restHelper.sendGETRequestForMultipleResults(request, Medicine.class,
                new MultipleResultsRESTListener<Medicine>() {
            @Override
            public void onGetResponse(List<Medicine> results) {
                medicines = results;
                List<String> options = new LinkedList<String>();
                for(Medicine medicine: results) {
                    options.add(medicine.getName());
                }

                clear();
                addAll(options);
            }

            @Override
            public void onError() {
                Log.e("MedicineOptionsAdapter", "error!");
            }
        });
    }

    public Medicine getMedicine(int index) {
        return medicines.get(index);
    }
}
