package de.otto.roborace.controller;

import de.otto.roborace.model.DataModel;
import lejos.hardware.motor.Motor;

/**
 * Created by luca on 06.03.15.
 */
public class MotorController {
    DataModel dataModel;
    public MotorController(DataModel dataModel) {
        this.dataModel = dataModel;
        System.out.println("started motorController.");

        new Thread(new Runnable() {
            @Override
            public void run() {
                controlMotors();
            }
        }).start();
    }
    private void controlMotors() {
    	Motor.D.stop();//heat up motor code
        while (true) {
        	if(dataModel.getTargetSpeed() > 0 ) {
        		Motor.D.forward();
        		Motor.D.setSpeed(dataModel.getTargetSpeed());
        	} else {
        		Motor.D.backward();
        		Motor.D.setSpeed(-dataModel.getTargetSpeed());
        	}

            // detect steering change
            if(dataModel.getSteeringChange() != 0) {
                System.out.println("steering change detected: " + dataModel.getSteeringChange());
                Motor.C.rotate(-dataModel.getSteeringChange());
                System.out.println("Motor.C.getPosition(): " + Motor.C.getPosition());
                dataModel.resetSteeringChange();
            }
        }
    }
}
