package com.example.addd.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "userRecondApi",
        version = "v1",
        resource = "userRecond",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.addd.example.com",
                ownerName = "backend.myapplication.addd.example.com",
                packagePath = ""
        )
)
public class UserRecondEndpoint {

    private static final Logger logger = Logger.getLogger(UserRecondEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(UserRecond.class);
    }

    /**
     * Returns the {@link UserRecond} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code UserRecond} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "userRecond/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public UserRecond get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting UserRecond with ID: " + id);
        UserRecond userRecond = ofy().load().type(UserRecond.class).id(id).now();
        if (userRecond == null) {
            throw new NotFoundException("Could not find UserRecond with ID: " + id);
        }
        return userRecond;
    }

    /**
     * Inserts a new {@code UserRecond}.
     */
    @ApiMethod(
            name = "insert",
            path = "userRecond",
            httpMethod = ApiMethod.HttpMethod.POST)
    public UserRecond insert(UserRecond userRecond) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that userRecond.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(userRecond).now();
        logger.info("Created UserRecond with ID: " + userRecond.getId());

        return ofy().load().entity(userRecond).now();
    }

    /**
     * Updates an existing {@code UserRecond}.
     *
     * @param id         the ID of the entity to be updated
     * @param userRecond the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code UserRecond}
     */
    @ApiMethod(
            name = "update",
            path = "userRecond/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public UserRecond update(@Named("id") Long id, UserRecond userRecond) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(userRecond).now();
        logger.info("Updated UserRecond: " + userRecond);
        return ofy().load().entity(userRecond).now();
    }

    /**
     * Deletes the specified {@code UserRecond}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code UserRecond}
     */
    @ApiMethod(
            name = "remove",
            path = "userRecond/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(UserRecond.class).id(id).now();
        logger.info("Deleted UserRecond with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "userRecond",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<UserRecond> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<UserRecond> query = ofy().load().type(UserRecond.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<UserRecond> queryIterator = query.iterator();
        List<UserRecond> userRecondList = new ArrayList<UserRecond>(limit);
        while (queryIterator.hasNext()) {
            userRecondList.add(queryIterator.next());
        }
        return CollectionResponse.<UserRecond>builder().setItems(userRecondList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(UserRecond.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find UserRecond with ID: " + id);
        }
    }
}