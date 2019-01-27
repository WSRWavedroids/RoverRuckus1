import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by wave on 1/05/2018.
 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class BBBDepotAutonomous extends LinearOpMode {
    private DcMotor FrontLeftDrive;
    private DcMotor FrontRightDrive;
    private DcMotor RearLeftDrive;
    private DcMotor RearRightDrive;
    private DcMotor HookMotorDrive;
    private Servo MarkerServo;

    //Old Methods and Teleop Outputs
    //private DcMotor ArmRotatorMotorDrive;
    //private DcMotor ArmExtenderMotorDrive;
    //private DcMotor ArmWristMotorDrive;
    //private double LeftJoystick;
    //private double LeftJoystick2;
    //private double RightJoystickY;
    //private Servo ContinuousSweeper;

    //final long msStraight   = 2000;
    //final long msTurn       = 1000;
    //final double drivePower = 1.0;
    //final int  numSides     = 4;
    // counter.countPrimes(10000)


    /*final*/ long start = System.nanoTime();
    double time = 0;
    int step = 0;

    public @Override
    void runOpMode() throws InterruptedException {
        FrontLeftDrive = hardwareMap.dcMotor.get("FrontLeft");
        FrontRightDrive = hardwareMap.dcMotor.get("FrontRight");
        RearLeftDrive = hardwareMap.dcMotor.get("RearLeft");
        RearRightDrive = hardwareMap.dcMotor.get("RearRight");
        MarkerServo = hardwareMap.servo.get("AutonomousServo");
        HookMotorDrive = hardwareMap.dcMotor.get("Hook");

        this.waitForStart();

        //Direction Setting
        FrontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        FrontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        RearLeftDrive.setDirection((DcMotor.Direction.REVERSE));
        RearRightDrive.setDirection(DcMotor.Direction.FORWARD);
        HookMotorDrive.setDirection(DcMotor.Direction.REVERSE);

        //Encoder Code
        /*HookMotorDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        HookMotorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RearLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RearRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);*/



        //Zero Power Behavior
        FrontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RearLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RearRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Main Code
        while (opModeIsActive()) {

            //Lower Latch
            if (time < 3) {
                HookMotorDrive.setPower(0.3);
            }
            //Stop unlatching
            else if (time < 4) {
                HookMotorDrive.setPower(0);
            }
            //Crab Away
            else if (time < 4.05) {
                FrontLeftDrive.setPower(0.5);
                FrontRightDrive.setPower(-0.5);
                RearLeftDrive.setPower(-0.5);
                RearRightDrive.setPower(0.5);
            }
            //Stop
            else if (time < 5) {
                FrontLeftDrive.setPower(0);
                FrontRightDrive.setPower(0);
                RearLeftDrive.setPower(0);
                RearRightDrive.setPower(0);
            }
            //Drive to Depot
            else if (time < 6) {
                FrontLeftDrive.setPower(-0.375);
                FrontRightDrive.setPower(-0.375);
                RearLeftDrive.setPower(-0.375);
                RearRightDrive.setPower(-0.375);
            }
            //Stop
            else if (time < 7) {
                FrontLeftDrive.setPower(0);
                FrontRightDrive.setPower(0);
                RearLeftDrive.setPower(0);
                RearRightDrive.setPower(0);
            }
            //Turn
            else if (time < 8) {
                FrontLeftDrive.setPower(-0.4);
                FrontRightDrive.setPower(0.4);
                RearLeftDrive.setPower(-0.4);
                RearRightDrive.setPower(0.4);
            }
            //Stop
            else if (time < 9) {
                FrontLeftDrive.setPower(0);
                FrontRightDrive.setPower(0);
                RearLeftDrive.setPower(0);
                RearRightDrive.setPower(0);
            }
            //Drop Marker
            else if (time < 10){
                MarkerServo.setPosition(-0.5);
            }

            //Time System
            long end = System.nanoTime();
            time = (end - start)/1000000000;


            //Telemetry
            //telemetry.addData("Front Left Motor Position", FrontLeftDrive.getCurrentPosition());
            //telemetry.addData("Front Right Motor Position", FrontRightDrive.getCurrentPosition());
            //telemetry.addData("Rear Left Motor Position", RearLeftDrive.getCurrentPosition());
            //telemetry.addData("Rear Right Motor Position", RearRightDrive.getCurrentPosition());
            //telemetry.addData("Hook Motor Position", HookMotorDrive.getCurrentPosition());
            telemetry.addData("Hook Motor Power",HookMotorDrive.getPower());
            telemetry.addData("Front Left Motor Power",FrontLeftDrive.getPower());
            telemetry.addData("Front Right Motor Power",FrontRightDrive.getPower());
            telemetry.addData("Rear Left Motor Power",RearLeftDrive.getPower());
            telemetry.addData("Rear Right Motor Power",RearRightDrive.getPower());
            telemetry.addData("Steps", step);
            telemetry.addData("Start", start);
            telemetry.addData("End", end);
            telemetry.addData("Time", time);
            telemetry.update();
            idle();

        }

    }
}
//Old Code and Encoder Code
    //Encoder Code

     /*while (opModeIsActive())
     {
     if (step == 0) {
     step = 1;
     HookMotorDrive.setPower(1);
     HookMotorDrive.setTargetPosition(rotationToTicks(5));
     } else if (!(HookMotorDrive.isBusy()) && step == 1) {
     step = 2;
     HookMotorDrive.setPower(0);
     FrontLeftDrive.setPower(0.1);
     FrontRightDrive.setPower(0.1);
     RearLeftDrive.setPower(0.1);
     RearRightDrive.setPower(0.1);
     FrontLeftDrive.setTargetPosition(rotationToTicks(-1));
     FrontRightDrive.setTargetPosition(rotationToTicks(1));
     RearLeftDrive.setTargetPosition(rotationToTicks(-1));
     RearRightDrive.setTargetPosition(rotationToTicks(1));
     } else if (!(FrontLeftDrive.isBusy() && FrontRightDrive.isBusy() && RearLeftDrive.isBusy() && RearRightDrive.isBusy()) && step == 2) {
     step = 3;
     HookMotorDrive.setTargetPosition(rotationToTicks(-5));
     } if (!HookMotorDrive.isBusy() && step == 3) {
     step = 4;
     FrontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     FrontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
     FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     FrontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
     RearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     RearLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
     RearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     RearRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
     FrontLeftDrive.setPower(0.1);
     FrontRightDrive.setPower(0.1);
     RearLeftDrive.setPower(0.1);
     RearRightDrive.setPower(0.1);
     FrontLeftDrive.setTargetPosition(rotationToTicks(-13));
     FrontRightDrive.setTargetPosition(rotationToTicks(-13));
     RearLeftDrive.setTargetPosition(rotationToTicks(-13));
     RearRightDrive.setTargetPosition(rotationToTicks(-13));


     }
     else if (-450 > FrontLeftDrive.getCurrentPosition() && -450 > FrontRightDrive.getCurrentPosition() && -450 > RearLeftDrive.getCurrentPosition() && -450 > RearRightDrive.getCurrentPosition() &&  step == 4) {
     step = 5;
     FrontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     FrontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
     FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     FrontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
     RearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     RearLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
     RearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     RearRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
     FrontLeftDrive.setPower(0);
     FrontRightDrive.setPower(0);
     RearLeftDrive.setPower(0);
     RearRightDrive.setPower(0);
     FrontLeftDrive.setPower(0.1);
     FrontRightDrive.setPower(0.1);
     RearLeftDrive.setPower(0.1);
     RearRightDrive.setPower(0.1);
     FrontLeftDrive.setTargetPosition(rotationToTicks(-25));
     FrontRightDrive.setTargetPosition(rotationToTicks(15));
     RearLeftDrive.setTargetPosition(rotationToTicks(-25));
     RearRightDrive.setTargetPosition(rotationToTicks(15));
     }
     else if (-880 > FrontLeftDrive.getCurrentPosition() && 520 < FrontRightDrive.getCurrentPosition() && -880 > RearLeftDrive.getCurrentPosition() && 520 < RearRightDrive.getCurrentPosition() && step == 5){
     step = 6;
     FrontLeftDrive.setPower(0);
     FrontRightDrive.setPower(0);
     RearLeftDrive.setPower(0);
     RearRightDrive.setPower(0);
     MarkerServo.setPosition(.45);
     }else if(!FrontLeftDrive.isBusy()&& !FrontRightDrive.isBusy()&& !RearLeftDrive.isBusy()&& !RearRightDrive.isBusy() && step == 6){
     step = 7;
     MarkerServo.setPosition(0);
     FrontLeftDrive.setPower(0.1);
     FrontRightDrive.setPower(0.1);
     RearLeftDrive.setPower(0.1);
     RearRightDrive.setPower(0.1);
     FrontLeftDrive.setTargetPosition(rotationToTicks(-7.5+5));
     FrontRightDrive.setTargetPosition(rotationToTicks(-7.5+5));
     RearLeftDrive.setTargetPosition(rotationToTicks(-7.5+5));
     RearRightDrive.setTargetPosition(rotationToTicks(-7.5+5));
     }else if (!(FrontLeftDrive.isBusy()&&FrontRightDrive.isBusy()&&RearLeftDrive.isBusy()&&RearRightDrive.isBusy()) && step == 7){
     FrontLeftDrive.setPower(0);
     FrontRightDrive.setPower(0);
     RearLeftDrive.setPower(0);
     RearRightDrive.setPower(0);
     }
     }*/






        //Rotation to Ticks Method
    /*public int rotationToTicks (double number){
        return (int)(number * 36.4);

    }*/






        //Old Methods
       /*void goStraight()
    {
        FrontLeftDrive.setPower(drivePower);
        FrontRightDrive.setPower(-drivePower);
        RearLeftDrive.setPower(drivePower);
        RearRightDrive.setPower(-drivePower);
    }
    void turnLeft()
    {
        FrontLeftDrive.setPower(-drivePower);
        FrontRightDrive.setPower(-drivePower);
        RearLeftDrive.setPower(-drivePower);
        RearRightDrive.setPower(-drivePower);
    }
    void stopRobot()
    {
        FrontLeftDrive.setPower(0);
        FrontRightDrive.setPower(0);
        RearLeftDrive.setPower(0);
        RearRightDrive.setPower(0);
    }

     for (int i = 0; i < numSides; i++)
     {
     goStraight();
     Thread.sleep(msStraight);

     turnLeft();
     Thread.sleep(msTurn);
     }

     stopRobot();
     }
     */