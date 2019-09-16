package com.example.torch.RecyclerViews.AdapterBag;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.torch.R;

public class ftechtoatl extends AsyncTask<Integer, Void, Integer> {
    private Context context;
    private View v;

    public ftechtoatl(View v, Context context) {
        this.v = v;
        this.context=context;
    }

    @Override
    protected Integer doInBackground(Integer... integers) {
        Integer integer = integers[0];
        return integer;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        TextView btnTotalPrice = v.findViewById(R.id.btnTotalPrice);
        btnTotalPrice.setText(String.valueOf(integer));

    }
}

