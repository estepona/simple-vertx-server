package com.estepona.starter.router;

import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class HelloRouter {
  public HttpMethod method;
  public String path;
  public Router router;
  private final Route route;

  public HelloRouter(HttpMethod method, String path, Vertx vertx) {
    this.method = method;
    this.path = path;
    this.router = Router.router(vertx);
    this.route = router.route(method, path);

    addHandler();
  }

  private void addHandler() {
    route.handler(BodyHandler.create());

    route.handler(context -> {
      String address = context.request().connection().remoteAddress().toString();

      // get query
      MultiMap queryParams = context.queryParams();
      String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";

      // get body
      String body = context.getBodyAsString();

      context.json(new JsonObject()
        .put("address", address)
        .put("name", name)
        .put("body", body)
        .put("message", "hello " + name + " connected from " + address));
    });
  }
}
