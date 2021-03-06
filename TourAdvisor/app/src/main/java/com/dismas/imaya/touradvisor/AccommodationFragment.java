package com.dismas.imaya.touradvisor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dismas.imaya.touradvisor.adapters.RecyclerViewAdapterAccommodation;
import com.dismas.imaya.touradvisor.configs.MapAllConfig;
import com.dismas.imaya.touradvisor.constructors.ParksAllConstructor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imaya on 12/2/16.
 */
public class AccommodationFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private LinearLayoutManager lLayout;

    SwipeRefreshLayout mSwipeRefreshLayout;

    ArrayList<ParksAllConstructor> accommodations = new ArrayList<>();


    private ProgressDialog loading;
    RecyclerViewAdapterAccommodation rcAdapter;
    FragmentManager mFragmentManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        onRestoreInstanceState(savedInstanceState);
        View x =  inflater.inflate(R.layout.fragment_accommodation, container, false);
        ((MainActivityDrawer) getActivity()).topToolBar.setTitle("Accommodations");

        lLayout = new LinearLayoutManager(getActivity());
//        lLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        final RecyclerView rView = (RecyclerView) x.findViewById(R.id.recycler_view);
        rView.setItemAnimator(new DefaultItemAnimator());
        rView.setLayoutManager(lLayout);
        final List<ParksAllConstructor> rowListItem = getAllSanctuaries();
        rcAdapter = new RecyclerViewAdapterAccommodation(getActivity(), rowListItem);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                rView.setAdapter(rcAdapter);
            }
        }, 3000);

        Toast.makeText(getActivity(), "Swipe down to refresh", Toast.LENGTH_LONG).show();
        mSwipeRefreshLayout = (SwipeRefreshLayout) x.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        RecyclerViewAdapterAccommodation.OnItemClickListener onItemClickListener = new RecyclerViewAdapterAccommodation.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//                Toast.makeText(getActivity(), "Clicked " + position, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                //Create the bundle
                getFragmentManager().beginTransaction().addToBackStack(null);
                Bundle bundle = new Bundle();
                bundle.putString("position", String.valueOf(position));
                bundle.putString("longitude", rowListItem.get(position).getLongitude());
                bundle.putString("latitude", rowListItem.get(position).getLatitude());
                bundle.putString("restaurant_name", rowListItem.get(position).getSite_name());
                bundle.putString("cost_per_day", rowListItem.get(position).getCost_per_day());
                bundle.putString("phone", rowListItem.get(position).getPhone());
                bundle.putString("email", rowListItem.get(position).getEmail());
                bundle.putString("location_name", rowListItem.get(position).getLocationName());
                bundle.putString("hotel_image", rowListItem.get(position).getSite_image());
                bundle.putString("interior_image", rowListItem.get(position).getInterior_image());
                bundle.putString("guest_image", rowListItem.get(position).getAttraction_image());
                intent.putExtras(bundle);
                startActivity(intent);


                //Put the value
//                DetailsFragment detailsFragment = new DetailsFragment();
//                Bundle args = new Bundle();
//                args.putString("position", String.valueOf(position));
//                detailsFragment.setArguments(args);
//
//                //Inflate the fragment
//                getFragmentManager().beginTransaction().addToBackStack(null).add(R.id.containerView, detailsFragment).commit();

            }
        };

        rcAdapter.setOnItemClickListener(onItemClickListener);
        // Inflate the layout for this fragment
        return x;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); //Will ignore onDestroy Method (Nested Fragments no need this if parent have it)
    }
    //Here you can restore saved data in onSaveInstanceState Bundle
    private void onRestoreInstanceState(Bundle savedInstanceState){
        if(savedInstanceState!=null){
            String SomeText = savedInstanceState.getString("title");
        }
    }

    //Here you Save your data
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title", "Some Text");
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            // Set title
            getActivity().getActionBar()
                    .setTitle("Accommodations");
        }
    }

    @Override
    public void onRefresh() {

    }
    public List<ParksAllConstructor> getAllSanctuaries(){
        loading = ProgressDialog.show(getActivity(), "Please wait...", "Fetching Accommodations...", false, false);

        String url = MapAllConfig.DATA_URL;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
                loading.dismiss();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

        return accommodations;
    }

    private void showJSON(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("accommodations");


            for (int i = 0; i < result.length(); i++) {

                JSONObject accommodationData = result.getJSONObject(i);
                ParksAllConstructor accommodation = new ParksAllConstructor();
                //park.setAttraction_site_id(accommodationData.getInt("attraction_site_id"));
                accommodation.setSite_name(accommodationData.getString("restaurant_name"));
                accommodation.setLocation_name(accommodationData.getString("location_name"));
                accommodation.setSite_image(accommodationData.getString("hotel_image"));
                accommodation.setCost_per_day(accommodationData.getString("cost_per_day"));
                accommodation.setPhone(accommodationData.getString("phone"));
                accommodation.setEmail(accommodationData.getString("email"));
                accommodation.setLongitude(accommodationData.getString("longitude"));
                accommodation.setLatitude(accommodationData.getString("latitude"));
                accommodation.setInterior_image(accommodationData.getString("interior_image"));
                accommodation.setAttraction_image(accommodationData.getString("guest_image"));
                accommodations.add(accommodation);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}