package pl.zed.dice.exception.user;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String email){
        super("Użytkownik z adresem="+email+" już istnieje");
    }
}
