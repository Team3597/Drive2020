package frc.robot;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Spark;

import com.kauailabs.navx.frc.AHRS;

public class MixedAuto {

  private static Spark wheelMotor = new Spark(RobotMap.WHEEL_MOTOR_PORT);

  private static AHRS ahrs = new AHRS(SerialPort.Port.kMXP);

  public static void align() {
  
    float x = Robot.limeLight.limeLightGetX();
    float a = Robot.limeLight.limeLightGetArea();

    float turn = 0f;
    float drive = 0f;
              
    if(Robot.limeLight.limeLightTargetFound()) {
      DriveTrain.drive = false;

      if(x <= -2f) {
        turn = -0.5f;
      } else if(x > 2f) {
        turn = 0.5f;
      } else {
        if (a <= 2.3f) {
          drive = -0.5f;
        } else if (a > 3.3f) {
          drive = 0.5f;
        }
      }

      DriveTrain.driveArcade(turn, drive);

    }
  }

  public static void rotateWheel(int pRotations) {
    String currentColor = "";
    String lastColor = ColorSensor.findColor();

    int colorChanges = 0;

    while(true) {
      currentColor = ColorSensor.findColor();

      wheelMotor.set(1f);
      if (currentColor != lastColor) {
        colorChanges++;
      }

      if (colorChanges >= pRotations * 8) {
        wheelMotor.set(0f);
        break;
      }
      lastColor = currentColor;
    }
  }

  public static double getGyro() {
    return ahrs.getAngle() % 360d;
  }

  public static void faceAngle(double pAngle, double pTolerance) {
    if (getGyro() < pAngle - pTolerance) {
      DriveTrain.driveArcade(0.5f, 0f);
    } else if (getGyro() > pAngle + pTolerance) {
      DriveTrain.driveArcade(-0.5f, 0f);
    }
  }

  public static void rotateWheel(String pColor) {
    
    String targetColor = "";

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

    while (true) {
      String currentColor = ColorSensor.findColor();

      wheelMotor.set(0.5f); //Fix speed later
      if (currentColor == targetColor) {
        wheelMotor.set(0f);
        break;
      }
    }
  }

  public static void stop() {
    DriveTrain.drive = true;
  }
  
}
