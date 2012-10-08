package com.jayway.javaone.sample.constraints;

import com.jayway.forest.legacy.constraint.Constraint;
import com.jayway.forest.legacy.constraint.ConstraintEvaluator;
import com.jayway.forest.legacy.core.RoleManager;
import com.jayway.forest.legacy.roles.Resource;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
