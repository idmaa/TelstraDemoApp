package com.telstra.demoapp.activties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.telstra.demoapp.R;
import com.telstra.demoapp.fragments.adapters.ListViewAdapter;
import com.telstra.demoapp.utils.JSONParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class MainActivity extends Activity {
	// Declare Variables
	JSONObject jsonobject;
	JSONArray jsonarray;
	ListView listview;
	ListViewAdapter adapter;
	ProgressDialog mProgressDialog;
	List<HashMap<String, String>> arraylist;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initializeViews();
		// Execute DownloadJSON AsyncTask
		new DownloadJSON().execute();
	}

	private void initializeViews(){
				// Get the view from listview_main.xml
				setContentView(R.layout.listview_main);
				
		}

	// DownloadJSON AsyncTask
	private class DownloadJSON extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressdialog
			mProgressDialog = new ProgressDialog(MainActivity.this);
			// Set progressdialog title
			mProgressDialog.setTitle("In Progress");
			// Set progressdialog message
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			// Show progressdialog
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// Create the array 
			arraylist = new ArrayList<HashMap<String, String>>();
			JSONParser jParser = new JSONParser();
			// get JSON data from URL
			JSONArray json = jParser.getJSONFromUrl("http://www.dropbox.com/s/g41ldl6t0afw9dv/facts.json?dl=1");
			for (int i = 0; i < json.length(); i++) {
				try {
					JSONObject c = json.getJSONObject(i);
					
					String vtitle = c.getString(getString(R.string.VTITLE));
					String vdescription = c.getString(getString(R.string.VDESCRIPTION));
					String vimage = c.getString(getString(R.string.VIMAGE));
					HashMap<String, String> map = new HashMap<String, String>();
					// Add child node to HashMap key & value
					map.put(getString(R.string.VTITLE), vtitle);
					map.put(getString(R.string.VDESCRIPTION), vdescription);
					map.put(getString(R.string.VIMAGE), vimage);
		// Set the JSON Objects into the array
					arraylist.add(map);
				}
			 catch (JSONException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			}
			return null;
		}

		
		@Override
		protected void onPostExecute(Void args) {
			getActionBar().setTitle(JSONParser.jSonTitle);
			
			// Locate the listview in listview_main.xml
			listview = (ListView) findViewById(R.id.listview);
			// Pass the results into ListViewAdapter.java
			adapter = new ListViewAdapter(MainActivity.this, arraylist);
			// Binds the Adapter to the ListView
			listview.setAdapter(adapter);
			// Close the progressdialog
			mProgressDialog.dismiss();
			refreshAdapter();
		}
		
		public void refreshAdapter() {
			adapter.notifyDataSetInvalidated();
		}
	}
}