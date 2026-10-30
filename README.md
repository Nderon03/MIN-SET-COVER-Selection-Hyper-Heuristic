## Setup and Running the Program

### 1. Project Setup
Download the `src` folder, output files and test instances, and place them within the same project directory. Ensure the directory structure is maintained so the program can locate the required files.

### 2. Select the Problem Instance
Open the `CwRunner` class and locate the following line:

```java
Problem problem = new Problem(" ");
```

Replace the empty string with the file path of the test instance you want to use.

### 3. Configure the Output File
In `CwRunner`, locate:

```java
String filename = " ";
```

Replace the empty string with the destination path and filename where you want the program's results to be saved.

### 4. Configure the Runtime and Iterations
The program's execution settings can be adjusted within `CwRunner`:

- **Runtime:** Change the value associated with the `long end` variable to set the desired trial duration in milliseconds. For example, `20000` represents 20 seconds.
- **Maximum steps:** Adjust the maximum number of steps to control how many iterations each trial can perform.

If a trial finishes before the requested runtime, the actual execution time is displayed.

### 5. Run the Program
Run the `CwRunner` class, which contains the program's `main` method and manages the experiments.

### 6. Viewing the Results

**IDE Console Output**

During execution, the console displays the best objective values obtained by each heuristic at each iteration.

Once all trials are complete, the console displays:
- Total execution time.
- Fastest and slowest heuristics.
- Best and worst reinforcement learning scores.
- The overall best-performing heuristic.

**Output File**

The selected output file records the experimental results, including:
- The best objective value and current solution values for each iteration.
- The best objective value achieved in each trial.
- The corresponding best solution for each trial.

The results allow the performance of the different heuristics to be compared across the experiments.
