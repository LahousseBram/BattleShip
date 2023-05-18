package be.howest.ti.battleship.web;

public class NotYetImplementedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NotYetImplementedException(String endpoint) {
        super("This endpoint has not yet been implemented: " + endpoint);
    }
}

