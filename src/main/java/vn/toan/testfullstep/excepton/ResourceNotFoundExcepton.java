package vn.toan.testfullstep.excepton;

public class ResourceNotFoundExcepton extends RuntimeException {
    public ResourceNotFoundExcepton(String message) {
        super(message);
    }
}
