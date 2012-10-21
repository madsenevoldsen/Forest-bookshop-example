package com.jayway.javaone.sample.resources;

import javax.servlet.http.HttpServletRequest;

import com.jayway.forest.constraint.DoNotDiscover;
import com.jayway.forest.core.RoleManager;
import com.jayway.forest.exceptions.BadRequestException;
import com.jayway.forest.exceptions.NotFoundException;
import com.jayway.forest.roles.ReadableResource;
import com.jayway.javaone.sample.constraints.HasBoughtBook;
import com.jayway.javaone.sample.constraints.LoggedIn;
import com.jayway.javaone.sample.domain.Book;
import com.jayway.javaone.sample.domain.BookRepository;
import com.jayway.javaone.sample.domain.CustomerRepository;

public class BookResource implements ReadableResource<BookDTO> {

	private final Book book;

    public BookResource(String id) {
        book = BookRepository.get(id);
        if (book == null) {
            throw new NotFoundException( "No such book '" + id + "'");
        }
	}

    @DoNotDiscover
	public Book getBook() {
		return book;
	}

    @LoggedIn( true )
	@HasBoughtBook(false)
    public void buy(PinDTO pin) {
		if (!"1234".equals(pin.getPin())) {
			throw new BadRequestException("Invalid PIN code");
		}
        HttpServletRequest request = RoleManager.role(HttpServletRequest.class);
        String username = (String) request.getSession().getAttribute( LoggedIn.USERNAME );
		CustomerRepository.getCustomer( username ).bought(book);
    }

    @LoggedIn(true)
    @HasBoughtBook(true)
    public String download() {
        return "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    }

    @Override
    public BookDTO read() {
        return new BookDTO( book.getId(), book.getTitle(), book.getAuthor() );
    }
}
