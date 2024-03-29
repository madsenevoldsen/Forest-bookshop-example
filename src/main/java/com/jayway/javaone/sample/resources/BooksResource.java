package com.jayway.javaone.sample.resources;

import java.util.ArrayList;
import java.util.List;

import com.jayway.forest.roles.IdDiscoverableResource;
import com.jayway.forest.roles.Linkable;
import com.jayway.forest.roles.Resource;
import com.jayway.javaone.sample.domain.Book;
import com.jayway.javaone.sample.domain.BookRepository;

public class BooksResource implements IdDiscoverableResource {
	
    @Override
    public List<Linkable> discover() {
        List<Linkable> links = new ArrayList<Linkable>();
        for (Book book : BookRepository.findAll()) {
            links.add( new Linkable(book.getId(), book.getTitle()) );
		}
        return links;
    }

    @Override
    public Resource id( String id ) {
		return new BookResource(id);
    }
}
