package com.londonappbrewery.destini;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // TODO: Steps 4 & 8 - Declare member variables here:
    Button mButtonTop, mButtonBottom, mStartButton;
    TextView mStoryTextView;
    ArrayList<Chapter> mTheStory;
    int mCurrentChapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Step 5 - Wire up the 3 views from the layout to the member variables:
        mButtonTop      = (Button) findViewById(R.id.buttonTop);
        mButtonBottom   = (Button) findViewById(R.id.buttonBottom);
        mStoryTextView  = (TextView) findViewById(R.id.storyTextView);
        mStartButton    = (Button) findViewById(R.id.button_start);
        mTheStory = new ArrayList<Chapter>();
        createChapters();

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartButton.setVisibility(View.GONE);
                mStoryTextView.setVisibility(View.VISIBLE);
                mButtonTop.setVisibility(View.VISIBLE);
                mButtonBottom.setVisibility(View.VISIBLE);
                mStartButton.setClickable(false);

                mCurrentChapter = 0;
            }
        });

        // TODO: Steps 6, 7, & 9 - Set a listener on the top button:
        mButtonTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentChapter==-1) {
                    resetStory(0);
                    return;
                }
                Chapter tChapter = mTheStory.get(mCurrentChapter);
                int tChoiceId = 0;
                int tNextChapter = tChapter.getNextChapter(tChoiceId);
                if(-1!=tNextChapter){
                    mCurrentChapter = tNextChapter;
                    updateChapter();
                }else{
                    mButtonBottom.setVisibility(View.GONE);
                    mButtonTop.setVisibility(View.GONE);
                }
            }
        });

        // TODO: Steps 6, 7, & 9 - Set a listener on the bottom button:
        mButtonBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chapter tChapter = mTheStory.get(mCurrentChapter);
                int tChoiceId = 1;
                int tNextChapter = tChapter.getNextChapter(tChoiceId);
                if(-1!=tNextChapter){
                    mCurrentChapter = tNextChapter;
                    updateChapter();
                }else{
                    mButtonBottom.setVisibility(View.GONE);
                    mButtonTop.setVisibility(View.GONE);
                }
            }
        });
    }

    private void updateChapter(){
        Chapter tChapter;
        try{
            tChapter = mTheStory.get(mCurrentChapter);
            Log.d("Destini","mCurrentChapter::"+mCurrentChapter+"Story::"+getString(tChapter.getStory()));
        }catch(Exception e){
            return;
        }

        mStoryTextView.setText(tChapter.getStory());

        if(tChapter.getChoice(0)!=-1)
            mButtonTop.setText(tChapter.getChoice(0));
        else {
            mCurrentChapter = -1;
            mButtonTop.setText("Restart!");
        }

        if(tChapter.getChoice(1)!=-1)
            mButtonBottom.setText(tChapter.getChoice(1));
        else
            mButtonBottom.setVisibility(View.GONE);
    }

    private void resetStory(int pCurrentChapter){
        Log.d("Destini","reset story");
        mCurrentChapter = pCurrentChapter;
        mButtonBottom.setVisibility(View.VISIBLE);
        mButtonTop.setVisibility(View.VISIBLE);
        updateChapter();
    }

    private void createChapters(){
        Chapter Ch1 = new Chapter(0);
        Chapter Ch2 = new Chapter(1);
        Chapter Ch3 = new Chapter(2);
        Chapter Ch4 = new Chapter(3);
        Chapter Ch5 = new Chapter(4);
        Chapter Ch6 = new Chapter(5);


        Ch1.setStory(R.string.T1_Story);
        Ch1.setChoices(R.string.T1_Ans1,0);
        Ch1.setChoices(R.string.T1_Ans2,1);
        Ch1.setNextChapter(2,0);
        Ch1.setNextChapter(1,1);

        Ch2.setStory(R.string.T2_Story);
        Ch2.setChoices(R.string.T2_Ans1,0);
        Ch2.setChoices(R.string.T2_Ans2,1);
        Ch2.setNextChapter(2,0);
        Ch2.setNextChapter(3,1);


        Ch3.setStory(R.string.T3_Story);
        Ch3.setChoices(R.string.T3_Ans1,0);
        Ch3.setChoices(R.string.T3_Ans2,1);
        Ch3.setNextChapter(5,0);
        Ch3.setNextChapter(4,1);

        Ch4.setStory(R.string.T4_End);

        Ch5.setStory(R.string.T5_End);

        Ch6.setStory(R.string.T6_End);

        mTheStory.add(Ch1);
        mTheStory.add(Ch2);
        mTheStory.add(Ch3);
        mTheStory.add(Ch4);
        mTheStory.add(Ch5);
        mTheStory.add(Ch6);
    }
}
