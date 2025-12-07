package pl.miloszgilga.pwp.backend.server;

import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;

class JaxRsHttpContextHandler extends HttpContextHandler {
  private static final String SCANNING_PACKAGES = "pl.miloszgilga.pwp.backend.rest";

  JaxRsHttpContextHandler(ErrorHandler errorHandler) {
    super(errorHandler);
  }

  @Override
  public ContextHandler createHandler() {
    final ResourceConfig jerseyConfig = new ResourceConfig().packages(SCANNING_PACKAGES);
    jerseyConfig.property(ServerProperties.WADL_FEATURE_DISABLE, true); // disable JVM 9+ warning

    final ServletContainer servletContainer = new ServletContainer(jerseyConfig);
    final ServletHolder jerseyServlet = new ServletHolder(servletContainer);

    final ServletContextHandler context = new ServletContextHandler();
    context.setContextPath("/api");
    context.addServlet(jerseyServlet, "/*");

    return context;
  }
}
