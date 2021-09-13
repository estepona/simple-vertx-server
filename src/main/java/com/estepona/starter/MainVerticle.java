package com.estepona.starter;

import com.estepona.starter.router.HelloRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    HelloRouter helloRouter = new HelloRouter(HttpMethod.POST, "/hello", vertx);

    vertx.createHttpServer()
      .requestHandler(helloRouter.router)
      .listen(8888, http -> {
        if (http.succeeded()) {
          startPromise.complete();
          System.out.println("HTTP server started on port 8888");
        } else {
          startPromise.fail(http.cause());
        }
      });
  }
}
