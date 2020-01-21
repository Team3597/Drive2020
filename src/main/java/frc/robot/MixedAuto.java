package frc.robot;

public class MixedAuto {

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

  public static void stop() {
    DriveTrain.drive = true;
    LimeLight.limeLightSetPipeline(1);
  }
  
  public static void alignAndShoot(float pArea, float pAngle) {
    if(align(pArea)) {
      Shooter.shoot(pAngle);
    }
  }

}
