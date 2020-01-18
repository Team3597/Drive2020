package frc.robot;

public class MixedAuto {

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

  public static void stop() {
    DriveTrain.drive = true;
  }
  
}
