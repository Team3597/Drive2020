package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

  public static IO io = new IO();
  public static LimeLight limeLight = new LimeLight();
  public static DriveTrain driveTrain = new DriveTrain();
  public static Laser laser = new Laser();
  public static Shooter shooter = new Shooter();
  public static MixedAuto mixedAuto = new MixedAuto();
  public static ColorSensor colorSensor = new ColorSensor();
  public static Climber climber = new Climber();

  @Override
  public void robotInit() {
    
  }

  @Override
  public void autonomousInit() {
    Auto.resetAutoShoot();
  }

  @Override
  public void autonomousPeriodic() {
    Auto.autoShoot();
  }

  @Override
  public void teleopInit() {
    
  }

  @Override
  public void teleopPeriodic() {
    switch (IO.buttonLayout) {
      default:
      case 0:
        DriveTrain.arcadeDriveWithJoystick();
      break;
      case 1:
        DriveTrain.driveWithTriggers();
      break;
    }

    IO.driveButtonsPressed();
    IO.shootButtonsPressed();

    // Shooter.shiftHopper();
    Shooter.moveArm();
  }

  @Override
  public void robotPeriodic() {
    // System.out.println(Shooter.getPot());
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
