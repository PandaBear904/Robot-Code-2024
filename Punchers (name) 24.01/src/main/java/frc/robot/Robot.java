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


public class Robot extends TimedRobot {

  // Sets up autonomous routines.
  private static final String kDefaultAuto = "Default";
  private static final String kDrive = "Drive";
  private static final String kScoreDrive = "ScoreDrive";
  private static final String kScoreDriveT = "ScoreDriveT";
  private static final String kScore = "Score";
  private static final String kScoreDriveScore = "ScoreDriveScore";
  private static final String kNothing = "Nothing";
  private static final String k3NoteAuto = "3NoteAuto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  //Autonomous Length in Seconds
  public final double autonomousLengthSeconds = 15;


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
  private DifferentialDrive driveTrain;

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

  // Drive Motor Encoders
  private final RelativeEncoder frontLeftMotorEncoder = frontLeftMotor.getEncoder(); // Front Left Motor Controller
  private final RelativeEncoder backLeftMotorEncoder = backLeftMotor.getEncoder(); // Back Left Motor Controller
  private final RelativeEncoder frontRightMotorEncoder = frontLeftMotor.getEncoder(); // Front Right Motor Controller
  private final RelativeEncoder backRightMotorEncoder = frontLeftMotor.getEncoder(); // Back Right Motor Controller
  private final RelativeEncoder topShooterEncoder = topShooter.getEncoder();
  private final RelativeEncoder bottomShooterEncoder = bottomShooter.getEncoder();

  // Half speed
  public boolean halfSpeed = false;

  // Limit Switches
  DigitalInput AmpLimit = new DigitalInput(0); // Amp 
  DigitalInput SubWooferLimit = new DigitalInput(1); // subwoofer
  DigitalInput PodiumLimit = new DigitalInput(2); // Podium
  DigitalInput BackPostLimit = new DigitalInput(3); // BackPost
  DigitalInput NoteSensor = new DigitalInput(4); // light sensor to see if we have game piece


  public Integer armDesired;
  public Integer armCurrent;
  

  //Speeds  have all various speeds here
  public final double topShooterSpeed = 1; // this is for auto
  public final double bottomShooterSpeed = 1; // this is for auto
  public final double shooterSpeed = 0.2;
  public final double intakeAndFeedMotors = 0.5;
  public final double intakeSpeed = 1;
  public final double shooterFeedSpeed = 1;
  public final double feedSpeed = 0.3;
  public final double armSpeed = 0.25; // set to 0.25

  public boolean shooter = true;
  public boolean shooterFeed = false;
  public boolean topWheels = false;
  public boolean bottomWheels = false;
  public boolean feedWheels = false;
  public boolean climbMotors = false;
  public boolean intake = false;
  public boolean override = false;
  public boolean climb = false;
  public boolean rFeed = false;

  //Auto Stuff
  //Distants robot need to move in auto
  public final double distanceOutStartArea = 100; // Need to test!!!!!!!!!!! 
  // we need to be about 72in
  public final double autoSpeed = 0.5;

  public final double distancePerRotation = 2.23;

  /* public Robot(){ // Do we need?

  } */


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
    SmartDashboard.putNumber("Top Shooter Motor Encoder", topShooterEncoder.getPosition());
    SmartDashboard.putNumber("Bottom Shooter Motor Encoder", bottomShooterEncoder.getPosition());

