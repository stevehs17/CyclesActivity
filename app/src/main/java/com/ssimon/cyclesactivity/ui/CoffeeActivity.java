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

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class CoffeeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    static final String EXTRA_COFFEEID = "EXTRA_COFFEEID";
    private List<Coffee> coffees = null;
    private CoffeesAdapter adapter = null;
    private long selectedCoffeeId = -1;

    static final String TAG = "CoffeeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coffee_activity);
        ListView lv = (ListView) findViewById(R.id.list_coffees);
        lv.setOnItemClickListener(this);
    }

    /*
    @Override
    protected void onStart() {
        super.onStart();
        Utils.registerEventBus(this);
        Utils.postEvent(new CoffeeRefreshEvent());
    }
    */

    @Override
    protected void onResume() {
        super.onResume();
        Utils.registerEventBus(this);
        Utils.postEvent(new CoffeeRefreshEvent());
    }
    /*
    @Override
    protected void onStop() {
        Utils.unregisterEventBus(this);
        super.onStop();
    }
    */

    @Override
    protected void onPause() {
        Utils.unregisterEventBus(this);
        super.onPause();
    }



    @Override
    public void onItemClick(AdapterView<?> unused1, View item, int unused2, long unused3) {
        selectedCoffeeId = (Long) item.getTag(R.id.coffee_id);
        Log.v(TAG, "selectedCoffeeId = " + selectedCoffeeId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setCoffeeList(CoffeeRefreshEvent e) {
        coffees = CoffeeCache.getCoffees();
        if (coffees == null) {
            DatabaseHelper dh = DatabaseHelper.getInstance(this);
            dh.refreshCoffeeCache();
        } else if (adapter == null) {
            adapter = new CoffeesAdapter(this, coffees);
            ListView lv = (ListView) findViewById(R.id.list_coffees);
            lv.setAdapter(adapter);
            lv.setItemChecked(0, true);

            lv.performItemClick(lv.getAdapter().getView(0, null, null), 0,
                    lv.getAdapter().getItemId(0));
            Button btn = (Button) findViewById(R.id.btn_delete);
            if (coffees.size() == 1) {
                btn.setEnabled(false);
            } else {
                btn.setEnabled(true);
            }
        } else {
            adapter.clear();
            adapter.addAll(coffees);
            adapter.notifyDataSetChanged();
            //todo: will probably need to click 0th item, as above
            ListView lv = (ListView) findViewById(R.id.list_coffees);
            lv.performItemClick(lv.getAdapter().getView(0, null, null), 0,
                    lv.getAdapter().getItemId(0));
            Button btn = (Button) findViewById(R.id.btn_delete);
            if (coffees.size() == 1) {
                btn.setEnabled(false);
            } else {
                btn.setEnabled(true);
            }
        }
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
                //v = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
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

    public void onClickNewCoffee(View unused) {
        /*
        CreateCoffeeDialog d = CreateCoffeeDialog.newInstance();
        d.show(getSupportFragmentManager(), "");
        */
        Intent i = new Intent(this, AddcoffeeActivity.class);
        i.putExtra(EXTRA_COFFEEID, selectedCoffeeId);
        startActivity(i);
    }

    public void onClickDeleteCoffee(View unused) {
        DatabaseHelper.getInstance(this).deleteCoffee(selectedCoffeeId);
    }

    public void onClickEditCoffee(View unused) {
        Intent i = new Intent(this, VolumeActivity.class);
        i.putExtra(EXTRA_COFFEEID, selectedCoffeeId);
        startActivity(i);
    }
}
