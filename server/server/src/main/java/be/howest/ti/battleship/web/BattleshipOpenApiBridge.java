package be.howest.ti.battleship.web;

import be.howest.ti.battleship.logic.*;
import be.howest.ti.battleship.web.request.*;
import be.howest.ti.battleship.web.request.Response;
import be.howest.ti.battleship.web.tokens.PlainTextTokens;
import be.howest.ti.battleship.web.tokens.TokenManager;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BearerAuthHandler;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.openapi.RouterBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BattleshipOpenApiBridge {
    private static final Logger LOGGER = Logger.getLogger(BattleshipOpenApiBridge.class.getName());
    public static final String BATTLESHIP_REQUEST = "battleship-request";
    private final BattleshipServiceImpl battleshipServiceImpl;

    private final TokenManager tokenManager;
    private final BattleshipService service;

    public BattleshipOpenApiBridge() {
        this(new BattleshipServiceImpl(), new PlainTextTokens());
    }

    BattleshipOpenApiBridge(BattleshipService service, TokenManager tokenManager) {
        this.service = service;
        this.tokenManager = tokenManager;
        this.battleshipServiceImpl = new BattleshipServiceImpl();
    }


    public Router buildRouter(RouterBuilder routerBuilder) {
        LOGGER.log(Level.INFO, "Check if service is set");
        Objects.requireNonNull(service); // mainly to remove 'unused' warning in initial/empty implementation.

        LOGGER.log(Level.INFO, "Installing CORS handlers");
        routerBuilder.rootHandler(createCorsHandler());

        LOGGER.log(Level.INFO, "Installing CORS handlers");
        routerBuilder.rootHandler(BodyHandler.create());

        LOGGER.log(Level.INFO, "Installing security handlers");
        routerBuilder.securityHandler("playerToken", BearerAuthHandler.create(tokenManager));


        LOGGER.log(Level.INFO, "Installing Failure handlers");
        routerBuilder.operations().forEach(op -> op.failureHandler(this::onFailedRequest));

        LOGGER.log(Level.INFO, "Installing Actual handlers");

        routerBuilder.operation("get-info")
                .handler(ctx -> getInfo(new GetInfoRequest(ctx)));

        routerBuilder.operation("get-ships")
                .handler(ctx -> getShips(new GetShipsRequest(ctx)));

        routerBuilder.operation("delete-games")
                .handler(ctx -> deleteGames(new DeleteGamesRequest(ctx, this.battleshipServiceImpl.getGameManager())));

        routerBuilder.operation("get-games")
                .handler(ctx -> getGames(new GetGamesRequest(ctx)));

        routerBuilder.operation("join-game")
                .handler(ctx -> joinGame(new JoinGameRequest(ctx)));

        routerBuilder.operation("get-game-by-id")
                .handler(ctx -> getGameById(new GetGameByIdRequest(ctx)));

        routerBuilder.operation("fire-salvo")
                .handler(ctx -> fireSalvo(new FireSalvoRequest(ctx)));

        routerBuilder.operation("move-ship")
                .handler(ctx -> moveShip(new MoveShipRequest(ctx)));

        routerBuilder.operation("get-fleet-details")
                .handler(ctx -> getFleetDetails(new GetFleetDetailsRequest(ctx)));

        Router router = routerBuilder.createRouter();

        router.post("/demo")
                .handler(ctx -> postDemo(new PostDemoRequest(ctx)))
                .failureHandler(this::onFailedRequest);

        return router;
    }

    private void getInfo(GetInfoRequest request) {
        // Get data out of request.
        // throw new ForbiddenAccessException("Reason") if needed.
        // Pass data to the service (or token manager)
        // Set results in the request

        // Send response:
        request.sendResponse();
    }

    private void deleteGames(DeleteGamesRequest request) {
        // Get data out of request.
        // throw new ForbiddenAccessException("Reason") if needed.
        // Pass data to the service (or token manager)
        // Set results in the request
        // Send response:
        request.sendResponse();
    }

    private void getShips(GetShipsRequest request) {
        request.setShipTypes(ShipType.getDefaultShipTypes());
        request.sendResponse();
    }

    private void getGames(GetGamesRequest request) {
        // Get data out of request.
        // throw new ForbiddenAccessException("Reason") if needed.
        // Pass data to the service (or token manager)
        // Set results in the request
        request.setGames(new ArrayList<>(service.getGameManager().getListOfGames().values()));
        request.setGameManager(service.getGameManager());
        // Send response:
        request.sendResponse();
    }

    private void joinGame(JoinGameRequest request) {
        // Get data out of request.
        Game game = request.getGame();
        String commanderName = request.getCommanderName();
        // throw new ForbiddenAccessException("Reason") if needed.
        // Pass data to the service (or token manager)
        if (tokenManager.isValidPlayerName(commanderName)) {
            Game matchedGame = service.getGameManager().matchGame(game);
            if (matchedGame == null) {
                service.getGameManager().addGame(game);
            } else {
                service.getGameManager().getGameByID(matchedGame.getGameID()).join(game.getAtackingCommander());
                game = matchedGame;
            }
            BattleshipUser user = new BattleshipUser(game.getGameID(), commanderName);
            String playerToken = tokenManager.createToken(user);
            // Set results in the request
            request.setGameId(game.getGameID());
            request.setPlayerToken(playerToken);
            // Send response:
            request.sendResponse();
        } else {
            throw new InvalidRequestException("Invalid username");
        }
    }


    private void getGameById(GetGameByIdRequest request) {
        // Get data out of request.
        String gameId = request.getGameId();
        // throw new ForbiddenAccessException("Reason") if needed.
        // Pass data to the service (or token manager)
        // Set results in the request
        request.setGame(service.getGameManager().getGameByID(gameId));
        // Send response:
        request.sendResponse();
    }

    private void fireSalvo(FireSalvoRequest request) {
        // Get data out of request.
        String gameId = request.getGameId();
        Game game = service.getGameManager().getGameByID(gameId);
        String defendingCommanderName = request.getDefendingCommanderName();
        // throw new ForbiddenAccessException("Reason") if needed.

        if (defendingCommanderName.equalsIgnoreCase(request.getUser().getCommander())) {
            throw new ForbiddenAccessException("You cannot attack your own army!");
        }
        if (game.getAtackingCommander().getName().equals(defendingCommanderName)) {
            throw new ForbiddenAccessException("Only the attacking commander can attack");
        }
        // Pass data to the service (or token manager)
        // Set results in the request
        request.setPositionStrings();
        request.setGame(game);
        // Send response:
        request.sendResponse();
    }

    private void moveShip(MoveShipRequest request) {
        // Get data out of request.
        String gameId = request.getGameId();
        Game game = service.getGameManager().getGameByID(gameId);
        Ship ship = request.getShip(game);
        // throw new ForbiddenAccessException("Reason") if needed.
        if (!game.getAtackingCommander().getName().equalsIgnoreCase(request.getUser().getCommander())) {
            throw new ForbiddenAccessException("You can only move your own ships");
        }
        if (!ship.getSunkPositions().isEmpty()) {
            throw new ForbiddenAccessException("Cannot move partially sunken ship");
        }
        // Pass data to the service (or token manager)
        ship.move();
        game.nextTurn();
        // Set results in the request
        request.setShip(ship);

        // Send response:
        request.sendResponse();
    }

    private void getFleetDetails(GetFleetDetailsRequest request) {
        // Get data out of request.
        // throw new ForbiddenAccessException("Reason") if needed.
        // Pass data to the service (or token manager)
        // Set results in the request
        Commander currentCommander = service.getGameManager().getCommanderByName(request.getGameID(), request.getGameCommander());
        request.setFleet(currentCommander.getFleet());
        // Send response:
        request.sendResponse();
    }

    private void postDemo(PostDemoRequest request) {

        List<Person> ppl = request.getPeople();

        // throw new ForbiddenAccessException("Reason") if needed.
        if (ppl.contains(new Person("James Bond", 7))) {
            throw new ForbiddenAccessException("You are not allowed to sort spies.");
        }

        if (request.isOrderByName()) {// Get data out of request.
            // Pass data to the service (or token manager)
            // Set results in the request
            request.setSortedPeople(service.sortByName(ppl));
        } else if (request.isOrderByAge()) {// Get data out of request.
            // Pass data to the service (or token manager)
            // Set results in the request
            request.setSortedPeople(service.sortByAge(ppl));
        } else {
            throw new InvalidRequestException("A demo request should contain a valid order.");
        }







        // Send response:
        request.sendResponse();
    }

    private void onFailedRequest(RoutingContext ctx) {
        Throwable cause = ctx.failure();
        int code = ctx.statusCode();
        String quote = Objects.isNull(cause) ? "" + code : cause.getMessage();

        // Map custom runtime exceptions to a HTTP status code.
        LOGGER.log(Level.INFO, "Failed request cause", cause);

        if (cause instanceof InvalidRequestException) {
            code = 400;
        } else if (cause instanceof IllegalArgumentException) {
            code = 400;
        } else if (cause instanceof ForbiddenAccessException) {
            code = 403;
        } else if (cause instanceof BattleshipResourceNotFoundException) {
            code = 404;
        } else if (cause instanceof BattleshipException) {
            code = 409;
        } else if (cause instanceof NotYetImplementedException) {
            code = 501;
        } else {
            LOGGER.log(Level.WARNING, "Failed request", cause);
        }

        Response.sendFailure(ctx, code, quote);
    }

private CorsHandler createCorsHandler() {
    return CorsHandler.create(".*.")
            .allowedHeader("x-requested-with")
            .allowedHeader("Access-Control-Allow-Origin")
            .allowedHeader("Access-Control-Allow-Credentials")
            .allowCredentials(true)
            .allowedHeader("origin")
            .allowedHeader("Content-Type")
            .allowedHeader("Authorization")
            .allowedHeader("accept")
            .allowedMethod(HttpMethod.HEAD)
            .allowedMethod(HttpMethod.GET)
            .allowedMethod(HttpMethod.POST)
            .allowedMethod(HttpMethod.OPTIONS)
            .allowedMethod(HttpMethod.PATCH)
            .allowedMethod(HttpMethod.DELETE)
            .allowedMethod(HttpMethod.PUT);
}
}
