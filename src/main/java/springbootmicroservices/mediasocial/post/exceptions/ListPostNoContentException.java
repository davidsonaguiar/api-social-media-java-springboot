package springbootmicroservices.mediasocial.post.exceptions;

public class ListPostNoContentException extends RuntimeException {
    public ListPostNoContentException() {
        super("List of posts is empty");
    }
}
