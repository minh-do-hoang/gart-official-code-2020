package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;

import static frc.robot.Constants.DRIVE_CONST.*;

public class Drivebase extends SubsystemBase {
    /**
     * The Singleton instance of this Drivebase. External classes should
     * use the {@link #getInstance()} method to get the instance.
     */
    private static Drivebase INSTANCE;

    /**
     * Returns the Singleton instance of this Drivebase. This static method
     * should be used by external classes, rather than the constructor
     * to get the instance of this class. For example: {@code Drivebase.getInstance();}
     */
    public static Drivebase getInstance() {
        // Fast (non-synchronized) check to reduce overhead of acquiring a lock when it's not needed
        if (INSTANCE == null) {
            // Lock to make thread safe 
            synchronized (Drivebase.class) {
                // check nullness again as multiple threads can reach above null check
                if (INSTANCE == null) {
                    INSTANCE = new Drivebase();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Creates a new instance of this Drivebase.
     * This constructor is private since this class is a Singleton. External classes
     * should use the {@link #getInstance()} method to get the instance.
     */

    private Drivebase() {
        leftFollow.follow(leftEncoded);
        rightFollow.follow(rightEncoded);
        // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
        //       in the constructor or in the robot coordination class, such as RobotContainer.
        //       Also, you can call addChild(name, sendableChild) to associate sendables with the subsystem
        //       such as SpeedControllers, Encoders, DigitalInputs, etc.
    }

    WPI_TalonSRX leftEncoded = new WPI_TalonSRX(LEFT_ENCODED_CAN);
    WPI_TalonSRX leftFollow = new WPI_TalonSRX(LEFT_FOLLOW_CAN);
    WPI_TalonSRX rightEncoded = new WPI_TalonSRX(RIGHT_ENCODED_CAN);
    WPI_TalonSRX rightFollow = new WPI_TalonSRX(RIGHT_FOLLOW_CAN);

    public void drive(double leftSpeed, double rightSpeed) {
        leftEncoded.set(leftSpeed);
        rightEncoded.set(rightSpeed);
    }

    @Override
    public void periodic() {
        double multiplier = RobotContainer.stick.getRawAxis(3) > 0.5 ? 0.4 : 0.8;
        drive(RobotContainer.stick.getRawAxis(1) * multiplier, RobotContainer.stick.getRawAxis(5) * multiplier);
    }
}

