 // Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// Code for our 2024 robot by Scotty, Logan, and Mr.N

package frc.robot;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.cameraserver.CameraServer;


//This is for importing stuff. Need to have all of the 3rd party stuff too.
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID;

import java.io.File;
import edu.wpi.first.wpilibj.Filesystem;
import swervelib.parser.SwerveParser;
import swervelib.SwerveDrive;
import edu.wpi.first.math.util.Units;
import java.io.IOException;




public class Robot extends TimedRobot {

  // frc robot init function
  @Override
  public void robotInit() {
    
    try {
      File settingsFile = new File(Filesystem.getDeployDirectory(),"swerve");
      SwerveDrive swerveDrive = new SwerveParser(settingsFile).createSwerveDrive(Units.feetToMeters(14.5));
    } catch (Exception e) {
      e.printStackTrace();
      // Handle the exception, e.g., log the error or notify the user
    }
  }

  @Override
  public void robotPeriodic() {
    
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void simulationPeriodic() {
  }



  // Add an opening curly brace here
  // Rest of the code...

//   if (-blueController.getRawAxis(4)==0); {
//     driveTrain.arcadeDrive(0,0);}  
//   if (halfSpeed) { // if half speed true
//     driveTrain.arcadeDrive(-blueController.getRawAxis(1) * 3/4, -blueController.getRawAxis(4) * 3/4);}
//   else if (-blueController.getRawAxis(4)>0) {
//     driveTrain.arcadeDrive(Math.pow(-blueController.getRawAxis(1)*.2+.8, 7), Math.pow(-blueController.getRawAxis(4)*.2+.8, 7));}
//   else if (-blueController.getRawAxis(4)<0) {
//     driveTrain.arcadeDrive(Math.pow(-blueController.getRawAxis(1)*.2-.8, 7), Math.pow(-blueController.getRawAxis(4)*.2-.8, 7));}
//   else if (-blueController.getRawAxis(1)>0) {
//     driveTrain.arcadeDrive(Math.pow(-blueController.getRawAxis(1)*.2+.8, 7), Math.pow(-blueController.getRawAxis(4)*.2+.8, 7));}
//   else if (-blueController.getRawAxis(1)<0) {
//     driveTrain.arcadeDrive(Math.pow(-blueController.getRawAxis(1)*.2-.8, 7), Math.pow(-blueController.getRawAxis(4)*.2-.8, 7));}*/
  


//   // Intake
//   if (blueController.getRawAxis(2) > 0.1){
//     intake = true;
//     feedWheels = true;
//   } else if (blueController.getRawAxis(3) > 0.1){
//     intake = false;
//     feedWheels = false;
//   }

//   // reading arm sensor
//   if (armMotorEncoder.getPosition() <= 0){
//     armCurrent = 2;
//   }
//   if (armMotorEncoder.getPosition() >= 137){
//     armCurrent = 1;
//   }
//   if (armMotorEncoder.getPosition() <= -65){
//     armCurrent = 3;
//   } 


//   if (override == true){
//     // Sets the arm motor direction
//     armMotor.set(Math.pow(-redController.getRawAxis(5), 3));
//   } else {
//     if (armCurrent - armDesired < 0){
//       armMotor.set(-armSpeed);
//     } else if (armCurrent - armDesired > 0){
//       armMotor.set(armSpeed);
//     } else if (armDesired == 2){
//       if (armCurrent - armDesired < 0){
//       armMotor.set(armSpeed);}
//     } else if (armCurrent - armDesired == 0){
//       armMotor.set(0);
//   }
// }

//   //buttons on red controller
//   if(redController.getRawButton(1)){
//     override = false;
//   } else if(redController.getRawButton(2)){
//     armDesired = 1;
//     override = false;
//   }else if (redController.getRawButton(3)){ // sqaure button Speaker
//     armDesired = 3;
//     override = false;
//   }else if (redController.getRawButton(4)){ // triangle button Amp
//     armDesired = 2;
//     override = false;
//   } else if (redController.getRawButton(5)){ // L1 button  Shooter Off
//     feedWheels = false;
//     rFeed = false;
//   }else if (redController.getRawButton(6)){ //R1 button backwards feed
//     rFeed = true;
//   } else if (redController.getRawButton(9)){ // R3 moves climber
//     climb = true;
//     override  = false;
//   } else if (redController.getRawButton(10)){ // L3 turns on arm
//     override = true;
//     climb = false;
//   }

//   //put boolean if statements here to turn on and off motors
//   // booleans


//   if (feedWheels == true){
//     feedMotor.set(feedSpeed);
//   } else if (rFeed == true) {
//     feedMotor.set(-feedSpeed);
//   } else {
//     feedMotor.set(0);
//   }
//   if (intake == true){
//     intakeMotor.set(intakeSpeed);
//   } else {
//     intakeMotor.set(0);
//   }

//   if (shooter == true){
//     if (redController.getRawAxis(2) >= 0.1){ // Speaker
//       topShooter.set(1);
//       bottomShooter.set(1);
//       feedWheels = true;
//     } else if (redController.getRawAxis(3) >= 0.1){ // Amp
//       topShooter.set(0.15);
//       bottomShooter.set(0.15);
//       feedWheels = true;
//     } else if (redController.getRawAxis(3) == 0){
//       topShooter.set(0);
//       bottomShooter.set(0);
//   }
// }
// if (NoteSensor.get()){
//       intake = false;
//       feedWheels = false;
//   }

//   if (climb == true){
//     climbMotor1.set(redController.getRawAxis(1)/2);
//     climbMotor2.set(-redController.getRawAxis(5)/2);
//   }
// }



//   /* This function is called once when teleop is enabled*/
//   @Override
//   public void teleopInit() {
//     frontLeftMotor.setIdleMode(IdleMode.kCoast);
//     backLeftMotor.setIdleMode(IdleMode.kCoast);
//     backRightMotor.setIdleMode(IdleMode.kCoast);
//     frontRightMotor.setIdleMode(IdleMode.kCoast);


//     //Sets motors Idle Mode to break
//     topShooter.setIdleMode(IdleMode.kCoast);
//     bottomShooter.setIdleMode(IdleMode.kCoast);
//     intakeMotor.setIdleMode(IdleMode.kCoast);
//     armMotor.setIdleMode(IdleMode.kBrake);
//     climbMotor1.setIdleMode(IdleMode.kBrake);
//     climbMotor2.setIdleMode(IdleMode.kBrake);
//   }



//   public void shoot(){
//     topShooter.set(topShooterSpeed);
//     bottomShooter.set(bottomShooterSpeed);
//     Timer.delay(1);
//     feedMotor.set(feedSpeed);
//     Timer.delay(0.75);
//     feedMotor.set(0);
//     topShooter.set(0);
//     bottomShooter.set(0);
//   }

  
//   public void driveDistance(double distance){
//     if(distance < 0){
//       while ((frontLeftMotorEncoder.getPosition() * distancePerRotation > distance)){
//         driveTrain.arcadeDrive(-0.5, 0);
//     }
//     } else {
//       while ((frontLeftMotorEncoder.getPosition() * distancePerRotation < distance)){
//         driveTrain.arcadeDrive(0.5, 0);
//       }
//     }
//   }
  



//   public void Score(){
//     shoot();
//   }

//   public void Score_Drive_Score(){
//     shoot();
//     intakeMotor.set(0.5); // this won't work need to use .set motors.
//     feedMotor.set(0.3);
//     driveDistance(distanceOutStartArea);
//     if (NoteSensor.get()){
//       intake = false;
//       feedWheels = false;
//     }
//     Timer.delay(0.005);
//     feedMotor.set(0);
//     intakeMotor.set(0);
//     driveDistance(-distanceOutStartAreaShort);
//     shoot();
//     intakeMotor.set(0);
//     feedMotor.set(0);
//     Timer.delay(autonomousLengthSeconds - Timer.getMatchTime());
//   }

//   public void Drive(){
//     driveDistance(distanceOutStartArea);
//   }
  
//   public void ScoreDrive(){
//     shoot();
//     driveDistance(distanceOutStartArea);
//     intake = true;
//   }

//   public void nothing(){
//     Timer.delay(autonomousLengthSeconds - Timer.getMatchTime());
//   }
//   public void DriveLong(){
//     driveDistance(distanceOutStartAreaLong);
//   }
//   public void DriveShort(){
//     driveDistance(distanceOutStartAreaShort);
//   }
 


//   @Override
//   public void autonomousInit() {
//     m_autoSelected = m_chooser.getSelected();

//     frontLeftMotorEncoder.setPosition(0);
//     backLeftMotorEncoder.setPosition(0);
//     frontRightMotorEncoder.setPosition(0);
//     backRightMotorEncoder.setPosition(0);
    
//   switch (m_autoSelected) {
//     case kDefaultAuto:
//     Score();
//     driveDistance(distanceOutStartArea);
//     break;
//     case kScore:
//     Score();
//     break;
//     case kDrive:
//     Drive();
//     break;
//     case kScoreDriveScore:
//     Score_Drive_Score();
//     break;
//     case kNothing:
//     nothing();
//     break;
//     case kScoreDrive:
//     Score();
//     driveDistance(distanceOutStartArea);
//     break;
//     case kScoreDriveLong:
//     Score();
//     driveDistance(distanceOutStartAreaLong);
//     break;
//     case kScoreDriveShort:
//     Score();
//     driveDistance(distanceOutStartAreaShort);
//     } 
//   }
//     @Override
//   public void autonomousPeriodic(){
//   }

//     @Override
//   public void disabledPeriodic(){
//   }

//     @Override
//   public void disabledInit(){
//   }

//     @Override
//   public void testInit(){
//   }

//     @Override
//   public void testPeriodic(){
//   }

//     @Override
//   public void simulationInit(){
//   }

//     @Override
//   public void simulationPeriodic(){
//   }
}
