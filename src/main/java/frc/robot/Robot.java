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

  @Override
  public void robotInit() {
    
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {

  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    DriveTrain.driveWithTriggers();

    IO.driveButtonsPressed();
    IO.shootButtonsPressed();

    Shooter.moveArm();
  }

  @Override
  public void robotPeriodic() {
    
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
