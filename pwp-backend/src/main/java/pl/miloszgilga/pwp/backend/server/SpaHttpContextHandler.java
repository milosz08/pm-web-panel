package pl.miloszgilga.pwp.backend.server;

import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.rewrite.handler.RewriteRegexRule;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceFactory;

import java.net.URL;

class SpaHttpContextHandler extends HttpContextHandler {
  private static final String SPA_ENTRYPOINT_FILE = "index.html";
  private static final String SPA_RESOURCES_DIR = "static";

  SpaHttpContextHandler(ErrorHandler errorHandler) {
    super(errorHandler);
  }

  @Override
  public ContextHandler createHandler() {
    final ContextHandler context = new ContextHandler();
    context.setContextPath("/");

    final URL staticUrl = SpaHttpContextHandler.class.getResource("/" + SPA_RESOURCES_DIR);
    if (staticUrl != null) {
      final ResourceHandler resourceHandler = new ResourceHandler();
      final Resource baseResource = ResourceFactory.of(resourceHandler).newResource(staticUrl);
      resourceHandler.setDirAllowed(false);
      resourceHandler.setWelcomeFiles(SPA_ENTRYPOINT_FILE);
      resourceHandler.setBaseResource(baseResource);

      final RewriteRegexRule spaRule = new RewriteRegexRule();
      spaRule.setRegex("^/(?!api|.*\\.\\w+).*$");
      spaRule.setReplacement("/" + SPA_ENTRYPOINT_FILE);

      final RewriteHandler rewriteHandler = new RewriteHandler();
      rewriteHandler.addRule(spaRule);
      rewriteHandler.setHandler(resourceHandler);

      context.setHandler(rewriteHandler);
    }
    return context;
  }
}
