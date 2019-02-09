//package org.firstinspires.ftc.robotcontroller;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


/**
 * Copied by Sam on 12/29/2018; printed as of 1/6/2019
 */
@TeleOp
public class BalanceBoartBotV2 extends LinearOpMode {

    private DcMotor FrontLeftDrive;
    private DcMotor FrontRightDrive;
    private DcMotor RearLeftDrive;
    private DcMotor RearRightDrive;
    private DcMotor ArmRotatorMotorDrive;
    private DcMotor ArmExtenderMotorDrive;
    private DcMotor HookMotorDrive;

    private Servo MarkerServo;
    private Servo WristServo;
    private CRServo Sweeper;
    private CRServo OtherSweeper;


    private double LeftJoystickY;
    private double LeftJoystickX;
    private double RightJoystickY;
    private double RightJoystickX;



    /////////////////////////////////////////////////////////////
    int Servo = 0;
    double ServoPosition = 0;
    int ArmRotations;
    int ServoToggle = 0;
    int WristServoToggle = 0;
    //private Servo Servo2;
    int Ryan = 0; //what does this do?
    /////////////////////////////////////////////////////////////


    @Override
    public void runOpMode() {
        FrontLeftDrive = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontRightDrive = hardwareMap.get(DcMotor.class, "FrontRight");
        RearLeftDrive = hardwareMap.get(DcMotor.class, "RearLeft");
        RearRightDrive = hardwareMap.get(DcMotor.class, "RearRight");

        ArmRotatorMotorDrive = hardwareMap.get(DcMotor.class, "ArmRotator");
        ArmExtenderMotorDrive = hardwareMap.get(DcMotor.class, "ArmExtender");
        HookMotorDrive = hardwareMap.get(DcMotor.class, "Hook");


        MarkerServo = hardwareMap.get(Servo.class, "AutonomousServo");
        Sweeper = hardwareMap.get(CRServo.class, "Sweeper");
        OtherSweeper = hardwareMap.get(CRServo.class, "OtherSweeper");
        WristServo = hardwareMap.get(Servo.class, "WristServo");


        ///////////////////////////////////////////////////////
        //Servo1.setPosition(0);
        ///////////////////////////////////////////////////////



        ArmRotatorMotorDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ArmRotatorMotorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        ///////////////////////////////////////////////////////
        //Servo2 = hardwareMap.get(Servo.class, "Servo 3");
        //Servo2.setPosition(0);
        //servoTest = hardwareMap.get(Servo.class "servoTest");
        //This is the right version
        ///////////////////////////////////////////////////////


        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver preses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            LeftJoystickY = gamepad1.left_stick_y;
            LeftJoystickY = Range.clip(LeftJoystickY, -1.0, 1.0);

            LeftJoystickX = gamepad1.left_stick_x;
            LeftJoystickX = Range.clip(LeftJoystickX, -1.0, 1.0);

            RightJoystickY = gamepad1.right_stick_y;
            RightJoystickY = Range.clip(RightJoystickY, -1.0, 1.0);

            RightJoystickX = gamepad1.right_stick_x;
            RightJoystickX = Range.clip(RightJoystickX, -1.0, 1.0);

            double tangent = 0;
            double power = 0;
            tangent = LeftJoystickY/LeftJoystickX;
            double extenderPower = 0;
            int rotatorPosition = 0;

            //these three
            double RightJoystickTimesConstant = 0;
            int RightJoystickInt = 0;
            ArmRotatorMotorDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);





            ////////////////////////////////////////////////////////////////
            /*if (gamepad1.a && ServoToggle == 0) {

                Sweeper.setPower(2);
                OtherSweeper.setPower(-2);
                ServoToggle = 1;

            }

            if (gamepad1.a && ServoToggle == 1) {

                Sweeper.setPower(-2);
                OtherSweeper.setPower(2);
                ServoToggle = 0;

            }

            if (gamepad1.b) {

                Sweeper.setPower(0);
                OtherSweeper.setPower(0);

            }*/
            //////////////////////////////////////////////////////


            if (gamepad1.a) {
                Sweeper.setPower(2);
                OtherSweeper.setPower(-2);
            }
            else if (gamepad1.b) {
                Sweeper.setPower(0);
                OtherSweeper.setPower(0);
            }
            else {
                Sweeper.setPower(-2);
                OtherSweeper.setPower(2);
            }



