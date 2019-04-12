package com.ssimon.cyclesactivity.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssimon.cyclesactivity.R;

public class CreateCoffeeDialog extends DialogFragment {
    static public CreateCoffeeDialog newInstance() {
        CreateCoffeeDialog d = new CreateCoffeeDialog();
        return d;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inf, @Nullable ViewGroup container, @Nullable Bundle savedState) {
        return inf.inflate(R.layout.coffee_dialog_create, container);
    }
}
