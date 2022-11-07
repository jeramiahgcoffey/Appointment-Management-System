package util;

import model.Contact;
import model.Customer;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Abstract class that contains static List utility methods.
 *
 * @author Jeramiah Coffey
 */
public abstract class ListUtils {
    /**
     * Collects a list of Contact objects into a Map object.
     *
     * @param list The list of Contacts to collect.
     * @return A Map of Contact objects.
     */
    public static Map<Integer, Contact> toContactMap(List<Contact> list) {
        return list.stream().collect(Collectors.toMap(Contact::id, Function.identity()));
    }

    /**
     * Collects a list of Customer objects into a Map object.
     *
     * @param list The list of Customers to collect.
     * @return A Map of Customer objects.
     */
    public static Map<Integer, Customer> toCustomerMap(List<Customer> list) {
        return list.stream().collect(Collectors.toMap(Customer::getId, Function.identity()));
    }
}
