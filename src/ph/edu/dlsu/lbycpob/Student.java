package ph.edu.dlsu.lbycpob;

// ============================================================
// Student.java
// RESPONSIBILITY: Stores a single student's data (data model)
// SRP: Pure data container — no I/O, no computation
// STATIC CONCEPT: NO static members here — and intentionally so.
//   Each Student object holds its OWN name, rawGrade, etc.
//   If these were static, ALL students would share one name,
//   one grade, and so on — which is clearly wrong.
//   RULE: Use instance fields when each object needs its own copy.
// ============================================================
public class Student {
    // [DECLARE] Instance fields — each Student object gets its own copy
    // [UNDERSTAND] "private" means only methods inside Student can read
    //              or write these directly. Outside classes must use getters/setters.
    private String name;
    private double rawGrade;
    private char letterRank;
    private String idNumber;
    private String numericGrade;

    // [CONSTRUCT] Constructor
    public Student() {

    }

    // [CONSTRUCT] Constructor: accepts all fields at creation
    // [UNDERSTAND] Uses setters so validation logic is applied automatically.
    //              If a bad value is passed, the setter prints an error
    //              and leaves the field at its default (0 / null / '\0').
    public Student(String name, String idNumber, double rawGrade, String numericGrade, char letterRank) {
        setName(name);
        setRawGrade(rawGrade);
        setLetterRank(letterRank);
        setIdNumber(idNumber);
        setNumericGrade(numericGrade);
    }

    // [SETTER] Sets letter rank with validation
    // [UNDERSTAND] Valid letter ranks are S, A, B, C, D, E, P, F
    // [DECISION] If invalid, prints error and leaves letterRank unchanged
    public void setLetterRank(char letterRank) {
        letterRank = Character.toUpperCase(letterRank);
        if (!(letterRank == 'S' || letterRank == 'A' || letterRank == 'B' ||
                letterRank == 'C' || letterRank == 'D' || letterRank == 'E' ||
                letterRank == 'P' || letterRank == 'F')) {
            IO.println("Invalid: Letter rank format.");
            return;
        }
        this.letterRank = letterRank;
    }

    // [SETTER] Sets numeric grade string with validation
    // [UNDERSTAND] Numeric grade must be a valid format (e.g., "4.0", "3.5", "0.0")
    // [DECISION] If null or blank, prints error and leaves numericGrade unchanged
    public void setNumericGrade(String numericGrade) {
        if (numericGrade == null) {
            IO.println("Invalid: Wrong numeric grade format.");
            return;
        }

        numericGrade = numericGrade.trim();

        if (!(numericGrade.equals("4.0") ||
                numericGrade.equals("3.5") ||
                numericGrade.equals("3.0") ||
                numericGrade.equals("2.5") ||
                numericGrade.equals("2.0") ||
                numericGrade.equals("1.5") ||
                numericGrade.equals("1.0") ||
                numericGrade.equals("0.0"))) {
            IO.println("Invalid: Wrong numeric grade format.");
            return;
        }
        this.numericGrade = numericGrade;
    }


    // [SETTER] Sets raw grade with validation
    // [UNDERSTAND] Raw grade must be between 0.0 and 100.0
    // [DECISION] If out of range, prints error and leaves rawGrade unchanged
    public void setRawGrade(double rawGrade) {
        if (rawGrade < 0.0 || rawGrade > 100.0) {
            IO.println("Invalid: Raw grade, must be between 0.0 and 100.0.");
            return;
        }
        this.rawGrade = rawGrade;
    }

    // [SETTER] Sets student name with validation
    // [UNDERSTAND] Name cannot be null or blank
    // [DECISION] If invalid, prints error and leaves name unchanged
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            IO.println("Invalid: Name cannot be empty.");
            return;
        }
        name = name.trim();
        this.name = name;
    }

    // [SETTER] Sets student ID number
    public void setIdNumber(String idNumber) {
        if (idNumber == null || idNumber.trim().isEmpty()) {
            IO.println("Invalid: ID number cannot be empty.");
            return;
        }
        idNumber = idNumber.trim();
        this.idNumber = idNumber;
    }

    // GETTERS:

    // [GETTER] Returns student name
    public String getName() {
        return name;
    }

    // [GETTER] Returns raw grade (weighted score)
    public double getRawGrade() {
        return rawGrade;
    }

    // [GETTER] Returns numeric grade string (e.g., "3.5")
    public String getNumericGrade() {
        return numericGrade;
    }

    // [GETTER] Returns letter rank char (e.g., 'A')
    public char getLetterRank() {
        return letterRank;
    }

    // [GETTER] Returns student ID number string
    public String getIdNumber() {
        return idNumber;
    }
}
