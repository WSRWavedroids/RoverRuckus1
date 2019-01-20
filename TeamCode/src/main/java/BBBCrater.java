import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Sam on 1/8/2019.
 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class BBBCrater extends LinearOpMode {
    // Declaration of variables
    DcMotor FrontLeftMotor;
    DcMotor FrontRightMotor;
    DcMotor RearLeftMotor;
    DcMotor RearRightMotor;
    DcMotor HookMotorDrive;
    Servo MarkerServo;


    //------------------------------
    //How much of this will I need?

    final long msStraight   = 2000;
    final long msTurn       = 1000;
    final double drivePower = 1.0;
    final int  numSides     = 4;
    final long start = System.nanoTime();
    //counter.countPrimes(10000)
    long end = System.nanoTime();
    double time = 0;
    int step = 0;

    //------------------------------



    public @Override void runOpMode() throws InterruptedException
    {
        FrontLeftMotor   = hardwareMap.dcMotor.get("FrontLeft");
        FrontRightMotor  = hardwareMap.dcMotor.get("FrontRight");
        RearLeftMotor    = hardwareMap.dcMotor.get("RearLeft");
        RearRightMotor    = hardwareMap.dcMotor.get("RearRight");
        HookMotorDrive = hardwareMap.dcMotor.get("Hook");
        MarkerServo = hardwareMap.servo.get("AutonomousServo");


        FrontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        FrontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        RearLeftMotor.setDirection((DcMotor.Direction.FORWARD));
        RearRightMotor.setDirection(DcMotor.Direction.REVERSE);
        HookMotorDrive.setDirection(DcMotor.Direction.FORWARD);

        this.waitForStart();


        HookMotorDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        HookMotorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FrontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FrontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        RearLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RearLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        RearRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RearRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //1 rotation is roughly 1440 ticks




        while (opModeIsActive()) {

            if (step == 0) {
                step = 1;
                HookMotorDrive.setPower(.3);
                HookMotorDrive.setTargetPosition(rotationToTicks(5));
            } else if (!(HookMotorDrive.isBusy()) && step == 1) {
                step = 2;
                MarkerServo.setPosition(1);
            } else if (MarkerServo.getPosition() == 1 && step == 2) {
                step = 3;
                HookMotorDrive.setTargetPosition(rotationToTicks(-5));
            } if (!HookMotorDrive.isBusy() && step == 0) {
                step = 1;
                FrontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                FrontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                FrontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                FrontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                RearLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RearLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                RearRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RearRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                FrontLeftMotor.setPower(0.5);
                FrontRightMotor.setPower(0.5);
                RearLeftMotor.setPower(0.5);
                RearRightMotor.setPower(0.5);
                FrontLeftMotor.setTargetPosition(rotationToTicks(-3));
                FrontRightMotor.setTargetPosition(rotationToTicks(-3));
                RearLeftMotor.setTargetPosition(rotationToTicks(-3));
                RearRightMotor.setTargetPosition(rotationToTicks(-3));
            }
            else if (!(FrontLeftMotor.isBusy() && FrontRightMotor.isBusy() && RearLeftMotor.isBusy() && RearRightMotor.isBusy()) && step == 1) {
                step = 2;
                FrontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                FrontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                FrontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                FrontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                RearLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RearLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                RearRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RearRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                FrontLeftMotor.setPower(0);
                FrontRightMotor.setPower(0);
                RearLeftMotor.setPower(0);
                RearRightMotor.setPower(0);
                FrontLeftMotor.setPower(0.5);
                FrontRightMotor.setPower(0.5);
                RearLeftMotor.setPower(0.5);
                RearRightMotor.setPower(0.5);
                FrontLeftMotor.setTargetPosition(rotationToTicks(-2.5-3));
                FrontRightMotor.setTargetPosition(rotationToTicks(2.5-3));
                RearLeftMotor.setTargetPosition(rotationToTicks(-2.5-3));
                RearRightMotor.setTargetPosition(rotationToTicks(2.5-3));
            }
            else if (!(FrontLeftMotor.isBusy()&&FrontRightMotor.isBusy()&&RearLeftMotor.isBusy()&&RearRightMotor.isBusy()) && step == 2){
                step = 3;
                FrontLeftMotor.setPower(0);
                FrontRightMotor.setPower(0);
                RearLeftMotor.setPower(0);
                RearRightMotor.setPower(0);
                FrontLeftMotor.setPower(0.5);
                FrontRightMotor.setPower(0.5);
                RearLeftMotor.setPower(0.5);
                RearRightMotor.setPower(0.5);
                FrontLeftMotor.setTargetPosition(rotationToTicks(3));
                FrontRightMotor.setTargetPosition(rotationToTicks(3));
                RearLeftMotor.setTargetPosition(rotationToTicks(3));
                RearRightMotor.setTargetPosition(rotationToTicks(3));
            }else if(!(FrontLeftMotor.isBusy()&&FrontRightMotor.isBusy()&&RearLeftMotor.isBusy()&&RearRightMotor.isBusy()) && step == 3){
                FrontLeftMotor.setPower(0);
                FrontRightMotor.setPower(0);
                RearLeftMotor.setPower(0);
                RearRightMotor.setPower(0);

            }

            telemetry.addData("Hook Motor Position", HookMotorDrive.getCurrentPosition());
            telemetry.addData("Front Left Motor Position", FrontLeftMotor.getCurrentPosition());
            telemetry.addData("Front Right Motor Position", FrontRightMotor.getCurrentPosition());
            telemetry.addData("Rear Left Motor Position", RearLeftMotor.getCurrentPosition());
            telemetry.addData("Rear Right Motor Position", RearRightMotor.getCurrentPosition());
            //telemetry.addData("Marker Motor Position", MarkerServo.getPosition());
            //telemetry.addData("Hook Servo Position", Servo1.getPosition());
            telemetry.addData("Steps", step);
            telemetry.update();
            idle();


        }


    }


       /*


       //CODE BY TIME
       NEED MarkerServo fixed


       while (opModeIsActive())
        {
            if(time < 1.5)
            {
                Hook.setPower(1);
            }
            else if(time < 4)
            {
                Hook.setPower(0);
                Servo1.setPosition(0);
            }
            else if(time < 7 )
            {
                FrontLeftMotor.setPower(-1);
                FrontRightMotor.setPower(-1);
                RearLeftMotor.setPower(-1);
                RearRightMotor.setPower(-1);
            }
            else if(time < 10)
            {
                FrontLeftMotor.setPower(0);
                FrontRightMotor.setPower(0);
                RearLeftMotor.setPower(0);
                RearRightMotor.setPower(0);
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
        FrontLeftMotor.setPower(drivePower);
        FrontRightMotor.setPower(-drivePower);
        RearLeftMotor.setPower(drivePower);
        RearRightMotor.setPower(-drivePower);
    }
    void turnLeft()
    {
        FrontLeftMotor.setPower(-drivePower);
        FrontRightMotor.setPower(-drivePower);
        RearLeftMotor.setPower(-drivePower);
        RearRightMotor.setPower(-drivePower);
    }
    void stopRobot()
    {
        FrontLeftMotor.setPower(0);
        FrontRightMotor.setPower(0);
        RearLeftMotor.setPower(0);
        RearRightMotor.setPower(0);
    } */

    public int rotationToTicks (double number){
        return (int)(number * 1440);

    }

}

