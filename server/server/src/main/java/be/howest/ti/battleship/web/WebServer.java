package be.howest.ti.battleship.web;

import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.buffer.impl.BufferImpl;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.openapi.RouterBuilder;

import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebServer extends AbstractVerticle {
    private static final Logger LOGGER = Logger.getLogger(WebServer.class.getName());
    private static final String EXPECTED_LOGGER_FILE_NAME = "2023.project-i:battleship-server-34.log";
    private static final int MAX_LOG_FILE = 15000;
    
    private Promise<Void> startPromise;
    private final BattleshipOpenApiBridge openApiBridge;

    public WebServer(BattleshipOpenApiBridge bridge) {
        this.openApiBridge = bridge;
    }

    public WebServer() {
        this(new BattleshipOpenApiBridge());
    }

    @Override
    public void start(Promise<Void> startPromise) {
        this.startPromise = startPromise;
        ConfigRetriever.create(vertx).getConfig()
                .onFailure(cause -> shutDown("Failed to load configuration", cause))
                .onSuccess(configuration -> {
                    LOGGER.log(Level.INFO, "Configuration loaded: {0}", configuration);
                    start(startPromise, configuration);
                });
    }

    private void start(Promise<Void> startPromise, JsonObject configuration) {
        RouterBuilder.create(vertx, configuration.getJsonObject("api").getString("url"))
                .onFailure(cause -> shutDown("Failed to load API specification", cause))
                .onSuccess(routerBuilder -> {
                    LOGGER.log(Level.INFO, "API specification loaded: {0}",
                            routerBuilder.getOpenAPI().getOpenAPI().getJsonObject("info").getString("version"));
                    start(startPromise, configuration, routerBuilder);
                });
    }

    private void start(Promise<Void> startPromise, JsonObject configuration, RouterBuilder routerBuilder) {
        Router mainRouter = Router.router(vertx);


        mainRouter.route("/*").subRouter(
                openApiBridge.buildRouter(routerBuilder)
        );


        mainRouter.route("/logs").handler(this::showLogs);

        final int port = configuration.getJsonObject("http").getInteger("port");
        LOGGER.log(Level.INFO, "Server will be listening at port {0}", port);

        vertx.createHttpServer()
                .requestHandler(mainRouter)
                .listen(port)
                .onFailure(cause -> shutDown("Failed to start server", cause))
                .onSuccess(server -> {
                    LOGGER.log(Level.INFO, "Server is listening on port: {0}", server.actualPort());
                    LOGGER.log(Level.INFO, "Handler: {0}", server.requestHandler());
                    startPromise.complete();
                });
    }

    private void showLogs(RoutingContext ctx) {

        String path = Paths.get(
                        System.getProperty("java.io.tmpdir"),
                        EXPECTED_LOGGER_FILE_NAME)
                .toString();

        vertx.fileSystem().open(path, new OpenOptions().setWrite(false).setCreate(false))
                .onSuccess(file -> {
                    long size = file.sizeBlocking();

                    long position;
                    int length;
                    if (size > MAX_LOG_FILE) {
                        position = size - MAX_LOG_FILE;
                        length = MAX_LOG_FILE;
                    } else {
                        position = 0;
                        length = MAX_LOG_FILE;
                    }


                    file.read(new BufferImpl(), 0, position, length)
                            .onSuccess(logs -> ctx.response()
                                    .putHeader("Content-Type", "text/plain")
                                    .send(logs))
                            .onFailure(ex -> sendPlainText(ctx, 500, "Unable to read" ));
                })
                .onFailure(ex -> sendPlainText(ctx, 404,"Not found"));
    }

    private void sendPlainText(RoutingContext ctx, int status, String txt){
        ctx.response()
                .setStatusCode(status)
                .putHeader("Content-Type", "text/plain")
                .send(txt);
    }

    private void shutDown(String quote, Throwable cause) {
        LOGGER.log(Level.SEVERE, quote, cause);
        LOGGER.info("Shutting down");
        vertx.close();
        startPromise.fail(cause);
    }

}
