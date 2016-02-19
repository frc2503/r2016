# r2016

This is the code for our 2016 robot.

## PWM

 port number | y-splitter? | Motor Controller type | description
|---|---|---|---|
0|Yes|Talon|Left Side Rhino Track
1|Yes|Talon|Right Side Rhino Track
2|No|Talon|Shooter
3|No|Spark|Winch
4|No|Talon|Hook
5|No|Servo|Camera X Rotation Servo
6|No|Servo|Camera Y Rotation Servo
7|No|Talon|Pick up motor

## DIO
port number | description
|---|---|
0|Left Track Encoder A
1|Left Track Encoder B
2|Right Track Encoder A
3|Right Track Encoder B
4|Hooker Encoder A
5|Hooker Encoder B
6|Intake Limit Switch
7|Camera Light Relay (if used)

## Pneumatics
port number | description
|---|---|
0|Raise arm
1|Lower arm
