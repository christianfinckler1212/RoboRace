package de.otto.roborace.model;

/**
 * Created by luca on 06.03.15.
 */
public class DataModel {
	private static final int STEERING_MAX = 40;

	private int targetSpeed;
	private Steering desiredSteeringDirection = Steering.NONE;

	private long boundaryReachedTime = 0;

	public Steering getDesiredSteeringDirection() {
		return desiredSteeringDirection;
	}

	public void setDesiredSteeringDirection(Steering desiredSteeringDirection) {
		this.desiredSteeringDirection = desiredSteeringDirection;
	}

	public int getTargetSpeed() {
		if(isBoundaryReached()) {
			System.out.println("Target Speed: " + targetSpeed + " Slow Target Speed: " + ((int)Math.ceil(targetSpeed * 0.2)));
			return (int)Math.ceil(targetSpeed * 0.2);
		} else {
			return targetSpeed;
		}
	}

	public void resetSteering() {
		desiredSteeringDirection = null;
	}

	public void setTargetSpeed(int targetSpeed) {
		this.targetSpeed = targetSpeed * 15;
		
	}

	public boolean isBoundaryReached() {
		return System.currentTimeMillis() - boundaryReachedTime < 2000;
	}

	public void courseBoundaryReached() {
		boundaryReachedTime = System.currentTimeMillis();
	}
}
