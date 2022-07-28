COS126 Final Project: Implementation

Please complete the following questions and upload this readme.txt to the
TigerFile assignment for the "Final Project Implementation".


/**********************************************************************
 * Basic Information                                                  *
 **********************************************************************/

Name 1: Oliver Shu

NetID 1: yizhans

Name 2: Wilbert Joseph

NetID 2: wj9138

Project preceptor name: John Yang

Project title: Cellular Automata

CodePost link for proposal feedback: https://codepost.io/code/488239

Link to project video: https://www.loom.com/share/3c9d90ccaa204426941a86cb02a2f3c6

Approximate number of hours to complete the final project
Number of hours: 24 hrs


/**********************************************************************
 * Required Questions                                                 *
 **********************************************************************/

Describe your project in a few sentences.

Our project implements a cellular automata with two or three states and all the possible survival rules, whose initial pattern can be randomly generated or read from an input file. Our project also draws the animation of the cellular automata, and implements a basic GUI; you can pause the animation, reset the pattern to its initial state, and change the status of cells by mouse click.


Describe in detail your three features and where in your program
they are implemented (e.g. starting and ending line numbers,
function names, etc.).
1.

Our first feature is to construct a cellular automata. The program initializes the size, survival rule, number of states, and the initial pattern. The initial pattern can be read from a file or randomly generated.

Line 28 - 127, with two constructors CellularAutomata(size, rule, prob) & CellularAutomata(size, rule, filename), and a couple of helper functions for code reusability.


2.

Our second feature is to update the cellular automata according to the rule. We first count for every cell how many neighbors are alive, and update according to this count. Also the current status of the automata can be reset to the initial pattern.

Line 143 - 219, with methods update() and reset().


3.

Our third feature is to draw the animation of the automata, and a GUI which allows the user to pause, resume and reset the automata, or to change the status of particular cells by mouse click when paused.

Line 223 - 336, with method drawAnimation(), and a bunch of helper functions.


Describe in detail how to compile and run your program. Include a few example
run commands and the expected results of running your program. For non-textual
outputs (e.g. graphical or auditory), feel free to describe in words what the
output should be or reference output files (e.g. images, audio files) of the
expected output.


Compile by – javac-introcs CellularAutomata.java
Run by specifying three command line input describing the size, rule and initial pattern.
– java-introcs CellularAutomata <size> <String rule> <double prob or String filename>

For example,
Conway’s Game of Life:
– java-introcs CellularAutomata 64 23/3/2 0.6
Seed:
– java-introcs CellularAutomata 64 23/3/2 data/seed.txt



Describe how your program accepts user input and mention the line number(s) at
which your program accepts user input.

The program accepts command line arguments for the constructor as the user input, at line 385 and 388.



Describe how your program produces output based on user input (mention line
numbers).

Our program draws the animation of the automata, along with a GUI, using the StdDraw library, at line 390.



Describe the data structure your program uses and how it supports your program's
functionality (include the variable name and the line number(s) at which it is
declared and initialized).

Our program mainly uses many 2d boolean arrays to describe the status of the cells.
At lines 21-24 we declares private boolean[][] status; private boolean[][] isDying; and private boolean[][] initialStatus;




List the two custom functions written by your project group, including function
signatures and line numbers; if your project group wrote more than two custom
functions, choose the two functions that were most extensively tested.
1. 
Line 57
public CellularAutomata(int size, String rule, String filename)

2.
Line 189
public void update()


List the line numbers where you test each of your two custom functions twice.
For each of the four tests (two for each function), explain what was being
tested and the expected result. For non-textual results (e.g. graphical or
auditory), you may describe in your own words what the expected result
should be or reference output files (e.g. images, audio files).
1.
Line 343-345

The test function outputs the initial pattern, with each row representing an alive cell. We can check against the input file to see whether the constructor works well.


2.
Line 347-349

Same as 1

3.
Line 355-357

The test function outputs the pattern after one update. The patterns are so simple that we can derive the new pattern after one update by hand to see whether it agrees with the output. If so, then the update function works well.


