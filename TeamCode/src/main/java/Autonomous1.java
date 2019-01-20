/**
 * Created by wave on 10/21/2018.
 */


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;

/**
 * This autonomous OpMode drives the robot forward for a while, turns left, then drives forward
 * again and turns left, etc, until a polygon has been completed. The straights and turns are controlled
 * with simple time values and thus are necessarily only approximate and crude: the point here
 * is not to make a perfect regular polygon but rather to demonstrate the structure of code.
 *
 * This OpMode expects two motors, named 'motorLeft' and 'motorRight' respectively. The
 * OpMode works with both legacy and modern motor controllers.
 */
@Autonomous(name="Auto Polygon (Linear)", group="Swerve Examples")
@Disabled
public class Autonomous1 extends LinearOpMode
{
    DcMotor FrontLeftMotor;
    DcMotor FrontRightMotor;
    DcMotor RearLeftMotor;
    DcMotor RearRightMotor;

    final long msStraight   = 2000;
    final long msTurn       = 1000;
    final double drivePower = 1.0;
    final int  numSides     = 4;

    public @Override void runOpMode() throws InterruptedException
    {
        FrontLeftMotor   = hardwareMap.dcMotor.get("FrontLeft");
        FrontRightMotor  = hardwareMap.dcMotor.get("FrontRight");
        RearLeftMotor    = hardwareMap.dcMotor.get("RearLeft");
        RearRightMotor    = hardwareMap.dcMotor.get("RearRight");

        FrontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        FrontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        RearLeftMotor.setDirection((DcMotor.Direction.FORWARD));
        RearRightMotor.setDirection(DcMotor.Direction.REVERSE);

        this.waitForStart();

        for (int i = 0; i < numSides; i++)
        {
            goStraight();
            Thread.sleep(msStraight);

            turnLeft();
            Thread.sleep(msTurn);
        }

        stopRobot();
    }

    void goStraight()
    {
        FrontLeftMotor.setPower(drivePower);
        FrontRightMotor.setPower(-drivePower);
        RearLeftMotor.setPower(drivePower);
        RearRightMotor.setPower(-drivePower);
    }
    void turnLeft()
    {
        FrontLeftMotor.setPower(-drivePower);
        FrontRightMotor.setPower(-drivePower);
        RearLeftMotor.setPower(-drivePower);
        RearRightMotor.setPower(-drivePower);
    }
    void stopRobot()
    {
        FrontLeftMotor.setPower(0);
       FrontRightMotor.setPower(0);
       RearLeftMotor.setPower(0);
       RearRightMotor.setPower(0);
    }
}
