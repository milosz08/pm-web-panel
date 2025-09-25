package pl.miloszgilga.pwp.backend;

import pl.miloszgilga.pwp.backend.server.HttpServer;

class PwpBackendEntrypoint implements Runnable {
  private final HttpServer httpServer;

  PwpBackendEntrypoint() {
    httpServer = new HttpServer(8690);
  }

  void init() {
    httpServer.start();
  }

  public static void main(String[] args) {
    final PwpBackendEntrypoint pwpBackendEntrypoint = new PwpBackendEntrypoint();
    Runtime.getRuntime().addShutdownHook(new Thread(pwpBackendEntrypoint));
    pwpBackendEntrypoint.init();
  }

  @Override
  public void run() {
    httpServer.terminate();
  }
}
