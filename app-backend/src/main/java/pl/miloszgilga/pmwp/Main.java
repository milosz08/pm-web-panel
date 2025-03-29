package pl.miloszgilga.pmwp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.javalin.Javalin;

import java.io.File;
import java.io.IOException;
import java.util.Map;

class Main {
  private static final String SPA_CLASSPATH_PATH = "/static";
  private static final String CONFIG_FILE_LOCATION = "config.yml";

  private final ObjectMapper configMapper = new ObjectMapper(new YAMLFactory());

  public static void main(String[] args) throws IOException {
    final Main main = new Main();
    main.launchServer();
  }

  private void launchServer() throws IOException {
    final AppConfig appConfig = configMapper
      .readValue(new File(CONFIG_FILE_LOCATION), AppConfig.class);

    final Javalin app = Javalin.create(config -> {
      config.showJavalinBanner = false;
      // SPA
      config.spaRoot.addFile("/", SPA_CLASSPATH_PATH + "/index.html");
      config.staticFiles.add(staticFiles -> staticFiles.directory = SPA_CLASSPATH_PATH);
    });

    app.get("/api/hello", ctx -> ctx.json(Map.of("message", "Hello from API!")));

    app.start(appConfig.getServer().getPort());
  }
}
