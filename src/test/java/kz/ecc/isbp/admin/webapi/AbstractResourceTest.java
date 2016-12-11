package kz.ecc.isbp.admin.webapi;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import kz.ecc.isbp.admin.common.webapi.exception.EJBTransactionRolledbackExceptionMapperImpl;
import kz.ecc.isbp.admin.common.webapi.exception.ExceptionMapperImpl;
import kz.ecc.isbp.admin.common.webapi.exception.WebApplicationExceptionMapperImpl;


public class AbstractResourceTest {
	private static Server server = null;
	private static AbstractBinder binder;
	private static Object resource;
	
	protected static void setBinder(AbstractBinder binder) {
		AbstractResourceTest.binder = binder;
	}
	
	protected static void setResource(Object resource) {
		AbstractResourceTest.resource = resource;
	}
	
	protected static void start(Binding binding) throws Exception {
		ResourceConfig config = new ResourceConfig();
		config.register(binder);
		config.register(resource);
		config.register(WebApplicationExceptionMapperImpl.class);
		config.register(EJBTransactionRolledbackExceptionMapperImpl.class);
		config.register(ExceptionMapperImpl.class);
		
    	ServletHolder servlet = new ServletHolder(new ServletContainer(config));
    	server = new Server(2222);
    	ServletContextHandler context = new ServletContextHandler(server, "/*");
    	context.addServlet(servlet, "/*");

    	server.start();		
	}
	
	
	protected static void stop() throws Exception {
		server.stop();
	}
}
