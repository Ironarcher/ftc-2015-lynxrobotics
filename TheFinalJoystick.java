package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/*Copyright (c) <year> <copyright holders>



Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:



The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.



THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.*/
/**
 * Created by ethan on 11/2/15.
 */
//DISCLAIMER---NOTHING IN THIS CODE IS GUARENTEED TO WORK.  USE AT YOUR OWN RISK
public class TheFinalJoystick extends OpMode{
    //motors for driving
    DcMotor motorRF;
    DcMotor motorRB;
    DcMotor motorLF;
    DcMotor motorLB;

    //For continuous servo control
    private float servo_rate = 0.5f;

    //motors for arm
    DcMotor extendArm1;
    DcMotor extendArm2;
    DcMotor retractArm;
    DcMotor raiseArm;

    //servos for other shit
    Servo boxRotateServo;
    Servo putterServo;
    Servo trapdoorServo;
    Servo sweeperServo;
    Servo putterServo2;
    Servo servoList[] = new Servo[5];
    double servoCalibration[] = new double[5];

    //color sensor servo doesn't need to be here

    static double sweeperVal = 0.5;

    public TheFinalJoystick(){}

    @Override
    public void init(){
        motorRF = hardwareMap.dcMotor.get("motorRF");
        motorRB = hardwareMap.dcMotor.get("motorRB");
        motorLF = hardwareMap.dcMotor.get("motorLF");
        motorLB = hardwareMap.dcMotor.get("motorLB");

        extendArm1 = hardwareMap.dcMotor.get("extend1");
        extendArm2 = hardwareMap.dcMotor.get("extend2");

        raiseArm = hardwareMap.dcMotor.get("raise");

        retractArm = hardwareMap.dcMotor.get("retract");

        boxRotateServo = hardwareMap.servo.get("boxRotate");
        sweeperServo = hardwareMap.servo.get("sweeper");
        trapdoorServo = hardwareMap.servo.get("trapdoor");
        putterServo = hardwareMap.servo.get("putter");
        putterServo2 = hardwareMap.servo.get("putter2");

        servoList[0] = boxRotateServo;
        servoList[1] = sweeperServo;
        servoList[2] = trapdoorServo;
        servoList[3] = putterServo;
        servoList[4] = putterServo2;
        for (int i = 0; i < 5; i++) {
            servoCalibration[i] = servoList[i].getPosition();
        }

        motorRF.setPower(0.0);
        motorRB.setPower(0.0);
        motorLF.setPower(0.0);
        motorLB.setPower(0.0);
        extendArm1.setPower(0.0);
        extendArm2.setPower(0.0);
        retractArm.setPower(0.0);
        raiseArm.setPower(0.0);
    }

            /*Buttons that do stuff--This is probably flat out a lie:
        gamepad 1 joysticks (both)
        gamepad 1 X
        gamepad 1 bumpers (both)
        gamepad 2 dpad (all)
        gamepad 2 bumpers (both)
        gamepad 2 x/y/a/b
        */
    @Override
    public void loop(){
        //I did everything in a kinda stupid way but we need some kind of hierarchy or shit will be fucked




        //retract arm using dpad up/down--This has been moved to right joystick of gamepad2
        //at Kagoo's request
       /*if(gamepad2.dpad_up){
           raiseArm.setPower(1.0);
            extendArm1.setPower(-0.05);
            extendArm2.setPower(0.05);
            //Not sure which way these negatives should go
            retractArm.setPower(.25);
            extendArm1.setPower(-0.25);
            extendArm2.setPower(0.25);
        }else if(gamepad2.dpad_down){
            raiseArm.setPower(-1.0);
            extendArm1.setPower(0.05);
            extendArm2.setPower(-0.05);
            retractArm.setPower(-.25);
            extendArm1.setPower(0.25);
            extendArm2.setPower(-0.25);
        }else{
            retractArm.setPower(0.0);
        }
*/


        //rotate box using X/B om gamepad2--WE REALLY NEED TO FIX THIS
        if(gamepad2.x){
           boxRotateServo.setPosition(0.0);
            //boxRotateServo.setDirection(Servo.Direction.FORWARD);
           // setServo(0, -.5); //CHANGE TO SET SERVO RELATIVE
        }else if(gamepad2.b){
            boxRotateServo.setPosition(1.0);
            //boxRotateServo.setDirection(Servo.Direction.REVERSE);
            //setServo(0, .5);
        }else{
            boxRotateServo.setPosition(0.5);
            //boxRotateServo.setDirection(Servo.Direction.);
          //  setServo(0, 0);
        }


        //raise or lower arm using gamepad 2 joystick
        //again I am not sure on how the negatives should go so this and everything else will need to be checked
        //Are we sure we want the code to hard code a value to the motor.  It would be an easy task to manage to make it
        //so that the more you push the joystick the more power you get which might make control easier
        double joystickPwr = (Range.clip(gamepad2.left_stick_y,-1,1));
        //extendArm1.setPower(joystickPwr/4);
        //extendArm2.setPower(-joystickPwr/4);
        raiseArm.setPower(joystickPwr/2);



//Extend/retract the arm
        double joystickPwr2 = (Range.clip(gamepad2.right_stick_y,-1,1));
        retractArm.setPower(joystickPwr2);
        extendArm1.setPower(joystickPwr2);
        extendArm2.setPower(-joystickPwr2);

        //drive robot around using gamepad 1 joysticks--DRIVE TRAIN CODE
        double powerL = Range.clip(gamepad1.left_stick_y, -1.00, 1.00)/2;
        double powerR = -Range.clip(gamepad1.right_stick_y, -1.00, 1.00)/2;
        motorLB.setPower(powerL);
        motorLF.setPower(powerL);
        motorRB.setPower(powerR);
        motorRF.setPower(powerR);

        //control trap door with y and a--GAMEPAD 2
        if(gamepad2.y){
            //setServo(2, 1);
            trapdoorServo.setPosition(0);
        }else if(gamepad2.a){
            trapdoorServo.setPosition(1);
            //setServo(2, 0);  //purpose of this is missed
        }else{
            trapdoorServo.setPosition(0.5);
           // setServo(2, 0);
        }

        //control right putter with b---THIS SHOULD WORK.  MAYBE A LITTLE BIT OF FINE TUNING BUT I BELIEVE WE ARE GOOD
        //SERVO 4 = putterservo
        if(gamepad1.b) putterServo.setPosition(.1); //.2 needs testing/calibration
        else putterServo.setPosition(.75);
        //SERVO 5 = putterservo 2
        //control left putter with x
        if(gamepad1.x) putterServo2.setPosition(.95);
        else putterServo2.setPosition(.1);

        //control state of sweeper using bumpers
        //0 = CW, 1 = CCW, 2 = stop
        if(gamepad1.x) sweeperVal = 0.5;
        else if(gamepad1.right_bumper || gamepad2.right_bumper) sweeperVal = 1;
        else if(gamepad1.left_bumper || gamepad2.left_bumper) sweeperVal = 0;
        else sweeperVal = .5;
      //  setServo(1, sweeperVal);
        sweeperServo.setPosition(sweeperVal);

        //add stuff to telemetry
        //telemetry.addData("Sweeper Position", sweeperServo.getPosition());
        //telemetry.addData("Rotate Servo Position", boxRotateServo.getPosition());
    }

    @Override
    public void stop(){

    }

}
