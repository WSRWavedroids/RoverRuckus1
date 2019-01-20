/**
 * Created by wave on 10/9/2018.
 */
package org.firstinspires.ftc.robotcontroller;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by wave on 12/9/2017.
 */
@TeleOp
public class test2 extends LinearOpMode {
    private DcMotor  FrontLeftDrive;
    private DcMotor FrontRightDrive;
    private DcMotor RearLeftDrive;
    private DcMotor RearRightDrive;
    private double LeftJoystick;
    private double LeftJoystick2;




    @Override
    public void runOpMode() {
        FrontLeftDrive = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontRightDrive = hardwareMap.get(DcMotor.class, "FrontRight");
        RearLeftDrive = hardwareMap.get(DcMotor.class, "RearLeft");
        RearRightDrive = hardwareMap.get(DcMotor.class, "RearRight");

//This is the right version
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver preses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            LeftJoystick  =  gamepad1.left_stick_y;
            LeftJoystick = Range.clip(LeftJoystick, -1.0, 1.0);
            //FrontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //FrontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //RearLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //RearRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            // RUN_WITHOUT_ENCODER other one
            FrontRightDrive.setPower(-LeftJoystick);
            FrontLeftDrive.setPower(LeftJoystick);
            RearRightDrive.setPower(-LeftJoystick);
            RearLeftDrive.setPower(LeftJoystick);

            //Turning
            LeftJoystick2 =  gamepad1.left_stick_x;
            LeftJoystick2 =  Range.clip(LeftJoystick2, -1.0, 1.0);

            FrontLeftDrive.setPower(LeftJoystick2);
            FrontRightDrive.setPower(LeftJoystick2);
            RearLeftDrive.setPower(LeftJoystick2);
            RearRightDrive.setPower(LeftJoystick2);


            //Crabbing
            if (gamepad1.dpad_left) {

                FrontLeftDrive.setPower(1);
                FrontRightDrive.setPower(1);
                RearLeftDrive.setPower(-1);
                RearRightDrive.setPower(-1);
            }
            if (gamepad1.dpad_right) {

                FrontLeftDrive.setPower(-1);
                FrontRightDrive.setPower(-1);
                RearLeftDrive.setPower(1);
                RearRightDrive.setPower(1);

            }
            //FrontLeftDrive



            /*if (gamepad1.y) {
                Servo2.setPosition(1);

            }
            if (gamepad1.b){
                Servo2.setPosition(0);


            }*/
            telemetry.addData("FL Motor", FrontLeftDrive.getMode());
            telemetry.addData("FR Motor", FrontRightDrive.getMode());
            telemetry.addData("RL Motor", RearLeftDrive.getMode());
            telemetry.addData("RR Motor", RearRightDrive.getMode());
            telemetry.addData("Status", "Running");
            //telemetry.addData("Motor", "Running");
            telemetry.update();
        }

    }
}
