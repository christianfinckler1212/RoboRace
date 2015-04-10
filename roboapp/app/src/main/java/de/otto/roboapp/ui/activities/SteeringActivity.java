package de.otto.roboapp.ui.activities;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import de.otto.roboapp.R;
import de.otto.roboapp.RoboAppController;
import de.otto.roboapp.model.DataModel;
import de.otto.roboapp.model.RacingData;
import de.otto.roboapp.model.RacingState;
import de.otto.roboapp.model.SteeringDirection;
import de.otto.roboapp.ui.activities.base.AbstractUpdatableActivity;

import static de.otto.roboapp.util.ThreadStarter.processInNewThread;


public class SteeringActivity extends AbstractUpdatableActivity {

    TextView speedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steering);

        final RoboAppController roboAppController = (RoboAppController)getApplicationContext();

        SeekBar speedSlider = (SeekBar) findViewById(R.id.ID_Speed_Slider);
        speedText = (TextView) findViewById(R.id.ID_Speed);
        speedText.setText("0");


        speedSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
                processInNewThread(new Runnable() {
                    @Override
                    public void run() {
                        long targetSpeed = Math.round((progress - 20) * 1.25);
                        System.out.println("slided to " + targetSpeed);
                        roboAppController.steer(targetSpeed);
                    }
                });
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final ImageView t_imageViewLeft = (ImageView) findViewById(R.id.ID_Left);
        t_imageViewLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getX() >= 0 && event.getY() >= 0 && event.getX() < v.getWidth() && event.getY() < v.getHeight()) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        processInNewThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("pressed left");
                                roboAppController.steer(SteeringDirection.LEFT);
                            }
                        });
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        processInNewThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("released left");
                                roboAppController.steer(SteeringDirection.NONE);
                            }
                        });
                    }
                }
                return true;
            }
        });

        final ImageView t_imageViewRight = (ImageView) findViewById(R.id.ID_Rigth);
        t_imageViewRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getX() >= 0 && event.getY() >= 0 && event.getX() < v.getWidth() && event.getY() < v.getHeight()) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        processInNewThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("pressed right");
                                roboAppController.steer(SteeringDirection.RIGHT);
                            }
                        });
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        processInNewThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("released right");
                                roboAppController.steer(SteeringDirection.NONE);
                            }
                        });
                    }
                }

                return true;
            }
        });

        displayModel(roboAppController.getDataModel());
    }

    public void displayModel(DataModel dataModel) {

        try {
            speedText.setText(Integer.toString(dataModel.getRacingData().getCurrentSpeed()));
        } catch (Exception e){
            e.printStackTrace();
        }



        switch (dataModel.getRacingData().getRacingState()) {
            case COUNTDOWN_RUNNING: {
                final TextView t_textViewTimer = (TextView) findViewById(R.id.CountdownTimer);
                t_textViewTimer.setText(String.valueOf(dataModel.getRacingData().getCountdownRemainingTime()));
                break;
            }
            case STARTED: {
                final TextView t_textViewTimer = (TextView) findViewById(R.id.CountdownTimer);
                t_textViewTimer.setVisibility(View.INVISIBLE);
                final TextView t_textViewSpeed = (TextView) findViewById(R.id.ID_Speed);
                RacingData racingData = dataModel.getRacingData();
                int speed = (racingData != null) ? racingData.getCurrentSpeed() : -999;
                t_textViewSpeed.setText(String.valueOf(speed));
                break;
            }
        }
    }


    @Override
    public void updateActivity() {
        displayModel(((RoboAppController)getApplicationContext()).getDataModel());
    }
}
