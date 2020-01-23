package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class IO {

    public static final int DRIVE_JOYSTICK_PORT = 0;
    public static final int SHOOT_JOYSTICK_PORT = 1;

    public static final int LX_STICK_AXIS = 0;
    public static final int LY_STICK_AXIS = 1;
    public static final int RX_STICK_AXIS = 4;
    public static final int RY_STICK_AXIS = 5;

    public static final int L_TRIGGER_AXIS = 2;
    public static final int R_TRIGGER_AXIS = 3;

    public static final int PAD = 16;

    public static final int A_BUTTON = 1;
    public static final int B_BUTTON = 2;
    public static final int X_BUTTON = 3;
    public static final int Y_BUTTON = 4;
    public static final int LB_BUTTON = 5;
    public static final int RB_BUTTON = 6;
    public static final int BACK_BUTTON = 7;
    public static final int START_BUTTON = 8;
    public static final int L_STICK_BUTTON = 9;
    public static final int R_STICK_BUTTON = 10;
    public static final int L_TRIGGER_BUTTON = 11;
    public static final int R_TRIGGER_BUTTON = 12;

    public static Joystick driveJoystick;
    public static Joystick shootJoystick;

    public IO() {
        driveJoystick = new Joystick(DRIVE_JOYSTICK_PORT);
        shootJoystick = new Joystick(SHOOT_JOYSTICK_PORT);
    }

    private static int buttonPressed(Joystick pJoystick) {
        
        if(pJoystick.getRawButton(A_BUTTON)) {
            return A_BUTTON;
        } else if(pJoystick.getRawButton(B_BUTTON)) {
            return B_BUTTON;
        } else if(pJoystick.getRawButton(X_BUTTON)) {
            return X_BUTTON;
        } else if(pJoystick.getRawButton(Y_BUTTON)) {
            return Y_BUTTON;
        } else if (pJoystick.getRawButton(LB_BUTTON)) {
            return LB_BUTTON;
        } else if (pJoystick.getRawButton(RB_BUTTON)) {
            return RB_BUTTON;
        } else if (pJoystick.getRawButton(BACK_BUTTON)) {
            return BACK_BUTTON;
        } else if (pJoystick.getRawButton(START_BUTTON)) {
            return START_BUTTON;
        } else if (pJoystick.getRawButton(L_STICK_BUTTON)) {
            return L_STICK_BUTTON;
        } else if (pJoystick.getRawButton(R_STICK_BUTTON)) {
        return R_STICK_BUTTON;
        } else if (pJoystick.getRawAxis(L_TRIGGER_AXIS) > 0.1f) {
        return L_TRIGGER_BUTTON;
        } else if(pJoystick.getRawAxis(R_TRIGGER_AXIS) > 0.1f) {
        return R_TRIGGER_BUTTON;
        } else if(pJoystick.getPOV() > -1){
            return PAD;
        } else {
            return 0;
        } 
    } 

    public static void driveButtonsPressed() {              //DRIVE CONTROLLER
        switch (buttonPressed(IO.driveJoystick)) {
            case A_BUTTON:
                DriveTrain.toggleSlowSpeed();
                break;
            case B_BUTTON:
                DriveTrain.toggleInverseDrive();
                break;
            case X_BUTTON:
                MixedAuto.align(4f);
                break;
            case Y_BUTTON:
                MixedAuto.spinWheel(4);
                break;
            default:
                DriveTrain.stop();
                MixedAuto.stop();
        }
    }

    public static void shootButtonsPressed() {              //SHOOT CONTROLLER
        switch (buttonPressed(IO.shootJoystick)) {
            case A_BUTTON:
                
                break;
            case B_BUTTON:
                
                break;
            case X_BUTTON:
                
                break;
            case Y_BUTTON:
                
                break;
            case LB_BUTTON:
                Shooter.intake();
                break;
            case RB_BUTTON:
                Shooter.shoot(45f);
                break;
            case R_TRIGGER_BUTTON:
                
                break;
            case L_STICK_BUTTON:
                
                break;
            case R_STICK_BUTTON:
                
                break;
            case BACK_BUTTON:
                
                break;
            case START_BUTTON:
                
                break;
            case PAD:
                
                break;
            default:
                Shooter.stop();
          }
    }
}
