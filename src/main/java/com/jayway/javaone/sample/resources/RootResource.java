package com.jayway.javaone.sample.resources;

import com.jayway.forest.legacy.exceptions.BadRequestException;
import com.jayway.forest.legacy.roles.Resource;
import com.jayway.javaone.sample.constraints.LoggedIn;

import javax.servlet.http.HttpServletRequest;

import static com.jayway.forest.legacy.core.RoleManager.role;

public class RootResource implements Resource {

    @LoggedIn( false )
    public void login( LoginDTO login ) {
        String username = login.getUsername();
        if ( username == null || username.isEmpty() ) {
            throw new BadRequestException();
        }
        HttpServletRequest request = role(HttpServletRequest.class);
        request.getSession().setAttribute( LoggedIn.USERNAME, login.getUsername() );
    }

    @LoggedIn( true )
    public void logout() {
        HttpServletRequest request = role(HttpServletRequest.class);
        request.getSession().removeAttribute( LoggedIn.USERNAME );
    }


    public Resource books() {
        return new BooksResource();
    }
}
