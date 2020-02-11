package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain {

  private static DifferentialDrive driveTrain;

  /*private static CANSparkMax rightDrive;
  private static CANSparkMax leftDrive;*/
  private static WPI_TalonSRX rightDrive;
  private static WPI_TalonSRX leftDrive;

  private static float speed;
  private static final float DEFAULT_SPEED = 0.9f;
  private static final float SLOW_SPEED = 0.62f;

  public static boolean drive;
  private static boolean toggleSlowSpeed = true;

public DriveTrain() {

  /*rightDrive = new CANSparkMax(RobotMap.DRIVE_R_MOTOR_PORT, MotorType.kBrushless);
  leftDrive = new CANSparkMax(RobotMap.DRIVE_L_MOTOR_PORT, MotorType.kBrushless);*/
  rightDrive = new WPI_TalonSRX(RobotMap.DRIVE_R_MOTOR_PORT);
  leftDrive = new WPI_TalonSRX(RobotMap.DRIVE_L_MOTOR_PORT);

  //Add motor inverted

  driveTrain = new DifferentialDrive(leftDrive, rightDrive);
  

  drive = true;

  speed = DEFAULT_SPEED;
}

public static void driveArcade(float pRotation, float pSpeed) {
  driveTrain.arcadeDrive(pSpeed, pRotation);
}

public static void driveTank(float pLeftSpeed, float pRightSpeed) {
  driveTrain.tankDrive(pLeftSpeed, pRightSpeed);
}

public static void arcadeDriveWithJoystick() {
  if(drive) {
    float forward = (float) (IO.driveJoystick.getRawAxis(IO.RY_STICK_AXIS) * speed);
    float turn = (float) (IO.driveJoystick.getRawAxis(IO.LX_STICK_AXIS) * speed);

    driveArcade(turn, forward);
  }
}

public static void tankDriveWithJoystick() {
  if(drive) {
    float forward = (float) (IO.driveJoystick.getRawAxis(IO.LY_STICK_AXIS) * speed);
    float turn = (float) (IO.driveJoystick.getRawAxis(IO.RX_STICK_AXIS) * speed);

    driveTank(turn, forward);
  }
}

public static void rocketLeagueDrive() {
  if(drive) {
    float forward = 0f;

    if(IO.buttonPressed(IO.driveJoystick) == IO.L_TRIGGER_BUTTON) {
      forward = speed;
    } else if(IO.buttonPressed(IO.driveJoystick) == IO.R_TRIGGER_BUTTON) {
      forward = -speed;
    }

    float turn = (float) (IO.driveJoystick.getRawAxis(IO.LX_STICK_AXIS));

    driveArcade(turn, forward);
  }
}

public static void driveWithTriggers() {
  if (drive) {
    float forward = 0f;

    if(IO.buttonPressed(IO.driveJoystick) == IO.L_TRIGGER_BUTTON) {
      forward = speed * (float)(IO.driveJoystick.getRawAxis(IO.L_TRIGGER_AXIS));
    } else if(IO.buttonPressed(IO.driveJoystick) == IO.R_TRIGGER_BUTTON) {
      forward = -speed * (float)(IO.driveJoystick.getRawAxis(IO.R_TRIGGER_AXIS));
    }

    float turn = (float) (IO.driveJoystick.getRawAxis(IO.LX_STICK_AXIS));

    driveArcade(turn, forward);

  }
}

public static void toggleSlowSpeed() {
  if(toggleSlowSpeed) {
    toggleSlowSpeed = false;

    if(speed == DEFAULT_SPEED) {
      speed = SLOW_SPEED;
    } else {
      speed = DEFAULT_SPEED;
    }
  }
}

public static void stop() {
  toggleSlowSpeed = true;
}

}