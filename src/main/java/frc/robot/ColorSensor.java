package frc.robot;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.*;

public class ColorSensor {

    private static final I2C.Port i2cPort = I2C.Port.kOnboard;
    private static final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
    private static final ColorMatch colorMatcher = new ColorMatch();
    
    private static final Color BLUE_TARGET = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private static final Color GREEN_TARGET = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private static final Color RED_TARGET = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private static final Color YELLOW_TARGET = ColorMatch.makeColor(0.361, 0.524, 0.113);

    public ColorSensor() {
        colorMatcher.addColorMatch(BLUE_TARGET);
        colorMatcher.addColorMatch(GREEN_TARGET);
        colorMatcher.addColorMatch(RED_TARGET);
        colorMatcher.addColorMatch(YELLOW_TARGET);
    }

    public static String findColor() {
      Color detectedColor = colorSensor.getColor();

      String colorString;
      ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);

      if (match.color == BLUE_TARGET) {
        colorString = "Blue";
      } else if (match.color == RED_TARGET) {
        colorString = "Red";
      } else if (match.color == GREEN_TARGET) {
        colorString = "Green";
      } else if (match.color == YELLOW_TARGET) {
        colorString = "Yellow";
      } else {
        colorString = "Unknown";
      }

      SmartDashboard.putString("Detected Color", colorString);
      System.out.println(colorString);

      return colorString;
    }
}