package com.facebook.android.foxeswithfriends;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CustomAdapter extends BaseAdapter {
	
	private List<ParseObject> foxList;
	private ParseFile image;
	
	@Override
	public int getCount() {
		
		ParseQuery<ParseObject> countQuery = ParseQuery.getQuery("Fox");
		try {
			int foxCount = countQuery.count();
			return foxCount;
		} catch (ParseException e) {
			// something went wrong!
        	Log.d("count", "Error: " + e.getMessage());
        	return 0;
		}

	}
	
	@Override
	public ParseObject getItem(int position) {
		return foxList.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
    public View getView(int index, View view, final ViewGroup parent) {
 
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.list_item_fox, parent, false);
        }
 
        final TextView textView = (TextView) view.findViewById(R.id.name);
    	final ImageView imageView = (ImageView) view.findViewById(R.id.list_image);
        
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Fox");
        final int idx = index;
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> foxList, ParseException e) {
                if (e == null) {
                    Log.d("objectId", "Retrieved " + foxList.size() + " objects");
                    
                    String name = foxList.get(idx).getString("name");
                    textView.setText(name);
                    
                    image = (ParseFile)foxList.get(idx).get("image");
                    image.getDataInBackground(new GetDataCallback() {
                      public void done(byte[] data, ParseException e) {
                        if (e == null) {
                          // success!
                        	Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                            imageView.setImageBitmap(bmp);
                            
                        } else {
                          // something went wrong
                        	Log.d("image", "Error: " + e.getMessage());
                        }
                      }
                    });
                    
                } else {
                    Log.d("objectId", "Error: " + e.getMessage());
                }
            }
        });

        return view;
    }

	public CustomAdapter() {
		// TODO Auto-generated constructor stub
	}

}
