import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by wave on 10/16/2018.
 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class Autonomous2 extends LinearOpMode {
    DcMotor FrontLeftMotor;
    DcMotor FrontRightMotor;
    DcMotor RearLeftMotor;
    DcMotor RearRightMotor;
    DcMotor HookMotor;
    DcMotor MarkerMotor;
    Servo Servo1;

    final long msStraight   = 2000;
    final long msTurn       = 1000;
    final double drivePower = 1.0;
    final int  numSides     = 4;
    final long start = System.nanoTime();
    //counter.countPrimes(10000)
    long end = 0;
    double time = 0;
    public @Override void runOpMode() throws InterruptedException
    {
        FrontLeftMotor   = hardwareMap.dcMotor.get("FrontLeft");
        FrontRightMotor  = hardwareMap.dcMotor.get("FrontRight");
        RearLeftMotor    = hardwareMap.dcMotor.get("RearLeft");
        RearRightMotor    = hardwareMap.dcMotor.get("RearRight");
        MarkerMotor = hardwareMap.dcMotor.get("Marker");
        HookMotor = hardwareMap.dcMotor.get("Hook");
        Servo1 = hardwareMap.servo.get("Servo 0");

        FrontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        FrontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        RearLeftMotor.setDirection((DcMotor.Direction.FORWARD));
        RearRightMotor.setDirection(DcMotor.Direction.REVERSE);
        HookMotor.setDirection(DcMotor.Direction.FORWARD);

        this.waitForStart();

        /*FrontLeftMotor.setPower(1);
        FrontRightMotor.setPower(1);
        RearLeftMotor.setPower(1);
        RearRightMotor.setPower(1);
        */
        time = 0;



        while (opModeIsActive())
        {

            end = System.nanoTime();
            time = end-start/1000000000;

            telemetry.addData("Seconds", time);
            telemetry.update();

            if(time < 2)
            {
                HookMotor.setPower(1);
            }
            else if(time < 4)
            {
                HookMotor.setPower(0);
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
          /*  else if(time < 13)
            {
                MarkerServo.setPower(.7);
            }
            else if(time < 15)
            {
                MarkerServo.setPower(0);
            }*/





        }
    }

        /*for (int i = 0; i < numSides; i++)
        {
            goStraight();
            Thread.sleep(msStraight);

            turnLeft();
            Thread.sleep(msTurn);
        }

        stopRobot();
    }

   /* void goStraight()
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
    }
    */
}
