# PM Web Panel

An interactive and open-source management panel for PM2 instances updated in real time. It uses a
Java process runner, Spring Boot (server) and React environment, server-side events and websocket
protocol to send resources usage parameters and application logs in real time.

[GitHub repository](https://github.com/milosz08/pm-web-panel)
| [Support](https://github.com/sponsors/milosz08)

## Build image

```bash
docker build -t milosz08/pm-web-panel
```

## Create container

* Using command:

```bash
docker run -d \
  --name pm-web-panel \
  -p 8080:8080 \
  -e PM_WEB_PANEL_XMS=1024m \
  -e PM_WEB_PANEL_XMX=1024m \
  milosz08/pm-web-panel
```

* Using `docker-compose.yml` file:

```yaml
services:
  pm-web-panel:
    container_name: pm-web-panel
    image: milosz08/pm-web-panel
    ports:
      - '8080:8080'
    environment:
      PM_WEB_PANEL_XMS: 1024m
      PM_WEB_PANEL_XMX: 1024m
    networks:
      - pm-web-panel-network

  # other containers...

networks:
  pm-web-panel-network:
    driver: bridge
```

## License

This project is licensed under the AGPL-3.0 License.
