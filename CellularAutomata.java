/* Simulate a cellular automata, whose size, rule and initial pattern is given bu user input.
Draws a GUI which allows you to pause, resume, reset the animation,
and also change the status of cells by mouse clicks
e.g. -- javac-introcs CellularAutomata.java
     -- java-introcs CellularAutomata 64 23/3/2 0.6
 */


public class CellularAutomata {

    private int size;       // 2D CA is of dimension <size> x <size>

    private boolean[] ruleOn;
            // ruleOn[i] is true if for an alive cell, i alive out of all the 8 neighbors
    // would make it alive in the next time step. i = 0, 1, ..., 8.
    private boolean[] ruleOff;
            // ruleOff[i] is true if for a dead cell, i alive out of all the 8 neighbors
    // would make it alive in the next time step
    private int numStates;
            // number of different states. We only consider 2 states for alive/dead, and 3 states for another dying state
    // dying means alive in the previous time step, and will be dead in the next time step
    // the above three variables are encoded in a rule string like "23/3/2", described in the constructor

    private boolean[][] status;        // current pattern, true if a cell is alive
    private boolean[][] isDying;       // true if a cell is dying

    private boolean[][] initialStatus; // initial pattern. Only for non-randomly generated CA
    private double prob;
            // probability that a cell is alive in initial state. Only for randomly generated CA


    // Constructor for randomly-generated CA. Creates a CA with <size> and <rule>, and a random initial pattern.
    // For the initial pattern, every cell has a probability of <prob> to be alive.
    // rule is encoded in the form of "<ruleOn>/<ruleOff>/<numStates>" like "23/2/3". Details in the helper functions below.
    public CellularAutomata(int size, String rule, double prob) {
        // size
        if (size < 1)
            throw new IllegalArgumentException("the size of the CA should be a positive integer!");
        this.size = size;

        // ruleOn, ruleOff and numStates
        readRuleString(rule);

        // prob
        if (prob < 0.0 || prob > 1.0)
            throw new IllegalArgumentException("probability should be within 0 and 1");
        this.prob = prob;

        // Consider initial pattern consisting of only alive and dead, with no dying
        this.status = randomPattern(this.size, this.prob);
        this.isDying = new boolean[size][size];

        // don't need this variable for randomly generated CA
        initialStatus = null;
    }

    // Constructor for non-randomly generated CA. The only difference with the previous constructor
    // is that the initial pattern is read from a file, in the form that every row of "<row> <col>"
    // represents the location of an alive cell
    // e.g. "1 1 \n 2 1 \n 2 2"
    public CellularAutomata(int size, String rule, String filename) {
        // size
        if (size < 1)
            throw new IllegalArgumentException("the size of the CA should be a positive integer!");
        this.size = size;

        // ruleOn, ruleOff and numStates
        readRuleString(rule);

        // Initial pattern
        readPattern(filename);
        this.isDying = new boolean[size][size];

        // stores initial pattern for resetting
        this.initialStatus = copy(this.status);

        // don't need this variable for non-randomly generated CA
        this.prob = -1;
    }

    // helper instance method reading initial pattern from a file
    private void readPattern(String filename) {
        this.status = new boolean[this.size][this.size];
        In in = new In(filename);
        while (!in.isEmpty())
            this.status[in.readInt()][in.readInt()] = true;
    }


    // helper instance method to initialize the variables ruleOn, ruleOff, numStates
    // <rule> is of form "<ruleOn>/<ruleOff>/<numStates>"
    private void readRuleString(String rule) {
        String[] stArray = rule.split("/");
        this.ruleOn = readRuleOnOrOff(stArray[0]);
        this.ruleOff = readRuleOnOrOff(stArray[1]);
        this.numStates = Integer.parseInt(stArray[2]);
    }

    // helper static method to translate ruleOn and ruleOff.
    // Input <st> contains the number of alive neighbors of a cell to make the cell alive in the next time step
    // e.g. input st="23" would output rule[i] == true iff i == 2 or 3
    private static boolean[] readRuleOnOrOff(String st) {
        boolean[] ruleOnOrOff = new boolean[9];
        for (int i = 0; i < st.length(); i++)
            ruleOnOrOff[Character.getNumericValue(st.charAt(i))] = true;
        return ruleOnOrOff;
    }

    // helper static method to copy contents of grid onto another.
    private static boolean[][] copy(boolean[][] arr) {
        int numRows = arr.length;
        int numCols = arr[0].length;
        boolean[][] copy = new boolean[numRows][numCols];
        for (int row = 0; row < numRows; row++)
            for (int col = 0; col < numCols; col++)
                copy[row][col] = arr[row][col];
        return copy;
    }


