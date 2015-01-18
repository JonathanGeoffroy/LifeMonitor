package lifemonitor.application.controller.medicalRecord.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lifemonitor.application.R;
import lifemonitor.application.model.medicalRecord.MedicalRecordItem;

/**
 * Specific Adapter for <code>ShowMedicalRecordActivity</code><br/>
 * For each <code>MedicalRecordItem</code>, display its <code>title</code>, its <code>subtitle</code>, and color the item with its <code>color</code>
 *
 * @author Jonathan Geoffroy
 */
public class MedicalRecordAdapter extends BaseAdapter {
    /**
     * Layout inflater used to map each item with <code>R.layout.medical_record_item</code>
     */
    private final LayoutInflater layoutInflater;

    /**
     * All items to display into the ListView
     */
    private final List<MedicalRecordItem> medicalRecords;

    public MedicalRecordAdapter(Context context) {
        super();
        layoutInflater = LayoutInflater.from(context);
        medicalRecords = new ArrayList<MedicalRecordItem>();
    }

    @Override
    public int getCount() {
        return medicalRecords.size();
    }

    @Override
    public Object getItem(int position) {
        return medicalRecords.get(position);
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
            convertView = layoutInflater.inflate(R.layout.medical_record_item, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView)convertView.findViewById(R.id.medicalRecordItemTitle);
            viewHolder.subtitle = (TextView)convertView.findViewById(R.id.medicalRecordItemSubtitle);
            convertView.setTag(viewHolder);
        }
        else {
            // Retrieve old convertView
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Update displayed data & background color
        MedicalRecordItem medicalRecord = medicalRecords.get(position);
        viewHolder.title.setText(medicalRecord.getTitle());
        viewHolder.subtitle.setText(medicalRecord.getSubTitle());
        convertView.setBackgroundColor(medicalRecord.getColor());

        return convertView;
    }

    /**
     * Remove all items from the list
     */
    public void clear() {
        medicalRecords.clear();
        notifyDataSetChanged();
    }

    /**
     * add all <code>items</code> from the list<br/>
     * delegated method for <code>List.addAll</code>
     * @param items items to add from the List
     */
    public void addAll(List<MedicalRecordItem> items) {
        medicalRecords.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * ViewHolder used to retrieve a view created previously
     */
    private class ViewHolder {
        /**
         * Title information
         */
        private TextView title;

        /**
         * Subtitle information
         */
        private TextView subtitle;

        public ViewHolder() {}
    }
}
