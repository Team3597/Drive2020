package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class MixedAuto {

  private static Spark colorMotor;

  public MixedAuto() {
    colorMotor = new Spark(RobotMap.COLOR_MOTOR_PORT);
  }

  public static boolean align(float pArea) {
    System.out.println("Align!");

    LimeLight.limeLightSetPipeline(0);
  
    float x = LimeLight.limeLightGetX();
    float a = LimeLight.limeLightGetArea();

    float turn = 0f;
    float drive = 0f;

    if(LimeLight.limeLightTargetFound()) {
      System.out.println("Target Found!");
      DriveTrain.drive = false;

      if(x <= -2f) {
        turn = -0.4f;
      } else if(x > 2f) {
        turn = 0.4f;
      } else {
        if (a <= pArea - 0.3f) {
          drive = 0.4f;
        } else if (a >= pArea + 0.3f) {
          drive = -0.4f;
        } else {
          return true;
        }
      }

      DriveTrain.driveArcade(turn, drive);
      return false;
    }

    return false;
  }

  public static void alignAndShoot(float pArea, float pAngle) {
    if(align(pArea)) {
      Shooter.shoot(pAngle);
    }
  }

  public static void spinWheel(int pSpins) {
    String firstColor = ColorSensor.findColor();
    String lastColor = firstColor;

    int colorChanges = 0;

    while(true) {
      System.out.println("Changes:" + colorChanges + "LastColor:" + lastColor);
      colorMotor.set(1f);

      String currentColor = ColorSensor.findColor();
      if(currentColor != lastColor) {
        colorChanges++;
        if (colorChanges == pSpins * 8) {
          break;
        }
      }
      lastColor = currentColor;
    }
    colorMotor.set(0f);
  }

  public static void stop() {
    DriveTrain.drive = true;
    LimeLight.limeLightSetPipeline(1);
  }

}
