//customer validation
package source.model;

import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String email;
    private final String lastName;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public Customer(String firstName, String lastName, String email) {
        if (!Pattern.matches(EMAIL_REGEX, email)) {
            throw new IllegalArgumentException("in- valid");
        }
        this.firstName = firstName;
        this.email = email;
        this.lastName = lastName;

    }

    public String getEmail() {
        return email;
    }
@Override
    public String toString() {
        return "Customer: " + firstName + " " + lastName + " " + email;
    }
}
