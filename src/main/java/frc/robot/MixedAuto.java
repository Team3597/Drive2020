package frc.robot;

import edu.wpi.first.wpilibj.SerialPort;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

public class MixedAuto {

  public static WPI_TalonFX wheelMotor = new WPI_TalonFX(RobotMap.WHEEL_MOTOR_PORT);

  // private static AHRS ahrs = new AHRS(SerialPort.Port.kMXP);

  private static boolean spinningWheel = false;
  private static boolean doneColor = false;
  private static boolean doneRotations = false;
  private static String currentColor = "";
  private static String lastColor = "";
  private static String targetColor = "";
  private static int currentRotations = 0;
  private static int targetRotations = 0;

  public static void align() {
    LimeLight.limeLightSetPipeline(1);
  
    float x = LimeLight.limeLightGetX();

    float turn = 0f;
              
    if(LimeLight.limeLightTargetFound()) {
      DriveTrain.drive = false;

      turn = Math.abs(x) / 150 + 0.18f;

      if(x > 1f) {
        DriveTrain.driveArcade(turn, 0f);
      } else if (x < -1f ) {
        DriveTrain.driveArcade(-turn, 0f);
      }
    }
  }

  // public static double getGyro() {
  //   return ahrs.getAngle() % 360d;
  // }

  // public static void faceAngle(double pAngle, double pTolerance) {
  //   if (getGyro() < pAngle - pTolerance) {
  //     DriveTrain.driveArcade(0.5f, 0f);
  //   } else if (getGyro() > pAngle + pTolerance) {
  //     DriveTrain.driveArcade(-0.5f, 0f);
  //   }
  // }

  public static void rotateWheel(int pRotations) {
    if(!spinningWheel) {
      targetRotations = pRotations * 8;
      lastColor = ColorSensor.findColor();
      wheelMotor.set(0.2f);
      spinningWheel = true;
    } else if (!doneRotations) {
      currentColor = ColorSensor.findColor();
      if(currentColor != lastColor) {
        currentRotations++;
      }
      lastColor = currentColor;
      System.out.println(currentRotations);
      if(currentRotations >= targetRotations) {
        doneRotations = true;
        wheelMotor.set(0f);
      }
    }
  }

  public static void rotateWheel(String pColor) {
    if(!spinningWheel) {
      switch (pColor) {
      case "Red":
        targetColor = "Blue";
      break;
      case "Green":
        targetColor = "Yellow";
      break;
      case "Blue":
        targetColor = "Red";
      break;
      case "Yellow":
        targetColor = "Green";
      break;
      default:
        targetColor = "Unidentified";
      }
      wheelMotor.set(0.2f);
      spinningWheel = true;
    } else if(!doneColor) {
      currentColor = ColorSensor.findColor();
      System.out.println("Target: " + targetColor + " Current: " + currentColor);
      if(currentColor == targetColor) {
        doneColor = true;
        wheelMotor.set(0f);
      }
    }
  }

  public static void resetWheel() {
    doneColor = false;
    doneRotations = false;
    currentRotations = 0;
  }

  public static void stopDriver() {
    DriveTrain.drive = true;
    LimeLight.limeLightSetPipeline(0);
  }

  public static void stopShooter() {
    spinningWheel = false;
    wheelMotor.set(0f);
  }
  
}
