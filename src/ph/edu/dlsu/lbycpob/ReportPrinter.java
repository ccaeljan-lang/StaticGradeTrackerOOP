package ph.edu.dlsu.lbycpob;

// ============================================================
// ReportPrinter.java
// RESPONSIBILITY: All formatted console output for reports
// SRP: Renders data to the screen — no input, no computation
// STATIC CONCEPT: All methods are static because:
//   (1) They use no instance fields — everything they need is
//       either passed as a parameter or accessed via static
//       calls to GradeCalculator / GradeConstants.
//   (2) Making them static signals to the reader: "This is a
//       stateless utility class — you never need to store a
//       ReportPrinter object."
//   Callers write: ReportPrinter.printReport(repo)
//   instead of:   new ReportPrinter().printReport(repo)
// ============================================================
public class ReportPrinter {

    // [FORMAT] Prints a separator line of '=' characters
    // [STATIC] No instance state — pure output utility.
    public static void printSeparatorLine() {
        // [TRACE] GradeConstants.SEPARATOR replaces old C.SEPARATOR
        //         Accessed via class name because the field is static.
        IO.println("=".repeat(GradeConstants.SEPARATOR));
    }
    // [REPORT] Prints the full tabular grade report for all students
    // [STATIC] repo is passed as a parameter — nothing stored in this object.
    // [CHANGE] Added ID column header and ID data cell in each student row.
    // [UNDERSTAND] StringBuilder used instead of String concatenation
    // because String is immutable; each + creates a new object.
    // StringBuilder uses a mutable buffer, reducing allocations in loops.
    public static void printReport(StudentRepository repo) {
        // [VALIDATE] Guard: no data to display
        if (repo.getCount() == 0) {
            IO.println("No data to display. Please enter student data first.");
            return;
        }

        StringBuilder sb = new StringBuilder();

        // [FORMAT] Table width: 80 characters to fit the new ID column
        final int TABLE_WIDTH = 80;

        // [FORMAT] Build table header
        sb.append("\n");
        sb.repeat("=", TABLE_WIDTH).append("\n");

        // [TRACE] Column layout:
        //   %-5s  = No.       (5  chars, left-aligned)
        //   %-10s = ID        (10 chars, left-aligned)  [NEW]
        //   %-20s = Name      (20 chars, left-aligned)
        //   %-10s = Raw Grade (10 chars, left-aligned)
        //   %-7s  = Grade     (7  chars, left-aligned)
        //   %-6s  = Rank      (6  chars, left-aligned)
        //   %-s   = Remarks   (remaining)
        sb.append(String.format(
                "%-5s %-10s %-20s %-10s %-7s %-6s %-5s%n",
                "No.", "ID", "Name", "Raw Ave.", "Grade", "Rank", "Remarks"));

        sb.repeat("-", TABLE_WIDTH).append("\n");
        // [FORMAT] Build one row per student
        // [UNDERSTAND] for loop used because the index (i+1) is needed
        // for the row number column.
        for (int i = 0; i < repo.getCount(); i++) {
            Student s     = repo.getStudent(i);
            String  grade = s.getNumericGrade();
            char    rank  = s.getLetterRank();

            // [TRACE] s.getIdNumber() provides the 8-digit ID string  [NEW]
            // [TRACE] Static call: GradeCalculator.getRemarks(rank)
            sb.append(String.format(
                    "%-5d %-10s %-20s %-10.2f %-7s %-6c %-5s%n",
                    (i + 1),
                    s.getIdNumber(),          // [NEW] ID column value
                    s.getName(),
                    s.getRawGrade(),
                    grade,
                    rank,
                    GradeCalculator.getRemarks(rank)));
        }

        // [FORMAT] Build table footer
        sb.repeat("=", TABLE_WIDTH).append("\n");

        IO.print(sb.toString());
    }

    // [STATS] Prints highest, lowest, and class mean from the repository
    // [STATIC] repo is a parameter — this method owns no state.
    // [TRACE] Guard check scenario: repo.getCount() == 0
    //         When no data exists, display informative message and exit.
    public static void printClassStats(StudentRepository repo) {
        if (repo.getCount() == 0) {
            IO.println("\n[TRACE] studentCount = 0 - No data to process");
            IO.println("Cannot compute statistics. Please enter student data first.");
            return;
        }

        // [TRACE] Proceeding with statistics computation
        IO.println("\n[TRACE] Processing " + repo.getCount() + " student records...");

        // [INIT] Seed with first student so comparisons start correctly
        double highest      = repo.getStudent(0).getRawGrade();
        double lowest       = repo.getStudent(0).getRawGrade();
        double sum          = repo.getStudent(0).getRawGrade();
        int    highestIndex = 0;
        int    lowestIndex  = 0;

        // [TRACE] Loop from index 1 — index 0 already seeded above
        // [UNDERSTAND] for loop used because the index is needed to
        // track which student holds the highest/lowest grade.
        for (int i = 1; i < repo.getCount(); i++) {
            double grade = repo.getStudent(i).getRawGrade();
            sum += grade;

            if (grade > highest) { highest = grade; highestIndex = i; }
            if (grade < lowest)  { lowest  = grade; lowestIndex  = i; }
        }

        double classMean = sum / repo.getCount();

        // [FORMAT] Display formatted statistics report
        printSeparatorLine(); // [TRACE] Static call to sibling static method
        IO.println("           CLASS STATISTICS REPORT          ");
        printSeparatorLine();
        IO.println(String.format("  Total Students:     %d%n", repo.getCount()));
        IO.println("-".repeat(GradeConstants.SEPARATOR));

        // [TRACE] Static calls to GradeCalculator.assignLetterRank(grade)
        IO.println(String.format(
                "  Highest Raw Grade:  %s (%.2f) - Rank: %c-tier %n",
                repo.getStudent(highestIndex).getName(), highest,
                GradeCalculator.assignLetterRank(highest)));
        IO.println(String.format(
                "  Lowest Raw Grade:   %s (%.2f) - Rank: %c-tier %n",
                repo.getStudent(lowestIndex).getName(), lowest,
                GradeCalculator.assignLetterRank(lowest)));
        IO.println(String.format(
                "  Class Mean:              %.2f  - Rank: %c-tier %n",
                classMean, GradeCalculator.assignLetterRank(classMean)));
        printSeparatorLine();
    }
}