package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Spark;

public class Shooter {

    private static CANSparkMax rMotor;
    private static CANSparkMax lMotor;
    private static Spark rHopperMotor;
    private static Spark lHopperMotor;
    private static Spark intakeMotor;

    private static final float SHOOT_SPEED = 0.7f;
    private static final float HOPPER_SPEED = 0.7f;
    private static final float INTAKE_SPEED = 0.7f;

    private static long warmupTime = 1 * 1000; //One second warmup (in milliseconds)
    private static long shootTime = 10 * 1000; //Shoot for ten seconds
    private static boolean warmedUp = false;

    private static AnalogInput ultrasonic = new AnalogInput(RobotMap.ULTRASONIC_ANALOG_PORT);
    private static double ballIn = 1.0d; //Voltage when ball is in
    private static long loadTime = 2 * 1000; //Two seconds to load hopper

    public static float shootSpeed = 1f;

    /* @TODO:
     * Give infinate loops timeouts
     */

    public Shooter() {
        rMotor = new CANSparkMax(RobotMap.SHOOTER_R_MOTOR_PORT, MotorType.kBrushless);
        lMotor = new CANSparkMax(RobotMap.SHOOTER_L_MOTOR_PORT, MotorType.kBrushless);
        rHopperMotor = new Spark(RobotMap.SHOOTER_R_HOPPER_MOTOR_PORT);
        lHopperMotor = new Spark(RobotMap.SHOOTER_L_HOPPER_MOTOR_PORT);
        intakeMotor = new Spark(RobotMap.SHOOTER_INTAKE_MOTOR_PORT);

        rMotor.setInverted(RobotMap.SHOOTER_R_MOTOR_INVERTED);
        lMotor.setInverted(RobotMap.SHOOTER_L_MOTOR_INVERTED);
        rHopperMotor.setInverted(RobotMap.SHOOTER_R_HOPPER_MOTOR_INVERTED);
        lHopperMotor.setInverted(RobotMap.SHOOTER_L_HOPPER_MOTOR_INVERTED);
        intakeMotor.setInverted(RobotMap.SHOOTER_INTAKE_MOTOR_INVERTED);

        lMotor.follow(rMotor);
    }

    public static void shoot(float pAngle) {
        if (!warmedUp) { //If the shooter hasn't warmed up then warm up for warmupTime
            long start = System.currentTimeMillis();
            long now = 0;
            while (start - now < warmupTime) {
                setShooterMotors(SHOOT_SPEED);
                now = System.currentTimeMillis();
            }
            warmedUp = true;
        } else { //If shots haven't been fired fire shots for shootTime
            long start = System.currentTimeMillis();
            long now = 0;
            while (start - now < shootTime) {
                setHopperMotors(HOPPER_SPEED);
                setShooterMotors(SHOOT_SPEED);
                now = System.currentTimeMillis();
            }
            warmedUp = false;
        }
    }

    public static void intake () {
        if (ultrasonic.getValue() < ballIn) { //If a ball isn't in then intake one
        intakeMotor.set(INTAKE_SPEED);
        } else { //If a ball is in then load hopper
            while (true) { //Infinate loop
                setHopperMotors(HOPPER_SPEED);
                if (ultrasonic.getValue() < ballIn) { //If ball is in hopper
                    long start = System.currentTimeMillis();
                    long now = 0;
                    while (start - now < loadTime) { //Wait for loadTime
                        now = System.currentTimeMillis();
                    }
                    break;
                }
            }
        }
    }

    public static void changeShooterSpeed(float pSpeedAdded) {
        shootSpeed += pSpeedAdded;
        long start = System.currentTimeMillis();
        long now = 0;
        while (start - now < (2 * 1000)) { //Wait for 2 seconds
            now = System.currentTimeMillis();
        }
    }

    public static void runShooterMotors() {
        rMotor.set(shootSpeed);
        lMotor.set(shootSpeed);
    }

    public static void runHopperMotors() {
        rHopperMotor.set(HOPPER_SPEED);
        lHopperMotor.set(HOPPER_SPEED);
    }

    public static void setShooterMotors(float pSpeed) {
        rMotor.set(pSpeed);
    }
    
    public static void setHopperMotors(float pSpeed) {
        rHopperMotor.set(pSpeed);
        lHopperMotor.set(pSpeed);
    }

    public static void stop() {
        rMotor.set(0f);
        lMotor.set(0f);
        rHopperMotor.set(0f);
        lHopperMotor.set(0f);
    }

}