4.
Line 359-361

Same as 3



/**********************************************************************
 * Citing Resources                                                   *
 **********************************************************************/

List below *EVERY* resource your project group looked at (bullet lists and
links suffice).


1. https://robinforest.net/post/cellular-automata/  An interesting cellular automata animation
2. https://en.wikipedia.org/wiki/Seeds_(cellular_automaton)
3. https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
4. https://en.wikipedia.org/wiki/Brian%27s_Brain



Remember that you should *ALSO* be citing every resource that informed your
code at/near the line(s) of code that it informed.

Did you receive help from classmates, past COS 126 students, or anyone else?
If so, please list their names.  ("A Sunday lab TA" or "Office hours on
Thursday" is ok if you don't know their name.)
Yes or no?

No

Did you encounter any serious problems? If so, please describe.
Yes or no?


No

List any other comments here.




/**********************************************************************
 * Submission Checklist                                               *
 **********************************************************************/

Please mark that you’ve done all of the following steps:
[x] Created a project.zip file, unzipped its contents, and checked that our
    compile and run commands work on the unzipped contents. Ensure that the .zip
    file is under 50MB in size.
[x] Created and uploaded a Loom or YouTube video, set its thumbnail/starting
    frame to be an image of your program or a title slide, and checked that
    the video is viewable in an incognito browser.
[x] Uploaded all .java files to TigerFile.
[x] Uploaded project.zip file to TigerFile.

After you’ve submitted the above on TigerFile, remember to do the following:
[x] Complete and upload readme.txt to TigerFile.
[x] Complete and submit Google Form
    (https://forms.cs50.io/de2ccd26-d643-4b8a-8eaa-417487ba29ab).


/**********************************************************************
 * Partial Credit: Bug Report(s)                                      *
 **********************************************************************/

For partial credit for buggy features, you may include a bug report for at
most 4 bugs that your project group was not able to fix before the submission
deadline. For each bug report, copy and paste the following questions and
answer them in full. Your bug report should be detailed enough for the grader
to reproduce the bug. Note: if your code appears bug-free, you should not
submit any bug reports.

BUG REPORT #1:
1. Describe in a sentence or two the bug.




2. Describe in detail how to reproduce the bug (e.g. run commands, user input,
   etc.).




3. Describe the resulting effect of bug and provide evidence (e.g.
   copy-and-paste the buggy output, reference screenshot files and/or buggy
   output files, include a Loom video of reproducing and showing the effects of
   the bug, etc.).




4. Describe where in your program code you believe the bug occurs (e.g. line
   numbers).




5. Please describe what steps you tried to fix the bug.





/**********************************************************************
 * Extra Credit: Runtime Analysis                                     *
 **********************************************************************/

You may earn a small amount of extra credit by analyzing the efficiency of one
substantial component of your project. Please answer the following questions if
you would like to be considered for the extra credit for program analysis
(remember to copy and paste your answers to the following questions into the
Google form as well for credit).

Specify the scope of the component you are analyzing (e.g. function name,
starting and ending lines of specific .java file).




What is the estimated runtime (e.g. big-O complexity) of this component?
Provide justification for this runtime (i.e. explain in your own words why
you expect this component to have this runtime performance).




Provide experimental evidence in the form of timed analysis supporting this
runtime estimate. (Hint: you may find it helpful to use command-line
arguments/flags to run just the specified component being analyzed).





/**********************************************************************
 * Extra Credit: Packaging project as an executable .jar file         *
 **********************************************************************/

You may earn a small amount of extra credit by packaging your project as an
executable .jar file. Please answer the following question if you would like to
be considered for this extra credit opportunity (remember to copy and paste your
answers to the following questions into the Google form as well for credit).

Describe in detail how to execute your .jar application (e.g. what execution
command to use on the terminal). Include a few example execution commands and
the expected results of running your program. For non-textual outputs
(e.g. graphical or auditory), feel free to describe in words what the output
should be or reference output files (e.g. images, audio files) of the expected
output.



