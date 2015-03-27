package com.example.kin.decorate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.example.kin.decorate.common.DecorateUtils;


/**
 * An activity representing a single Item detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link ItemDetailFragment}.
 */
public class ItemDetailActivity extends ActionBarActivity implements ProjectItemListFragment.OnFragmentInteractionListener {

    ItemDetailFragment fragment = null;
    ProjectItemListFragment itemListFragment=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_ID));

            fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);

            itemListFragment = new ProjectItemListFragment();
            itemListFragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .replace(R.id.project_item_list, itemListFragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String id) {

        String type = DecorateUtils.getUserInfo(getApplicationContext(), "type", "");
//        if("user".equals(type)) {

            Intent intent=new Intent();
            intent.putExtra("iid", id);
            intent.setClass(this, ImgGridActivity.class);
            this.startActivity(intent);

//        }else{
//            for (ProjectItem item : itemListFragment.projectItemList) {
//                if (item.id.toString().equals(id)) {
//                    if (item.status.id < 203) {
//                        item.status.id++;
//                        final String url = new StringBuilder("http://123.57.248.228:8080/monitor/change_item_status/?id=")
//                                .append(item.id).append("&status=").append(item.status.id).toString();
//                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                                url, new Response.Listener<JSONArray>() {
//                            @Override
//                            public void onResponse(JSONArray response) {
//
//                            }
//                        }, new Response.ErrorListener() {
//
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                            }
//                        });
//
//                        Singleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);
//                    }
//                }
//            }
//        }

    }
}
