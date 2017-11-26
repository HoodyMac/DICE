package pl.zed.dice.exception.post;

public class WrongOwnerException extends RuntimeException{
    public WrongOwnerException() {
        super("Wrong Owner!");
    }
}
