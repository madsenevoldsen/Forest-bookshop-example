package com.jayway.javaone.sample.resources;

import static com.jayway.forest.core.RoleManager.role;

import javax.servlet.http.HttpServletRequest;

import com.jayway.forest.exceptions.BadRequestException;
import com.jayway.forest.roles.Resource;
import com.jayway.javaone.sample.constraints.LoggedIn;

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
