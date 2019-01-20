/**
 * Created by wave on 10/13/2018.
 */

package org.firstinspires.ftc.robotcontroller;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Drive Encoder", group="Exercises")
//@Disabled
public class Encoder extends LinearOpMode
{
    private DcMotor FrontLeftDrive;
    private DcMotor FrontRightDrive;
    private DcMotor RearLeftDrive;
    private DcMotor RearRightDrive;

    @Override
    public void runOpMode() throws InterruptedException
    {
        FrontLeftDrive = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontRightDrive = hardwareMap.get(DcMotor.class, "FrontRight");
        RearLeftDrive = hardwareMap.get(DcMotor.class, "RearLeft");
        RearRightDrive = hardwareMap.get(DcMotor.class, "RearRight");


        // reset encoder count kept by left motor.
        FrontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // set left motor to run to target encoder position and stop with brakes on.
        FrontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RearLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RearRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // set right motor to run without regard to an encoder.
        //rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("Mode", "waiting");
        telemetry.update();

        // wait for start button.

        waitForStart();

        telemetry.addData("Mode", "running");
        telemetry.update();

        // set left motor to run for 5000 encoder counts.

        FrontLeftDrive.setTargetPosition(-5000);
        FrontRightDrive.setTargetPosition(-5000);
        RearLeftDrive.setTargetPosition(-5000);
        RearRightDrive.setTargetPosition(-5000);

        // set both motors to 25% power. Movement will start.

        FrontLeftDrive.setPower(-0.25);
        FrontRightDrive.setPower(-0.25);
        RearLeftDrive.setPower(-0.25);
        RearRightDrive.setPower(-0.25);

        // wait while opmode is active and left motor is busy running to position.

        while (opModeIsActive() && FrontLeftDrive.isBusy() && FrontRightDrive.isBusy() && RearLeftDrive.isBusy() && RearRightDrive.isBusy())
        {
            telemetry.addData("encoder-fwd", FrontLeftDrive.getCurrentPosition() + "  busy=" + FrontLeftDrive.isBusy());
            telemetry.addData("encoder-fwd", FrontRightDrive.getCurrentPosition() + "  busy=" + FrontRightDrive.isBusy());
            telemetry.addData("encoder-fwd", RearLeftDrive.getCurrentPosition() + "  busy=" + RearLeftDrive.isBusy());
            telemetry.addData("encoder-fwd", RearRightDrive.getCurrentPosition() + "  busy=" + RearRightDrive.isBusy());
            telemetry.update();
            idle();
        }

        // set motor power to zero to turn off motors. The motors stop on their own but
        // power is still applied so we turn off the power.

        FrontLeftDrive.setPower(0.0);
        FrontRightDrive.setPower(0.0);
        RearLeftDrive.setPower(0.0);
        RearRightDrive.setPower(0.0);

        // wait 5 sec to you can observe the final encoder position.

        resetStartTime();

        while (opModeIsActive() && getRuntime() < 5)
        {
            telemetry.addData("encoder-fwd-end", FrontLeftDrive.getCurrentPosition() + "  busy=" + FrontLeftDrive.isBusy());
            telemetry.addData("encoder-fwd-end", FrontRightDrive.getCurrentPosition() + "  busy=" + FrontRightDrive.isBusy());
            telemetry.addData("encoder-fwd-end", RearLeftDrive.getCurrentPosition() + "  busy=" + RearLeftDrive.isBusy());
            telemetry.addData("encoder-fwd-end", RearRightDrive.getCurrentPosition() + "  busy=" + RearRightDrive.isBusy());
            telemetry.update();
            idle();
        }

        // set position for back up to starting point. In this example instead of
        // having the motor monitor the encoder we will monitor the encoder ourselves.

        //leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //leftMotor.setTargetPosition(0);

      //  leftMotor.setPower(0.25);
        //rightMotor.setPower(0.25);

        //while (opModeIsActive() && leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
        {
          //  telemetry.addData("encoder-back", leftMotor.getCurrentPosition());
            //telemetry.update();
            //idle();
        }

        // set motor power to zero to stop motors.

        //leftMotor.setPower(0.0);
        //rightMotor.setPower(0.0);

        resetStartTime();

      //  while (opModeIsActive() && getRuntime() < 5)
        {
        //    telemetry.addData("encoder-back-end", leftMotor.getCurrentPosition());
          //  telemetry.update();
            //idle();
        }
    } }

    /**
     * Created by wave on 10/13/2018.
     */

