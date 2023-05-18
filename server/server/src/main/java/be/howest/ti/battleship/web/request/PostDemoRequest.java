package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.Person;
import be.howest.ti.battleship.web.InvalidRequestException;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.ext.web.RoutingContext;

import java.util.List;


public class PostDemoRequest extends Request {

    private static class Body {

        @JsonProperty private String orderBy;

        @JsonProperty private List<Person> unsortedPeople;

    }
    private List<Person> sortedPeople;

    public PostDemoRequest(RoutingContext ctx) {
        super(ctx);
    }

    @Override
    public void sendResponse() {
        Response.sendJson(ctx, 200, sortedPeople);
    }

    public List<Person> getPeople() {
        List<Person> aList = ctx.body().asPojo(Body.class).unsortedPeople;
        if (aList == null) {
            throw new InvalidRequestException("The body needs a unsortedPeople array");
        }
        return aList;
    }

    public boolean isOrderByName() {
        return "name".equals(ctx.body().asPojo(Body.class).orderBy);
    }

    public boolean isOrderByAge() {
        return "age".equals(ctx.body().asPojo(Body.class).orderBy);
    }

    public void setSortedPeople(List<Person> sortedPeople) {
        this.sortedPeople = sortedPeople;
    }
}
