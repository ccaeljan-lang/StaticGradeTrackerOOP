package ph.edu.dlsu.lbycpob;

// ============================================================
// GradeCalculator.java
// RESPONSIBILITY: All grade computation logic
// SRP: Computes averages, raw grades, numeric grades, letter
//      ranks, and remarks — no I/O, no storage
// STATIC CONCEPT: Every method here is static because:
//   (1) None of them use instance fields (there is no "this.x").
//   (2) They are pure functions — output depends only on the
//       parameters passed in, nothing stored in the object.
//   (3) Callers write GradeCalculator.computeAverage(scores)
//       instead of "new GradeCalculator().computeAverage(scores)".
//   RULE OF THUMB: If a method never touches "this", make it static.
// ============================================================

public class GradeCalculator {

    // [COMPUTE] AVERAGE COMPUTATION METHOD
    // [STATIC] No instance state used — purely input → output math.
    // [TRACE] Input:  scores array of doubles (size varies)
    // [TRACE] Output: arithmetic mean as double
    // [UNDERSTAND] I used a for-each loop because I only need to read
    // values, not modify them or access indices.
    public static double computeAverage(double[] scores) {
        if (scores == null || scores.length == 0) { // [UNDERSTAND] Validates array if empty.
            return 0.0; // [UNDERSTAND] If it does then return value 0.
        }

        double sum = 0.0d; // [DECLARE] Sum: stores accumulating sum of scores.
        // [UNDERSTAND] for-each loop is best used for arrays to not depend on index values.
        // [DECISION] Used for-each loop as the auto-checker always reports out-of bounds results.
        for (double score : scores) {
            sum += score; // [UNDERSTAND] Accumulating sum.
        }

        return sum / scores.length; // [UNDERSTAND] Returns quotient which is the average itself.
    }

    // [COMPUTE] RAW GRADE COMPUTATION METHOD (individual parameters)
    // [STATIC] Reads only its parameters and GradeConstants static fields.
    //          No instance variables touched — qualifies as static.
    // [TRACE] Input:  five component scores (0.0 – 100.0 each)
    // [TRACE] Output: weighted raw score rounded to 2 decimal places
    // [UNDERSTAND] Using individual parameters makes the method signature
    // self-documenting; callers must supply all five components explicitly,
    // reducing the chance of misplacing array indices.
    public static double computeRawGrade(double labPerformance,
                                         double classParticipation,
                                         double teacherEvaluation,
                                         double practicalExam,
                                         double project) {

        // [TRACE] Each component multiplied by its weight, then summed
        // [UNDERSTAND] GradeConstants.LAB_WEIGHT replaces the old C.LAB_WEIGHT
        //              because GradeConstants fields are now static — accessed
        //              directly via the class name, no object needed.
        double raw = (labPerformance * 0.40) + (classParticipation * 0.05) +
                (teacherEvaluation * 0.05) + (practicalExam * 0.20) + (project * 0.30);
        // [UNDERSTAND] Each grade is multiplied to its weight, which would make the overall sum within 0-100 range.

        // [TRACE] Round to 2 decimal place
        return Math.round(raw * 100.0) / 100.0; // [UNDERSTAND] Returns rounded-off grade.
    }

    // [GRADE] NUMERIC GRADE ASSIGNMENT METHOD
    // [STATIC] Pure function: same input always produces the same output.
    // [TRACE] avg = 97.0 -> avg >= 96.0 true  -> "4.0"
    // [TRACE] avg = 86.6 -> avg >= 83.0 true  -> "2.5"
    // [TRACE] avg = 65.0 -> all conditions false -> "0.0"
    // [UNDERSTAND] if-else-if ladder used because boundaries are numeric
    // ranges, not discrete values a switch expression can test directly.
    public static String assignNumericGrade(double avg) {
        // [UNDERSTAND] Decides and return numeric grade based on range of scores.
        // [DECISION] Incorporated return on each branch for more code readability.
        if (avg < 70) {
            // [TRACE] avg = 68.5 -> avg < 70.0 is true -> returns "0.0"
            return "0.0";
        } else if (avg < 74) {
            // [TRACE] avg = 71.2 -> first condition fails, avg < 74.0 is true -> returns "1.0"
            return "1.0";
        } else if (avg < 78) {
            // [TRACE] avg = 76.8 -> first two fail, avg < 78.0 is true -> returns "1.5"
            return "1.5";
        } else if (avg < 83) {
            // [TRACE] avg = 81.0 -> previous conditions fail, avg < 83.0 is true -> returns "2.0"
            return "2.0";
        } else if (avg < 88) {
            // [TRACE] avg = 86.6 -> previous conditions fail, avg < 88.0 is true -> returns "2.5"
            return "2.5";
        } else if (avg < 92) {
            // [TRACE] avg = 91.0 -> previous conditions fail, avg < 92.0 is true -> returns "3.0"
            return "3.0";
        } else if (avg < 96) {
            // [TRACE] avg = 94.0 -> previous conditions fail, avg < 96.0 is true -> returns "3.5"
            return "3.5";
        } else {
            // [TRACE] avg = 97.0 -> all conditions fail -> else branch -> returns "4.0"
            return "4.0";
        }
    }

