/**
 * Created by wave on 2/3/2019.
 */

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

/**
 * Created by wave on 1/05/2018.
 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class BBBAutonomousEncoder extends LinearOpMode{
    private DcMotor FrontLeftDrive;
    private DcMotor FrontRightDrive;
    private DcMotor RearLeftDrive;
    private DcMotor RearRightDrive;
    private DcMotor HookMotorDrive;
    private Servo MarkerServo;
    private ElapsedTime runtime = new ElapsedTime();

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private static final String VUFORIA_KEY = "AUGCzyb/////AAABmS8CT2H2FkTzujFC8kN8GNN3df2+RztS4zMcUUVRSv9N1nav9OE9adzM8XmgacYvvx3tmYn2KzpDfvCJHaBdaZKjkql7qJuChviWVQltT5B52N5FQiwLQlw0ydmcWc+jzYsEYqKhR/AZZI3/Rv0gXG9KnFPRoThTmkQQy5MYz2QmLTMDyYIF3vzahuenRzcrim2JfoymeXh4TNg+cIezwiekR6J5J8ohi1nQ6TMWHX3/heQuMLygyLszJ1iccOTmwCgV687oGCq1Cw/KLRvP9Q9huLj/WP98+zxtlX6Z/T+Nzk51gPb90ZdBXKNoCdgP5/8rlx2gIL2x3R/Tkc+JGlVGfx9/OA1oWKeVFg40xZsK";

    private VuforiaLocalizer vuforia;

    private TFObjectDetector tfod;

    int MineralPosition = -1;
    double MineralConfidence = 0;

    /*final*/ long start = System.nanoTime();
    double time = 0;
    int step = 0;

    public @Override
    void runOpMode() throws InterruptedException {

        FrontLeftDrive = hardwareMap.dcMotor.get("FrontLeft");
        FrontRightDrive =  hardwareMap.dcMotor.get("FrontRight");
        RearLeftDrive = hardwareMap.dcMotor.get("RearLeft");
        RearRightDrive = hardwareMap.dcMotor.get("RearRight");
        HookMotorDrive = hardwareMap.dcMotor.get("Hook");
        MarkerServo = hardwareMap.servo.get("AutonomousServo");

        initVuforia();

        this.waitForStart();
        if (opModeIsActive()) {
            //Direction
            //Direction Setting
            FrontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
            FrontRightDrive.setDirection(DcMotor.Direction.FORWARD);
            RearLeftDrive.setDirection((DcMotor.Direction.REVERSE));
            RearRightDrive.setDirection(DcMotor.Direction.FORWARD);
            HookMotorDrive.setDirection(DcMotor.Direction.FORWARD);


            //Zero Power Behavior
            FrontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            FrontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            RearLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            RearRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            //Encoder Code
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


            if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
                initTfod();
            } else {
                telemetry.addData("Sorry!", "This device is not compatible with TFOD");
            }

            /** Wait for the game to begin */
            telemetry.addData(">", "Press Play to start tracking");
            telemetry.update();
            waitForStart();

            findMineral();
            if (MineralPosition == -1) {
                MineralPosition = 1;
            }
            MarkerServo.setPosition(.5);
            runtime.reset();
            //Main Code
            //**************************************************************************************************************************
            //**************************************************************************************************************************
            //**************************************************************************************************************************
            //**************************************************************************************************************************
            while (opModeIsActive()) {
                //drop from lander
                if (step == 0 && !isMotorBusy(5)) {
                    HookMotorDrive.setTargetPosition(HookMotorDrive.getCurrentPosition() - 2962);
                    HookMotorDrive.setPower(1);
                    step = 1;
                } else if (step == 1 && !isMotorBusy(3)) {
                    HookMotorDrive.setPower(0);
                    encoderDriveCrab(-30, 2, .5);
                    step = 2;
                } else if (step == 2 && !isMotorBusy(1.5)) {
                    encoderDriveForward(-50, 1.5, .1);
                    step = 3;
                }
                if (step >= 3) {
                    if (MineralPosition == 1) {
                        if (step == 3 && !isMotorBusy(2)) {
                            encoderDriveCrab(-30, 2, .5);
                            step = 4;
                        }

                    } else if (MineralPosition == 2) {
                        if (step == 3 && !isMotorBusy(2)) {
                            step = 4;
                        }

                    } else if (MineralPosition == 3) {
                        if (step == 3 && !isMotorBusy(2)) {
                        }
                    }
                }

                telemetry.addData("Position", MineralPosition);
                telemetry.addData("Servo", MarkerServo.getPosition());
                telemetry.addData("confidence", (MineralConfidence * 100) + '%');
                telemetry.addData("Hook Motor Power", HookMotorDrive.getPower());
                telemetry.addData("Hook Motor Position", HookMotorDrive.getCurrentPosition());
                telemetry.addData("Front Left Motor Power", FrontLeftDrive.getPower());
                telemetry.addData("Front Right Motor Power", FrontRightDrive.getPower());
                telemetry.addData("Rear Left Motor Power", RearLeftDrive.getPower());
                telemetry.addData("Rear Right Motor Power", RearRightDrive.getPower());
                telemetry.addData("step", step);
                //telemetry.addData("Steps", step);
                //telemetry.addData("Start", start);
                //telemetry.addData("End", end);
                telemetry.addData("Time", time);
                telemetry.update();
                idle();

            }
        }
    }

    private boolean isMotorBusy(double limit){
        if ((HookMotorDrive.isBusy() || FrontRightDrive.isBusy() || FrontLeftDrive.isBusy() || RearRightDrive.isBusy() || RearLeftDrive.isBusy())
                && runtime.seconds() < limit){
            return true;
        }else{
            return false;
        }
    }
    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = .50;//.40;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    public void encoderDriveForward(int ticks, double timeout, double power) {
        int FrontLeftDrivenewTarget;
        int RearLeftDrivenewTarget;
        int FrontRightDrivenewTarget;
        int RearRightDrivenewTarget;
        runtime.reset();
        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            FrontLeftDrivenewTarget = FrontLeftDrive.getCurrentPosition() + ticks;
            RearLeftDrivenewTarget = RearLeftDrive.getCurrentPosition() + ticks;
            FrontRightDrivenewTarget = FrontRightDrive.getCurrentPosition() + ticks;
            RearRightDrivenewTarget = RearRightDrive.getCurrentPosition() + ticks;

            FrontLeftDrive.setTargetPosition(FrontLeftDrivenewTarget);
            RearLeftDrive.setTargetPosition(RearLeftDrivenewTarget);
            FrontRightDrive.setTargetPosition(FrontRightDrivenewTarget);
            RearRightDrive.setTargetPosition(RearRightDrivenewTarget);

            // Turn On RUN_TO_POSITION
            FrontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RearLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RearRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //Set Power
            FrontLeftDrive.setPower(power);
            FrontRightDrive.setPower(power);
            RearLeftDrive.setPower(power);
            RearRightDrive.setPower(power);


            //Wait until it is done moving
            while (opModeIsActive() &&  isMotorBusy(timeout)) {
                telemetry.addData("Position", MineralPosition);
                telemetry.addData("Servo", MarkerServo.getPosition());
                telemetry.addData("confidence", (MineralConfidence * 100) + '%');
                telemetry.addData("Hook Motor Power",HookMotorDrive.getPower());
                telemetry.addData("Hook Motor Position",HookMotorDrive.getCurrentPosition());
                telemetry.addData("Front Left Motor Power",FrontLeftDrive.getPower());
                telemetry.addData("Front Right Motor Power",FrontRightDrive.getPower());
                telemetry.addData("Rear Left Motor Power",RearLeftDrive.getPower());
                telemetry.addData("Rear Right Motor Power",RearRightDrive.getPower());
                telemetry.addData("step", step);

                telemetry.update();
            }

            // Stop all motion;
            FrontLeftDrive.setPower(0);
            FrontRightDrive.setPower(0);
            RearLeftDrive.setPower(0);
            RearRightDrive.setPower(0);

            // Turn off RUN_TO_POSITION
            FrontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //  sleep(250);   // optional pause after each move
        }
    }
    public void encoderDriveCrab(int ticks, double timeout, double power) {
        int FrontLeftDrivenewTarget;
        int RearLeftDrivenewTarget;
        int FrontRightDrivenewTarget;
        int RearRightDrivenewTarget;
        runtime.reset();
        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            FrontLeftDrivenewTarget = FrontLeftDrive.getCurrentPosition() - ticks;
            RearLeftDrivenewTarget = RearLeftDrive.getCurrentPosition() + ticks;
            FrontRightDrivenewTarget = FrontRightDrive.getCurrentPosition() + ticks;
            RearRightDrivenewTarget = RearRightDrive.getCurrentPosition() - ticks;

            FrontLeftDrive.setTargetPosition(FrontLeftDrivenewTarget);
            RearLeftDrive.setTargetPosition(RearLeftDrivenewTarget);
            FrontRightDrive.setTargetPosition(FrontRightDrivenewTarget);
            RearRightDrive.setTargetPosition(RearRightDrivenewTarget);

            // Turn On RUN_TO_POSITION
            FrontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RearLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RearRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //Set Power
            FrontLeftDrive.setPower(power);
            FrontRightDrive.setPower(power);
            RearLeftDrive.setPower(power);
            RearRightDrive.setPower(power);


            //Wait until it is done moving
            while (opModeIsActive() && isMotorBusy(timeout)) {
                telemetry.addData("Position", MineralPosition);
                telemetry.addData("Servo", MarkerServo.getPosition());
                telemetry.addData("confidence", (MineralConfidence * 100) + '%');
                telemetry.addData("Hook Motor Power", HookMotorDrive.getPower());
                telemetry.addData("Hook Motor Position", HookMotorDrive.getCurrentPosition());
                telemetry.addData("Front Left Motor Power", FrontLeftDrive.getPower());
                telemetry.addData("Front Right Motor Power", FrontRightDrive.getPower());
                telemetry.addData("Rear Left Motor Power", RearLeftDrive.getPower());
                telemetry.addData("Rear Right Motor Power", RearRightDrive.getPower());
                telemetry.addData("step", step);
                telemetry.addData("crabbing", "crabbing");
                telemetry.update();
            }

            // Stop all motion;
            FrontLeftDrive.setPower(0);
            FrontRightDrive.setPower(0);
            RearLeftDrive.setPower(0);
            RearRightDrive.setPower(0);

            // Turn off RUN_TO_POSITION
            FrontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //  sleep(250);   // optional pause after each move
        }
    }

    void findMineral () {
        runtime.reset();
        if (opModeIsActive()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }
            while ((opModeIsActive() && runtime.seconds() < 5)|| MineralPosition == -1) { //try to get it for 5 seconds
                if (tfod != null && MineralPosition == -1) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.-
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        int goldMineralX = -1;
                        for (Recognition recognition : updatedRecognitions) {
                            if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                goldMineralX = (int) recognition.getLeft();
                                if (recognition.getConfidence() > MineralConfidence) {
                                    MineralConfidence = recognition.getConfidence();
                                }
                            }
                        }

                        if (goldMineralX != -1) {
                            if (goldMineralX < 100) {
                                telemetry.addData("Gold Mineral Position", "Right");
                                MineralPosition = 3;
                            } else if (goldMineralX < 600) {
                                telemetry.addData("Gold Mineral Position", "Center");
                                MineralPosition = 2;
                            } else {
                                telemetry.addData("Gold Mineral Position", "Left");
                                MineralPosition = 1;
                            }
                        }
                    }
                }
            }
        }
    }
}
