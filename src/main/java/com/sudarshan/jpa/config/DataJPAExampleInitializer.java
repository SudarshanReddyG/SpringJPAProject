package com.sudarshan.jpa.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class DataJPAExampleInitializer implements WebApplicationInitializer {

	private static final String DISPATCHER_SERVLET_NAME = "dispatcher";
	private static final String DISPATCHER_SERVLET_MAPPING = "/";

	@Override
	public void onStartup(ServletContext servletContxt) throws ServletException {
		//Loading application context
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(ApplicationContext.class);

		//Dispatcher servlet
		ServletRegistration.Dynamic dispatcher = servletContxt.addServlet(DISPATCHER_SERVLET_NAME, new DispatcherServlet(rootContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping(DISPATCHER_SERVLET_MAPPING);

		//Sitemesh
		FilterRegistration.Dynamic sitemesh = servletContxt.addFilter("sitemesh", new ConfigurableSiteMeshFilter());
		EnumSet<DispatcherType> sitemeshDispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
		sitemesh.addMappingForUrlPatterns(sitemeshDispatcherTypes, true, "*.jsp");

		//Context loader listener
		servletContxt.addListener(new ContextLoaderListener(rootContext));
	}

}
