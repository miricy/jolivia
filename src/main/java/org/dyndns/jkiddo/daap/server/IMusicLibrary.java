/*******************************************************************************
 * Copyright (c) 2013 Jens Kristian Villadsen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Jens Kristian Villadsen - Lead developer, owner and creator
 ******************************************************************************/
package org.dyndns.jkiddo.daap.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.dyndns.jkiddo.dmap.service.ILibraryResource;

public interface IMusicLibrary extends ILibraryResource
{
	public static final String DAAP_SERVICE_TYPE = "_daap._tcp.local.";

	@Path("/databases/{databaseId}/items/{itemId}/extra_data/artwork")
	@GET
	Response artwork(@PathParam("databaseId") long databaseId, @PathParam("itemId") long itemId, @QueryParam("session-id") long sessionId, @QueryParam("revision-number") long revisionNumber, @QueryParam("mw") String mw, @QueryParam("mh") String mh) throws Exception;

}