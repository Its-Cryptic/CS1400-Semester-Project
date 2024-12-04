# CS1400-Semester-Project
Semester Project for CS1400

This project is a highly configurable physics engine that can take a JSON config file and construct an environment with both static and dynamic forces.


# How to run:
#### 1. Head over to [releases](https://github.com/Its-Cryptic/CS1400-Semester-Project/releases)
#### 2. Download the latest release .jar file
#### 3. Run with the following command
```bash
java -jar PhysSim.1.0.jar
```
This will launch a window for a simple projectile motion demo.

If you want to access the dynamic environments, you can add the following argument:
```
-env:projectile.json
```
Currently, there are two examples built into the program, [projectile.json](https://github.com/Its-Cryptic/CS1400-Semester-Project/blob/main/src/main/resources/projectile.json) and [orbit.json](https://github.com/Its-Cryptic/CS1400-Semester-Project/blob/main/src/main/resources/orbit.json)

You can make your own environment configs and run it similarly by having the jar and JSON files in the same folder.

## JSON File Structure:
```
{ } The root tag
 | - [ ] constants: A list of constants that can be used in the forces
 |    | - ( ) name: The name of the constant, use the name in force strings
 |    ∟ - ( ) value: The value of the constant
 ∟ - [ ] objects: A list of objects in the environment
      | - ( ) name: The name of the object
      | - { } initial_values: The initial values of the object
      |    | - ( ) mass: The mass of the object
      |    | - [ ] position: A list of floats representing a 3D position
      |    |    | - ( ) x: The x position component
      |    |    | - ( ) y: The y position component
      |    |    ∟ - ( ) z: The z position component
      |    ∟ - [ ] velocity: A list of floats representing a 3D velocity
      |         | - ( ) x: The x velocity component
      |         | - ( ) y: The y velocity component
      |         ∟ - ( ) z: The z velocity component
      ∟ - [ ] forces: A list of forces acting on the object
           | - ( ) name: The name of the force
           ∟ - [ ] force: A list of floats or strings representing a 3D force
                | - ( ) x: The x force component (Can be a float or a string)
                | - ( ) y: The y force component (Can be a float or a string)
                ∟ - ( ) z: The z force component (Can be a float or a string)
```

## Example:
Below is a simple projectile motion simulation of an object with a mass of 5kg, initial velocity of (20, 40, 0) m/s, and a gravitational force acting on it proportional to its mass.
```json
{
  "constants": [
    {
      "name": "g",
      "value": -9.81
    }
  ],
  "objects": [
    {
      "name": "projectile",
      "initial_values": {
        "mass": 5,
        "position": [0, 0, 0],
        "velocity": [20, 40, 0]
      },
      "forces": [
        {
          "name": "gravity",
          "force": [0, "g * projectile.mass", 0]
        }
      ]
    }
  ]
}
```
