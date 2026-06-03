package ph.edu.dlsu.lbycpob;

// ============================================================
// StudentInputHandler.java
// RESPONSIBILITY: All console input and validation for students
// SRP: Reads raw data from the user — no storage, no computation
// STATIC CONCEPT: NO static methods here because this class
//   could reasonably be subclassed or swapped for a different
//   input source (e.g., file input). Instance design preserves
//   that flexibility.
//   However, we DO remove the old "private GradeCalculator calculator"
//   and "private GradeConstants C" instance fields — replaced by
//   direct static calls: GradeCalculator.computeAverage(...)
//   and GradeConstants.NUM_MODULES.
// ============================================================
public class StudentInputHandler {


    // [UNDERSTAND] No instance fields needed here anymore.
    //              GradeConstants fields and GradeCalculator methods
    //              are now static — called via their class names directly.

    // [INPUT] Prompts for and returns a validated, non-empty name
    // [UNDERSTAND] do-while used because the prompt must run at least
    // once; emptiness can only be checked after reading the input.
    public String inputName(int studentNumber) {
        String name;
        do {
            IO.print("Student " + studentNumber + " name: ");
            name = IO.readln().trim();
            if (name.isEmpty()) IO.println("Name cannot be blank. Try again.");
        } while (name.isEmpty());
        return name;
    }


    // [INPUT] Prompts for and returns a validated student count (1–MAX)
    // [UNDERSTAND] do-while used because the count prompt must appear
    // before the range check. A while loop would need an awkward
    // prime-read before the loop body.
    // [TRACE] GradeConstants.MAX_STUDENTS replaces the old C.MAX_STUDENTS
    //         because MAX_STUDENTS is now a static field.
    public int inputStudentCount() {
        int count;
        do {
            IO.print("How many students? (1-" + GradeConstants.MAX_STUDENTS + "): ");
            count = Integer.parseInt(IO.readln().trim());
            if (count < 1 || count > GradeConstants.MAX_STUDENTS)
                IO.println("Please enter a number between 1 and "
                        + GradeConstants.MAX_STUDENTS + ".");
        } while (count < 1 || count > GradeConstants.MAX_STUDENTS);
        return count;
    }

    // [INPUT] Prompts for and returns a validated single component score
    // [UNDERSTAND] Extracted to a helper to avoid duplication — all
    // single-score components share identical input/validation logic.
    // [TRACE] Valid range: GradeConstants.MIN_SCORE to GradeConstants.MAX_SCORE
    public double inputComponentScore(String prompt) {
        double score;
        do {
            IO.print(prompt);
            score = Double.parseDouble(IO.readln().trim());
            if (score < GradeConstants.MIN_SCORE || score > GradeConstants.MAX_SCORE)
                IO.println("  Score must be between "
                        + GradeConstants.MIN_SCORE + " and "
                        + GradeConstants.MAX_SCORE + ".");
        } while (score < GradeConstants.MIN_SCORE || score > GradeConstants.MAX_SCORE);
        return score;
    }


    // [INPUT] Reads 5 module scores, averages them, and returns the result
    // [UNDERSTAND] Isolated because lab performance requires special
    // handling: first average five module scores, then apply weight.
    // [TRACE] GradeCalculator.computeAverage() is now a static call —
    //         no "calculator" object needed.
    public double inputLabPerformance() {
        double[] moduleScores = new double[GradeConstants.NUM_MODULES];

        IO.println("  --- Laboratory Performance (40% of grade) ---");

        // [UNDERSTAND] for loop used because the number of modules
        // (NUM_MODULES) is known before iteration begins.
        for (int module = 0; module < GradeConstants.NUM_MODULES; module++) {
            moduleScores[module] = inputComponentScore(
                    "    Module " + (module + 1) + " score: ");
        }

        // [TRACE] Static call: GradeCalculator.computeAverage(moduleScores)
        //         No "new GradeCalculator()" object required.
        double labAverage = GradeCalculator.computeAverage(moduleScores);
        IO.println("    Laboratory Performance Average: "
                + String.format("%.2f", labAverage));
        return labAverage;
    }

    // [INPUT] Orchestrates full data entry for one student;
    //         returns a fully constructed Student object
    // [CHANGE] Now collects ID via inputIdNumber() before the name,
    //          then passes it into the Student constructor.
    public Student inputOneStudent(int studentNumber) {
        String name     = inputName(studentNumber);

        // [NEW] Collect and verify the student ID number
        // [TRACE] inputIdNumber() loops until a valid ID is entered
        String idNumber = inputIdNumber(studentNumber);

        IO.println("  --- Entering grades for " + name + " ---");

        // [INPUT] COMPONENT 1: Laboratory Performance — Weight: 40%
        double labPerformance     = inputLabPerformance();

        // [INPUT] COMPONENT 2: Class Participation — Weight: 5%
        double classParticipation = inputComponentScore(
                "  Class Participation (averaged): ");

        // [INPUT] COMPONENT 3: Teacher's Evaluation — Weight: 5%
        double teacherEvaluation  = inputComponentScore(
                "  Teacher's Evaluation (averaged): ");

        // [INPUT] COMPONENT 4: Practical Exam — Weight: 20%
        double practicalExam      = inputComponentScore(
                "  Practical Exam: ");

        // [INPUT] COMPONENT 5: Project — Weight: 30%
        double project            = inputComponentScore(
                "  Project: ");

        // [COMPUTE] Static calls — no calculator object needed
        double rawGrade     = GradeCalculator.computeRawGrade(
                labPerformance, classParticipation,
                teacherEvaluation, practicalExam, project);
        String numericGrade = GradeCalculator.assignNumericGrade(rawGrade);
        char   letterRank   = GradeCalculator.assignLetterRank(rawGrade);

        IO.println("  " + name + "'s final raw score: "
                + String.format("%.2f", rawGrade));

        // [RETURN] Construct and return the Student object — now includes idNumber
        // [TRACE] new Student(name, idNumber, rawGrade, numericGrade, letterRank)
        // return new Student(name, idNumber, rawGrade, numericGrade, letterRank);
        return new Student(name, idNumber, rawGrade, numericGrade, letterRank);
    }

    // [INPUT] Prompts for and returns a verified student ID number
    //         Uses IDVerifier.validateID() (static) to check the ID.
    //       Loop continues until the ID passes verification.
    // [UNDERSTAND] do-while used because the prompt must run at least
    //              once before the result can be checked.
    // [TRACE] "12399999" → IDVerifier.validateID returns "Valid student ID number."
    //         → does NOT start with "Invalid" → accepted, loop exits
    // [TRACE] "99999999" → validateID returns "Invalid ID number. Dot product..."
    //         → starts with "Invalid" → rejected, loop repeats
    public String inputIdNumber(int studentNumber) {
        String idNumber;
        String validationResult;

        do {
            IO.print("Student " + studentNumber + " ID number ("
                    + GradeConstants.ID_LENGTH + " digits): ");
            idNumber = IO.readln().trim();

            // [TRACE] Static call — IDVerifier.validateID(idNumber)
            //         Returns a descriptive result message string.
            validationResult = IDVerifier.validateID(idNumber);

            if (validationResult.startsWith("Invalid")) {
                // [TRACE] Print the specific reason the ID was rejected
                IO.println("  " + validationResult + " Please try again.");
            } else {
                // [TRACE] Print confirmation so the user knows it was accepted
                IO.println("  " + validationResult);
            }

        } while (validationResult.startsWith("Invalid"));

        return idNumber;
    }
}

