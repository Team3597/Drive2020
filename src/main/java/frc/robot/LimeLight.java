package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimeLight {

    private static NetworkTable limeLightTable;

    public LimeLight() {
        limeLightTable = NetworkTableInstance.getDefault().getTable("limelight");
    }

    public static float limeLightGetX() {
        NetworkTableEntry tx = limeLightTable.getEntry("tx");
        float x = (float) tx.getDouble(0d);
        return x;
      }
    
    public static float limeLightGetY() {
        NetworkTableEntry ty = limeLightTable.getEntry("ty");
        float y = (float) ty.getDouble(0d);
        return y;
    }
    
    public static float limeLightGetL() {
        NetworkTableEntry tl = limeLightTable.getEntry("tl");
        float l = (float) tl.getDouble(0d);
        return l;
    }
    
    public static float limeLightGetArea() {
        NetworkTableEntry ta = limeLightTable.getEntry("ta");
        float a = (float) ta.getDouble(0d);
        return a;
    }
    
    public static boolean limeLightTargetFound() {
        NetworkTableEntry tv = limeLightTable.getEntry("tv");
        double v = tv.getDouble(0d);
        if (v == 0d) {
          return false;
        } else {
          return true;
        }
    }
    
    public static void limeLightSetPipeline(int pPipeline) {
        if (pPipeline >= 0 && pPipeline <= 9) {
          limeLightTable.getEntry("pipeline").setValue(pPipeline);
        }
    }

}
