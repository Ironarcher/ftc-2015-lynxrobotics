package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;//Don't need .Servo for this file
import com.qualcomm.robotcore.util.Range;
import java.lang.Math;

/**
 * Created by ethan on 10/5/15.
 */
public class JoystickL extends OpMode {
    // Right/Left Front/Back
    DcMotor motorRF;
    DcMotor motorRB;
    DcMotor motorLF;
    DcMotor motorLB;

    public float ScalePower(float power){
        final float conversionFactor = 1f;
        return power*conversionFactor;
    }

    public void writeToTelemetry(float x, float y, float ml, float mr){
        telemetry.addData("xVal: ", x);
        telemetry.addData("yVal: ", y);
        telemetry.addData("Motor L: ", ml);
        telemetry.addData("Motor R: ", mr);
    }

    public JoystickL(){

    }

    public void init(){
        motorRF = hardwareMap.dcMotor.get("motor_1");
        motorRB = hardwareMap.dcMotor.get("motor_2");
        motorLF = hardwareMap.dcMotor.get("motor_3");
        motorLB = hardwareMap.dcMotor.get("motor_4");

    }

    @Override
    public void loop(){
        float xVal = (gamepad1.left_stick_x*Math.abs(gamepad1.left_stick_x)); //x*|x|
        float yVal = (gamepad1.right_stick_y*Math.abs(gamepad1.left_stick_y)); //y*|y|
        float motorL = yVal + xVal;
        float motorR = yVal - xVal;

        motorL = Range.clip(motorL, -1, 1);
        motorR = Range.clip(motorR, -1, 1);

        motorL = ScalePower(motorL);
        motorR = ScalePower(motorR);

        motorLF.setPower(motorL);
        motorLB.setPower(motorL);
        motorRF.setPower(motorR);
        motorRB.setPower(motorR);

        writeToTelemetry(xVal,yVal, motorL, motorR); //comment out when done testing
    }

    @Override
    public void stop(){

    }
}

