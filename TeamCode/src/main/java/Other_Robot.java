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
public class Other_Robot extends LinearOpMode {
    private DcMotor FrontLeftDrive;
    private DcMotor FrontRightDrive;
    private DcMotor RearLeftDrive;
    private DcMotor RearRightDrive;
    private DcMotor HookMotorDrive;
    private double LeftJoystick;
    private double LeftJoystick2;
    private double RightJoystickY;




    @Override
    public void runOpMode() {
        FrontLeftDrive = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontRightDrive = hardwareMap.get(DcMotor.class, "FrontRight");
        RearLeftDrive = hardwareMap.get(DcMotor.class, "RearLeft");
        RearRightDrive = hardwareMap.get(DcMotor.class, "RearRight");

        //Servo1.setPosition(0);


        //Servo2 = hardwareMap.get(Servo.class, "Servo 3");
        //Servo2.setPosition(0);
        //servoTest = hardwareMap.get(Servo.class "servoTest");
//This is the right version
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver preses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            LeftJoystick  =  gamepad1.left_stick_y;
            LeftJoystick = Range.clip(LeftJoystick, -1.0, 1.0);

            FrontLeftDrive.setPower(LeftJoystick);
            FrontRightDrive.setPower(-LeftJoystick);
            RearRightDrive.setPower(-LeftJoystick);
            RearLeftDrive.setPower(LeftJoystick);

            LeftJoystick2 =  gamepad1.left_stick_x;
            LeftJoystick2 =  Range.clip(LeftJoystick2, -1.0, 1.0);

            FrontLeftDrive.setPower(LeftJoystick2);
            FrontRightDrive.setPower(LeftJoystick2);
            RearLeftDrive.setPower(LeftJoystick2);
            RearRightDrive.setPower(LeftJoystick2);

            RightJoystickY = gamepad1.right_stick_y;
            RightJoystickY = Range.clip(RightJoystickY, -1.0, 1.0);
            //HookMotorDrive.setPower(-RightJoystickY);
            //HookMotorDrive.setPower(RightJoystickY);



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



           /*if (gamepad1.a && Servo == 1) {
               Servo = 2;
               Servo1.setPosition(.5);
               telemetry.addData("Servo", Servo1.getPosition());
           }
            if (gamepad1.a && Servo == 2) {
                Servo = 3;
                Servo1.setPosition(.75);
                telemetry.addData("Servo", Servo1.getPosition());
            }
            if (gamepad1.a && Servo == 3) {
                Servo = 4;
                Servo1.setPosition(1);
                telemetry.addData("Servo", Servo1.getPosition());
            }
            if (gamepad1.a && Servo == 4) {
                Servo = 5;
                Servo1.setPosition(.75);
                telemetry.addData("Servo", Servo1.getPosition());
            }
            if (gamepad1.a && Servo == 5) {
                Servo = 6;
                Servo1.setPosition(.5);
                telemetry.addData("Servo", Servo1.getPosition());
            }
            if (gamepad1.a && Servo == 6) {
                Servo = 7;
                Servo1.setPosition(.25);
                telemetry.addData("Servo", Servo1.getPosition());
            }
            if (gamepad1.a && Servo == 7) {
                Servo = 0;
                Servo1.setPosition(0);
                telemetry.addData("Servo", Servo1.getPosition());
            }*/

            /*if (gamepad1.y) {
                Servo1.setPosition(1);

            }
            if (gamepad1.b){
                Servo1.setPosition(0);


            }*/
            telemetry.addData("Status", "Running");
            //telemetry.addData("Motor", "Running");
            telemetry.update();
        }

    }
}