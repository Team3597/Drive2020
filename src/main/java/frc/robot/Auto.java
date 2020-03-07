package frc.robot;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.Timer;

public class Auto {

    private static Timer autonStateTimer;
    private static int autonState;

    // List of possible states    
    private static final int AUTON_STATE_DELAY = 1;
    private static final int AUTON_STATE_ALIGN = 2;
    private static final int AUTON_STATE_SHOOT = 3;
    private static final int AUTON_STATE_STOP_SHOOTING = 4;
    private static final int AUTON_STATE_DRIVE_BACKWARDS = 5;
    private static final int AUTON_STATE_DRIVE_FORWARDS = 9;
    private static final int AUTON_STATE_STOP_DRIVING = 6;
    private static final int AUTON_STATE_TURN_RIGHT = 7;
    private static final int AUTON_STATE_TURN_LEFT = 8;

    private static int position = 2;
    private static float delay = 1;

    private static final int LEFT_POSITION = 1;
    private static final int CENTER_POSITION = 2;
    private static final int RIGHT_POSITION = 3;

    private static List<Integer> states = new ArrayList<Integer>();

    private static void changeAutonState() {
        System.out.println("Change");
        if (states.size() >= 1) {
            states.remove(0);

            int nextState = states.get(0);

            if (nextState != autonState) {
                autonState = nextState;
                autonStateTimer.reset();
            }
        }
    }

    public static void resetAutoShoot() {

        DriveTrain.stop();
        Shooter.stopShooter();
        Shooter.stopDriver();

        System.out.println("Reset");
        position = 2;
        delay = 1f;

        int size = states.size();
        for (int index = size-1; index >= 0; index--) {
            states.remove(index);
        }

        switch(position) {
            case LEFT_POSITION:
                System.out.println("Left");
                states.add(AUTON_STATE_DELAY);
                states.add(AUTON_STATE_ALIGN);
                states.add(AUTON_STATE_SHOOT);
                states.add(AUTON_STATE_STOP_SHOOTING);
                states.add(AUTON_STATE_DRIVE_FORWARDS);
                states.add(AUTON_STATE_STOP_DRIVING);
            break;
            case CENTER_POSITION:
                System.out.println("Center");
                states.add(AUTON_STATE_DELAY);
                states.add(AUTON_STATE_ALIGN);
                states.add(AUTON_STATE_SHOOT);
                states.add(AUTON_STATE_STOP_SHOOTING);
                states.add(AUTON_STATE_DRIVE_BACKWARDS);
                states.add(AUTON_STATE_STOP_DRIVING);
            break;
            case RIGHT_POSITION:
                System.out.println("Right");
                states.add(AUTON_STATE_DELAY);
                states.add(AUTON_STATE_ALIGN);
                states.add(AUTON_STATE_SHOOT);
                states.add(AUTON_STATE_STOP_SHOOTING);
                states.add(AUTON_STATE_DRIVE_FORWARDS);
                states.add(AUTON_STATE_STOP_DRIVING);
            break;
        }

        autonStateTimer = new Timer();
        autonStateTimer.start();

        autonState = states.get(0);
    }

    public static void autoShoot() {
        switch(autonState) {
            case AUTON_STATE_DELAY:
                if (autonStateTimer.hasPeriodPassed(delay)) {
                    changeAutonState();
                }
            break;
            case AUTON_STATE_ALIGN:
                MixedAuto.align();
                if (autonStateTimer.hasPeriodPassed(3f)) {
                    Shooter.stopDriver();
                    changeAutonState();
                }
            break;
            case AUTON_STATE_SHOOT:
                Shooter.shoot();
                if (autonStateTimer.hasPeriodPassed(1f)) {
                    changeAutonState();
                }
            break;
                case AUTON_STATE_STOP_SHOOTING:
                Shooter.stopShooter();
                if (autonStateTimer.hasPeriodPassed(1f)) {
                    changeAutonState();
                }
            break;
                case AUTON_STATE_DRIVE_BACKWARDS:
                DriveTrain.driveArcade(0f, -0.5f);
                if(autonStateTimer.hasPeriodPassed(1f)) {
                    changeAutonState();
                }
            break;
            case AUTON_STATE_DRIVE_FORWARDS:
                DriveTrain.driveArcade(0f, 0.5f);
                if(autonStateTimer.hasPeriodPassed(2f)) {
                    changeAutonState();
                }
            break;
            case AUTON_STATE_STOP_DRIVING:
                DriveTrain.driveArcade(0f, 0f);
            break;
            case AUTON_STATE_TURN_RIGHT:
                DriveTrain.driveArcade(0.5f, 0f);
                if(autonStateTimer.hasPeriodPassed(1f)) {
                    changeAutonState();
                }
            break;
            case AUTON_STATE_TURN_LEFT:
                DriveTrain.driveArcade(-0.5f, 0f);
                if(autonStateTimer.hasPeriodPassed(1f)) {
                    changeAutonState();
                }
            break;
        }
    }
}