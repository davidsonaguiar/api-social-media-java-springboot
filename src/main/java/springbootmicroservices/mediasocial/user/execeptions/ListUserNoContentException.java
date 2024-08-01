package springbootmicroservices.mediasocial.user.execeptions;

public class ListUserNoContentException extends RuntimeException {
    public ListUserNoContentException(String message) {
        super(message);
    }
}
