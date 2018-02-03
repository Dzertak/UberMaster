package ubermaster.errorHandler;

public enum Errors {
    ser_1("SER-1"),
    ser_2("SER-2"),
    ser_3("SER-3"),
    ser_4("SER-4"),
    ser_5("SER-5"),
    ser_6("SER-6"),
    ser_7("SER-7"),
    ser_8("SER-8"),
    ser_9("SER-9"),
    ser_10("SER-10");

    private String fullName;
    Errors (String fullName) {this.fullName = fullName;}
    public String toString() {
        return this.getFullName();
    }
    String getFullName() {
        return fullName;
    }
}
