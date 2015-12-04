package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


/**
 * Created by Student on 10/21/2015.
 */
public class Test1 {
    DcMotor testMotor;
    public void init(){
        testMotor= hardwareMap.dcMotor.get("motor1");}
    public void loop(){
        testMotor.setPower(1);}
    }

