package com.example.loginregisterfirebase;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Pomodoro_Timer extends AppCompatActivity {
    private EditText mEditTextInput;
    private TextView mTextViewCountDown;
    private TextView mTextViewStatus;
    private SoundPool soundPool;
    private int whistle;
    private Button mButtonSet;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private int mCountSession;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;
    private boolean mConcentrationRunning;

    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;
    private static final String CHANNEL_ID = "timeOver Channel";
    private static final int NOTIFICATION_ID = 100;
    long breakMillisInput;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro_timer);

        Intent i = getIntent() ;
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String Timer_Value = sharedPreferences.getString("Timer","");
        //String Timer_Value = i.getStringExtra("Timer");
//        String fullname = intent.getStringExtra("fullname");

        mEditTextInput = findViewById(R.id.edit_text_input);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mTextViewStatus = findViewById(R.id.status);
        Toast.makeText(Pomodoro_Timer.this, "Timer Value : "+Timer_Value, Toast.LENGTH_SHORT).show();


        mButtonSet = findViewById(R.id.button_set);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);
        mCountSession = 0;
        mConcentrationRunning = false;

        //Alert sound of timer STARTS
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }else {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }

        whistle = soundPool.load(this, R.raw.whistle, 1);
        //Alert sound of timer ENDS
        //String inp = "10";
        String input = Timer_Value;
//        long millisInput = Long.parseLong(input) * 60000;
//        setTime(millisInput);
//        mEditTextInput.setText(input);
//        startTimer();
//        onStop();

        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String input = inp;

                mEditTextInput.setText(input);
                //I've changed this
//                String input = mEditTextInput.getText().toString();

                if (input.length() == 0) {
                    Toast.makeText(Pomodoro_Timer.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0) {
                    Toast.makeText(Pomodoro_Timer.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTime(millisInput);

                if( millisInput < 15){
                    breakMillisInput = 120000;
                    Toast.makeText(Pomodoro_Timer.this, "2 mins break time", Toast.LENGTH_SHORT).show();
                }
                else if(millisInput >= 15 && millisInput <=25){
                    breakMillisInput = 300000;
                    Toast.makeText(Pomodoro_Timer.this, "5 mins break time", Toast.LENGTH_SHORT).show();
                }
                else if(millisInput >= 26 && millisInput <=35){
                    breakMillisInput = 600000;
                    Toast.makeText(Pomodoro_Timer.this, "10 mins break time", Toast.LENGTH_SHORT).show();
                }
                else if(millisInput >= 36 && millisInput <=45){
                    breakMillisInput = 900000;
                    Toast.makeText(Pomodoro_Timer.this, "15 mins break time", Toast.LENGTH_SHORT).show();
                }
                else{
                    breakMillisInput = 600000;
                    Toast.makeText(Pomodoro_Timer.this, "10 mins break time", Toast.LENGTH_SHORT).show();
                }
//                mEditTextInput.setText(input);
                //mEditTextInput.setText("");
            }
        });



        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }
    private  void mNotification () {
        //icon png to bitmap START
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.timer_icon, null);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        assert bitmapDrawable != null;
        Bitmap app_large_icon = bitmapDrawable.getBitmap();
        //icon png to bitmap END

        //Notification of CountDown Timer STARTS
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification breakOver_notification;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            breakOver_notification = new Notification.Builder(this)
                    .setLargeIcon(app_large_icon)
                    .setSmallIcon(R.drawable.timer_icon)
                    .setContentText("BREAK TIME OVER")
                    .setSubText("Countdown Alert")
                    .setChannelId(CHANNEL_ID)
                    .build();
            nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID, "Countdown over channel", NotificationManager.IMPORTANCE_HIGH));

        }else {
            breakOver_notification = new Notification.Builder(this)
                    .setLargeIcon(app_large_icon)
                    .setSmallIcon(R.drawable.timer_icon)
                    .setContentText("BREAK TIME OVER")
                    .setSubText("Countdown Alert")
                    .build();


        }
        nm.notify(NOTIFICATION_ID, breakOver_notification);
    }
    //Notification of CountDown Timer ENDS

    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;//here time setting is done
        resetTimer();
        closeKeyboard();
    }

    //Concentration Timer STARTS
    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountSession = 0;

        mCountDownTimer = new CountDownTimer( mTimeLeftInMillis, 1000) {
            @SuppressLint("RestrictedApi")
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                mConcentrationRunning = true;
                Log.d(TAG, "isConcentrating: " + isConcentrationRunning());
            }
            @SuppressLint("RestrictedApi")
            @Override
            public void onFinish() {
                mTimerRunning = false;
                mConcentrationRunning = false;
                mBreakTimer();
                updateWatchInterface();
            }
        }.start();

        mTimerRunning = true;
        updateWatchInterface();
    }
    //Concentration Timer ENDS
    //Pause Timer

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateWatchInterface();
    }

    //Break Timer STARTS
    private void mBreakTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountSession = 1;
        mCountDownTimer = new CountDownTimer(breakMillisInput, 1000) {  //8 sec and 1 sec gap
            @SuppressLint("RestrictedApi")
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                mConcentrationRunning = false;
                Log.d(TAG, "isConcentrating: " + isConcentrationRunning());
                updateCountDownText();

            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mConcentrationRunning = false;
                mNotification();
                updateWatchInterface();
            }
        }.start();

        mTimerRunning = true;
        updateWatchInterface();
    }
    //Break Timer ENDS

    //Return value for app lock management here
    private boolean isConcentrationRunning () {
        return mConcentrationRunning;
    }

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600; //minis ko sec me change -> divide by 1000 then whole div by 3600 coz 1 hr = 3600 sec
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        mTextViewCountDown.setText(timeLeftFormatted);
        if (minutes == 0 && seconds == 1 ) {
            soundPool.play(whistle, 1,1,0,0,1);
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateWatchInterface() {
        if (mTimerRunning) {
            mTextViewStatus.setVisibility(View.VISIBLE);
            mEditTextInput.setVisibility(View.INVISIBLE);
            mButtonSet.setVisibility(View.INVISIBLE);
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText("Pause");
            mButtonStartPause.setVisibility(View.VISIBLE);
            if (mCountSession == 0) {
                mTextViewStatus.setText("Concentration Time");
            }else {
                mTextViewStatus.setText("Break Time");
            }
        } else {
            mEditTextInput.setVisibility(View.VISIBLE);
            mButtonSet.setVisibility(View.VISIBLE);
            mTextViewStatus.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText("Start");

            if (mTimeLeftInMillis < 1000) {
                mButtonStartPause.setVisibility(View.VISIBLE);
            } else {
                mButtonStartPause.setVisibility(View.VISIBLE);
            }

            if (mTimeLeftInMillis < mStartTimeInMillis) {
                mButtonReset.setVisibility(View.INVISIBLE);
            } else {
                mButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateWatchInterface();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();


            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateWatchInterface();
            }
            else {
                startTimer();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }
}