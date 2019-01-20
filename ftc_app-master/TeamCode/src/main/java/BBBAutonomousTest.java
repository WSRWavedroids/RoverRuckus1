import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Sam on 1/10/2019.
 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class BBBAutonomousTest extends LinearOpMode
{
    // Declaration of variables
    DcMotor FrontLeftMotor;
    DcMotor FrontRightMotor;
    DcMotor RearLeftMotor;
    DcMotor RearRightMotor;
    DcMotor Hook;
    Servo MarkerServo;


    float step = 0;


    public @Override void runOpMode() throws InterruptedException {
        FrontLeftMotor = hardwareMap.dcMotor.get("FrontLeft");
        FrontRightMotor = hardwareMap.dcMotor.get("FrontRight");
        RearLeftMotor = hardwareMap.dcMotor.get("RearLeft");
        RearRightMotor = hardwareMap.dcMotor.get("RearRight");
        Hook = hardwareMap.dcMotor.get("Hook");
        MarkerServo = hardwareMap.servo.get("AutonomousServo");


        FrontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        FrontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        RearLeftMotor.setDirection((DcMotor.Direction.FORWARD));
        RearRightMotor.setDirection(DcMotor.Direction.REVERSE);
        Hook.setDirection(DcMotor.Direction.FORWARD);

        this.waitForStart();
    }}
//I commented this out and added the brackets above while testing the old robot's autonomous because it was giving an error -Ryan
        /*
        Hook.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Hook.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FrontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FrontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        RearLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RearLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        RearRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RearRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //testing needed to figure out how many ticks in a rotation


   /*     while (opModeIsActive())
        {
            if (step == 0) {
                step = 1;
                Hook.setPower(.3);
                Hook.setTargetPosition();
            }
            else if (!(Hook.isBusy()) && step == 1) {
                step = 2;
                // needs to crab here
            } else if (Servo1.getPosition() == 1 && step == 2) {
                step = 3;
                Hook.setTargetPosition(rotationToTicks(0)); //requires testing



        }

    }

}
*/