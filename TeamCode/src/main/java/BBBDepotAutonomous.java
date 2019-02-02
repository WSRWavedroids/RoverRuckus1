import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
public class BBBDepotAutonomous extends LinearOpMode {
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

    int MineralPosition = 0;
    double MineralConfidence = 0;

    int MarkerDrop = 0;

    int Craboolen = 0;

    //Old Methods and Teleop Outputs
    //private DcMotor ArmRotatorMotorDrive;
    //private DcMotor ArmExtenderMotorDrive;
    //private DcMotor ArmWristMotorDrive;
    //private double LeftJoystick;
    //private double LeftJoystick2;
    //private double RightJoystickY;
    //private Servo ContinuousSweeper;

    //final long msStraight   = 2000;
    //final long msTurn       = 1000;
    //final double drivePower = 1.0;
    //final int  numSides     = 4;
    // counter.countPrimes(10000)


    /*final*/ long start = System.nanoTime();
    double time = 0;
    int step = 0;

    public @Override
    void runOpMode() throws InterruptedException {
        FrontLeftDrive = hardwareMap.dcMotor.get("FrontLeft");
        FrontRightDrive = hardwareMap.dcMotor.get("FrontRight");
        RearLeftDrive = hardwareMap.dcMotor.get("RearLeft");
        RearRightDrive = hardwareMap.dcMotor.get("RearRight");
        MarkerServo = hardwareMap.servo.get("AutonomousServo");
        HookMotorDrive = hardwareMap.dcMotor.get("Hook");

        initVuforia();

        this.waitForStart();

        //Direction Setting
        FrontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        FrontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        RearLeftDrive.setDirection((DcMotor.Direction.REVERSE));
        RearRightDrive.setDirection(DcMotor.Direction.FORWARD);
        HookMotorDrive.setDirection(DcMotor.Direction.REVERSE);

        //Encoder Code
        /*HookMotorDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        HookMotorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RearLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RearRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);*/



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


            //Main Code
        while (opModeIsActive()) {

            if (tfod != null && time < 3) {
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

                    if (goldMineralX != -1 ) {
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
                    } else {
                        telemetry.addData("Gold Mineral Position", "Left");
                        MineralPosition = 1;
                    }

                }
            }
            //Lower Latch
            else if (time < 4.75) {
                HookMotorDrive.setPower(0.3);
                tfod.shutdown();

            }
            //Stop unlatching
            else if (time < 7) {
                HookMotorDrive.setPower(0);
            }
            //Crab Away
            else if (time < 7.75) {

                    FrontLeftDrive.setPower(0.5);
                    FrontRightDrive.setPower(-0.5);
                    RearLeftDrive.setPower(-0.5);
                    RearRightDrive.setPower(0.5);
            }
            //Stop
            else if (time < 8) {
                FrontLeftDrive.setPower(0);
                FrontRightDrive.setPower(0);
                RearLeftDrive.setPower(0);
                RearRightDrive.setPower(0);
            }
            //Drive Forwards
            else if(time < 8.3){
                FrontLeftDrive.setPower(-0.375);
                FrontRightDrive.setPower(-0.375);
                RearLeftDrive.setPower(-0.375);
                RearRightDrive.setPower(-0.375);
            }
            //Stop
            else if (time < 9) {
                FrontLeftDrive.setPower(0);
                FrontRightDrive.setPower(0);
                RearLeftDrive.setPower(0);
                RearRightDrive.setPower(0);
            }
            //Mineral Steps
            else{
                if(MineralPosition == 1){ //Left

                    //Crab Left
                     if(time < 11){
                        if( ((int)(time * 10))%2 == 0) {
                            FrontLeftDrive.setPower(1);
                            FrontRightDrive.setPower(-1);
                            RearLeftDrive.setPower(-1);
                            RearRightDrive.setPower(1);
                        }else{
                            FrontLeftDrive.setPower(0);
                            FrontRightDrive.setPower(0);
                            RearLeftDrive.setPower(0);
                            RearRightDrive.setPower(0);
                        }
                    }
                    //Stop
                    else if (time < 15){
                        FrontLeftDrive.setPower(0);
                        FrontRightDrive.setPower(0);
                        RearLeftDrive.setPower(0);
                        RearRightDrive.setPower(0);
                    }
                    //Drive Forwards
                    else if (time < 16.5){
                        FrontLeftDrive.setPower(-0.375);
                        FrontRightDrive.setPower(-0.375);
                        RearLeftDrive.setPower(-0.375);
                        RearRightDrive.setPower(-0.375);
                    }
                    //Stop
                    else if (time < 17){
                        FrontLeftDrive.setPower(0);
                        FrontRightDrive.setPower(0);
                        RearLeftDrive.setPower(0);
                        RearRightDrive.setPower(0);
                    }
                    //Turn
                    else if(time < 18.75){
                        FrontLeftDrive.setPower(0.375);
                        FrontRightDrive.setPower(-0.375);
                        RearLeftDrive.setPower(0.375);
                        RearRightDrive.setPower(-0.375);
                    }
                    //Stop
                    else if (time < 21){
                        FrontLeftDrive.setPower(0);
                        FrontRightDrive.setPower(0);
                        RearLeftDrive.setPower(0);
                        RearRightDrive.setPower(0);
                    }


                }
                else if(MineralPosition == 2){ // Center

                    FrontLeftDrive.setPower(-0.375);
                    FrontRightDrive.setPower(-0.375);
                    RearLeftDrive.setPower(-0.375);
                    RearRightDrive.setPower(-0.375);

                }else if(MineralPosition ==3){ //Right
                    FrontLeftDrive.setPower(-0.5);
                    FrontRightDrive.setPower(0.5);
                    RearLeftDrive.setPower(0.5);
                    RearRightDrive.setPower(-0.5);
                }
            }

            //Drop Marker
            if (time > 21 && time < 20){
                MarkerServo.setPosition(0);
            } else {
                MarkerServo.setPosition(.5);
            }

            //Time System
            long end = System.nanoTime();
            time = (end - start)/1000000000.0;


            //Telemetry
            //telemetry.addData("Front Left Motor Position", FrontLeftDrive.getCurrentPosition());
            //telemetry.addData("Front Right Motor Position", FrontRightDrive.getCurrentPosition());
            //telemetry.addData("Rear Left Motor Position", RearLeftDrive.getCurrentPosition());
            //telemetry.addData("Rear Right Motor Position", RearRightDrive.getCurrentPosition());
            //telemetry.addData("Hook Motor Position", HookMotorDrive.getCurrentPosition());
            telemetry.addData("Position", MineralPosition);
            telemetry.addData("confidence", (MineralConfidence * 100) + '%');
            telemetry.addData("Hook Motor Power",HookMotorDrive.getPower());
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
        tfodParameters.minimumConfidence = .75;//.40;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}

//Old Code and Encoder Code
    //Encoder Code

     /*while (opModeIsActive())
     {
     if (step == 0) {
     step = 1;
     HookMotorDrive.setPower(1);
     HookMotorDrive.setTargetPosition(rotationToTicks(5));
     } else if (!(HookMotorDrive.isBusy()) && step == 1) {
     step = 2;
     HookMotorDrive.setPower(0);
     FrontLeftDrive.setPower(0.1);
     FrontRightDrive.setPower(0.1);
     RearLeftDrive.setPower(0.1);
     RearRightDrive.setPower(0.1);
     FrontLeftDrive.setTargetPosition(rotationToTicks(-1));
     FrontRightDrive.setTargetPosition(rotationToTicks(1));
     RearLeftDrive.setTargetPosition(rotationToTicks(-1));
     RearRightDrive.setTargetPosition(rotationToTicks(1));
     } else if (!(FrontLeftDrive.isBusy() && FrontRightDrive.isBusy() && RearLeftDrive.isBusy() && RearRightDrive.isBusy()) && step == 2) {
     step = 3;
     HookMotorDrive.setTargetPosition(rotationToTicks(-5));
     } if (!HookMotorDrive.isBusy() && step == 3) {
     step = 4;
     FrontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     FrontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
     FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     FrontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
     RearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     RearLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
     RearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     RearRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
     FrontLeftDrive.setPower(0.1);
     FrontRightDrive.setPower(0.1);
     RearLeftDrive.setPower(0.1);
     RearRightDrive.setPower(0.1);
     FrontLeftDrive.setTargetPosition(rotationToTicks(-13));
     FrontRightDrive.setTargetPosition(rotationToTicks(-13));
     RearLeftDrive.setTargetPosition(rotationToTicks(-13));
     RearRightDrive.setTargetPosition(rotationToTicks(-13));


     }
     else if (-450 > FrontLeftDrive.getCurrentPosition() && -450 > FrontRightDrive.getCurrentPosition() && -450 > RearLeftDrive.getCurrentPosition() && -450 > RearRightDrive.getCurrentPosition() &&  step == 4) {
     step = 5;
     FrontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     FrontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
     FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     FrontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
     RearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     RearLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
     RearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     RearRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
     FrontLeftDrive.setPower(0);
     FrontRightDrive.setPower(0);
     RearLeftDrive.setPower(0);
     RearRightDrive.setPower(0);
     FrontLeftDrive.setPower(0.1);
     FrontRightDrive.setPower(0.1);
     RearLeftDrive.setPower(0.1);
     RearRightDrive.setPower(0.1);
     FrontLeftDrive.setTargetPosition(rotationToTicks(-25));
     FrontRightDrive.setTargetPosition(rotationToTicks(15));
     RearLeftDrive.setTargetPosition(rotationToTicks(-25));
     RearRightDrive.setTargetPosition(rotationToTicks(15));
     }
     else if (-880 > FrontLeftDrive.getCurrentPosition() && 520 < FrontRightDrive.getCurrentPosition() && -880 > RearLeftDrive.getCurrentPosition() && 520 < RearRightDrive.getCurrentPosition() && step == 5){
     step = 6;
     FrontLeftDrive.setPower(0);
     FrontRightDrive.setPower(0);
     RearLeftDrive.setPower(0);
     RearRightDrive.setPower(0);
     MarkerServo.setPosition(.45);
     }else if(!FrontLeftDrive.isBusy()&& !FrontRightDrive.isBusy()&& !RearLeftDrive.isBusy()&& !RearRightDrive.isBusy() && step == 6){
     step = 7;
     MarkerServo.setPosition(0);
     FrontLeftDrive.setPower(0.1);
     FrontRightDrive.setPower(0.1);
     RearLeftDrive.setPower(0.1);
     RearRightDrive.setPower(0.1);
     FrontLeftDrive.setTargetPosition(rotationToTicks(-7.5+5));
     FrontRightDrive.setTargetPosition(rotationToTicks(-7.5+5));
     RearLeftDrive.setTargetPosition(rotationToTicks(-7.5+5));
     RearRightDrive.setTargetPosition(rotationToTicks(-7.5+5));
     }else if (!(FrontLeftDrive.isBusy()&&FrontRightDrive.isBusy()&&RearLeftDrive.isBusy()&&RearRightDrive.isBusy()) && step == 7){
     FrontLeftDrive.setPower(0);
     FrontRightDrive.setPower(0);
     RearLeftDrive.setPower(0);
     RearRightDrive.setPower(0);
     }
     }*/






        //Rotation to Ticks Method
    /*public int rotationToTicks (double number){
        return (int)(number * 36.4);

    }*/






        //Old Methods
       /*void goStraight()
    {
        FrontLeftDrive.setPower(drivePower);
        FrontRightDrive.setPower(-drivePower);
        RearLeftDrive.setPower(drivePower);
        RearRightDrive.setPower(-drivePower);
    }
    void turnLeft()
    {
        FrontLeftDrive.setPower(-drivePower);
        FrontRightDrive.setPower(-drivePower);
        RearLeftDrive.setPower(-drivePower);
        RearRightDrive.setPower(-drivePower);
    }
    void stopRobot()
    {
        FrontLeftDrive.setPower(0);
        FrontRightDrive.setPower(0);
        RearLeftDrive.setPower(0);
        RearRightDrive.setPower(0);
    }

     for (int i = 0; i < numSides; i++)
     {
     goStraight();
     Thread.sleep(msStraight);

     turnLeft();
     Thread.sleep(msTurn);
     }

     stopRobot();
     }
     */