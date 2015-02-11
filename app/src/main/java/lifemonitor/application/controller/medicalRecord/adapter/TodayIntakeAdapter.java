package lifemonitor.application.controller.medicalRecord.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lifemonitor.application.R;
import lifemonitor.application.controller.medicalRecord.adapter.items.TodayIntakeItem;
import lifemonitor.application.helper.rest.RESTHelper;
import lifemonitor.application.helper.rest.listeners.PostListener;
import lifemonitor.application.model.medicalRecord.Intake;

/**
 * Display each intake to take today into a ListView
 *
 * @author Jonathan Geoffroy
 */
public class TodayIntakeAdapter extends BaseAdapter {
    public static final int RED = Color.argb(255, 242, 222, 222);
    public static final int GREEN = Color.argb(255, 223, 240, 216);
    /**
     * Layout inflater used to map each item with <code>R.layout.medical_record_item</code>
     */
    private final LayoutInflater layoutInflater;

    /**
     * All items to display into the ListView
     */
    private final List<TodayIntakeItem> todayIntakeItems;
    private Context context;

    public TodayIntakeAdapter(Context context) {
        super();
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        todayIntakeItems = new ArrayList<>();
    }

    public void add(TodayIntakeItem item) {
        todayIntakeItems.add(item);
    }

    @Override
    public int getCount() {
        return todayIntakeItems.size();
    }

    @Override
    public Object getItem(int position) {
        return todayIntakeItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Try to retrieve previously created ViewHolder
        ViewHolder viewHolder;
        convertView = layoutInflater.inflate(R.layout.today_intake_item, null);
        viewHolder = new ViewHolder();
        viewHolder.medicineName = (TextView) convertView.findViewById(R.id.medicine_name);
        viewHolder.date = (TextView) convertView.findViewById(R.id.medicine_date);
        viewHolder.taken = (Switch) convertView.findViewById(R.id.taken_toggleButton);
        convertView.setTag(viewHolder);

        // Update displayed data & background color
        TodayIntakeItem item = todayIntakeItems.get(position);
        viewHolder.medicineName.setText(item.getMedicineName(context));
        viewHolder.date.setText(item.getDate());
        viewHolder.taken.setEnabled(!item.isTaken());
        viewHolder.taken.setChecked(item.isTaken());
        viewHolder.taken.setOnCheckedChangeListener(new TakenIntakeCheckedChangeListener(item, convertView));

        // Change background color
        if (item.isTaken()) {
            convertView.setBackgroundColor(GREEN);
        } else if (item.isForgotten()) {
            convertView.setBackgroundColor(RED);
        } else {
            convertView.setBackgroundColor(Color.WHITE);
        }

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        Collections.sort(todayIntakeItems);
        super.notifyDataSetChanged();
    }

    /**
     * ViewHolder used to retrieve a view created previously
     */
    private class ViewHolder {
        private TextView medicineName;
        private TextView date;
        private Switch taken;

        public ViewHolder() {
        }
    }

    /**
     * Display the intake as taken and send a request to REST Service when user changes the switch
     *
     * @author Jonathan Geoffroy
     */
    private class TakenIntakeCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {

        /**
         * The convertView to change background color
         */
        private final View convertView;

        private TodayIntakeItem todayIntakeItem;

        private TakenIntakeCheckedChangeListener(TodayIntakeItem todayIntakeItem, View convertView) {
            this.todayIntakeItem = todayIntakeItem;
            this.convertView = convertView;
        }

        @Override
        public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {
            if (buttonView.isEnabled() && isChecked) {
                // Display the intake as taken
                buttonView.setEnabled(false);
                convertView.setBackgroundColor(GREEN);

                // Send request to REST Service to inform that intake is taken
                RESTHelper<Intake> restHelper = new RESTHelper<>(context);
                int treatmentId = todayIntakeItem.getTreatment().getId();

                restHelper.sendPOSTRequest(todayIntakeItem.getIntake(), String.format("/treatments/%d/intakes", treatmentId), Intake.class, new PostListener<Intake>() {
                    @Override
                    public void onSuccess(Intake addedIntake) {
                        todayIntakeItem.setIntake(addedIntake);
                    }

                    @Override
                    public void onError() {
                        buttonView.setChecked(false);
                        buttonView.setEnabled(true);
                        convertView.setBackgroundColor(Color.WHITE);
                        Toast.makeText(context, R.string.connexion_error, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
}
