package pl.miloszgilga.pmwp;

import lombok.Data;

@Data
class AppConfig {
  private Server server;

  @Data
  static class Server {
    private int port;
  }
}
