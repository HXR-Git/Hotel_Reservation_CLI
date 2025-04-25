//creation of valid and invalid customer
package source.model;

public class Driver {
    public static void main(String[] args) {
        try {

            Customer validCustomer = new Customer("Harsha", "Reddy", "hxr@domain.com");
            System.out.println(validCustomer);

            Customer invalidCustomer = new Customer("Harsha", "Reddy", "invalid-email");
            System.out.println(invalidCustomer);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
    }
}
