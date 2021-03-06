package com.github.alien11689.webservices.restwithspring03.caching;

import com.github.alien11689.webservices.model.Change;
import com.github.alien11689.webservices.model.Review;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("/caching/change")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ChangesResourceWithCache {

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
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        CacheControl cc = new CacheControl();
        cc.setMaxAge(60 * 60 * 24); //in seconds

        EntityTag eTag = new EntityTag("change_" + id + "_" + change.hashCode());

        Optional<Date> maybeLastChangeModification = lastChangeModification(change);
        if (!maybeLastChangeModification.isPresent()) {
            return Response
                    .ok(change)
                    .header("ETag", eTag.getValue())
                    .build();
        } else {
            Response.ResponseBuilder builder = request.evaluatePreconditions(maybeLastChangeModification.get());
            if (builder == null) {
                return Response
                        .ok(change)
                        .cacheControl(cc)
                        .lastModified(maybeLastChangeModification.get())
                        .header("ETag", eTag.getValue())
                        .build();
            }
            return builder
                    .cacheControl(cc)
                    .lastModified(maybeLastChangeModification.get())
                    .header("ETag", eTag.getValue())
                    .build();
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

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response update(Change newChange, @Context Request request, @HeaderParam("If-Match") String checkMatch) {
        Change change = changes.get(newChange.getId());
        if (change == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (checkMatch != null && !checkMatch.isEmpty()) {
            EntityTag eTag = new EntityTag("change_" + change.getId() + "_" + change.hashCode());
            Response.ResponseBuilder builder = request.evaluatePreconditions(eTag);

            if (builder == null) {
                changes.put(newChange.getId(), newChange);
                return Response
                        .noContent()
                        .build();
            }else{
                return Response
                        .status(Response.Status.PRECONDITION_FAILED)
                        .build();
            }
        }else {
            return Response.noContent().build();
        }
    }

    @DELETE
    public void deleteChanges() {
        changes.clear();
    }
}
