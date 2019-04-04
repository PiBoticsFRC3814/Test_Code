/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//import com.analog.adis16448.frc.*;


/**
 * Add your docs here.
 */
public class PID extends PIDSubsystem {
  
  //public static ADIS16448_IMU gyro;

  public static WPI_TalonSRX motor1;
  public static WPI_TalonSRX motor2;

  public static DifferentialDrive pidrive;

  public PID() {
    super("SubsystemName", 1, 2, 3);
    pidrive = new DifferentialDrive(motor1, motor2);
    motor1 = new WPI_TalonSRX(11);
    motor2 = new WPI_TalonSRX(12);
   // gyro = new ADIS16448_IMU();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  protected double returnPIDInput() {
    return 0.0; //gyro.getYaw();
  }

  @Override
  protected void usePIDOutput(double output) {
    pidrive.arcadeDrive(0, output);
  }
}
