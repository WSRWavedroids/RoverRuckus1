import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by wave on 10/16/2018.
 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class AutonomousEncoder extends LinearOpMode {
    DcMotor FrontLeftMotor;
    DcMotor FrontRightMotor;
    DcMotor RearLeftMotor;
    DcMotor RearRightMotor;
    DcMotor HookMotor;
    //DcMotor MarkerMotor;
    Servo Servo1;
    //Servo MarkerServo;

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
        FrontLeftMotor   = hardwareMap.dcMotor.get("FrontLeft");
        FrontRightMotor  = hardwareMap.dcMotor.get("FrontRight");
        RearLeftMotor    = hardwareMap.dcMotor.get("RearLeft");
        RearRightMotor    = hardwareMap.dcMotor.get("RearRight");
        //MarkerMotor = hardwareMap.dcMotor.get("Marker");
        HookMotor = hardwareMap.dcMotor.get("Hook");
        Servo1 = hardwareMap.servo.get("Servo 0");
        //MarkerServo = hardwareMap.servo.get("MarkerServo");

        FrontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        FrontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        RearLeftMotor.setDirection((DcMotor.Direction.FORWARD));
        RearRightMotor.setDirection(DcMotor.Direction.REVERSE);
        HookMotor.setDirection(DcMotor.Direction.FORWARD);

        this.waitForStart();


        HookMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        HookMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FrontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FrontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        RearLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RearLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

       // MarkerMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //MarkerMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        RearRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RearRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //1 rotation is roughly 1440 ticks

        //Set Encoder to 0 at start
        Servo1.setPosition(.3);

        while (opModeIsActive()) {
            if (step == 0) {
                step = 1;
                HookMotor.setPower(.75);
                HookMotor.setTargetPosition(rotationToTicks(-8.5));
            } else if (!(HookMotor.isBusy()) && step == 1) {
                step = 2;
                Servo1.setPosition(1);
                HookMotor.setTargetPosition(rotationToTicks(-10));
            } else if (Servo1.getPosition() == 1 && step == 2) {
                step = 3;
                HookMotor.setPower(0);
                HookMotor.setTargetPosition(rotationToTicks(0));
            } if (!HookMotor.isBusy() && step == 3) {
                step = 4;
                /*FrontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                FrontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                FrontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                FrontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                RearLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RearLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                RearRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RearRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);*/
                FrontLeftMotor.setPower(1);
                FrontRightMotor.setPower(1);
                RearLeftMotor.setPower(1);
                RearRightMotor.setPower(1);
                FrontLeftMotor.setTargetPosition(rotationToTicks(-3.5));
                FrontRightMotor.setTargetPosition(rotationToTicks(-3.5));
                RearLeftMotor.setTargetPosition(rotationToTicks(-3.5));
                RearRightMotor.setTargetPosition(rotationToTicks(-3.5));
            }
             else if (!(FrontLeftMotor.isBusy() && FrontRightMotor.isBusy() && RearLeftMotor.isBusy() && RearRightMotor.isBusy()) && step == 4) {
                step = 5;
                /*FrontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                FrontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                FrontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                FrontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                RearLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RearLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                RearRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RearRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);*/
                FrontLeftMotor.setPower(0);
                FrontRightMotor.setPower(0);
                RearLeftMotor.setPower(0);
                RearRightMotor.setPower(0);
                FrontLeftMotor.setPower(1);
                FrontRightMotor.setPower(1);
                RearLeftMotor.setPower(1);
                RearRightMotor.setPower(1);
                FrontLeftMotor.setTargetPosition(rotationToTicks(-5));
                FrontRightMotor.setTargetPosition(rotationToTicks(5));
                RearLeftMotor.setTargetPosition(rotationToTicks(-5));
                RearRightMotor.setTargetPosition(rotationToTicks(5));
            }
            else if (!(FrontLeftMotor.isBusy()&&FrontRightMotor.isBusy()&&RearLeftMotor.isBusy()&&RearRightMotor.isBusy()) && step == 5){
                step = 6;
                FrontLeftMotor.setPower(0);
                FrontRightMotor.setPower(0);
                RearLeftMotor.setPower(0);
                RearRightMotor.setPower(0);
                //MarkerServo.setPosition(.5);
            }else if(/*MarkerServo.getPosition() == 0.5 &&*/ step == 6){
                step = 7;
                //MarkerMotor.setPower(0);
                FrontLeftMotor.setPower(1);
                FrontRightMotor.setPower(1);
                RearLeftMotor.setPower(1);
                RearRightMotor.setPower(1);
                FrontLeftMotor.setTargetPosition(rotationToTicks(3));
                FrontRightMotor.setTargetPosition(rotationToTicks(-3));
                RearLeftMotor.setTargetPosition(rotationToTicks(3));
                RearRightMotor.setTargetPosition(rotationToTicks(-3));
            }else if (!(FrontLeftMotor.isBusy()&&FrontRightMotor.isBusy()&&RearLeftMotor.isBusy()&&RearRightMotor.isBusy()) && step == 7){
                step = 8;
                FrontLeftMotor.setPower(0);
                FrontRightMotor.setPower(0);
                RearLeftMotor.setPower(0);
                RearRightMotor.setPower(0);
                FrontLeftMotor.setPower(1);
                FrontRightMotor.setPower(1);
                RearLeftMotor.setPower(1);
                RearRightMotor.setPower(1);
                FrontLeftMotor.setTargetPosition(rotationToTicks(-7));
                FrontRightMotor.setTargetPosition(rotationToTicks(-7));
                RearLeftMotor.setTargetPosition(rotationToTicks(7));
                RearRightMotor.setTargetPosition(rotationToTicks(-7));
            }else if(!(FrontLeftMotor.isBusy()&&FrontRightMotor.isBusy()&&RearLeftMotor.isBusy()&&RearRightMotor.isBusy()) && step == 8){
                step = 9;
                FrontLeftMotor.setPower(0);
                FrontRightMotor.setPower(0);
                RearLeftMotor.setPower(0);
                RearRightMotor.setPower(0);
            }else if(!(FrontLeftMotor.isBusy()&&FrontRightMotor.isBusy()&&RearLeftMotor.isBusy()&&RearRightMotor.isBusy()) && step == 9){
                step = 10;
                FrontLeftMotor.setPower(0);
                FrontRightMotor.setPower(0);
                RearLeftMotor.setPower(0);
                RearRightMotor.setPower(0);
            }

            telemetry.addData("Hook Motor Position", HookMotor.getCurrentPosition());
            telemetry.addData("Front Left Motor Position", FrontLeftMotor.getCurrentPosition());
            telemetry.addData("Front Right Motor Position", FrontRightMotor.getCurrentPosition());
            telemetry.addData("Rear Left Motor Position", RearLeftMotor.getCurrentPosition());
            telemetry.addData("Rear Right Motor Position", RearRightMotor.getCurrentPosition());
            //telemetry.addData("Marker Motor Position", MarkerMotor.getCurrentPosition());
            telemetry.addData("Hook Servo Position", Servo1.getPosition());
            telemetry.addData("Steps", step);
            telemetry.update();
            idle();
        }


        }


       /* while (opModeIsActive())
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