            if (LeftJoystickX == 0 || Math.abs(tangent) >= 1) { //controls whether the code registers forward/backward or left/right

                if (LeftJoystickY >= 0) {
                    power = .5*LeftJoystickY*LeftJoystickY+LeftJoystickX*LeftJoystickX;
                }
                else {
                    power = -.5*(LeftJoystickY*LeftJoystickY+LeftJoystickX*LeftJoystickX);
                }

                FrontLeftDrive.setPower(power);
                FrontRightDrive.setPower(-power);
                RearRightDrive.setPower(-power);
                RearLeftDrive.setPower(power);


                telemetry.addData("Front Left Motor Position", FrontLeftDrive.getCurrentPosition());
                telemetry.addData("Front Right Motor Position", FrontRightDrive.getCurrentPosition());
                telemetry.addData("Rear Left Motor Position", RearLeftDrive.getCurrentPosition());
                telemetry.addData("Rear Right Motor Position", RearRightDrive.getCurrentPosition());

            }
            else {

                if (gamepad1.left_trigger > .1) {

                    FrontLeftDrive.setPower(-LeftJoystickX);
                    FrontRightDrive.setPower(-LeftJoystickX);
                    RearLeftDrive.setPower(LeftJoystickX);
                    RearRightDrive.setPower(LeftJoystickX);

                }
                else {

                    power = .5*LeftJoystickX;
                    FrontLeftDrive.setPower(power);
                    FrontRightDrive.setPower(power);
                    RearRightDrive.setPower(power);
                    RearLeftDrive.setPower(power);

                }

            }


            if (gamepad1.dpad_up) {

                HookMotorDrive.setPower(.5);

            }
            else if (gamepad1.dpad_down) {

                HookMotorDrive.setPower(-.5);

            }
            else {

                HookMotorDrive.setPower(0);

            }

            if (Math.abs(RightJoystickY) > .1) {

                extenderPower = -.6*Math.abs(RightJoystickY)*RightJoystickY;
                ArmExtenderMotorDrive.setPower(extenderPower);

            }
            else {

                ArmExtenderMotorDrive.setPower(0);

            }


            ///////////////////////////////////////////////////////////////
            /*if (gamepad1.right_trigger > .1 && WristServoToggle == 0){
                WristServoToggle = 1;
            }
            if(WristServoToggle == 1)    {
                WristServo.setPosition(1);
            }
            if (gamepad1.right_trigger > .1 && WristServoToggle == 1){
                WristServoToggle = 0;
            }
            if(WristServoToggle == 0){
                WristServo.setPosition(-1);
            }*/
            //////////////////////////////////////////////////////////////


            if (gamepad1.right_trigger > .1) {
                WristServo.setPosition(1);
            }
            else {
                WristServo.setPosition(0);
            }

            if (Math.abs(RightJoystickX) > .1) {

                // 1440 ticks per rotation

                //this code

                /*
                if (RightJoystickX > .1) {
                    if (ArmRotations < 0) {
                        ArmRotations = ArmRotations + 1;
                        ArmRotatorMotorDrive.setTargetPosition((ArmRotations));
                        ArmRotatorMotorDrive.setPower(-.25);
                    }
                }
                else {
                    if (ArmRotations > -360) {
                        ArmRotations = ArmRotations - 1;
                        ArmRotatorMotorDrive.setTargetPosition((ArmRotations));
                        ArmRotatorMotorDrive.setPower(.25);
                    }
                }
                */

                //this code worked, and now I'm not sure why it doesn't, so I'm trying what enrique recommended
                ///*
                    RightJoystickTimesConstant = 10 * RightJoystickX;
                    RightJoystickInt = (int) RightJoystickTimesConstant;
                    rotatorPosition = rotatorPosition + RightJoystickInt;
                    ///*
                    telemetry.addData("Right Joystick Int", RightJoystickInt);
                    telemetry.addData("Right Joystick", RightJoystickX);
                    telemetry.addData("Rotator Position", rotatorPosition);
                    telemetry.update();
                    //*/
                    ArmRotatorMotorDrive.setTargetPosition((RightJoystickInt)); // change back to rotatorPosition if it doesn't work
                    ArmRotatorMotorDrive.setPower(.25);
                //*/





                /*
                RightJoystickTimesConstant = -10 * RightJoystickX;
                RightJoystickInt = (int) RightJoystickTimesConstant;
                rotatorPosition = rotatorPosition + RightJoystickInt;
                telemetry.addData("Right Joystick", RightJoystickX);
                telemetry.addData("Rotator Position", rotatorPosition);
                telemetry.addData("Right Joystick Int", RightJoystickInt);
                telemetry.update();

                ArmRotatorMotorDrive.setTargetPosition((rotatorPosition * 10));
                ArmRotatorMotorDrive.setPower(.25);
                */



                /*ArmRotatorMotorDrive.setTargetPosition(rotatorPosition);
                if (rotatorPosition < 56) {
                    ArmRotatorMotorDrive.setPower(-.75);
                }
                else if (rotatorPosition < 112) {
                    ArmRotatorMotorDrive.setPower(-.5);
                }
                else {
                    ArmRotatorMotorDrive.setPower(-.3);
                }*/

            }
            else {
                ArmRotatorMotorDrive.setPower(0);
            }




           /*if (gamepad1.a ) {
                ServoPosition = ServoPosition + 0.05;
               Servo1.setPosition(ServoPosition);
               telemetry.addData("Servo", Servo1.getPosition());
           }
           if (gamepad1.b){
                ServoPosition = ServoPosition - 0.05;
                Servo1.setPosition(ServoPosition);
                telemetry.addData("Servo", Servo1.getPosition());
           }*/

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
