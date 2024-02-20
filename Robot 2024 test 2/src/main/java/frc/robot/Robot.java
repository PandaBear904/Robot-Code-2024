// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// Code for our 2024 robot by Scotty, Logan, Nick, Alex, and Mr.N

package frc.robot;

 


import com.revrobotics.CANSparkMax;
//import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.cameraserver.CameraServer;

//This is for importing stuff. Need to have all of the 3rd party stuff too.




import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.GenericHID;


/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with
 * arcade steering.
 */
public class Robot extends TimedRobot {

  // Sets up autonomous routines.
  private static final String kDefaultAuto = "Default";

  private String m_autoString;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();


  // Sets up controllers
  private final GenericHID redController = new GenericHID(1);
  private final GenericHID blueController = new GenericHID(0);

  // Drive motor
  private final CANSparkMax frontLeftMotor = new CANSparkMax(1, MotorType.kBrushless);
  private final CANSparkMax backLeftMotor = new CANSparkMax(2, MotorType.kBrushless);
  //private final MotorControllerGroup leftDriveMotors = new MotorControllerGroup(frontLeftMotor, backLeftMotor);


  private final CANSparkMax frontRightMotor = new CANSparkMax(3, MotorType.kBrushless);  
  private final CANSparkMax backRightMotor = new CANSparkMax(4, MotorType.kBrushless);
  //private final MotorControllerGroup rightDriveMotors = new MotorControllerGroup(frontRightMotor, backRightMotor);


  // Drive Motor Encoders
  /*private final RelativeEncoder frontLeftMotorEncoder = frontLeftMotor.getEncoder(); // Front Left Motor Controller
  private final RelativeEncoder backLeftMotorEncoder = backLeftMotor.getEncoder(); // Back Left Motor Controller
  private final RelativeEncoder frontRightMotorEncoder = frontLeftMotor.getEncoder(); // Front Right Motor Controller
  private final RelativeEncoder backRightMotorEncoder = frontLeftMotor.getEncoder(); // Back Right Motor Controller
  */
  // The Drive Trian
  private final DifferentialDrive driveTrain = new DifferentialDrive(frontLeftMotor, frontRightMotor);

  // Motor for intake here

  //Haft speed
  public boolean halfSpeed = false;






  public Robot() {

    

  }

  @Override
  public void robotInit() {
  // Set up the options you see for auto on SmartDashboard. Need to add more when we are done with auto code.
  m_chooser.setDefaultOption(m_autoString, kDefaultAuto);

  SmartDashboard.putData("Auto choices", m_chooser);





  
  // This is to change whether or not in invert the motors
  // switch of null before pushing the code
  frontLeftMotor.setInverted(false);
  backLeftMotor.setInverted(false);
  frontRightMotor.setInverted(true);
  backRightMotor.setInverted(true);

  // groups motor controlers
  backLeftMotor.follow(frontLeftMotor);
  backRightMotor.follow(frontRightMotor);
  

  // Sets up Cammera
  CameraServer.startAutomaticCapture();


    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.

  }

  @Override
  public void teleopPeriodic() {
    //Sets up the drive train, Left stick controls the forward and back. Right controls turning
    //Want cubic function. currently linear, look up deadlands
    if (blueController.getRawButton(5)) { //l1 half speed
      halfSpeed = true;
    } else if (blueController.getRawButton(6)) { //R1 full speed
    halfSpeed = false;
  }

  if (halfSpeed) { // if half speed true
    driveTrain.arcadeDrive(-blueController.getRawAxis(1)/2, -blueController.getRawAxis(4)/2);
  } else {
    driveTrain.arcadeDrive(Math.pow(-blueController.getRawAxis(1), 3), Math.pow(-blueController.getRawAxis(4), 3));
  }
  //buttons on controller
  if (redController.getRawButton(1)){ // x button
    
  } else if (redController.getRawButton(2)){ // O button

  } else if (redController.getRawButton(3)){ // sqaure button

  } else if (redController.getRawButton(4)){ // triande button

  } else if (redController.getRawButton(5)){ // L1 button

  } else if (redController.getRawButton(6)){ //R1 button

  }

  // Set the break mode for drive train
  if (blueController.getRawAxis(2) > 0.1){
    frontLeftMotor.setIdleMode(IdleMode.kBrake);
    backLeftMotor.setIdleMode(IdleMode.kBrake);
    frontRightMotor.setIdleMode(IdleMode.kBrake);
    backRightMotor.setIdleMode(IdleMode.kBrake);
  } else if (blueController.getRawAxis(3) > 0.1){
    frontLeftMotor.setIdleMode(IdleMode.kCoast);
    backLeftMotor.setIdleMode(IdleMode.kCoast);
    frontRightMotor.setIdleMode(IdleMode.kCoast);
    backRightMotor.setIdleMode(IdleMode.kCoast);
  }
  }

  /* This function is called once when teleop is enabled*/
  @Override
  public void teleopInit() {
    //Sets motors IdleMode to coast, Need to add 3 second wait
    frontLeftMotor.setIdleMode(IdleMode.kCoast);
    backLeftMotor.setIdleMode(IdleMode.kCoast);
    backRightMotor.setIdleMode(IdleMode.kCoast);
    frontRightMotor.setIdleMode(IdleMode.kCoast);
  }



}
