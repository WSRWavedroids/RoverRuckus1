import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
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
    private DcMotor ArmRotatorMotorDrive;
    private DcMotor ArmExtenderMotorDrive;
    private DcMotor ArmWristMotorDrive;
    private DcMotor HookMotorDrive;
    private double LeftJoystick;
    private double LeftJoystick2;
    private double RightJoystickY;
    private Servo MarkerServo;
    private Servo ContinuousSweeper;


    final long msStraight   = 2000;
    final long msTurn       = 1000;
    final double drivePower = 1.0;
    final int  numSides     = 4;
    final long start = System.nanoTime();
    //counter.countPrimes(10000)
    long end = System.nanoTime();
    double time = 0;
    int step = 0;
    public @Override void runOpMode() throws InterruptedException
    {
        FrontLeftDrive   = hardwareMap.dcMotor.get("FrontLeft");
        FrontRightDrive  = hardwareMap.dcMotor.get("FrontRight");
        RearLeftDrive    = hardwareMap.dcMotor.get("RearLeft");
        RearRightDrive    = hardwareMap.dcMotor.get("RearRight");
        MarkerServo = hardwareMap.servo.get("AutonomousServo");
        HookMotorDrive = hardwareMap.dcMotor.get("Hook");
        //Servo1 = hardwareMap.servo.get("Servo 0");

        FrontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        FrontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        RearLeftDrive.setDirection((DcMotor.Direction.FORWARD));
        RearRightDrive.setDirection(DcMotor.Direction.REVERSE);
        HookMotorDrive.setDirection(DcMotor.Direction.FORWARD);

        this.waitForStart();


        HookMotorDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        HookMotorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FrontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        RearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RearLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        RearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RearRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //1 rotation is roughly 1440 ticks


        while (opModeIsActive()) {
            if (step == 0) {
                step = 1;
                HookMotorDrive.setPower(1);
                HookMotorDrive.setTargetPosition(rotationToTicks(5));
            } else if (!(HookMotorDrive.isBusy()) && step == 1) {
                step = 2;
                FrontLeftDrive.setPower(0.5);
                FrontRightDrive.setPower(0.5);
                RearLeftDrive.setPower(0.5);
                RearRightDrive.setPower(0.5);
                FrontLeftDrive.setTargetPosition(rotationToTicks(-1));
                FrontRightDrive.setTargetPosition(rotationToTicks(1));
                RearLeftDrive.setTargetPosition(rotationToTicks(-1));
                RearRightDrive.setTargetPosition(rotationToTicks(1));
            } else if (!(FrontLeftDrive.isBusy() && FrontRightDrive.isBusy() && RearLeftDrive.isBusy() && RearRightDrive.isBusy()) && step == 2) {
                step = 3;
                HookMotorDrive.setTargetPosition(rotationToTicks(-5));
            } if (!HookMotorDrive.isBusy() && step == 3) {
                step = 4;
                /*FrontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                FrontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                FrontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                RearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RearLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                RearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RearRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);*/
                FrontLeftDrive.setPower(0.5);
                FrontRightDrive.setPower(0.5);
                RearLeftDrive.setPower(0.5);
                RearRightDrive.setPower(0.5);
                FrontLeftDrive.setTargetPosition(rotationToTicks(-5));
                FrontRightDrive.setTargetPosition(rotationToTicks(-5));
                RearLeftDrive.setTargetPosition(rotationToTicks(-5));
                RearRightDrive.setTargetPosition(rotationToTicks(-5));
            }
            else if (!(FrontLeftDrive.isBusy() && FrontRightDrive.isBusy() && RearLeftDrive.isBusy() && RearRightDrive.isBusy()) && step == 4) {
                step = 5;
                /*FrontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                FrontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                FrontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                RearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RearLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                RearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RearRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);*/
                FrontLeftDrive.setPower(0);
                FrontRightDrive.setPower(0);
                RearLeftDrive.setPower(0);
                RearRightDrive.setPower(0);
                FrontLeftDrive.setPower(0.5);
                FrontRightDrive.setPower(0.5);
                RearLeftDrive.setPower(0.5);
                RearRightDrive.setPower(0.5);
                FrontLeftDrive.setTargetPosition(rotationToTicks(-2.5-4.5));
                FrontRightDrive.setTargetPosition(rotationToTicks(2.5-4.5));
                RearLeftDrive.setTargetPosition(rotationToTicks(-2.5-4.5));
                RearRightDrive.setTargetPosition(rotationToTicks(2.5-4.5));
            }
            else if (!(FrontLeftDrive.isBusy()&&FrontRightDrive.isBusy()&&RearLeftDrive.isBusy()&&RearRightDrive.isBusy()) && step == 5){
                step = 6;
                FrontLeftDrive.setPower(0);
                FrontRightDrive.setPower(0);
                RearLeftDrive.setPower(0);
                RearRightDrive.setPower(0);
                MarkerServo.setPosition(.75);
            }else if(!(FrontLeftDrive.isBusy()&&FrontRightDrive.isBusy()&&RearLeftDrive.isBusy()&&RearRightDrive.isBusy()) && step == 6){
                step = 7;
                MarkerServo.setPosition(0);
                FrontLeftDrive.setPower(0.5);
                FrontRightDrive.setPower(0.5);
                RearLeftDrive.setPower(0.5);
                RearRightDrive.setPower(0.5);
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

            telemetry.addData("Front Left Motor Position", FrontLeftDrive.getCurrentPosition());
            telemetry.addData("Front Right Motor Position", FrontRightDrive.getCurrentPosition());
            telemetry.addData("Rear Left Motor Position", RearLeftDrive.getCurrentPosition());
            telemetry.addData("Rear Right Motor Position", RearRightDrive.getCurrentPosition());
            telemetry.addData("Steps", step);
            telemetry.update();
            idle();
        }


    }


       /* while (opModeIsActive())
        {
            if(time < 1.5)
            {
                HookMotorDrive.setPower(0.5);
            }
            else if(time < 4)
            {
                HookMotorDrive.setPower(0);
                Servo1.setPosition(0);
            }
            else if(time < 7 )
            {
                FrontLeftDrive.setPower(-1);
                FrontRightDrive.setPower(-1);
                RearLeftDrive.setPower(-1);
                RearRightDrive.setPower(-1);
            }
            else if(time < 10)
            {
                FrontLeftDrive.setPower(0);
                FrontRightDrive.setPower(0);
                RearLeftDrive.setPower(0);
                RearRightDrive.setPower(0);
            }
            else if(time < 13)
            {
                MarkerServo.setPower(.7);
            }
            else if(time < 15)
            {
                MarkerServo.setPower(0);
            }
            end = System.nanoTime();
            time = end-start/1000000000;


        telemetry.addData("Seconds", end-start/1000000000);

        }*/


        /*for (int i = 0; i < numSides; i++)
        {
            goStraight();
            Thread.sleep(msStraight);

            turnLeft();
            Thread.sleep(msTurn);
        }

        stopRobot();
    }*/

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
    } */

    public int rotationToTicks (double number){
        return (int)(number * 1440);

    }

}

