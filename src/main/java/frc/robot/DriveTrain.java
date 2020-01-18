package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain {

  private static DifferentialDrive driveTrain;

  private static WPI_VictorSPX rightDrive;
  private static WPI_VictorSPX leftDrive;

  private static float speed;
  private static final float DEFAULT_SPEED = 0.9f;
  private static final float SLOW_SPEED = 0.62f;

  public static boolean drive;
  private static boolean toggleSlowSpeed = true;

  private static boolean toggleInverse = true;
  private static float inverted = 1;

public DriveTrain() {

  rightDrive = new WPI_VictorSPX(RobotMap.DRIVE_R_MOTOR_PORT);
  leftDrive = new WPI_VictorSPX(RobotMap.DRIVE_L_MOTOR_PORT);

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
    float forward = (float) (IO.driveJoystick.getRawAxis(IO.RY_STICK_AXIS) * speed * inverted);
    float turn = (float) (IO.driveJoystick.getRawAxis(IO.LX_STICK_AXIS) * speed);

    driveArcade(turn, forward);
  }
}

public static void tankDriveWithJoystick() {
  if(drive) {
    float forward = (float) (IO.driveJoystick.getRawAxis(IO.LY_STICK_AXIS) * speed * inverted);
    float turn = (float) (IO.driveJoystick.getRawAxis(IO.RX_STICK_AXIS) * speed * inverted);

    driveTank(turn, forward);
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

public static void toggleInverseDrive(){
  if(toggleInverse){
    toggleInverse = false;

    if(inverted == 1){
      inverted = -1;
    }
    else{
      inverted = 1;
    }
  }
}

public static void stop() {
  toggleSlowSpeed = true;
  toggleInverse = true;
}

}