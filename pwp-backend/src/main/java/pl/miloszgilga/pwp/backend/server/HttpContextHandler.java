package pl.miloszgilga.pwp.backend.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ErrorHandler;

abstract class HttpContextHandler {
  private final ErrorHandler errorHandler;

  HttpContextHandler(ErrorHandler errorHandler) {
    this.errorHandler = errorHandler;
  }

  Handler createWithErrorHandler() {
    final ContextHandler handler = createHandler();
    handler.setErrorHandler(errorHandler);
    return handler;
  }

  protected abstract ContextHandler createHandler();
}
