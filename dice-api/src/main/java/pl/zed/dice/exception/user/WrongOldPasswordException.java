package pl.zed.dice.exception.user;

public class WrongOldPasswordException extends RuntimeException {
    public WrongOldPasswordException() {
        super("Wrong old password");
    }
}
