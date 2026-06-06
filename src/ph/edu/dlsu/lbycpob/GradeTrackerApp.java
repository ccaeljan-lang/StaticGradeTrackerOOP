// [IMPORTANT NOTE] CREATED STATIC FIRST AND THEN TURNED IT INTO NON-STATIC.
// So the commit history
// of the static code is part of the GradeTrackerOOP already.
// Which finished at "FINISHED CODE" commit message.

package ph.edu.dlsu.lbycpob;

// ===========================================================
// GradeTrackerApp.java
// RESPONSIBILITY: Application entry point and menu controller
// SRP: Orchestrates navigation between features — delegates all
//      real work to the specialist classes
// STATIC CONCEPT:
//   (1) main(String[] args) MUST be static — the JVM calls it
//       before any object is ever created, so it cannot belong
//       to an instance.
//   (2) repo, inputHandler, and idVerifier are instance fields
//       because they hold state that belongs to one running app.
//   (3) ReportPrinter methods are called as static:
//           ReportPrinter.printReport(repo)
//       since ReportPrinter has no instance state.
// ============================================================
public class GradeTrackerApp {

    // [DECLARE] Instance fields — each running app gets its own copies
    private StudentRepository repo = new StudentRepository();
    private StudentInputHandler inputHandler = new StudentInputHandler();
    private IDVerifier idVerifier = new IDVerifier();

    // [ENTRY] Standard Java entry point — must be static
    // [UNDERSTAND] The JVM starts here. Because main() is static,
    //              it has no "this" — so we create one GradeTrackerApp
    //              instance and hand control to its instance method.
    public void main() {
        // [TRACE] Create one app object, then start the menu loop
        displayMenu();
    }

    // [MENU] Main menu loop (instance method — uses repo, inputHandler, etc.)
    // [UNDERSTAND] do-while used so the menu always appears at least
    // once before checking if the user chose to exit.
    void displayMenu() {
        String choice;
        do {
            ReportPrinter.printSeparatorLine(); // [TRACE] Static call — no object needed
            IO.println("\t\t\t GRADE TRACKER MENU");
            ReportPrinter.printSeparatorLine();
            IO.println("\t1. Enter Student Data");
            IO.println("\t2. View Report");
            IO.println("\t3. View Class Statistics");
            IO.println("\t4. Verify ID Number");
            IO.println("\t5. Exit");
            ReportPrinter.printSeparatorLine();
            IO.print("Enter choice (1-5): ");

            choice = IO.readln().trim();

            // [UNDERSTAND] Switch expression used instead of if-else because
            // choice holds a small set of discrete string values. Each arm
            // is a single method call — more readable than chained if-else.
            switch (choice) {
                case "1" -> inputStudentData();
                case "2" -> ReportPrinter.printReport(repo);      // [TRACE] Static call
                case "3" -> ReportPrinter.printClassStats(repo);  // [TRACE] Static call
                case "4" -> idVerifier.verifyID();
                case "5" -> IO.println("Goodbye!");
                default -> IO.println("Invalid choice. Please enter 1 to 5.");
            }

        } while (!choice.equals("5"));
    }

    // [INPUT] Collects data for all students and stores them in the repo
    // [UNDERSTAND] for loop used because studentCount is known upfront —
    // the index and termination condition are visible on a single line.
    private void inputStudentData() {
        IO.println("STUDENT DATA ENTRY:");
        int count = inputHandler.inputStudentCount();
        for (int i = 0; i < count; i++) {
            Student s = inputHandler.inputOneStudent(i + 1);
            repo.addStudent(s);
        }
        IO.println("Data saved for " + count + " student(s).");
    }
}