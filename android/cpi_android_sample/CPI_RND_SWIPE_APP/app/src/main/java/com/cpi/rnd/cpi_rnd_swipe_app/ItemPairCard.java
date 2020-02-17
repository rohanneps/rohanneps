package com.cpi.rnd.cpi_rnd_swipe_app;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

@Layout(R.layout.itempair_card_view)
public class ItemPairCard {

    @View(R.id.searchImageView)
    private ImageView searchImageView;

    @View(R.id.resultImageView)
    private ImageView resultImageView;

    @View(R.id.sPrice)
    private TextView sPrice;

    @View(R.id.sTitle)
    private TextView sTitle;

    @View(R.id.rPrice)
    private TextView rPrice;

    @View(R.id.rTitle)
    private TextView rTitle;

    @View(R.id.showDetails)
    private TextView showDetails;

    private ItemPairProfile mProfile;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;
    private DatabaseHandler db;

    public ItemPairCard(Context context, ItemPairProfile profile, SwipePlaceHolderView swipeView) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;
        db = new DatabaseHandler(context);
    }

    @Resolve
    private void onResolved(){
        Glide.with(mContext).load(mProfile.getS_image_url()).into(searchImageView);
        Glide.with(mContext).load(mProfile.getR_image_url()).into(resultImageView);
//        sPrice.setText(mProfile.getS_price());
        sTitle.setText(mProfile.getS_price());

//        rPrice.setText(mProfile.getR_price());
        rTitle.setText(mProfile.getR_price());

        // Pop Up Window showing details
        showDetails.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View v){
                Log.d("Event", "Pop Up window called");
                Intent n = new Intent(mContext, PopUpDetailClass.class);
                n.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                n.putExtra("sPrice", mProfile.getS_price());
                n.putExtra("rPrice", mProfile.getR_price());

                n.putExtra("sTitle", mProfile.getS_title());
                n.putExtra("rTitle", mProfile.getR_title());
                n.putExtra("sDesc", mProfile.getS_title());
                n.putExtra("rDesc", mProfile.getR_title());

                n.putExtra("sUrl", mProfile.getS_prod_url());
                n.putExtra("rUrl", mProfile.getR_prod_url());
                mContext.startActivity(n);
            }

        });

        searchImageView.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View v){
                Log.d("Event", "Pop Up window called");
                Intent n = new Intent(mContext, PopUpImageClass.class);
                n.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                n.putExtra("imageUrl", mProfile.getS_image_url());
                mContext.startActivity(n);
            }

        });

        resultImageView.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View v){
                Log.d("Event", "Pop Up window called");
                Intent n = new Intent(mContext, PopUpImageClass.class);
                n.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                n.putExtra("imageUrl", mProfile.getR_image_url());
                mContext.startActivity(n);
            }

        });
    }


    // Item Pair is Not matched
    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
        db.updateRowResult(mProfile.getRow_id(),"non_match");
        Log.d("EVENT", String.format("Updating %d with non_match", mProfile.getRow_id()));
//        mSwipeView.addView(this);
    }

    @SwipeCancelState
    private void onSwipeCancelState(){

//        Log.d("EVENT", "onSwipeCancelState");

    }


    // Item Pair is matched
    @SwipeIn
    private void onSwipeIn(){
        Log.d("EVENT", "onSwipedIn");
        db.updateRowResult(mProfile.getRow_id(),"match");
        Log.d("EVENT", String.format("Updating %d with match", mProfile.getRow_id()));
    }

    @SwipeInState
    private void onSwipeInState(){
//        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
//        Log.d("EVENT", "onSwipeOutState");
    }


}
