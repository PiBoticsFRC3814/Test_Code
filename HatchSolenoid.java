/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
//import frc.robot.commands.*;

/**
 * Add your docs here.
 */
public class HatchSolenoid extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  
  DoubleSolenoid tiltSolenoid;
  public static boolean tiltDirection = false;
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    
    tiltSolenoid = new DoubleSolenoid(0, RobotMap.hatch_Tilt1, RobotMap.hatch_Tilt2);
    
  }

   

  public void tiltForward()
  {
    tiltSolenoid.set(Value.kForward);
    tiltDirection = false;
  }
  public void tiltBack()
  {
    tiltSolenoid.set(Value.kReverse);
    tiltDirection = true;
  }

}
