package com.example.byechoi.dice;


import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Animation appearingAnim;
    private Animation disappearingAnim;
    private Animation shakingAnim;
    private ArrayList<ImageView> ivList;
    private ArrayList<AnimationDrawable> drawablesList;
    private int ivCnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ivList = new ArrayList<>();
        drawablesList = new ArrayList<>();
        ivCnt = 1;

        ivList.add( (ImageView) findViewById(R.id.iv_1) );
        ivList.add( (ImageView) findViewById(R.id.iv_2) );
        ivList.add( (ImageView) findViewById(R.id.iv_3) );

        ivList.get(0).setVisibility(View.VISIBLE);
        ivList.get(1).setVisibility(View.INVISIBLE);
        ivList.get(2).setVisibility(View.INVISIBLE);

        for(ImageView iv : ivList){
            drawablesList.add((AnimationDrawable) iv.getBackground());
        }

        appearingAnim = AnimationUtils.loadAnimation(this, R.anim.appearing);
        disappearingAnim = AnimationUtils.loadAnimation(this, R.anim.disappearing);
        shakingAnim = AnimationUtils.loadAnimation(this, R.anim.shaking);
        bindShuffleAnim();

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivCnt++;
                if(ivCnt > ivList.size()){
                    ivCnt = 1;
                }
                for(ImageView iv : ivList){
                    iv.setVisibility(View.INVISIBLE);
                }
                for(int i = 0; i < ivCnt; i++){
                    ivList.get(i).setVisibility(View.VISIBLE);
                }
            }
        });

        final LinearLayout touchArea = findViewById(R.id.touch_area);
        touchArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < ivCnt; i++){
                    ivList.get(i).startAnimation(shakingAnim);
                }
            }
        });
    }
    private void bindShuffleAnim () {
        shakingAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                for(AnimationDrawable drawable : drawablesList) {
                    drawable.start();
                }

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                for(AnimationDrawable drawable : drawablesList) {
                    Random r = new Random();
                    int randomIndex = r.nextInt(6);
                    drawable.stop();
                    drawable.selectDrawable(randomIndex);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
