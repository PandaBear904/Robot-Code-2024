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
import edu.wpi.first.wpilibj.GenericHID;


public class Robot extends TimedRobot {

  // Sets up autonomous routines.
  private static final String kDefaultAuto = "Default";
  private static final String kScoreDrive = "ScoreDrive";
  private static final String kNothing = "Nothing";
  private String m_autoSelected;

  private String m_autoString;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  //Autonomous Length in Seconds
  public final double autonomousLengthSecends = 15;


  // Sets up controllers
  private final GenericHID redController = new GenericHID(1);
  private final GenericHID blueController = new GenericHID(0);

  // Drive motors
  // Left side
  private final CANSparkMax frontLeftMotor = new CANSparkMax(1, MotorType.kBrushless);
  private final CANSparkMax backLeftMotor = new CANSparkMax(2, MotorType.kBrushless);
  // Right side
  private final CANSparkMax frontRightMotor = new CANSparkMax(3, MotorType.kBrushless);  
  private final CANSparkMax backRightMotor = new CANSparkMax(4, MotorType.kBrushless);

  // The Drive Trian
  private final DifferentialDrive driveTrain = new DifferentialDrive(frontLeftMotor, frontRightMotor);

  // Drive Motor Encoders
  private final RelativeEncoder frontLeftMotorEncoder = frontLeftMotor.getEncoder(); // Front Left Motor Controller
  private final RelativeEncoder backLeftMotorEncoder = backLeftMotor.getEncoder(); // Back Left Motor Controller
  private final RelativeEncoder frontRightMotorEncoder = frontLeftMotor.getEncoder(); // Front Right Motor Controller
  private final RelativeEncoder backRightMotorEncoder = frontLeftMotor.getEncoder(); // Back Right Motor Controller
  



  // Intake Motor
  private final CANSparkMax intakeMotor = new CANSparkMax(5,MotorType.kBrushed);
  private final CANSparkMax armMotor = new CANSparkMax(6, MotorType.kBrushless);
  // Shooter Motors
  // Shooters
  private final CANSparkMax topShooter = new CANSparkMax(7, MotorType.kBrushless);
  private final CANSparkMax bottomShooter = new CANSparkMax(8, MotorType.kBrushless);
  private final CANSparkMax feedMotor = new CANSparkMax(9, MotorType.kBrushless);
  //Climb motor
  private final CANSparkMax climbMotor1 = new CANSparkMax(10, MotorType.kBrushed);
  private final CANSparkMax climbMotor2 = new CANSparkMax(11, MotorType.kBrushed);
  //Haft speed
  public boolean halfSpeed = false;

 
  //Auto Stuff
  //Distants robot need to move in auto
  public final double distanceOutStartArea = 200; // Need to test!!!!!!!!!!!

  public final double distancePerRotation = 2.23;
  

  //Speeds  have all various speeds here
  public final double topShooterSpeed = 1;
  public final double bottomShooterSpeed = 1;
  public final double shooterSpeed = 1;
  public final double intakeAndFeedMotors = 1;
  public final double shooterAndFeedMotors = 1;
  public final double intakeSpeed = 1;
  public final double feedSpeed = 1;

  public boolean topWheels = false;
  public boolean bottomWheels = false;
  public boolean feedWheels = false;
  public boolean climbMotors = false;
  public boolean intakeAndFeed = false;



  public Robot(){

  }


  @Override
  public void robotPeriodic(){
    SmartDashboard.putNumber("Front Right Motor Encoder Position", frontRightMotorEncoder.getPosition());
    SmartDashboard.putNumber("Front Right Motor Encoder Distance (rotation*2.23)", frontRightMotorEncoder.getPosition() * distancePerRotation);
    SmartDashboard.putNumber("Front Left Motor Encoder Position", frontLeftMotorEncoder.getPosition());
    SmartDashboard.putNumber("Front Left Motor Encoder Distance (rotation*2.23)", frontLeftMotorEncoder.getPosition() * distancePerRotation);
    SmartDashboard.putNumber("Back Right Motor Encoder Position", backRightMotorEncoder.getPosition());
    SmartDashboard.putNumber("Back Right Motor Encoder Distance (rotation*2.23)", backRightMotorEncoder.getPosition() * distancePerRotation);
    SmartDashboard.putNumber("Back Left Motor Encoder Position", backLeftMotorEncoder.getPosition());
    SmartDashboard.putNumber("Back Left Motor Encoder Distance (rotation*2.23)", backLeftMotorEncoder.getPosition() * distancePerRotation);
  }

