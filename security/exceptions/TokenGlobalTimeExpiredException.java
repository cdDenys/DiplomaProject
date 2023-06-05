package adaptiveschool.security.exceptions;

public class TokenGlobalTimeExpiredException extends RuntimeException {
    public TokenGlobalTimeExpiredException() {
        super();
    }

    public TokenGlobalTimeExpiredException(String s) {
        super(s);
    }
}
