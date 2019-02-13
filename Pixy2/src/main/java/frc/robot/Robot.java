/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static ExampleSubsystem m_subsystem = new ExampleSubsystem();
  public static OI m_oi;
  public static I2C pixy2;
  public static int pix_addr;
  public static Joystick stick1;

  public final byte[] CHECKSUM_GETBLOCKS = {
    (byte) 0xae,
    (byte) 0xc1,
    (byte) 0x20,
    (byte) 0x02,
    (byte) 0x01,
    (byte) 0x04
  }; //tells the pixy2 to send block data

  public final byte[] CHECKSUM_GETLINEFEATURES = {
    (byte) 0xae,
    (byte) 0xc1,
    (byte) 0x30,
    (byte) 0x02,
    (byte) 0x00,
    (byte) 0x07
  };

  public final byte[] CHECKSUM_VERSIONREQUEST = {
    (byte) 0xae,
    (byte) 0xc1,
    (byte) 0x0e,
    (byte) 0x00
  };

  public final byte[] CHECKSUM_LAMPON = {
    (byte) 0xae,
    (byte) 0xc1,
    (byte) 0x16,
    (byte) 0x02,
    (byte) 0x01,
    (byte) 0x01
  };

  public final byte[] CHECKSUM_LAMPOFF = {
    (byte) 0xae,
    (byte) 0xc1,
    (byte) 0x16,
    (byte) 0x02,
    (byte) 0x00,
    (byte) 0x00
  };

  public byte[] pixyRead = new byte[32];



  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    pix_addr = 0x54;
    pixy2 = new I2C(Port.kOnboard, pix_addr);
    stick1 = new Joystick(0);

    m_oi = new OI();
    m_chooser.setDefaultOption("Default Auto", new ExampleCommand());
    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", m_chooser);
    SmartDashboard.putRaw("pixyBlock", pixyRead);


  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();

    if(stick1.getRawButton(1)){
      pixy2.writeBulk(CHECKSUM_GETLINEFEATURES);
      pixy2.readOnly(pixyRead, 32);
      SmartDashboard.putRaw("pixyBlock", pixyRead);
    }
    if(stick1.getRawButton(2)){
      pixy2.writeBulk(CHECKSUM_VERSIONREQUEST);
      pixy2.readOnly(pixyRead, 32);
      SmartDashboard.putRaw("pixyBlock", pixyRead);
    }
    if(stick1.getRawButton(3)){
      pixy2.writeBulk(CHECKSUM_LAMPON);
    }
    if(stick1.getRawButton(4)){
      pixy2.writeBulk(CHECKSUM_LAMPOFF);
    }


  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
