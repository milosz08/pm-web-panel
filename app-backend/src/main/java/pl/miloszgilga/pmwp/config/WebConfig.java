package pl.miloszgilga.pmwp.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration
class WebConfig implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // for api
    registry.addResourceHandler("/api/**");
    // for SPA (static files handler)
    registry
      .addResourceHandler("/**")
      .addResourceLocations("classpath:/static/")
      .resourceChain(true)
      .addResolver(new SpaResourceResolver());
  }

  private static class SpaResourceResolver extends PathResourceResolver {
    @Override
    protected Resource getResource(
      @NotNull String resourcePath,
      Resource location
    ) throws IOException {
      final Resource resource = location.createRelative(resourcePath);
      if (resource.exists() && resource.isReadable()) {
        return resource;
      }
      return new ClassPathResource("/static/index.html");
    }
  }
}
