package lifemonitor.application.controller.medicalRecord.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import lifemonitor.application.R;
import lifemonitor.application.model.medicalRecord.Illness;

/**
 * @author Romain Philippon
 */
public class DiseaseAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private LinkedList<Illness> illnesses;

    public DiseaseAdapter(Context context) {
        super();
        this.layoutInflater = LayoutInflater.from(context);
        this.illnesses = new LinkedList<>();
    }

    /**
     * Add an illness in the adapter and sort it
     * @param item
     */
    public void add(Illness item) {
        this.illnesses.add(item);
        this.sort();
    }

    /**
     * Add a collection of illness objects in the adapter and sort it
     * @param items
     */
    public void addAll(List<Illness> items) {
        this.illnesses.addAll(items);
        this.sort();
    }

    @Override
    public int getCount() {
        return this.illnesses.size();
    }

    @Override
    public Object getItem(int position) {
        return this.illnesses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.disease_item, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.diseaseNameItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Illness illness = illnesses.get(position);
        viewHolder.name.setText(illness.getName());

        return convertView;
    }

    public static class ViewHolder{
        public TextView name;
    }

    /**
     * Sort item by illness name
     */
    public void sort() {
        Collections.sort(this.illnesses);
    }
}
