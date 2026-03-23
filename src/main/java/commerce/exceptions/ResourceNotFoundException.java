package commerce.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, String name) {
        super(String.format("Cannot find %s with name: %s", resource, name));

    }

    public ResourceNotFoundException(String resource, Integer id) {
        super(String.format("Cannot find %s with id: %d", resource, id));
    }
}
