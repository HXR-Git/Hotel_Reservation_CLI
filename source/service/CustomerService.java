//mangee customer data
package source.service;

import source.model.Customer;
import java.util.*;

public class CustomerService {
    private static final CustomerService INSTANCE = new CustomerService();
    private final Map<String, Customer> customers = new HashMap<>();

    private CustomerService() {}

    public static CustomerService getInstance() {
        return INSTANCE;
    }

    public void addCustomer(String firstName, String lastName, String email) {
        if (customers.containsKey(email)) {
            throw new IllegalArgumentException("Email " + email + " is already exist");
        }
        customers.put(email, new Customer(firstName, lastName, email));
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }

    public Customer getCustomer(String email) {
        return customers.get(email);
    }

    @Override
    public String toString() {
       return  customers.size() +"";
    }


}
