package com.jayway.javaone.sample;

import com.jayway.forest.core.Application;
import com.jayway.forest.di.grove.GroveDependencyInjectionImpl;
import com.jayway.forest.roles.Resource;
import com.jayway.forest.servlet.RestfulServlet;
import com.jayway.javaone.sample.resources.RootResource;

public class RestService extends RestfulServlet {

	public void init() {
		initForest(new Application() {

            @Override
            public Resource root() {
                return new RootResource();
            }

            @Override
            public void setupRequestContext() {
            }

        }, new GroveDependencyInjectionImpl());
	}

}
