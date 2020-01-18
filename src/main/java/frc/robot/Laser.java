package frc.robot;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;

public class Laser {

    private static final int CALIBRATION_OFFSET = -5;

    private Counter counter;

    public Laser () {
        DigitalSource source = new DigitalInput(RobotMap.LASER_DIO_PORT);
        counter = new Counter(source);
        counter.setMaxPeriod(1.0);
        counter.setSemiPeriodMode(true);
        counter.reset();
    }

    public double getDistance() {
        double cm;
        
        cm = (counter.getPeriod() * 1000000f / 10f) + CALIBRATION_OFFSET;
        return cm;
    }

    public double getDistOnAngle(float angle) {
        return getDistance() * Math.cos(angle);
    }
}