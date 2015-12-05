
package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.ColorSensor;
import java.lang.*;

    /**
     * Created by ethan on 11/2/15.
     */
//DISCLAIMER---NOTHING IN THIS CODE IS GUARENTEED TO WORK.  USE AT YOUR OWN RISK
    public class auto1 extends OpMode {
        ColorSensor color;
        public int currentColor;
        //this needs to be hard coded
        int ourColor;
        int aab = 0;


        //motors for driving
        DcMotor motorRF;
        DcMotor motorRB;
        DcMotor motorLF;
        DcMotor motorLB;
        int hi = 0;


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

        double sweeperVal = 0.5;

        public auto1() {
        }

        @Override
        public void init() {
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


        //rotate box using X/B om gamepad2--WE REALLY NEED TO FIX THIS
        /*    if(gamepad2.x){
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
            }*/


        //raise or lower arm using gamepad 2 joystick
        //again I am not sure on how the negatives should go so this and everything else will need to be checked
        //Are we sure we want the code to hard code a value to the motor.  It would be an easy task to manage to make it
        //so that the more you push the joystick the more power you get which might make control easier


        void raiseArm(double speed) {
            raiseArm.setPower(speed / 2);
        }


        //Extend/retract the arm

        void extendArm(double speed) {
            retractArm.setPower(speed);
            extendArm1.setPower(speed);
            extendArm2.setPower(-speed);
        }

        //drive robot around using gamepad 1 joysticks--DRIVE TRAIN CODE
        void driveTrain(double powerL, double powerR) {
            motorLB.setPower(powerL);
            motorLF.setPower(powerL);
            motorRB.setPower(powerR);
            motorRF.setPower(powerR);
        }

        //control trap door with y and a--GAMEPAD 2
        void trapdoorOpen() {
            trapdoorServo.setPosition(0);
        }

        void trapdoorClose() {
            trapdoorServo.setPosition(1);
            //setServo(2, 0);  //purpose of this is missed
        }

        void trapdoorStop() {
            trapdoorServo.setPosition(0.5);
        }


        //control state of sweeper using bumpers
        //0 = CW, 1 = CCW, 2 = stop
        void sweeperUse(double speed) {
            sweeperServo.setPosition(speed);
        }

        void wait(int time) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                //handle the exceptions
                Thread.currentThread().interrupt();
            }
        }

        //add stuff to telemetry
        //telemetry.addData("Sweeper Position", sweeperServo.getPosition());
        //telemetry.addData("Rotate Servo Position", boxRotateServo.getPosition());
        @Override
        public void loop() {
        }

        //move straight forwards
        void run() {
            driveTrain(1, 1);
            //Wait for an x amount of time-trial + error
            wait(6000);
            //turn right
            driveTrain(1, -1);
            //Wait for a but of time again
            wait(300);
            //Now go forward again
            driveTrain(1, 1);
            //Wait some more
            wait(3000);

            //NOW WE NEED TO FIGURE OUT HOW TO MONITOR AND PUSH BUTTON
            //WHEN IT IS OUR COLOR PLEASE HIT THE BUTTON
            currentColor = color.argb();

        //While it is not our color, a hard coded value DON"T HIT THE BUTTON

        while(currentColor-ourColor>10||currentColor-ourColor<-10)
        {
            driveTrain(.1, -.1);



    }}




        @Override
        public void stop() {

        }

    }


