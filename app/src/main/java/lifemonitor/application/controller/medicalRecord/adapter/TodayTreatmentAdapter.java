package lifemonitor.application.controller.medicalRecord.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lifemonitor.application.R;
import lifemonitor.application.controller.medicalRecord.adapter.items.TodayTreatmentItem;

/**
 * Display each dose to take today into a ListView
 */
public class TodayTreatmentAdapter extends BaseAdapter {
    /**
     * Layout inflater used to map each item with <code>R.layout.medical_record_item</code>
     */
    private final LayoutInflater layoutInflater;

    /**
     * All items to display into the ListView
     */
    private final List<TodayTreatmentItem> todayTreatmentItems;
    private Context context;

    public TodayTreatmentAdapter(Context context) {
        super();
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        todayTreatmentItems = new ArrayList<TodayTreatmentItem>();
    }

    public void addAll(List<TodayTreatmentItem> items) {
        todayTreatmentItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return todayTreatmentItems.size();
    }

    @Override
    public Object getItem(int position) {
        return todayTreatmentItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Try to retrieve previously created ViewHolder
        ViewHolder viewHolder;
        if(convertView == null) {
            // Add data into new convertView
            convertView = layoutInflater.inflate(R.layout.today_treatment_item, null);
            viewHolder = new ViewHolder();
            viewHolder.medicineName = (TextView)convertView.findViewById(R.id.medicine_name);
            viewHolder.date = (TextView)convertView.findViewById(R.id.medicine_date);
            viewHolder.taken = (Switch)convertView.findViewById(R.id.taken_toggleButton);
            convertView.setTag(viewHolder);
        }
        else {
            // Retrieve old convertView
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Update displayed data & background color
        TodayTreatmentItem item = todayTreatmentItems.get(position);
        viewHolder.medicineName.setText(item.getMedicineName(context));
        viewHolder.date.setText(item.getDate());
        viewHolder.taken.setChecked(item.isTaken());

        return convertView;
    }

    /**
     * ViewHolder used to retrieve a view created previously
     */
    private class ViewHolder {
        private TextView medicineName;
        private TextView date;
        private Switch taken;
        private Color backgroundColor;

        public ViewHolder() {}
    }
}
