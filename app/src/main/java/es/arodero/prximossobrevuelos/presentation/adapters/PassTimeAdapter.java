package es.arodero.prximossobrevuelos.presentation.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import es.arodero.prximossobrevuelos.R;
import es.arodero.prximossobrevuelos.model.entities.PassTimeEntity;

/**
 * Created by arodero on 25/12/2015.
 */
public class PassTimeAdapter extends ArrayAdapter<PassTimeEntity> {
    public PassTimeAdapter(Context context, ArrayList<PassTimeEntity> passTimes) {
        super(context, 0, passTimes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PassTimeEntity passTime = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_list_item_layout, parent, false);
        }

        DateFormat df = new DateFormat();
        String title = df.format(
                convertView.getResources().getString(R.string.item_title_pattern),
                passTime.getRisetime() * 1000).toString();

        Integer minutes = passTime.getDuration() / 60;
        Integer seconds = passTime.getDuration() % 60;
        String subtitle = String.format(
                convertView.getResources().getString(R.string.item_subtitle_pattern),
                minutes, seconds);

        TextView tvItemTitle = (TextView) convertView.findViewById(R.id.itemTitle);
        TextView tvItemSubTitle = (TextView) convertView.findViewById(R.id.itemSubtitle);
        tvItemTitle.setText(title);
        tvItemSubTitle.setText(subtitle);

        return convertView;
    }
}
