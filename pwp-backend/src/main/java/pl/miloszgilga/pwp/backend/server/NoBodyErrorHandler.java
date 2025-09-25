package pl.miloszgilga.pwp.backend.server;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.util.Callback;

class NoBodyErrorHandler extends ErrorHandler {
  @Override
  public boolean handle(Request req, Response res, Callback callback) {
    callback.succeeded(); // asynchronous, we must inform jetty, that operation is completed
    return true;
  }
}