    // helper static method to generate a random pattern with dimension <size> x <size>
    // every cell is true with probability <prob>
    private static boolean[][] randomPattern(int size, double prob) {
        boolean[][] pattern = new boolean[size][size];
        for (int row = 0; row < size; row++)
            for (int col = 0; col < size; col++) {
                if (StdRandom.uniform() < prob)
                    pattern[row][col] = true;
            }
        return pattern;
    }

    // print the current pattern. Outputs <size> and the pattern expressed in the same way as the input file
    public void printPattern() {
        StringBuilder sb = new StringBuilder();
        /* String s = size + "\n";
        sb.append(s); */
        for (int row = 0; row < size; row++)
            for (int col = 0; col < size; col++)
                if (status[row][col]) {
                    String s = row + " " + col + "\n";
                    sb.append(s);
                }
        System.out.println(sb);
    }

    // For the current pattern, the function counts how many neighbors are alive for every cell.
    // the cells on the corner or edges also have 8 neighbors that are on the opposite edge for ease of code.
    // Otherwise we should treat these corner points differently by tons of lines
    // we consider alive cells and add the count to its neighbors
    public static int[][] countOnNeighbor(boolean[][] status) {
        int size = status.length;
        int[][] count = new int[size][size];
        for (int row = 0; row < size; row++)
            for (int col = 0; col < size; col++) {
                if (status[row][col]) {
                    // uses floorMod to avoid negative indices
                    int top = Math.floorMod(row - 1, size);
                    int bot = Math.floorMod(row + 1, size);
                    int left = Math.floorMod(col - 1, size);
                    int right = Math.floorMod(col + 1, size);

                    count[top][left]++;
                    count[top][col]++;
                    count[top][right]++;

                    count[row][left]++;
                    count[row][right]++;

                    count[bot][left]++;
                    count[bot][col]++;
                    count[bot][right]++;
                }
            }
        return count;
    }

