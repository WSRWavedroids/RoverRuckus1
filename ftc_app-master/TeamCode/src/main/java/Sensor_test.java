/**
 * Created by wave on 10/16/2018.
 */

import android.hardware.Sensor;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class Sensor_test extends LinearOpMode {
    private ColorSensor Sensor1;
    @Override
    public void runOpMode() {
         Sensor1 = hardwareMap.get(ColorSensor.class, "Sensor1");
        Sensor1.enableLed(false);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            String testColor = "";
            if(isYellow(Sensor1.red(), Sensor1.green(),Sensor1.blue())){
                //Sensor1.enableLed(true);
                testColor = "Yellow";
            }else{
                //Sensor1.enableLed(false);
                testColor = "Not Yellow";
            }
            telemetry.addData("Hue", Sensor1.argb() );
            telemetry.addData("Alpha", Sensor1.alpha());
            telemetry.addData("Red", Sensor1.red());
            telemetry.addData("Green", Sensor1.green());
            telemetry.addData("Blue", Sensor1.blue());
            telemetry.addData("Blue", testColor);

            telemetry.update();
            // Wait for the game to start (driver preses PLAY)
            waitForStart();
        }
    }

    public static boolean isYellow(int red, int green, int blue){
        boolean result = false;
        if (blue < red && blue < green && red > green){
            result = true;
        }
        return result;
    }
}