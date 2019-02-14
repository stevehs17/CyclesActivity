package com.ssimon.cyclesactivity.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ssimon.cyclesactivity.R;
import com.ssimon.cyclesactivity.data.CoffeesCache;
import com.ssimon.cyclesactivity.data.DatabaseHelper;
import com.ssimon.cyclesactivity.message.CoffeesRefreshEvent;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.util.AndroidUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class CoffeesActivity extends AppCompatActivity {
    List<Coffee> coffees = null;
    CoffeesAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coffees_activity);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AndroidUtils.registerEventBus(this);
        AndroidUtils.postStickyEvent(new CoffeesRefreshEvent());
    }

    @Override
    protected void onStop() {
        AndroidUtils.unregisterEventBus(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void setCoffeeList(CoffeesRefreshEvent e) {
        AndroidUtils.removeStickyEvent(e);
        coffees = CoffeesCache.getCoffees();
        if (coffees == null) {
            DatabaseHelper dh = DatabaseHelper.getInstance(this);
            dh.refreshCoffeesCache();
        } else if (adapter == null) {
            adapter = new CoffeesAdapter(this, coffees);
            ListView lv = (ListView) findViewById(R.id.list_coffees);
            lv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private class CoffeesAdapter extends ArrayAdapter<Coffee> {
        public CoffeesAdapter(Context ctx, List<Coffee> coffees) {
            super(ctx, 0, coffees);
        }

        @NonNull
        @Override
        public View getView(int position, View v, ViewGroup unused) {
            ViewHolder h;

            if (v == null) {
                v = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
                h = new ViewHolder(v);
                v.setTag(R.id.holder, h);
            } else {
                h = (ViewHolder) v.getTag(R.id.holder);
            }
            Coffee c = getItem(position);
            h.name.setText(c.name());
            v.setTag(R.id.coffee, c);
            return v;
        }

        private class ViewHolder {
            final TextView name;
            ViewHolder(View v) {
                name = v.findViewById(android.R.id.text1);
            }
        }
    }

    public void onClickEditCoffee(View unused) {
        Intent i = new Intent(this, VolumesActivity.class);
        startActivity(i);
    }
}
