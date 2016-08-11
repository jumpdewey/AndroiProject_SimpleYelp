package com.weidu.weiduyelp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeListFragment extends Fragment {
    private int mPosition;
    private List<Restaurant> data;
    private final static String KEY_POSITION = "key_position";
    private final static String INITIAL_POSITION = "pref_position";
    private final static String SELECTED_TYPE = "pref_selected_show";
    public static final int SHOW_PREFERENCES = 1;
    public final static String TAG = "Yelp";
    ImageTextArrayAdapter adapter;
    public HomeListFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        data = setupData();
        SharedPreferences mySharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (savedInstanceState != null){
            mPosition = savedInstanceState.getInt(KEY_POSITION, 0);
            Log.d(TAG,"onActivityCreated savedInstanceState mPosition " + mPosition);
        } else {
            mPosition = mySharedPreferences.getInt(INITIAL_POSITION, 0);
            Log.d(TAG,"onActivityCreated mySharedPreferences mPosition " + mPosition);
        }
        ListView listview = (ListView)getActivity().findViewById(R.id.listView);
        adapter = new ImageTextArrayAdapter(getActivity(),R.layout.format,data);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long arg3) {
                clickItem(view);
            }
        });

    }

    public void clickItem(View view){
        TextView name = (TextView)view.findViewById(R.id.name);
        String str = name.getText().toString();
        String tel = "tel:";
        switch(str){
            case "Salt's Cure":
                tel+="213-387-2300";
                break;
            case "Howlin' Ray's":
                tel+="266-117-2415";
                break;
            case "Moruno":
                tel+="424-256-7274";
                break;
            case "Kali":
                tel+="323-460-4170";
                break;
            case "Highland Park Bowl":
                tel+="323-297-0070";
                break;
            case "Catch":
                tel+="213-514-5724";
                break;
            case "Geoffrey’s":
                tel+="323-951-1210";
                break;
            case "Gladstone’s":
                tel+="323-651-5866";
        }
        Intent myIntent = new Intent(Intent.ACTION_DIAL);
        myIntent.setData(Uri.parse(tel));
        startActivity(myIntent);
    }

    private List<Restaurant> setupData(){
        List<Restaurant> dataRestaurant;
        String m1 = getResources().getString(R.string.$);
        String m2 = getResources().getString(R.string.$$);
        String m3 = getResources().getString(R.string.$$$);
        String m4 = getResources().getString(R.string.$$$$);
        Restaurant[] restaurants;
        SharedPreferences mySharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        int showPlanetTypeId = Integer.parseInt(mySharedPreferences.
                getString(SELECTED_TYPE, "0"));
        switch(showPlanetTypeId){
            case 1:
                restaurants = new Restaurant[]{
                        new Restaurant(R.drawable.n5,"Salt's Cure","1155 Highland Blvd Los Angeles","Top quality",m1),
                        new Restaurant(R.drawable.n8,"Gladstone’s","17300 Pacific Coast Hwy","seafood",m1)
                };
                break;
            case 2:
                restaurants = new Restaurant[]{
                        new Restaurant(R.drawable.n1,"Howlin' Ray's","727 N Broadway #128 Los Angeles","Chicken",m2),
                        new Restaurant(R.drawable.n3,"Kali","5722 Melrose Ave Los Angeles","Refined",m2)
                };
                break;
            case 3:
                restaurants = new Restaurant[]{
                        new Restaurant(R.drawable.n4,"Highland Park Bowl","5621 N Figueroa St Los Angeles","Cocktail",m3),
                        new Restaurant(R.drawable.n7,"Geoffrey’s","27400 Pacific Coast Hwy","crab",m3)
                };
                break;
            case 4:
                restaurants = new Restaurant[]{
                        new Restaurant(R.drawable.n2,"Moruno","6333 W 3rd St Los Angeles","Spanish",m4),
                        new Restaurant(R.drawable.n6,"Catch","Casa Del Mar, 1910 Ocean Way","view",m4)
                };
                break;
            default:
                restaurants = new Restaurant[]{
                        new Restaurant(R.drawable.n1,"Howlin' Ray's","727 N Broadway #128 Los Angeles","Chicken","$$"),
                        new Restaurant(R.drawable.n2,"Moruno","6333 W 3rd St Los Angeles","Spanish","$$$$"),
                        new Restaurant(R.drawable.n3,"Kali","5722 Melrose Ave Los Angeles","Refined","$$"),
                        new Restaurant(R.drawable.n4,"Highland Park Bowl","5621 N Figueroa St Los Angeles","Cocktail","$$$"),
                        new Restaurant(R.drawable.n5,"Salt's Cure","1155 Highland Blvd Los Angeles","Top quality","$"),
                        new Restaurant(R.drawable.n6,"Catch","Casa Del Mar, 1910 Ocean Way","view","$$$$"),
                        new Restaurant(R.drawable.n7,"Geoffrey’s","27400 Pacific Coast Hwy","crab","$$$"),
                        new Restaurant(R.drawable.n8,"Gladstone’s","17300 Pacific Coast Hwy","seafood","$")
                };
        }

        dataRestaurant = new ArrayList<>(Arrays.asList(restaurants));
        return dataRestaurant;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG,"PlanetListActivityFragment onOptionsItemSelected: " +
                Integer.toHexString(item.getItemId()));
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivityForResult(new Intent(getActivity(),
                        SettingsActivity.class),SHOW_PREFERENCES);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroy() {
        savePrefs(INITIAL_POSITION, mPosition);
        super.onDestroy();
    }
    private void savePrefs(String key, int value) {
        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt(key, value);
        ed.apply();
    }

    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_POSITION, mPosition);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        Log.d(TAG, "PlanetListActivityFragment onCreateOptionsMenu");
//        inflater.inflate(R.menu.menu_settings, menu);
//    }
}
