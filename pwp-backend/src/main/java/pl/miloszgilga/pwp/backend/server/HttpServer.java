package pl.miloszgilga.pwp.backend.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HttpServer extends Thread {
  private static final Logger LOG = LoggerFactory.getLogger(HttpServer.class);
  private static final int SERVER_STOP_TIMEOUT_MILLIS = 5000;

  private final Server server;

  public HttpServer(int port) {
    super("JettyServer");
    server = new Server(port);
    server.setStopTimeout(SERVER_STOP_TIMEOUT_MILLIS);

    final NoBodyErrorHandler errorHandler = new NoBodyErrorHandler();
    final List<HttpContextHandler> handlers = List.of(
      new JaxRsHttpContextHandler(errorHandler),
      new SpaHttpContextHandler(errorHandler)
    );
    final List<Handler> createdHandlers = handlers.stream()
      .map(HttpContextHandler::createWithErrorHandler)
      .toList();

    final ContextHandlerCollection contextHandlerCollection = new ContextHandlerCollection();
    contextHandlerCollection.setHandlers(createdHandlers);
    server.setHandler(contextHandlerCollection);
  }

  @Override
  public void run() {
    try {
      server.start();
      server.join();
    } catch (Exception ex) {
      LOG.error("Unable to start HTTP server. Cause: {}.", ex.getMessage());
    }
  }

  public void terminate() {
    try {
      if (server.isStopping() || server.isStopped()) {
        return;
      }
      server.stop();
    } catch (Exception e) {
      LOG.error("Unable to stop HTTP server. Cause: {}.", e.getMessage());
    }
  }
}