    // SmartDashboard.putNumber("Arm Motor Encoder", armMotorEncoder.getPosition()); //Might need to add back in if we get encoder.
  }

  @Override
  public void robotInit() {
  // time left in the game
  SmartDashboard.putNumber("Time (seconds)", Timer.getFPGATimestamp());
  // Set up the options you see for auto on SmartDashboard. Need to add more when we are done with auto code.
  // Note if there is nothing here code will not work
  m_chooser.setDefaultOption("Default - Score_Drive", kScoreDrive);
  m_chooser.addOption("Score", kScore);
  m_chooser.addOption("Score + Drive distance", kScoreDrive);
  m_chooser.addOption("Score + Drive timed", kScoreDriveT);
  m_chooser.addOption("Score_Drive_Score", kScoreDriveScore);
  m_chooser.addOption("Move", kDrive);
  m_chooser.addOption("Do_Nothing", kNothing);
  m_chooser.addOption("3_Note_Auto", k3NoteAuto);
  SmartDashboard.putData("Auto choices", m_chooser);


  SmartDashboard.putData("Amp_Level", AmpLimit);
  SmartDashboard.putData("SubWoofer_Level", SubWooferLimit);
  SmartDashboard.putData("Podium_Level", PodiumLimit);
  SmartDashboard.putData("BackPost_Level",BackPostLimit );
  SmartDashboard.putData("Note_Sensor", NoteSensor);


  
  // This is to change whether or not in invert the motors
  frontLeftMotor.setInverted(false);
  backLeftMotor.setInverted(false);
  frontRightMotor.setInverted(true);
  backRightMotor.setInverted(true);

  topShooter.setInverted(true);
  bottomShooter.setInverted(true);

  intakeMotor.setInverted(true);
  armMotor.setInverted(false);
  feedMotor.setInverted(false);

  climbMotor1.setInverted(false);
  climbMotor2.setInverted(false);

  // groups motor controlers
  backLeftMotor.follow(frontLeftMotor);
  backRightMotor.follow(frontRightMotor);

  // Shooter Arm state
  //armDesired = 1;
  //armCurrent = 1;

  // Sets up Cammera
  CameraServer.startAutomaticCapture();
  driveTrain = new DifferentialDrive(frontLeftMotor::set, frontRightMotor::set);
  }

  @Override
  public void teleopPeriodic() {
    //Sets up the drive train, Left stick controls the forward and back. Right controls turning
    //Test deadband
    //no deadband so far 2/29/2024
    //blue controller
    if (blueController.getRawButton(5)) { //L1 half speed
      halfSpeed = true;
    } else if (blueController.getRawButton(6)) { //R1 full speed
    halfSpeed = false;
    } else if (blueController.getRawButton(3)){ // square button  intake motor
    intake = true;
    feedWheels = true;
    } else if (blueController.getRawButton(4)){ // triangle button  OFF button
    intake = false;
    feedWheels = false;
    } 

  if (halfSpeed) { // if half speed true
    driveTrain.arcadeDrive(-blueController.getRawAxis(1) * 3/4, -blueController.getRawAxis(4) * 3/4);
  } else {
    driveTrain.arcadeDrive(Math.pow(-blueController.getRawAxis(1), 3), Math.pow(-blueController.getRawAxis(4), 3));
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

  //if (blueController.getRawButton(2)){ // o button reverse
  //reverseIntakeandFeed = true;
  //} else 




  // reading arm sensor
  /*if (AmpLimit.get()){
    armCurrent = 0;
  }
  if (SubWooferLimit.get()){
    armCurrent = 1;
  }
  if (PodiumLimit.get()){
    armCurrent = 2;
  }
  if (BackPostLimit.get()){
    armCurrent = 3;
  }*/


  if (override == true){
    // Sets the arm motor direction
    armMotor.set(Math.pow(-redController.getRawAxis(5), 3));
  }/* else {
    if (armCurrent - armDesired < 0){
      armMotor.set(-armSpeed);
    } else if (armCurrent - armDesired > 0){
      armMotor.set(armSpeed);
    } else if (armCurrent - armDesired == 0){
      armMotor.set(0);
    }

  }*/

  //buttons on red controller
  if (redController.getRawButton(1)){ // x button BackLeg
    armDesired = 3;
    override = false;
  } else if (redController.getRawButton(2)){ // O button Podium
    armDesired = 2;
    override = false;
  }else if (redController.getRawButton(3)){ // sqaure button SubWoofer
    armDesired = 1;
    override = false;
  }else if (redController.getRawButton(4)){ // triangle button Amp
    armDesired = 0;
    override = false;
  } else if (redController.getRawButton(5)){ // L1 button  Shooter Off
    feedWheels = false;
    rFeed = false;
  }else if (redController.getRawButton(6)){ //R1 button turns on shooter
    rFeed = true;
  } else if (redController.getRawButton(9)){ // R3 moves climber
    climb = true;
    override  = false;
  } else if (redController.getRawButton(10)){ // L3 turns on arm
    override = true;
    climb = false;
  }

  //put boolean if statements here to turn on and off motors
  // booleans


  if (feedWheels == true){
    Timer.delay(0.5);
    feedMotor.set(feedSpeed);
  } else if (rFeed == true) {
    feedMotor.set(-feedSpeed);
  } else {
    feedMotor.set(0);
  }
  if (intake == true){
    intakeMotor.set(intakeSpeed);
  } else {
    intakeMotor.set(0);
  }

  if (shooter == true){
    if (redController.getRawAxis(2) >= 0.1){
      topShooter.set(1);
      bottomShooter.set(1);
      feedWheels = true;
    } else if (redController.getRawAxis(3) >= 0.1){
      topShooter.set(0.1);
      bottomShooter.set(0.1);
      feedWheels = true;
    } else if (redController.getRawAxis(3) == 0){
      topShooter.set(0);
      bottomShooter.set(0);
  }
}
if (NoteSensor.get()){
      intake = false;
      feedWheels = false;

  }
  if (climb == true){
    climbMotor1.set(redController.getRawAxis(1)/2);
    climbMotor2.set(-redController.getRawAxis(5)/2);
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


    //Sets motors Idle Mode to break
    topShooter.setIdleMode(IdleMode.kCoast);
    bottomShooter.setIdleMode(IdleMode.kCoast);
    intakeMotor.setIdleMode(IdleMode.kCoast);
    armMotor.setIdleMode(IdleMode.kBrake);
    climbMotor1.setIdleMode(IdleMode.kBrake);
    climbMotor2.setIdleMode(IdleMode.kBrake);
  }

  @Override
  public void autonomousPeriodic(){
  driveTrain = new DifferentialDrive(frontLeftMotor::set, frontRightMotor::set);
  }

  public void shoot(){
    topShooter.set(topShooterSpeed);
    bottomShooter.set(bottomShooterSpeed);
    Timer.delay(1);
    feedMotor.set(feedSpeed);
    Timer.delay(0.75);
    feedMotor.set(0);
    topShooter.set(0);
    bottomShooter.set(0);
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
  public void driveTimeBack(){
    while (Timer.getMatchTime() <= 10){
      driveTrain.arcadeDrive(-0.6, 0);
    }
  }

  public void driveTimeForwad(){
    while (Timer.getMatchTime() <= 10){
      driveTrain.arcadeDrive(0.6, 0);
    }
  }



  public void Score(){
    shoot();
  }
  public void Score_Drive_Score(){ // Add note sensor to this
    shoot();
    Timer.delay(0.25);
    intake = true; // this won't work need to use .set motors.
    if (NoteSensor.get()){
      intakeMotor.set(0);
    }
    driveDistance(distanceOutStartArea);
    Timer.delay(0.25);
    driveDistance(-distanceOutStartArea);
    shoot();
  }

  public void Drive(){
    driveDistance(distanceOutStartArea);
  }
  
  public void ScoreDrive(){
    shoot();
    Timer.delay(0.25);
    driveDistance(distanceOutStartArea);
  }

  public void nothing(){
    Timer.delay(autonomousLengthSeconds - Timer.getMatchTime());
  }
 
  public void ScoreDriveT(){
    shoot();
    driveTimeBack();
  }


  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();

    frontLeftMotorEncoder.setPosition(0);
    backLeftMotorEncoder.setPosition(0);
    frontRightMotorEncoder.setPosition(0);
    backRightMotorEncoder.setPosition(0);
    
  switch (m_autoSelected) {
    case kDefaultAuto:
    ScoreDrive();
    break;
    case kScore:
    Score();
    break;
    case kDrive:
    Drive();
    break;
    case kScoreDriveScore:
    Score_Drive_Score();
    break;
    case kNothing:
    nothing();
    break;
    case kScoreDrive:
    shoot();
    driveDistance(distanceOutStartArea);
    break;
    case kScoreDriveT:
    ScoreDriveT();
    break;
    case k3NoteAuto:
    shoot();
    intake = true;
    driveTimeBack();
    driveTimeForwad();
    shoot();
    //Right now this is two note need to have the robot turn to get a three not
    } 
  }

    @Override
  public void disabledPeriodic(){
  }

    @Override
  public void disabledInit(){
  }

    @Override
  public void testInit(){
  }

    @Override
  public void testPeriodic(){
  }

    @Override
  public void simulationInit(){
  }

    @Override
  public void simulationPeriodic(){
  }
}
