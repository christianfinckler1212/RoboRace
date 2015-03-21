package de.otto.roboapp.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import de.otto.roboapp.R;
import de.otto.roboapp.RoboAppController;
import de.otto.roboapp.model.DataModel;
import de.otto.roboapp.model.RacingData;
import de.otto.roboapp.model.SteeringDirection;
import de.otto.roboapp.ui.activities.base.AbstractUpdatableActivity;

import static de.otto.roboapp.util.ThreadStarter.processInNewThread;


public class SteeringActivity extends AbstractUpdatableActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steering);

        final RoboAppController roboAppController = (RoboAppController)getApplicationContext();

        SeekBar speedSlider = (SeekBar) findViewById(R.id.ID_Speed_Slider);


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
        t_imageViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processInNewThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("clicked left");
                        roboAppController.steer(SteeringDirection.LEFT);
                    }
                });
            }
        });

        final ImageView t_imageViewRight = (ImageView) findViewById(R.id.ID_Rigth);
        t_imageViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processInNewThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("clicked right");
                        roboAppController.steer(SteeringDirection.RIGHT);
                    }
                });
            }
        });


        displayModel(roboAppController.getDataModel());
    }

    public void displayModel(DataModel dataModel) {
        final TextView t_textViewSpeed = (TextView) findViewById(R.id.ID_Speed);
        RacingData racingData = dataModel.getRacingData();
        int speed = (racingData != null) ? racingData.getCurrentSpeed() : -999;
        t_textViewSpeed.setText(String.valueOf(speed));
    }


    @Override
    public void updateActivity() {
        displayModel(((RoboAppController)getApplicationContext()).getDataModel());
    }
}
