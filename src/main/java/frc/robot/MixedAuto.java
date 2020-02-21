package frc.robot;

import edu.wpi.first.wpilibj.SerialPort;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

public class MixedAuto {

  private static WPI_TalonFX wheelMotor = new WPI_TalonFX(RobotMap.WHEEL_MOTOR_PORT);

  private static AHRS ahrs = new AHRS(SerialPort.Port.kMXP);

  public static void align() {
    LimeLight.limeLightSetPipeline(1);
  
    float x = LimeLight.limeLightGetX();

    float turn = 0f;
              
    if(LimeLight.limeLightTargetFound()) {
      DriveTrain.drive = false;

      turn = Math.abs(x) / 200 + 0.2f;

      if(x > 0.5f) {
        DriveTrain.driveArcade(turn, 0f);
      } else if (x < -2.5f ) {
        DriveTrain.driveArcade(-turn, 0f);
      }
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
    LimeLight.limeLightSetPipeline(0);
  }
  
}
