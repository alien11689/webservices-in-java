package com.github.alien11689.webservices.restwithspring03.caching;

import com.github.alien11689.webservices.model.Change;
import com.github.alien11689.webservices.model.Review;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("/caching/change")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ChangesServiceWithCache {

    private AtomicLong changeCounter = new AtomicLong();

    private Map<Long, Change> changes = new HashMap<>();

    private Optional<Date> lastChangeModification(Change change) {
        if (change.getReviews() == null || change.getReviews().isEmpty()) {
            return Optional.empty();
        }
        return change.getReviews()
                .stream()
                .map(Review::getReviewDate)
                .sorted()
                .max(LocalDateTime::compareTo)
                .map(ldt -> Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant()));
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id, @Context Request request) {
        Change change = changes.get(id);
        if (change == null) {
            return Response.noContent().build();
        }
        CacheControl cc = new CacheControl();
        cc.setMaxAge(6000); //in seconds
        cc.setPrivate(true);

        Optional<Date> maybeLastChangeModification = lastChangeModification(change);
        if (!maybeLastChangeModification.isPresent()) {
            return Response.ok(change).build();
        } else {
            Response.ResponseBuilder builder = request.evaluatePreconditions(maybeLastChangeModification.get());
            if (builder == null) {
                return Response.ok(change).cacheControl(cc).lastModified(maybeLastChangeModification.get()).build();
            }
            return builder.cacheControl(cc).status(304).lastModified(maybeLastChangeModification.get()).build();
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Change create(Change change) {
        long newId = changeCounter.incrementAndGet();
        change.setId(newId);
        changes.put(newId, change);
        return change;
    }

    @DELETE
    public void deleteChanges() {
        changes.clear();
    }
}
