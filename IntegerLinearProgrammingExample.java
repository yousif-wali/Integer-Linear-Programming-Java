import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPConstraint;

public class IntegerLinearProgrammingExample {
    static {
        Loader.loadNativeLibraries();
    }

    public static void main(String[] args) {
        // Create the linear solver with the SCIP backend.
        MPSolver solver = new MPSolver("IntegerLinearProgrammingExample", MPSolver.OptimizationProblemType.SCIP_MIXED_INTEGER_PROGRAMMING);

        // Create the variables x and y.
        MPVariable x = solver.makeIntVar(0.0, Double.POSITIVE_INFINITY, "x");
        MPVariable y = solver.makeIntVar(0.0, Double.POSITIVE_INFINITY, "y");

        // Create a linear constraint: x + 2y ≤ 14
        MPConstraint c1 = solver.makeConstraint(-Double.POSITIVE_INFINITY, 14.0);
        c1.setCoefficient(x, 1);
        c1.setCoefficient(y, 2);

        // Create another linear constraint: 3x - y ≥ 0
        MPConstraint c2 = solver.makeConstraint(0.0, Double.POSITIVE_INFINITY);
        c2.setCoefficient(x, 3);
        c2.setCoefficient(y, -1);

        // Create the objective function: 3x + 4y.
        MPObjective objective = solver.objective();
        objective.setCoefficient(x, 3);
        objective.setCoefficient(y, 4);
        objective.setMaximization();

        // Solve the problem and print the solution.
        final MPSolver.ResultStatus resultStatus = solver.solve();

        if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
            System.out.println("Solution found!");
            System.out.println("Objective value = " + objective.value());
            System.out.println("x = " + x.solutionValue());
            System.out.println("y = " + y.solutionValue());
        } else {
            System.out.println("The problem does not have an optimal solution.");
        }
    }
}
