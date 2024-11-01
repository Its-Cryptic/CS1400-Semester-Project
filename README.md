# CS1400-Semester-Project
Semester Project for CS1400

This project is a highly configurable physics engine that can take a JSON config file and construct an environment with both static and dynamic forces.

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

# How to run:
#### 1. Clone the repository
#### 2. Load the gradle project (This is important! The project uses numerous libraries and will not run without them)
#### 2. Build the project with gradle (Tasks -> build -> build)
#### 3. Run the project with the following command:
```bash
java -jar build/libs/CS1400-Semester-Project-1.0-SNAPSHOT.jar <path_to_config_file>
```