    // print the count result for testing.
    public static void printCount(int[][] count) {
        int size = count.length;
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                String s = count[row][col] + " ";
                sb.append(s);
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    // update the pattern for one time step
    public void update() {
        int[][] count = countOnNeighbor(status);
        boolean[][] newStatus = new boolean[size][size];
        boolean[][] newDyingStatus = new boolean[size][size];
        for (int row = 0; row < size; row++)
            for (int col = 0; col < size; col++) {
                if (status[row][col] && ruleOn[count[row][col]] ||
                        !isDying[row][col] && !status[row][col] &&
                                ruleOff[count[row][col]])
                    newStatus[row][col] = true;
                // previously alive cells are now dying
                if (numStates == 3 && status[row][col])
                    newDyingStatus[row][col] = true;
            }
        isDying = newDyingStatus;
        status = newStatus;
    }

    // resets grid layout to initial status. The initial status refers to the inputted pattern
    // for the non-randomly generated CA, and a new random-generated pattern with the same probability
    // for the randomly generated CA.
    public void reset() {
        // if manually inputted initial status
        if (this.initialStatus != null)
            this.status = copy(this.initialStatus);
        else
            this.status = randomPattern(this.size, this.prob);

        // resets dying cells as well
        isDying = new boolean[size][size];
    }


    // helper function transforming the array index into the coordinates of StdDraw
    // cell [<i>][<j>] in the array corresponds to the square in the StdDraw canvas
    // whose top left vertex has coordinate (getX, getY)
    private static int getX(int i, int j, int size) {
        return j;
    }

    private static int getY(int i, int j, int size) {
        return size - i;
    }

    // draw the current pattern by StdDraw
    public void drawPattern() {
        // StdDraw.setScale(0, size);
        for (int row = 0; row < size; row++)
            for (int col = 0; col < size; col++) {
                if (status[row][col])
                    StdDraw.filledSquare(getX(row, col, size) + 0.5, getY(row, col, size) - 0.5,
                                         0.5);
                if (isDying[row][col]) {
                    StdDraw.setPenColor(StdDraw.GRAY);
                    StdDraw.filledSquare(getX(row, col, size) + 0.5, getY(row, col, size) - 0.5,
                                         0.5);
                    StdDraw.setPenColor(StdDraw.BLACK);
                }
            }
    }


    // draw Start and Pause button
    private void drawButton(double heightButton) {
        StdDraw.line(0, size, size, size);
        StdDraw.line(0.333 * size, size, 0.333 * size, (1 + heightButton) * size);
        StdDraw.line(0.667 * size, size, 0.667 * size, (1 + heightButton) * size);
        StdDraw.text(0.167 * size, (1 + 0.5 * heightButton) * size, "Start");
        StdDraw.text(0.5 * size, (1 + 0.5 * heightButton) * size, "Pause");
        StdDraw.text(0.833 * size, (1 + 0.5 * heightButton) * size, "Reset");
    }

    // whether the mouse clicks the Start button area
    private boolean isStart(double x, double y, double heightButton) {
        return (0 < x) && (x < 0.333 * size) && (size < y) && (y < (1 + heightButton) * size);
    }

    // whether the mouse clicks the Pause button area
    private boolean isPause(double x, double y, double heightButton) {
        return (0.333 * size < x) && (x < 0.667 * size) && (size < y) && (y
                < (1 + heightButton) * size);
    }

    // whether the mouse clicks the Reset button area
    private boolean isReset(double x, double y, double heightButton) {
        return (0.667 * size < x) && (x < size) && (size < y) && (y < (1 + heightButton) * size);
    }

    // flips state of cell depending on location of mouse
    private void flip(double x, double y) {
        int col = (int) x;
        int row = (int) (size - y);
        if (isDying[row][col])
            isDying[row][col] = !isDying[row][col];
        else status[row][col] = !status[row][col];
    }

    // Update ball position and draw it.
    private void updateAnimation(double heightButton) {
        StdDraw.clear();
        drawButton(heightButton);
        drawPattern();
        StdDraw.show();
    }

    // draws and displays grid layout and buttons and animates cellular lifespan
    // over small time steps
    public void drawAnimation() {
        double heightButton = 0.15;
        // StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, size);
        StdDraw.setYscale(0, (1 + heightButton) * size);
        StdDraw.enableDoubleBuffering();
        boolean pressed = StdDraw.isMousePressed();
        double x = StdDraw.mouseX();
        double y = StdDraw.mouseY();

        while (true) {
            // while user does not click the Pause button, run the CA
            while (!(pressed && isPause(x, y, heightButton))) {
                pressed = StdDraw.isMousePressed();
                x = StdDraw.mouseX();
                y = StdDraw.mouseY();
                if (pressed && isReset(x, y, heightButton))
                    reset();
                update();
                updateAnimation(heightButton);
                StdDraw.pause(50);
            }

            // while the user does not click the Start button, allow reset and flip
            while (!(pressed && isStart(x, y, heightButton))) {
                pressed = StdDraw.isMousePressed();
                x = StdDraw.mouseX();
                y = StdDraw.mouseY();
                if (pressed && isReset(x, y, heightButton)) {
                    reset();
                    updateAnimation(heightButton);
                }

                StdDraw.pause(100);
                if (!pressed && StdDraw.isMousePressed() && y < size) {
                    flip(x, y);
                    updateAnimation(heightButton);
                }
            }
            // System.out.println("Out!");
            StdDraw.pause(100);
        }
    }

    // The test function tries out 2 different automata, with inputs read from file
    public static void testMethods() {
        System.out.println("Tests for the constructor:");
        // The test function outputs the initial pattern, with each row representing an alive cell.
        // We can check against the input file to see whether the constructor works well.
        CellularAutomata ca1 = new CellularAutomata(64, "23/3/2", "data/test1.txt");
        System.out.println("Test 1:");
        ca1.printPattern();  // test 1

        CellularAutomata ca2 = new CellularAutomata(5, "/2/3", "data/test2.txt");
        System.out.println("Test 2:");
        ca2.printPattern();  // test 2

        // The test function outputs the pattern after one update. The patterns are so simple that
        // we can derive the new pattern after one update by hand to see whether it agrees with the output.
        // If so, then the update function works well.
        System.out.println("Tests for update() function:");
        ca1.update();
        System.out.println("Test 1:");
        ca1.printPattern(); // test 3

        ca2.update();
        System.out.println("Test 2:");
        ca2.printPattern(); // test 4

        System.out.println("Check if the pattern aligns with your expectation.");
    }

    // main method to instantiate CA object and run animation
    public static void main(String[] args) {
        // CellularAutomataOrig ca = new CellularAutomataOrig("data/s4_test.txt");
        // CellularAutomata ca = new CellularAutomata(Integer.parseInt(args[0]), args[1], Double.parseDouble(args[2]));
        // code learned from the final project examples
        if (args[0].equals("-t") || args[0].equals("--test")) {
            testMethods();
            return;
        }
        else if (args[0].equals("--help") || args[0].equals("-h")) {
            StdOut.println("Execution command pattern: java-introcs CellularAutomata "
                                   + "<size> <rule> <prob> or <size> <rule> <filename>");
            StdOut.println("You can also use the --test flag to test your methods");
            return;
        }

        CellularAutomata ca;
        // check whether the third input is a probability number or a text path
        try {
            ca = new CellularAutomata(Integer.parseInt(args[0]), args[1],
                                      Double.parseDouble(args[2]));
        }
        catch (NumberFormatException e) {
            ca = new CellularAutomata(Integer.parseInt(args[0]), args[1], args[2]);
        }
        ca.drawAnimation();
    }
}
