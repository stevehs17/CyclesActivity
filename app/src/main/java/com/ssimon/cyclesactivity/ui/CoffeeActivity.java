package com.ssimon.cyclesactivity.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ssimon.cyclesactivity.R;
import com.ssimon.cyclesactivity.data.CoffeeCache;
import com.ssimon.cyclesactivity.data.DatabaseHelper;
import com.ssimon.cyclesactivity.message.CoffeeRefreshEvent;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.util.Utils;

import java.util.List;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CoffeeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    static final String EXTRA_COFFEEID = "EXTRA_COFFEEID";
    private List<Coffee> coffees = null;
    private CoffeesAdapter adapter = null;
    private Button deleteButton;
    private ListView coffeeList;
    private long selectedCoffeeId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coffee_activity);
        coffeeList = (ListView) findViewById(R.id.list_coffees);
        coffeeList.setOnItemClickListener(this);
        deleteButton = (Button) findViewById(R.id.btn_delete);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.registerEventBus(this);
        Utils.postEvent(new CoffeeRefreshEvent());
    }

    @Override
    protected void onPause() {
        Utils.unregisterEventBus(this);
        super.onPause();
    }

    @Override
    public void onItemClick(AdapterView<?> unused1, View item, int unused2, long unused3) {
        selectedCoffeeId = (Long) item.getTag(R.id.coffee_id);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setCoffeeList(CoffeeRefreshEvent e) {
        coffees = CoffeeCache.getCoffees();
        if (coffees == null) {
            DatabaseHelper dh = DatabaseHelper.getInstance(this);
            dh.refreshCoffeeCache();
        } else if (adapter == null) {
            adapter = new CoffeesAdapter(this, coffees);
            coffeeList.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.addAll(coffees);
            adapter.notifyDataSetChanged();
        }
        if (coffees != null && coffees.size() > 1)
            Utils.enableButton(deleteButton);
        else
            Utils.disableButton(deleteButton);
        if (adapter != null)
            coffeeList.setItemChecked(0, true);
    }



    private class CoffeesAdapter extends ArrayAdapter<Coffee> {
        CoffeesAdapter(Context ctx, List<Coffee> coffees) {
            super(ctx, 0, coffees);
        }

        @NonNull
        @Override
        public View getView(int position, View v, ViewGroup unused) {
            ViewHolder h;

            if (v == null) {
                v = getLayoutInflater().inflate(android.R.layout.simple_list_item_activated_1, null);
                h = new ViewHolder(v);
                v.setTag(R.id.holder, h);
            } else {
                h = (ViewHolder) v.getTag(R.id.holder);
            }
            Coffee c = getItem(position);
            h.name.setText(c.name());
            v.setTag(R.id.coffee_id, c.id());
            return v;
        }

        private class ViewHolder {
            final TextView name;
            ViewHolder(View v) {
                name = v.findViewById(android.R.id.text1);
            }
        }
    }

    public void onClickAddCoffee(View unused) {
        Intent i = new Intent(this, AddcoffeeActivity.class);
        startActivity(i);
    }

    public void onClickDeleteCoffee(View unused) {
        //DatabaseHelper.getInstance(this).deleteCoffee(selectedCoffeeId);
        View v = coffeeList.getSelectedView();
        long id = (Long) v.getTag(R.id.coffee_id);
        DatabaseHelper.getInstance(this).deleteCoffee(id);
    }

    public void onClickEditCoffee(View unused) {
        Intent i = new Intent(this, VolumeActivity.class);
        //View v = coffeeList.getSelectedView();
        //long id = (Long) v.getTag(R.id.coffee_id);

        int n = coffeeList.getCheckedItemPosition();
        Coffee c = (Coffee) coffeeList.getItemAtPosition(n);
        //i.putExtra(EXTRA_COFFEEID, id);
        //i.putExtra(EXTRA_COFFEEID, selectedCoffeeId);
        i.putExtra(EXTRA_COFFEEID, c.id());
        startActivity(i);
    }
}
