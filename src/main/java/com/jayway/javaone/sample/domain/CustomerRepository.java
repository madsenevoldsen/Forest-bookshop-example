package com.jayway.javaone.sample.domain;

import java.util.HashMap;
import java.util.Map;

public class CustomerRepository {

    private static Map<String, Customer> customerMap = new HashMap<String, Customer>();

	public static Customer getCustomer( String username ) {
        Customer customer = customerMap.get(username);
        if ( customer == null ) {
            customer = new Customer( username );
            customerMap.put( username, customer );
        }
        return customer;
	}
}
