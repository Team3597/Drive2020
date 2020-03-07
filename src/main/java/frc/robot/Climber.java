package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Climber {

    private static CANSparkMax topClimber;
    private static CANSparkMax bottomClimber;

    private static CANEncoder encoder;

    private static float climberPosition = 0;

    private static final float CLIMB_SPEED = 1f;

    public Climber() {
        topClimber = new CANSparkMax(RobotMap.CLIMBER_T_MOTOR_PORT, MotorType.kBrushless);
        bottomClimber = new CANSparkMax(RobotMap.CLIMBER_B_MOTOR_PORT, MotorType.kBrushless);

        encoder = new CANEncoder(topClimber);
        encoder.setPosition(0f);
    }

    public static void climbUp() {
        climberPosition = (float) encoder.getPosition();
        if(climberPosition <= 400f) {
            topClimber.set(CLIMB_SPEED);
            bottomClimber.set(CLIMB_SPEED);
        } else {
            topClimber.set(0f);
            bottomClimber.set(0f);
        }
        System.out.println(climberPosition);
    }

    public static void climbDown() {
        climberPosition = (float) encoder.getPosition();
        if(climberPosition >= 0f) {
            topClimber.set(-CLIMB_SPEED);
            bottomClimber.set(-CLIMB_SPEED);
        } else {
            topClimber.set(0f);
            bottomClimber.set(0f);
        }
        System.out.println(climberPosition);
    }

    public static void stop() {
        topClimber.set(0f);
        bottomClimber.set(0f);
    }

}