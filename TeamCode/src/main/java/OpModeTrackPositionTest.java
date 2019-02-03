import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by wave on 1/11/2019.
 */
@TeleOp
public class OpModeTrackPositionTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        // Hardware mapping
        DcMotor FrontLeftDrive = hardwareMap.get(DcMotor.class, "FrontLeft");
        DcMotor FrontRightDrive = hardwareMap.get(DcMotor.class, "FrontRight");
        DcMotor RearLeftDrive = hardwareMap.get(DcMotor.class, "RearLeft");
        DcMotor RearRightDrive = hardwareMap.get(DcMotor.class, "RearRight");
        DcMotor HookMotorDrive = hardwareMap.dcMotor.get("Hook");
        Servo MarkerServo = hardwareMap.servo.get("AutonomousServo");

        //Variables for loop below
        double LeftJoystick;
        double RightJoystickY;
        double ServoPosition = 0;
        double LeftJoystick2;

        //Variables for position Tracking
        int FrontLeftPosition = 0;
        int FrontRightPosition = 0;
        int RearLeftPosition = 0;
        int RearRightPosition = 0;
        int hookmotorPostion = 0;
        double MarkerServoPosition = 0;

        //Wait for the program to start
        waitForStart();

        while (opModeIsActive()) {
            //set joystick variables
            LeftJoystick = Range.clip(gamepad1.left_stick_y, -1.0, 1.0);
            LeftJoystick2 =  Range.clip(gamepad1.left_stick_x, -1.0, 1.0);
            RightJoystickY = Range.clip(gamepad1.right_stick_y, -1.0, 1.0);

            //Move forward
            FrontLeftDrive.setPower(LeftJoystick);
            FrontRightDrive.setPower(-LeftJoystick);
            RearRightDrive.setPower(-LeftJoystick);
            RearLeftDrive.setPower(LeftJoystick);

            //Turn
            FrontLeftDrive.setPower(LeftJoystick2);
            FrontRightDrive.setPower(LeftJoystick2);
            RearLeftDrive.setPower(LeftJoystick2);
            RearRightDrive.setPower(LeftJoystick2);





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

            if (gamepad1.dpad_up) {

                HookMotorDrive.setPower(1);

            }else if (gamepad1.dpad_down) {

                HookMotorDrive.setPower(-1);

            }else{
                HookMotorDrive.setPower(0);
            }

            if (gamepad1.a ) {
                ServoPosition = ServoPosition + 0.05;
                MarkerServo.setPosition(ServoPosition);
            }
            if (gamepad1.b){
                ServoPosition = ServoPosition - 0.05;
                MarkerServo.setPosition(ServoPosition);
            }

            //Blank Positions to zero
            if (gamepad1.y){
                FrontLeftPosition = FrontLeftDrive.getCurrentPosition();
                FrontRightPosition = FrontRightDrive.getCurrentPosition();
                RearLeftPosition = RearLeftDrive.getCurrentPosition();
                RearRightPosition = RearRightDrive.getCurrentPosition();
                MarkerServoPosition = MarkerServo.getPosition();
                hookmotorPostion = HookMotorDrive.getCurrentPosition();
            }
            //get motor positions

            telemetry.addData("FrontLeftDrive", FrontLeftPosition - FrontLeftDrive.getCurrentPosition());
            telemetry.addData("FrontRightDrive", FrontRightPosition - FrontRightDrive.getCurrentPosition());
            telemetry.addData("RearLeftDrive", RearLeftPosition - RearLeftDrive.getCurrentPosition());
            telemetry.addData("RearRightDrive", RearRightPosition - RearRightDrive.getCurrentPosition());
            telemetry.addData("HookMotor",      hookmotorPostion - HookMotorDrive.getCurrentPosition());
            telemetry.addData("Servo", MarkerServoPosition - MarkerServo.getPosition());
            telemetry.update();

        }
    }

}
