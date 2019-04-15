/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;;


/**
 * Add your docs here.
 */
public class PID extends PIDSubsystem {
  
  public static ADXRS450_Gyro gyro;

  public static WPI_TalonSRX left;
  public static WPI_TalonSRX right;

  public static DifferentialDrive pidrive;

  public PID() {
    super("PID", 0.02, 0.00003, 0.05);
    getPIDController().setContinuous(false);

    left = new WPI_TalonSRX(RobotMap.left_Drive);
    right = new WPI_TalonSRX(RobotMap.right_Drive);
    gyro = new ADXRS450_Gyro();
    pidrive = new DifferentialDrive(left, right);
  }

  @Override
  public void initDefaultCommand() {
    
  }

  @Override
  protected double returnPIDInput() {
    SmartDashboard.putNumber("Gyro", gyro.getAngle());
    return gyro.getAngle();
  }

  @Override
  protected void usePIDOutput(double output) {
    pidrive.arcadeDrive(0, output);
    SmartDashboard.putNumber("Output", output);
  }
}
