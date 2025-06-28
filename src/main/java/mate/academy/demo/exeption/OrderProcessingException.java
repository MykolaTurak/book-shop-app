package mate.academy.demo.exeption;

public class OrderProcessingException extends RuntimeException {
    public OrderProcessingException(String shoppingCartIsEmpty) {
    }
}

