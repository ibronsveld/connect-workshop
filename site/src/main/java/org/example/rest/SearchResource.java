package org.example.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.FilterException;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.onehippo.cms7.essentials.components.paging.Pageable;
import org.onehippo.cms7.essentials.components.rest.BaseRestResource;
import org.onehippo.cms7.essentials.components.rest.ctx.DefaultRestContext;
import org.example.beans.ContentDocument;
import org.onehippo.crosslink.rest.DataSourceRestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @version "$Id$"
 */

@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
@Path("/search")
public class SearchResource extends BaseRestResource {
    private static final Logger log = LoggerFactory.getLogger(SearchResource.class);

    @GET
    @Path("/?q={query}")
    public List<ContentDocument> index(@Context HttpServletRequest request, @QueryParam("q") String query) {

        List<ContentDocument> docs = new ArrayList<>();

        try {
            HstQuery hstQuery = this.createQuery(new DefaultRestContext(this, request), ContentDocument.class, Subtypes.INCLUDE);
            Filter filter = hstQuery.createFilter();

            filter.addContains(".", query);

            hstQuery.setFilter(filter);

            HstQueryResult queryResult = hstQuery.execute();
            HippoBeanIterator hippoBeans = queryResult.getHippoBeans();

            while (hippoBeans.hasNext()) {
                docs.add((ContentDocument)hippoBeans.nextHippoBean());
            }

        } catch (FilterException e) {
            log.error("Error doing filter", e);

        } catch (QueryException e) {
            log.error("Error executing query", e);

        }

        return docs;
    }
}
