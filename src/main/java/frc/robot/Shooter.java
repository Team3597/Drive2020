package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;

public class Shooter {

    private static WPI_TalonSRX rightShoot;
    private static WPI_TalonSRX leftShoot;
    private static Spark rightHopper;
    private static Spark leftHopper;
    private static Spark intake;
    private static WPI_TalonSRX intakeArm;
    public static DigitalInput limitSwitch;
    private static AnalogPotentiometer armPot;

    private static final float SHOOT_SPEED = 0.7f;
    private static final float HOPPER_SPEED = 0.7f;
    private static final float INTAKE_SPEED = 0.7f;
    private static final float ARM_SPEED = 0.5f;

    private static long warmupTime = 1 * 1000; //One second warmup (in milliseconds)
    private static long shootTime = 3 * 1000; //Shoot for ten seconds
    public static boolean warmedUp = false;

    private static long loadTime = 2 * 1000; //Two seconds to load hopper

    public static float shootSpeed = 1f;

    public static float potUp = 0.29f;
    public static float potDown = 0.52f;

    public static boolean down =false;
    public static boolean atPosition = false;

    /* @TODO:
     * Give infinate loops timeouts
     */

    public Shooter() {
        rightShoot = new WPI_TalonSRX(RobotMap.SHOOTER_R_MOTOR_PORT);
        leftShoot = new WPI_TalonSRX(RobotMap.SHOOTER_L_MOTOR_PORT);
        rightHopper = new Spark(RobotMap.SHOOTER_R_HOPPER_MOTOR_PORT);
        leftHopper = new Spark(RobotMap.SHOOTER_L_HOPPER_MOTOR_PORT);
        intake = new Spark(RobotMap.SHOOTER_INTAKE_MOTOR_PORT);
        intakeArm = new WPI_TalonSRX(RobotMap.SHOOTER_INTAKE_ARM_MOTOR_PORT);

        limitSwitch = new DigitalInput(RobotMap.SHOOTER_INTAKE_SWITCH_DIO_PORT);
        armPot = new AnalogPotentiometer(RobotMap.SHOOTER_INTAKE_ARM_POT_AI_PORT);

        rightShoot.setInverted(RobotMap.SHOOTER_R_MOTOR_INVERTED);
        leftShoot.setInverted(RobotMap.SHOOTER_L_MOTOR_INVERTED);
        rightHopper.setInverted(RobotMap.SHOOTER_R_HOPPER_MOTOR_INVERTED);
        leftHopper.setInverted(RobotMap.SHOOTER_L_HOPPER_MOTOR_INVERTED);
        intake.setInverted(RobotMap.SHOOTER_INTAKE_MOTOR_INVERTED);
        intakeArm.setInverted(RobotMap.SHOOTER_INTAKE_ARM_MOTOR_INVERTED);

        leftShoot.follow(rightShoot);
    }

    public static void shoot() {
        while(true) {
            if (warmedUp == false) { //If the shooter hasn't warmed up then warm up for warmupTime
                long start = System.currentTimeMillis();
                long now = System.currentTimeMillis();
                while (now - start < warmupTime) {
                    setShooterMotors((float)(findShootSpeed()));
                    now = System.currentTimeMillis();
                }
                warmedUp = true;
            } else { //If warmed up then fire shots
                long start = System.currentTimeMillis();
                long now = System.currentTimeMillis();
                while (now - start < shootTime) {
                    setHopperMotors(HOPPER_SPEED);
                    setShooterMotors(SHOOT_SPEED);
                    now = System.currentTimeMillis();
                }
                warmedUp = false;
                break;
            }
        }
    }

    public static double findShootSpeed() {
        double distance = Laser.getDistance();
        // final double a = -8.9396 * Math.pow(10d, -8d); a * Math.pow(distance, 4)
        final double b = 0.0000085;
        final double c = -0.00690642;
        final double d = 1.84773;
        final double f = -105.547;

        double speed = b * Math.pow(distance, 3) + c * Math.pow(distance, 2) + d * distance + f;

        return speed / 100;
    }

    public static void intake () {
        if(atPosition && !down){
            down = true;
            atPosition = false;
            moveArm();
        }
        if (limitSwitch.get() == true) { //If a ball isn't in then intake one
        intake.set(INTAKE_SPEED);
        setHopperMotors(0f);
        } else { //If a ball is in then index it
            setHopperMotors(HOPPER_SPEED);
        }
    }

    public static void changeShooterSpeed(float pSpeedAdded) {
        long start = System.currentTimeMillis();
        long now = 0;
        while (now - start < (0.5 * 1000)) { //Wait for 1/2 second
            now = System.currentTimeMillis();
        }
        shootSpeed += pSpeedAdded;
    }

    public static void runShooterMotors() {
        rightShoot.set(shootSpeed);
    }

    public static void runHopperMotors() {
        rightHopper.set(HOPPER_SPEED);
        leftHopper.set(HOPPER_SPEED);
    }

    public static void setShooterMotors(float pSpeed) {
        rightShoot.set(pSpeed);
    }
    
    public static void setHopperMotors(float pSpeed) {
        rightHopper.set(pSpeed);
        leftHopper.set(pSpeed);
    }

    public static float getPot() {
        return (float)(armPot.get());
    }

    public static void moveArm() {
        if(!down){ //If arm needs to go up put arm up
            if(armPot.get() > potUp) intakeArm.set(ARM_SPEED); //If arm isn't up put arm up
            else { //If arm is up then it's in position and should stop moving
                intakeArm.set(0);
                atPosition = true;
            }
        } else if (down){ //If arm needs to go down put arm down
            if(armPot.get() < potDown) intakeArm.set(-ARM_SPEED); //If arm isn't down put arm down
            else { //If arm is down then it's in position and should stop moving
                intakeArm.set(0);
                atPosition = true;
            }
        }
    }

    public static void stop() {
        rightShoot.set(0f);
        rightHopper.set(0f);
        leftHopper.set(0f);
        intake.set(0f);
        if(atPosition){
            down = false;
            atPosition = false;
        }
    }
}