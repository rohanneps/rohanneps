package com.cpi.rnd.cpi_rnd_swipe_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.listeners.ItemRemovedListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    private String filterCriteria = "all";
    private int row_id=0;
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Pair Listing");
//        this.deleteDatabase("rnd_cpi_test");
//        this.deleteDatabase("rnd_cpi_test.db");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            filterCriteria = extras.getString("criteria");
            try {
                row_id = Integer.parseInt(extras.getString("rowID"));
            }
            catch(NumberFormatException e){
            }
        }
        try {
            Log.d("EVENT", filterCriteria);
        }
        catch(NullPointerException e){
            filterCriteria="all";
        }
        Log.d("DB",String.format("table row count %d",db.getTableRowCount()));
        mSwipeView = (SwipePlaceHolderView) findViewById(R.id.swipeView);
        mContext = getApplicationContext();

        mSwipeView.getBuilder()
                .setDisplayViewCount(4)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(17)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.itempair_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.itempair_swipe_out_msg_view));

        mSwipeView.addItemRemoveListener(new ItemRemovedListener() {
            @Override
            public void onItemRemoved(int count) {
                if (count ==0){
                    Log.d("EVENT", "End of current stack");
                    if(filterCriteria=="all"){
                        Toast.makeText(getApplicationContext(), "Task completed",Toast.LENGTH_SHORT);
                    }
                    else {

                        Intent i = new Intent(getApplicationContext(), ItemPairListingActivity.class);
                        i.putExtra("criteria", filterCriteria);
                        startActivity(i);
                        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                    }
                }
            }
        });

        List<ItemPairProfile> itemPairList = new ArrayList<ItemPairProfile>();

        if (filterCriteria == "all") {
            Log.d("EVENT", "Called in start with all");
            itemPairList = Utils.loadItemPairs(this.getApplicationContext());
        }
        else{
            Log.d("EVENT", "Called in filter section");
            itemPairList = Utils.loadItemPairsById(this.getApplicationContext(),row_id);
        }

        addRowDetailsToTable(itemPairList);


        for (final ItemPairProfile itempair : itemPairList) {
            mSwipeView.addView(new ItemPairCard(mContext, itempair, mSwipeView));
            }

        findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("EVENT", "cross pressed");
                mSwipeView.doSwipe(false);
            }
        });

        findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("EVENT", "right pressed");
                mSwipeView.doSwipe(true);
            }
        });
    }

    public void addRowDetailsToTable(List<ItemPairProfile> itemPairList) {
        DatabaseHandler db = new DatabaseHandler(this);

        for (ItemPairProfile itempair : itemPairList) {
            int row_id = itempair.getRow_id();
            if(!(db.rowIsPresent(row_id))){
                db.addRowResult(row_id, "");
//                Log.d("DB",String.format("inserted row %d",row_id));
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_view_menu, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                // match listing for current pair
                Intent i = new Intent(getApplicationContext(), ItemPairListingActivity.class);
                i.putExtra("criteria", "match");
                startActivity(i);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                return true;

            case R.id.item2:
                // Non match listing for current pair
                i = new Intent(getApplicationContext(), ItemPairListingActivity.class);
                i.putExtra("criteria", "non_match");
                startActivity(i);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {

        if (filterCriteria == "all"){

        }
        else{
            Intent i = new Intent(getApplicationContext(), ItemPairListingActivity.class);
            i.putExtra("criteria", "match");
            startActivity(i);
            overridePendingTransition(R.anim.enter, R.anim.exit);
        }
    }


    @Override
    public void onStart(){
        Log.d("Event","Main Activity onStart");
        super.onStart();
    }

    @Override
    public void onResume(){
        Log.d("Event","Main Activity onResume");
        super.onResume();
    }
}
