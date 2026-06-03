package ph.edu.dlsu.lbycpob;

// ============================================================
// StudentRepository.java
// RESPONSIBILITY: Stores and retrieves the list of students
// SRP: Manages the student collection — no I/O, no computation
// STATIC CONCEPT: NO static members here — intentionally.
//   The students[] array and count are instance fields because
//   each StudentRepository object manages its own list.
//   Making them static would mean all possible repositories
//   across the program share one list — a dangerous side-effect.
//   RULE: Keep fields instance-level when each object needs
//         its own independent state.
// ============================================================
public class StudentRepository {
    // [DECLARE] Instance fields — this repository's own array and counter
    // [UNDERSTAND] MAX_STUDENTS is now referenced as a static constant
    //              directly from GradeConstants — no object needed.
    private Student[] students = new Student[GradeConstants.MAX_STUDENTS];
    private int count = 0;

    // [STORE] Adds one Student record to the repository
    // [VALIDATE] Guard: reject if capacity is already full
    public void addStudent(Student s) {
        // [TRACE] count >= MAX_STUDENTS means the array is full
        if (count >= GradeConstants.MAX_STUDENTS) {
            return;
        }

        // [TRACE] students[count] = s, then count incremented by 1
        students[count] = s;
        count++;
    }

    // [RETRIEVE] Returns a Student at the given index
    // [VALIDATE] Guard: return null for out-of-range index
    // [TRACE] Valid range: 0 (inclusive) to count (exclusive)
    public Student getStudent(int index) {
        if (index < 0 || index >= count) {
            return null;
        }
        return students[index];
    }

    // [RETRIEVE] Returns total number of stored students
    public int getCount() {
        return count;
    }
}