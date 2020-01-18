package frc.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Spark;

public class Shooter {

    private static Spark armMotor;
    private static Spark rMotor;
    private static Spark lMotor;
    private static Spark rHopperMotor;
    private static Spark lHopperMotor;

    private static final float ARM_SPEED = 0.8f;
    private static final float SHOOT_SPEED = 1f;
    private static final float HOPPER_SPEED = 1f;

    private static AnalogPotentiometer pot;
    private static double potDownValue = 10d;

    private static long warmupTime = 1 * 1000; //One second warmup (in milliseconds)
    private static long shootTime = 10 * 1000; //Shoot for ten seconds
    private static boolean warmedUp = false;
    private static boolean shotsFired = false;
    private static boolean inPosition = false;

    private static AnalogInput ultrasonic = new AnalogInput(RobotMap.ULTRASONIC_ANALOG_PORT);
    private static double ballIn = 1.0d; //Voltage when ball is in
    private static long loadTime = 2 * 1000; //Two seconds to load hopper

    /* @TODO:
     * Give infinate loops timeouts
     */

    public Shooter() {
        armMotor = new Spark(RobotMap.SHOOTER_ARM_MOTOR_PORT);
        rMotor = new Spark(RobotMap.SHOOTER_R_MOTOR_PORT);
        lMotor = new Spark(RobotMap.SHOOTER_L_MOTOR_PORT);
        rHopperMotor = new Spark(RobotMap.SHOOTER_R_HOPPER_MOTOR_PORT);
        lHopperMotor = new Spark(RobotMap.SHOOTER_L_HOPPER_MOTOR_PORT);

        armMotor.setInverted(RobotMap.SHOOTER_ARM_MOTOR_INVERTED);
        rMotor.setInverted(RobotMap.SHOOTER_R_MOTOR_INVERTED);
        lMotor.setInverted(RobotMap.SHOOTER_L_MOTOR_INVERTED);
        rHopperMotor.setInverted(RobotMap.SHOOTER_R_HOPPER_MOTOR_INVERTED);
        lHopperMotor.setInverted(RobotMap.SHOOTER_L_HOPPER_MOTOR_INVERTED);

        pot = new AnalogPotentiometer(RobotMap.SHOOTER_POT_ANALOG_PORT);
    }

    public static void shoot(float pAngle) {
        while (true) { //Infinate loop
            if (!inPosition) { //If the arm isn't in position then put it in position
                double angle = pot.get();
                if (angle + 1f < pAngle || angle - 1f < pAngle) { //If arm isn't up put it up @TODO: Export tolerances
                    armMotor.set(ARM_SPEED);
                } else if(angle + 1f > pAngle || angle - 1f > pAngle) {
                    armMotor.set(ARM_SPEED);
                } else {
                    inPosition = true;
                }
            } else if (!warmedUp) { //If the shooter hasn't warmed up then warm up for warmupTime
                long start = System.currentTimeMillis();
                long now = 0;
                while (start - now < warmupTime) {
                    rMotor.set(SHOOT_SPEED);
                    lMotor.set(SHOOT_SPEED);
                    now = System.currentTimeMillis();
                }
                warmedUp = true;
            } else if (!shotsFired) { //If shots haven't been fired fire shots for shootTime
                long start = System.currentTimeMillis();
                long now = 0;
                while (start - now < shootTime) {
                    rHopperMotor.set(HOPPER_SPEED);
                    lHopperMotor.set(HOPPER_SPEED);
                    rMotor.set(SHOOT_SPEED);
                    lMotor.set(SHOOT_SPEED);
                    now = System.currentTimeMillis();
                }
                shotsFired = true;
            } else { //If warmed up and shots have been fired exit infinate loop
                warmedUp = false;
                shotsFired = false;
                inPosition = false;
                break;
            }
        }
    }

    public static void shootWithJoystick() {
        float speed = (float)(IO.shootJoystick.getRawAxis(IO.RX_STICK_AXIS));
        if (speed > 0.1f || speed < -0.1f) {
            System.out.println(speed);
            rMotor.set(-speed);
            lMotor.set(-speed);
        }
    }

    public static void intake () {
        double angle = pot.get();
        if (angle < potDownValue) { //If arm isn't down then put it down
            armMotor.set(-ARM_SPEED);
        } else if (ultrasonic.getValue() < ballIn) { //If a ball isn't in then intake one
            rMotor.set(-SHOOT_SPEED);
            lMotor.set(-SHOOT_SPEED);
        } else { //If a ball is in then load hopper
            while (true) { //Infinate loop
                rMotor.set(SHOOT_SPEED);
                lMotor.set(SHOOT_SPEED);
                rHopperMotor.set(-SHOOT_SPEED);
                lHopperMotor.set(-SHOOT_SPEED);
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
    
    public static void stop() {
        rMotor.set(0f);
        lMotor.set(0f);
        rHopperMotor.set(0f);
        lHopperMotor.set(0f);
    }

}