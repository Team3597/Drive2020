package frc.robot;

public class RobotMap {

    //Drive
    public static final int DRIVE_F_R_MOTOR_PORT = 13; //Spark Max
    public static final int DRIVE_F_L_MOTOR_PORT = 11; //Spark Max
    public static final int DRIVE_B_R_MOTOR_PORT = 14; //Spark Max
    public static final int DRIVE_B_L_MOTOR_PORT = 12; //Spark Max

    public static final boolean DRIVE_F_R_MOTOR_INVERTED = false;
    public static final boolean DRIVE_F_L_MOTOR_INVERTED = false;
    public static final boolean DRIVE_B_R_MOTOR_INVERTED = false;
    public static final boolean DRIVE_B_L_MOTOR_INVERTED = false;

    //Shooter
    public static final int SHOOTER_R_MOTOR_PORT = 4; //Talon
    public static final int SHOOTER_L_MOTOR_PORT = 3; //Talon
    public static final int SHOOTER_R_HOPPER_MOTOR_PORT = 2; //Spark
    public static final int SHOOTER_L_HOPPER_MOTOR_PORT = 1; //Spark
    public static final int SHOOTER_INTAKE_MOTOR_PORT = 0; //Spark
    public static final int SHOOTER_INTAKE_ARM_MOTOR_PORT = 2; //Talon
    public static final int SHOOTER_INTAKE_ARM_POT_AI_PORT = 0;
    public static final int SHOOTER_INTAKE_ENCODER_DIO_PORT = 9;

    public static final boolean SHOOTER_R_MOTOR_INVERTED = true;
    public static final boolean SHOOTER_L_MOTOR_INVERTED = true;
    public static final boolean SHOOTER_R_HOPPER_MOTOR_INVERTED = false;
    public static final boolean SHOOTER_L_HOPPER_MOTOR_INVERTED = true;
    public static final boolean SHOOTER_INTAKE_MOTOR_INVERTED = true;
    public static final boolean SHOOTER_INTAKE_ARM_MOTOR_INVERTED = false;

    //Climber
    public static final int CLIMBER_T_MOTOR_PORT = 16; //Spark Max
    public static final int CLIMBER_B_MOTOR_PORT = 15; //Spark Max
    
    //Color Wheel
    public static final int WHEEL_MOTOR_PORT = 1; //Talon

    //Laser
    public static final int LASER_DIO_PORT = 1;
}
