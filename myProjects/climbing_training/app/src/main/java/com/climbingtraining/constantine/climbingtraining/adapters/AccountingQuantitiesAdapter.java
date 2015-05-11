package com.climbingtraining.constantine.climbingtraining.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.data.dto.AccountingQuantity;

import java.util.List;

/**
 * Created by KonstantinSysoev on 11.05.15.
 */
public class AccountingQuantitiesAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<AccountingQuantity> accountingQuantities;

    public AccountingQuantitiesAdapter(Context context, List<AccountingQuantity> accountingQuantities) {
        this.context = context;
        this.accountingQuantities = accountingQuantities;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return accountingQuantities.size();
    }

    @Override
    public Object getItem(int position) {
        return accountingQuantities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.accounting_quantity_list_layout, null);
        }

        AccountingQuantity accountingQuantity = (AccountingQuantity) getItem(position);

        TextView title = (TextView) convertView.findViewById(R.id.aq_list_title);
        title.setText(accountingQuantity.getExercise().getName());

        ImageView image = (ImageView) convertView.findViewById(R.id.aq_list_layout_image);
        if (!accountingQuantity.getExercise().getImagePath().isEmpty()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(accountingQuantity.getExercise().getImagePath());
            image.setImageBitmap(myBitmap);
        }

        TextView numberApproaches = (TextView) convertView.findViewById(R.id.aq_list_layout_number_approaches);
        numberApproaches.setText(Integer.toString(accountingQuantity.getNumberApproaches()));

        TextView numberTimeApproach = (TextView) convertView.findViewById(R.id.aq_list_layout_number_time_approach);
        numberTimeApproach.setText(Integer.toString(accountingQuantity.getNumberTimeApproach()));

        TextView additionalWeight = (TextView) convertView.findViewById(R.id.aq_list_layout_additional_weight);
        additionalWeight.setText(Float.toString(accountingQuantity.getAdditionalWeight()));

        TextView distance = (TextView) convertView.findViewById(R.id.aq_list_layout_distance);
        distance.setText(Float.toString(accountingQuantity.getDistance()));

        return convertView;
    }
}