  @Override
  public void robotInit() {
  // Set up the options you see for auto on SmartDashboard. Need to add more when we are done with auto code.
  // Note if there is nothing here code will not work
  m_chooser.setDefaultOption(m_autoString, kNothing);

  SmartDashboard.putData("Auto choices", m_chooser);


  // This is to change whether or not in invert the motors
  frontLeftMotor.setInverted(false);
  backLeftMotor.setInverted(false);
  frontRightMotor.setInverted(true);
  backRightMotor.setInverted(true);

  topShooter.setInverted(false);
  bottomShooter.setInverted(false);

  intakeMotor.setInverted(false);
  armMotor.setInverted(false);
  feedMotor.setInverted(false);

  climbMotor1.setInverted(false);
  climbMotor2.setInverted(false);

  // groups motor controlers
  backLeftMotor.follow(frontLeftMotor);
  backRightMotor.follow(frontRightMotor);
  climbMotor2.follow(climbMotor1);


  // Sets up Cammera
  CameraServer.startAutomaticCapture();

  }

  @Override
  public void teleopPeriodic() {
    //Sets up the drive train, Left stick controls the forward and back. Right controls turning
    //Want cubic function. currently linear, look up deadlands
    //blue controller
    if (blueController.getRawButton(5)) { //L1 half speed
      halfSpeed = true;
    } else if (blueController.getRawButton(6)) { //R1 full speed
    halfSpeed = false;
  }

  if (halfSpeed) { // if half speed true
    driveTrain.arcadeDrive(-blueController.getRawAxis(1)/2, -blueController.getRawAxis(4)/2);
  } else {
    driveTrain.arcadeDrive(Math.pow(-blueController.getRawAxis(1), 3), Math.pow(-blueController.getRawAxis(4), 3));
  }
  //buttons on red controller
  if (redController.getRawButton(1)){ // x button 
    
  } else if (redController.getRawButton(2)){ // O button 
    
  } else if (redController.getRawButton(3)){ // sqaure button intake motor
    intakeAndFeed = true;
  } else if (redController.getRawButton(4)){ // triande button OFF button
    topWheels = false;
    bottomWheels = false;
    intakeAndFeed = false;
  } else if (redController.getRawButton(5)){ // L1 button 

  } else if (redController.getRawButton(6)){ //R1 button turns on shooter
    topWheels = true;
    bottomWheels = true;
  }
  armMotor.set(Math.pow(-redController.getRawAxis(5), 3));
  if (redController.getRawAxis(3) <= 1){
    feedWheels = true;
  }
  // Need to add R2 to shot the game piece out
  // Add a reverse button


  //put boolean if statements here to turn on and off motors
  // booleans

  if (topWheels == true){
    topShooter.set(shooterSpeed);
  } else {
    topShooter.set(0);
  }
  if (bottomWheels == true){
    bottomShooter.set(shooterSpeed);
  } else {
    bottomShooter.set(0);
  }
  if (feedWheels == true){
    feedMotor.set(feedSpeed);
  } else {
    feedMotor.set(0);
  }
  if (intakeAndFeed == true){
    feedMotor.set(intakeAndFeedMotors);
    intakeMotor.set(intakeAndFeedMotors);
  } else {
    feedMotor.set(0);
    intakeMotor.set(0);
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
    intakeMotor.setIdleMode(IdleMode.kCoast);

    //Sets motors Idle Mode to break
    bottomShooter.setIdleMode(IdleMode.kBrake);
    climbMotor1.setIdleMode(IdleMode.kBrake);
  }



   public void score(){
    topWheels = true;
    Timer.delay(1);
    bottomWheels = true;
    Timer.delay(0.75);
    topWheels = false; 
    bottomWheels = false;
  }


  public void driveDistance(double distance){
    if(distance < 0){
      while ((frontLeftMotorEncoder.getPosition() * distancePerRotation > distance)){
        driveTrain.arcadeDrive(-0.6, 0);
    }
    } else {
      while ((frontLeftMotorEncoder.getPosition() * distancePerRotation < distance)){
        driveTrain.arcadeDrive(0.6, 0);
      }
    }
  }

  public void nothing(){
    Timer.delay(autonomousLengthSecends - Timer.getMatchTime());
  }

  public void ScoreDrive(){
    score();
    driveDistance(distanceOutStartArea);
  }


  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();

  switch (m_autoSelected) {
    case kDefaultAuto:

  } 
  }
}

