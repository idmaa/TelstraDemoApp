package com.telstra.demoapp.fragments.adapters;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.telstra.demoapp.R;
import com.telstra.demoapp.fragments.ImageLoader;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	List<HashMap<String, String>> data;
	ImageLoader imageLoader;

	public ListViewAdapter(Context context,
				List<HashMap<String, String>> arraylist) {
		this.context = context;
		data = arraylist;
		imageLoader = new ImageLoader(context);

	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// Declare Variables
		TextView title;
		TextView description;
		ImageView image;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.listview_item, parent, false);
		// Get the position from the results
		Map<String, String> resultp = new HashMap<String, String>();
		resultp = data.get(position);

		// Locate the TextViews in listview_item.xml
		title = (TextView) itemView.findViewById(R.id.title); 
		description = (TextView) itemView.findViewById(R.id.descripton);
		// Locate the ImageView in listview_item.xml
		image = (ImageView) itemView.findViewById(R.id.image);

		// Capture position and set results to the TextViews
		title.setText(resultp.get("title"));
		description.setText(resultp.get("description"));
		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class to download and cache
		// images
		imageLoader.displayImage(resultp.get("imageHref"), image);
		return itemView;
	}
}
