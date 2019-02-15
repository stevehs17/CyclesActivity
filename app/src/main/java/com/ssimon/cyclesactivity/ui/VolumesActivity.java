/*
 Copyright (c) 2019 Steven H. Simon
 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License. You may obtain	a copy
 of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
 by applicable law or agreed to in writing, software distributed under the
 License is distributed on an "AS IS" BASIS,	WITHOUT	WARRANTIES OR CONDITIONS
 OF ANY KIND, either express or implied. See the License for the specific
 language governing permissions and limitations under the License.
*/

package com.ssimon.cyclesactivity.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ssimon.cyclesactivity.Const;
import com.ssimon.cyclesactivity.R;

import java.util.ArrayList;
import java.util.List;

import com.ssimon.cyclesactivity.data.CoffeesCache;
import com.ssimon.cyclesactivity.data.DatabaseHelper;
import com.ssimon.cyclesactivity.message.CoffeesRefreshEvent;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;
import com.ssimon.cyclesactivity.util.Utils;
import com.ssimon.cyclesactivity.util.Checker;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class VolumesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    static final String EXTRA_VOLUME_IDX = "EXTRA_VOLUME_IDX";
    static final String EXTRA_COFFEE = "EXTRA_COFFEE";

    static final String EXTRA_VOLUMEID = "EXTRA_VOLUMEID";

    private List<Volume> volumes = null;
    private VolumesAdapter adapter = null;

    private long selectedVolumeId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volumes_activity);
        long coffeeId = getIntent().getLongExtra(CoffeesActivity.EXTRA_COFFEEID, Const.UNSET_DATABASE_ID);
        Checker.atLeast(coffeeId, Const.MIN_DATABASE_ID);
        ListView lv = (ListView) findViewById(R.id.list_volumes);
        lv.setOnItemClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Utils.registerEventBus(this);
        Utils.postEvent(new CoffeesRefreshEvent());
    }

    @Override
    protected void onStop() {
        Utils.unregisterEventBus(this);
        super.onStop();
    }

    @Override
    public void onItemClick(AdapterView<?> unused1, View item, int unused2, long unused3) {
        selectedVolumeId = (Long) item.getTag(R.id.volume_id);
        Log.v(CoffeesActivity.TAG, "selectedVolumeId = " + selectedVolumeId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setCoffeeList(CoffeesRefreshEvent e) {
        List<Coffee> coffees = CoffeesCache.getCoffees();
        if (coffees == null) {
            DatabaseHelper dh = DatabaseHelper.getInstance(this);
            dh.refreshCoffeesCache();
        } else if (adapter == null) {
            long coffeeId = getIntent().getLongExtra(CoffeesActivity.EXTRA_COFFEEID, Const.UNSET_DATABASE_ID);
            Checker.atLeast(coffeeId, Const.MIN_DATABASE_ID);
            volumes = Utils.getVolumesByCoffeeId(coffeeId, coffees);
            adapter = new VolumesAdapter(this, volumes);
            ListView lv = (ListView) findViewById(R.id.list_volumes);
            lv.setAdapter(adapter);
            lv.setItemChecked(0, true);

            lv.performItemClick(lv.getAdapter().getView(0, null, null), 0,
                    lv.getAdapter().getItemId(0));
        } else {
            adapter.notifyDataSetChanged();
            //todo: will probably need to click 0th item, as above
        }
    }

    /*
    List<Volume> getVolumesByCoffeeId(long coffeeId, List<Coffee> coffees) {
        for (Coffee c : coffees) {
            if (c.id() == coffeeId)
                return c.volumes();
        }
        throw new IllegalStateException("No coffee found with id = " + coffeeId);
    }
    */

    private class VolumesAdapter extends ArrayAdapter<Volume> {
        public VolumesAdapter(Context ctx, List<Volume> coffees) {
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
            Volume vol = getItem(position);
            h.name.setText(vol.name());
            v.setTag(R.id.volume_id, vol.id());
            return v;
        }

        private class ViewHolder {
            final TextView name;
            ViewHolder(View v) {
                name = v.findViewById(android.R.id.text1);
            }
        }
    }


    /*
    public void onClickEditVolume(View unused) {
        Cycle c = new Cycle(Cycle.MIN_VOLUME + 500, Cycle.MIN_BREWTIME + 30, Cycle.MIN_VACUUMTIME + 40);
        ArrayList<Cycle> cycles = new ArrayList<>();
        cycles.add(c);
        cycles.add(c);
        cycles.add(c);
        cycles.add(c);
        cycles.add(c);
        c = new Cycle(Cycle.MIN_VOLUME, Cycle.MIN_BREWTIME, Cycle.MIN_VACUUMTIME);
        cycles.add(c);
        Volume v = new Volume(1, cycles);
        List<Volume> volumes = new ArrayList<>();
        volumes.add(v);
        volumes.add(v);
        Coffee cof = new Coffee(5, "coffee", volumes, 1);
        Intent i = new Intent(this, CyclesActivity.class);
        i.putExtra(EXTRA_COFFEE, cof);
        i.putExtra(EXTRA_VOLUME_IDX, 1);
        startActivity(i);
    }
    */

    public void onClickEditVolume(View unused) {
        Intent i = new Intent(this, CyclesActivity.class);
        long coffeeId = getIntent().getLongExtra(CoffeesActivity.EXTRA_COFFEEID, Const.UNSET_DATABASE_ID);
        i.putExtra(CoffeesActivity.EXTRA_COFFEEID, coffeeId);
        i.putExtra(EXTRA_VOLUMEID, selectedVolumeId);


        Cycle c = new Cycle(Cycle.MIN_VOLUME + 500, Cycle.MIN_BREWTIME + 30, Cycle.MIN_VACUUMTIME + 40);
        ArrayList<Cycle> cycles = new ArrayList<>();
        cycles.add(c);
        cycles.add(c);
        cycles.add(c);
        cycles.add(c);
        cycles.add(c);
        c = new Cycle(Cycle.MIN_VOLUME, Cycle.MIN_BREWTIME, Cycle.MIN_VACUUMTIME);
        cycles.add(c);
        Volume v = new Volume(1, cycles);
        List<Volume> volumes = new ArrayList<>();
        volumes.add(v);
        volumes.add(v);
        Coffee cof = new Coffee(5, "coffee", volumes, 1);
        i.putExtra(EXTRA_COFFEE, cof);
        i.putExtra(EXTRA_VOLUME_IDX, 1);

        startActivity(i);
    }
}
