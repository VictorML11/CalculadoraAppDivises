package vmaestro.pis2017.ub.edu;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by victor on 15/02/2018.
 */

public class CustomAdapter extends BaseAdapter{

    private List<Currency> currencies;
    private Context context;

    public CustomAdapter(Context context, List<Currency> currencies) {
        super();
        this.context = context;
        this.currencies = currencies;
    }

    public int getCount() {
        return this.currencies.size();
    }

    public Object getItem(int i) {
        return this.currencies.get(i);
    }

    public long getItemId(int i) {
        return this.currencies.indexOf(this.getItem(i));
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Currency cur_obj = currencies.get(position);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.spinner_divisas, parent, false);

        TextView label = (TextView) row.findViewById(R.id.textDiv);
        label.setText(cur_obj.getType().getName());

        ImageView icon = (ImageView) row.findViewById(R.id.imgDiv);
        icon.setImageResource(cur_obj.getImgId());

        return row;
    }
}

