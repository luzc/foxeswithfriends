package com.facebook.android.foxeswithfriends;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

public class FoxListFragment extends ListFragment {

    public FoxListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//        CustomAdapter customAdapter = new CustomAdapter();
//        
//        setListAdapter(customAdapter);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        
        CustomAdapter customAdapter = new CustomAdapter();
        
        setListAdapter(customAdapter);
    }

}
