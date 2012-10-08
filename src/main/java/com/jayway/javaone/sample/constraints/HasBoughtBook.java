package com.jayway.javaone.sample.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jayway.forest.legacy.constraint.Constraint;
import com.jayway.forest.legacy.constraint.ConstraintEvaluator;
import com.jayway.forest.legacy.core.RoleManager;
import com.jayway.javaone.sample.domain.CustomerRepository;
import com.jayway.javaone.sample.resources.BookResource;

import javax.servlet.http.HttpServletRequest;

/**
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(HasBoughtBook.Evaluator.class)
public @interface HasBoughtBook {

    boolean value();

    class Evaluator implements ConstraintEvaluator<HasBoughtBook, BookResource> {
    	
        public boolean isValid( HasBoughtBook annotation, BookResource resource) {
            HttpServletRequest request = RoleManager.role(HttpServletRequest.class);
            String username = (String) request.getSession().getAttribute( LoggedIn.USERNAME );
            if ( username == null || username.isEmpty() ) return false;

            return annotation.value() == CustomerRepository.getCustomer( username ).ownsBook(resource.getBook());
        }

    }

}