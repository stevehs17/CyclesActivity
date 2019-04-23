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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ssimon.cyclesactivity.Const;
import com.ssimon.cyclesactivity.R;
import com.ssimon.cyclesactivity.data.CoffeeCache;
import com.ssimon.cyclesactivity.data.DatabaseHelper;
import com.ssimon.cyclesactivity.message.CoffeeRefreshEvent;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;
import com.ssimon.cyclesactivity.util.Utils;
import com.ssimon.cyclesactivity.util.Checker;

import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

public class VolumeActivity extends AppCompatActivity {
    static final String EXTRA_VOLUMEID = "EXTRA_VOLUMEID";
    private ListView volumeList;
    private Button deleteButton;
    private VolumesAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volume_activity);
        volumeList = (ListView) findViewById(R.id.list_volumes);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setVolumeList(CoffeeRefreshEvent e) {
        List<Coffee> coffees = CoffeeCache.getCoffees();
        if (coffees == null) {
            DatabaseHelper.getInstance(this). refreshCoffeeCache();
            return;
        }
        Coffee coffee = Utils.getCoffeeById(getCoffeeId(), coffees);
        List<Volume> volumes = new ArrayList<>(coffee.volumes());
        if (adapter == null) {
            TextView tv = (TextView) findViewById(R.id.txt_coffee);
            tv.setText(coffee.name());
            adapter = new VolumesAdapter(this, volumes);
            volumeList.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.addAll(volumes);
            adapter.notifyDataSetChanged();
        }
        if (coffee.volumes().size() == 1)
            Utils.disableButton(deleteButton);
        else
            Utils.enableButton(deleteButton);
        volumeList.setItemChecked(0, true);
    }

    private long getCoffeeId() {
        long id = getIntent().getLongExtra(CoffeeActivity.EXTRA_COFFEEID,
                Const.UNSET_DATABASE_ID);
        if (id == Const.UNSET_DATABASE_ID)
            throw new IllegalStateException("coffee ID not set");
        return id;
    }

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
                v.setTag(h);
            } else {
                h = (ViewHolder) v.getTag();
            }
            Volume vol = getItem(position);
            h.name.setText(vol.name());
            return v;
        }

        private class ViewHolder {
            final TextView name;
            ViewHolder(View v) {
                name = v.findViewById(android.R.id.text1);
            }
        }

    }

   public void onClickEditVolume(View unused) {
       Intent i = new Intent(this, CycleActivity.class);
       i.putExtra(CoffeeActivity.EXTRA_COFFEEID, getCoffeeId());
       int n = volumeList.getCheckedItemPosition();
       Volume v = (Volume) volumeList.getItemAtPosition(n);
       i.putExtra(EXTRA_VOLUMEID, v.id());
       startActivity(i);
    }

    /*
    public void onClickAddVolume(View unused) {
        Intent i = new Intent(this, CycleActivity.class);
        i.putExtra(CoffeeActivity.EXTRA_COFFEEID, getCoffeeId());
        i.putExtra(EXTRA_VOLUMEID, Const.UNSET_DATABASE_ID);
        startActivity(i);
    }

    public void onClickDeleteVolume(View unused) {
        int n = volumeList.getCheckedItemPosition();
        Volume v = (Volume) volumeList.getItemAtPosition(n);
        DatabaseHelper.getInstance(this).deleteVolume(v.id());
    }
    */
}