    // [GRADE] LETTER RANK ASSIGNMENT METHOD
    // [STATIC] Pure function — no instance fields referenced.
    // [TRACE] avg = 97.0 -> 'S'   avg = 86.6 -> 'C'
    // [TRACE] avg = 72.0 -> 'P'   avg = 65.0 -> 'F'
    public static char assignLetterRank(double avg) {
        // [UNDERSTAND] Decides and assigns based on range of scores.
        // [DECISION] Incorporated return on each branch for more code readability.
        if (avg < 70) {
            // [TRACE] avg = 68.5 -> avg < 70.0 is true -> returns 'F'
            return 'F';
        } else if (avg < 74) {
            // [TRACE] avg = 71.2 -> avg < 74.0 is true (after failing <70) -> returns 'P'
            return 'P';
        } else if (avg < 78) {
            // [TRACE] avg = 76.8 -> avg < 78.0 is true (after failing <70, <74) -> returns 'E'
            return 'E';
        } else if (avg < 83) {
            // [TRACE] avg = 81.0 -> avg < 83.0 is true (after previous failures) -> returns 'D'
            return 'D';
        } else if (avg < 88) {
            // [TRACE] avg = 86.6 -> avg < 88.0 is true -> returns 'C'
            return 'C';
        } else if (avg < 92) {
            // [TRACE] avg = 91.0 -> avg < 92.0 is true -> returns 'B'
            return 'B';
        } else if (avg < 96) {
            // [TRACE] avg = 94.0 -> avg < 96.0 is true -> returns 'A'
            return 'A';
        } else {
            // [TRACE] avg = 97.0 -> all previous conditions fail -> returns 'S'
            return 'S';
        }
    }

    // [REMARK] REMARKS METHOD — overloaded for numeric grade String
    // [STATIC] Input string maps deterministically to an output string.
    //          No object state involved — static is correct here.
    // [TRACE] Input:  grade string e.g. "4.0", "3.5" … "0.0"
    // [TRACE] Output: remark string describing performance level
    // [UNDERSTAND] Switch expression used because inputs are a small,
    // discrete set of known strings — more readable than if-else chains.
    public static String getRemarks(String grade) {
        String remarks = ""; // [DECLARE] Remarks: stores remarks of a student for returning value.
        // [DECISION] remark variable reassigning works unlike returning it directly for each branch.
        // [UNDERSTAND] Trimming the input because ots a string would make it accurate for each cases of the switch.
        // [DECISION] I trimmed the value so it would assign the value properly.
        // Used switch since I'm comparing one value.
        switch (grade.trim()) { // [AI-CHECK]: Confirmed that this works instead of trimming in the function call.
            // [TRACE] grade = "0.0" -> match found -> returns "Failed"
            case "0.0" -> remarks = "Failed";
            // [TRACE] grade = "1.0" -> match found -> returns "Poor/Passed"
            case "1.0" -> remarks = "Poor/Passed";
            // [TRACE] grade = "1.5" -> match found -> returns "Fair"
            case "1.5" -> remarks = "Fair";
            // [TRACE] grade = "2.0" -> match found -> returns "Satisfactory"
            case "2.0" -> remarks = "Satisfactory";
            // [TRACE] grade = "2.5" -> match found -> returns "Good"
            case "2.5" -> remarks = "Good";
            // [TRACE] grade = "3.0" -> match found -> returns "Very Good"
            case "3.0" -> remarks = "Very Good";
            // [TRACE] grade = "3.5" -> match found -> returns "Superior"
            case "3.5" -> remarks = "Superior";
            // [TRACE] grade = "4.0" -> match found -> returns "Excellent"
            case "4.0" -> remarks = "Excellent";
            // [TRACE] grade = " + grade + " -> no match found -> returns "Unknown"
            default -> remarks = "Unknown";
        }
        return remarks; // [UNDERSTAND] Returns remark.
    }

    // [REMARK] REMARKS METHOD — overloaded for letter rank char
    // [STATIC] Same reasoning: pure mapping, no instance state.
    // [UNDERSTAND] Java allows two methods with the same name "getRemarks"
    //              as long as their parameter types differ — this is called
    //              METHOD OVERLOADING. The compiler picks the right one
    //              based on whether you pass a String or a char.
    // [TRACE] Input:  rank char e.g. 'S', 'A', 'B' … 'F'
    // [TRACE] Output: remark string describing performance level
    public static String getRemarks(char rank) {
        String remarks = ""; // [DECLARE] Remarks: stores remarks of a student for returning value.
        // [DECISION] remark variable reassigning works unlike returning it directly for each branch.
        // [UNDERSTAND] toUpperCase() turns a string/character into uppercase.
        // [DECISION] Used toUpperCase() so that when variations of lowercase exist it could still work properly.
        // Used switch since I'm comparing one value.
        switch (Character.toUpperCase(rank)) { // [AI-CHECK]: Confirmed how to use toUpperCase method.
            // [TRACE] grade = "0.0" -> match found -> returns "Failed"
            case 'F' -> remarks = "Failed";
            // [TRACE] grade = "1.0" -> match found -> returns "Poor/Passed"
            case 'P' -> remarks = "Poor/Passed";
            // [TRACE] grade = "1.5" -> match found -> returns "Fair"
            case 'E' -> remarks = "Fair";
            // [TRACE] grade = "2.0" -> match found -> returns "Satisfactory"
            case 'D' -> remarks = "Satisfactory";
            // [TRACE] grade = "2.5" -> match found -> returns "Good"
            case 'C' -> remarks = "Good";
            // [TRACE] grade = "3.0" -> match found -> returns "Very Good"
            case 'B' -> remarks = "Very Good";
            // [TRACE] grade = "3.5" -> match found -> returns "Superior"
            case 'A' -> remarks = "Superior";
            // [TRACE] grade = "4.0" -> match found -> returns "Excellent"
            case 'S' -> remarks = "Excellent";
            // [TRACE] grade = " + grade + " -> no match found -> returns "Unknown"
            default -> remarks = "Unknown";
        }
        return remarks;
    }
}