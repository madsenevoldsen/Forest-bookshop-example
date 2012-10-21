package com.jayway.javaone.sample.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.servlet.http.HttpServletRequest;

import com.jayway.forest.constraint.Constraint;
import com.jayway.forest.constraint.ConstraintEvaluator;
import com.jayway.forest.core.RoleManager;
import com.jayway.forest.roles.Resource;

/**
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(LoggedIn.Evaluator.class)
public @interface LoggedIn {

    public static final String USERNAME = "bookshop-username";

    boolean value();

    class Evaluator implements ConstraintEvaluator<LoggedIn, Resource> {
    	
        public boolean isValid( LoggedIn annotation, Resource resource) {
            HttpServletRequest request = RoleManager.role(HttpServletRequest.class);
            String username = (String) request.getSession().getAttribute( USERNAME );
            boolean loggedIn = !( username == null || username.isEmpty() );

            return loggedIn == annotation.value();
        }

    }

}
