package pl.miloszgilga.pmwp;

import lombok.Data;

@Data
public class AppConfig {
  private Server server;

  @Data
  public static class Server {
    private int port;
  }
}
