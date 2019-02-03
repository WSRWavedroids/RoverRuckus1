/**
 * Created by wave on 2/3/2019.
 */

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
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

        //Zero Power Behavior
        FrontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RearLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RearRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }
            for (int i = 0; i < 10; i++) {
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
            if (MineralPosition == -1){
                MineralPosition = 1;
            }
            MarkerServo.setPosition(.5);

            //Main Code
            while (opModeIsActive()) {
                //drop from lander
                if (step == 0 && !isMotorBusy(HookMotorDrive, FrontLeftDrive, FrontRightDrive, RearLeftDrive, RearRightDrive)){
                    HookMotorDrive.setPower(1);
                    HookMotorDrive.setTargetPosition(HookMotorDrive.getCurrentPosition() - 2962);
                    step = 1;

                }
                telemetry.addData("Position", MineralPosition);
                telemetry.addData("Servo", MarkerServo.getPosition());
                telemetry.addData("confidence", (MineralConfidence * 100) + '%');
                telemetry.addData("Hook Motor Power",HookMotorDrive.getPower());
                telemetry.addData("Hook Motor Position",HookMotorDrive.getCurrentPosition());
                telemetry.addData("Front Left Motor Power",FrontLeftDrive.getPower());
                telemetry.addData("Front Right Motor Power",FrontRightDrive.getPower());
                telemetry.addData("Rear Left Motor Power",RearLeftDrive.getPower());
                telemetry.addData("Rear Right Motor Power",RearRightDrive.getPower());
                //telemetry.addData("Steps", step);
                //telemetry.addData("Start", start);
                //telemetry.addData("End", end);
                telemetry.addData("Time", time);
                telemetry.update();
                idle();

            }


        }

    }

    private boolean isMotorBusy(DcMotor a, DcMotor b, DcMotor c, DcMotor d, DcMotor e){
        if (a.isBusy() || b.isBusy() || c.isBusy() || e.isBusy() || d.isBusy()){
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
}
