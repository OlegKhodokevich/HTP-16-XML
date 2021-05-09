package by.khodokevich.taskxml.exception;

public class ProjectXMLException extends Exception{
    public ProjectXMLException() {
    }

    public ProjectXMLException(String message) {
        super(message);
    }

    public ProjectXMLException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectXMLException(Throwable cause) {
        super(cause);
    }
}
