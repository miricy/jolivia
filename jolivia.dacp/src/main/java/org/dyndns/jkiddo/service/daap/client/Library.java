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
/*
 TunesRemote+ - http://code.google.com/p/tunesremote-plus/

 Copyright (C) 2008 Jeffrey Sharkey, http://jsharkey.org/
 Copyright (C) 2010 TunesRemote+, http://code.google.com/p/tunesremote-plus/

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.

 The Initial Developer of the Original Code is Jeffrey Sharkey.
 Portions created by Jeffrey Sharkey are
 Copyright (C) 2008. Jeffrey Sharkey, http://jsharkey.org/
 All Rights Reserved.
 */

package org.dyndns.jkiddo.service.daap.client;

import org.dyndns.jkiddo.dmap.chunks.audio.AlbumSearchContainer;
import org.dyndns.jkiddo.dmap.chunks.audio.ArtistSearchContainer;
import org.dyndns.jkiddo.dmap.chunks.audio.DatabaseBrowse;
import org.dyndns.jkiddo.dmap.chunks.audio.DatabaseItems;
import org.dyndns.jkiddo.dmap.chunks.audio.ItemsContainer;
import org.dyndns.jkiddo.dmap.chunks.audio.SongUserRating;
import org.dyndns.jkiddo.dmp.chunks.media.ListingItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Library
{

	public final static Logger logger = LoggerFactory.getLogger(Library.class);

	final Session session;

	Library(final Session session)
	{
		this.session = session;
	}

	/**
	 * Performs a search of the DACP Server sending it search criteria and an index of how many items to find.
	 * <p>
	 * 
	 * @param listener
	 *            the TagListener to capture records coming in for the UI
	 * @param search
	 *            the search criteria
	 * @param start
	 *            items to start with for paging (usually 0)
	 * @param items
	 *            the total items to return in this search
	 * @return the count of records returned or -1 if nothing found
	 * @throws Exception
	 */
	public ItemsContainer readSearch(final String search, final long start, final long items) throws Exception
	{
		final String encodedSearch = RequestHelper.escapeUrlString(search);
		//return RequestHelper.requestParsed(String.format("%s/databases/%d/containers/%d/items?session-id=%s&meta=dmap.itemname,dmap.itemid,dmap.persistentid,daap.songartist,daap.songalbum,daap.songtime,daap.songuserrating,daap.songtracknumber&type=music&sort=name&include-sort-headers=1&query=(('com.apple.itunes.mediakind:1','com.apple.itunes.mediakind:4','com.apple.itunes.mediakind:8')+('dmap.itemname:*%s*','daap.songartist:*%s*','daap.songalbum:*%s*'))&index=%d-%d", session.getRequestBase(), session.getDatabase().getItemId(), session.getDatabase().getMasterContainer().getItemId(), session.getSessionId(), encodedSearch, encodedSearch, encodedSearch, start, items), false);
		return RequestHelper.requestParsed(String.format("%s/databases/%d/containers/%d/items?session-id=%s&meta=all&type=music&sort=name&include-sort-headers=1&query=(('com.apple.itunes.mediakind:1','com.apple.itunes.mediakind:4','com.apple.itunes.mediakind:8')+('dmap.itemname:*%s*','daap.songartist:*%s*','daap.songalbum:*%s*'))&index=%d-%d", session.getRequestBase(), session.getDatabase().getItemId(), session.getDatabase().getMasterContainer().getItemId(), session.getSessionId(), encodedSearch, encodedSearch, encodedSearch, start, items), false);
	}

	public DatabaseBrowse getAllArtists() throws Exception
	{
		// request ALL artists for performance
		// /databases/%d/browse/artists?session-id=%s&include-sort-headers=1&index=%d-%d
		return RequestHelper.requestParsed(String.format("%s/databases/%d/browse/artists?session-id=%s&include-sort-headers=1", session.getRequestBase(), session.getDatabase().getItemId(), session.getSessionId()), false, true);
	}

	public AlbumSearchContainer getAlbums(final String artist) throws Exception
	{
		final String encodedArtist = RequestHelper.escapeUrlString(artist);
		// make albums request for this artist
		// http://192.168.254.128:3689/databases/36/groups?session-id=1034286700&meta=dmap.itemname,dmap.itemid,dmap.persistentid,daap.songartist&type=music&group-type=albums&sort=artist&include-sort-headers=1

		return RequestHelper.requestParsed(String.format("%s/databases/%d/groups?session-id=%s&meta=dmap.itemname,dmap.itemid,dmap.persistentid,daap.songartist&type=music&group-type=albums&sort=artist&include-sort-headers=1&query='daap.songartist:%s'", session.getRequestBase(), session.getDatabase().getItemId(), session.getSessionId(), encodedArtist), false);
	}

	public AlbumSearchContainer getAllAlbums() throws Exception
	{
		// make partial album list request
		// http://192.168.254.128:3689/databases/36/groups?session-id=1034286700&meta=dmap.itemname,dmap.itemid,dmap.persistentid,daap.songartist&type=music&group-type=albums&sort=artist&include-sort-headers=1&index=0-50
		return RequestHelper.requestParsed(String.format("%s/databases/%d/groups?session-id=%s&meta=dmap.itemname,dmap.itemid,dmap.persistentid,daap.songartist&type=music&group-type=albums&sort=album&include-sort-headers=1", session.getRequestBase(), session.getDatabase().getItemId(), session.getSessionId()), false);
	}

	public ArtistSearchContainer getArtists() throws Exception
	{
		return RequestHelper.requestParsed(String.format("%s/databases/%d/groups?meta=dmap.itemname,dmap.itemid,dmap.persistentid,daap.songartist,daap.groupalbumcount,daap.songartistid&type=music&group-type=artists&sort=album&include-sort-headers=1&query=('daap.songartist!:'+('com.apple.itunes.extended-media-kind:1','com.apple.itunes.extended-media-kind:32'))&session-id=%s", session.getRequestBase(), session.getDatabase().getItemId(), session.getSessionId()), false);
	}

	public ItemsContainer getAllTracks() throws Exception
	{
		
		// make tracks list request
		// http://192.168.254.128:3689/databases/36/containers/113/items?session-id=1301749047&meta=dmap.itemname,dmap.itemid,daap.songartist,daap.songalbum,daap.songalbum,daap.songtime,daap.songtracknumber&type=music&sort=album&query='daap.songalbumid:11624070975347817354'
		return RequestHelper.requestParsed(String.format("%s/databases/%d/containers/%d/items?session-id=%s&meta=dmap.itemname,dmap.itemid,daap.songartist,daap.songalbum,daap.songtime,dmap.containeritemid,com.apple.tunes.has-video,com.apple.itunes.can-be-genius-seed&type=music&sort=name&include-sort-headers=1&query=('com.apple.itunes.mediakind:1','com.apple.itunes.mediakind:32')", session.getRequestBase(), session.getDatabase().getItemId(), session.getDatabase().getMasterContainer().getItemId(), session.getSessionId()), false);
	}

	public ItemsContainer getTracks(final String albumid) throws Exception
	{
		// make tracks list request
		// http://192.168.254.128:3689/databases/36/containers/113/items?session-id=1301749047&meta=dmap.itemname,dmap.itemid,daap.songartist,daap.songalbum,daap.songalbum,daap.songtime,daap.songtracknumber&type=music&sort=album&query='daap.songalbumid:11624070975347817354'
		return RequestHelper.requestParsed(String.format("%s/databases/%d/containers/%d/items?session-id=%s&meta=dmap.itemname,dmap.itemid,daap.songartist,daap.songalbum,daap.songalbum,daap.songtime,daap.songuserrating,daap.songtracknumber&type=music&sort=album&query='daap.songalbumid:%s'", session.getRequestBase(), session.getDatabase().getItemId(), session.getDatabase().getMasterContainer().getItemId(), session.getSessionId(), albumid), false);
	}

	public ItemsContainer getAllTracks(final String artist) throws Exception
	{
		final String encodedArtist = RequestHelper.escapeUrlString(artist);

		// make tracks list request
		// http://192.168.254.128:3689/databases/36/containers/113/items?session-id=1301749047&meta=dmap.itemname,dmap.itemid,daap.songartist,daap.songalbum,daap.songalbum,daap.songtime,daap.songtracknumber&type=music&sort=album&query='daap.songalbumid:11624070975347817354'
		return RequestHelper.requestParsed(String.format("%s/databases/%d/containers/%d/items?session-id=%s&meta=dmap.itemname,dmap.itemid,daap.songartist,daap.songalbum,daap.songalbum,daap.songtime,daap.songuserrating,daap.songtracknumber&type=music&sort=album&query='daap.songartist:%s'", session.getRequestBase(), session.getDatabase().getItemId(), session.getDatabase().getMasterContainer().getItemId(), session.getSessionId(), encodedArtist), false);
	}

	public ItemsContainer getPlaylistSongs(final String playlistid) throws Exception
	{
		// http://192.168.254.128:3689/databases/36/containers/1234/items?session-id=2025037772&meta=dmap.itemname,dmap.itemid,daap.songartist,daap.songalbum,dmap.containeritemid,com.apple.tunes.has-video
		return RequestHelper.requestParsed(String.format("%s/databases/%d/containers/%s/items?session-id=%s&meta=dmap.itemname,dmap.itemid,daap.songartst,daap.songalbum,daap.songtime,dmap.containeritemid,com.apple.tunes.has-video", session.getRequestBase(), session.getDatabase().getItemId(), playlistid, session.getSessionId()), false);
	}

	public ItemsContainer getRadioPlaylist(final String playlistid) throws Exception
	{
		// GET /databases/24691/containers/24699/items?
		// meta=dmap.itemname,dmap.itemid,daap.songartist,daap.songalbum,
		// dmap.containeritemid,com.apple.itunes.has-video,daap.songdisabled,
		// com.apple.itunes.mediakind,daap.songdescription
		// &type=music&session-id=345827905
		return RequestHelper.requestParsed(String.format("%s/databases/%d/containers/%s/items?" + "meta=dmap.itemname,dmap.itemid,daap.songartist,daap.songalbum," + "dmap.containeritemid,com.apple.itunes.has-video,daap.songdisabled," + "com.apple.itunes.mediakind,daap.songdescription" + "&type=music&session-id=%s", session.getRequestBase(), session.getRadioDatabase().getItemId(), playlistid, session.getSessionId()), false);
	}

	public SongUserRating getTrackRating(final long trackId) throws Exception
	{
		// MonkeyTunes style would be with PlaylistSongs instead of DatabaseSongs
		final DatabaseItems databaseSongs = RequestHelper.requestParsed(String.format("%s/databases/%d/items?session-id=%s&meta=daap.songuserrating&type=music&query='dmap.itemid:%d'", session.getRequestBase(), session.getDatabase().getItemId(), session.getSessionId(), trackId));
		final ListingItem listingItem = databaseSongs.getListing().getSingleListingItemContainingClass(SongUserRating.class);
		return listingItem.getSpecificChunk(SongUserRating.class);
	}

	public ItemsContainer getUnknownQuery() throws Exception
	{
		return RequestHelper.requestParsed(String.format("%s/databases/%d/containers/%s/items?meta=dmap.itemname,dmap.itemid,daap.songartist,daap.songalbumartist,daap.songalbum,com.apple.itunes.cloud-id,dmap.containeritemid,com.apple.itunes.has-video,com.apple.itunes.itms-songid,com.apple.itunes.extended-media-kind,dmap.downloadstatus,daap.songdisabled&type=music&sort=name&include-sort-headers=1&query=('com.apple.itunes.extended-media-kind:1','com.apple.itunes.extended-media-kind:32')&session-id=%s", session.getRequestBase(), session.getDatabase().getItemId(), session.getDatabase().getMasterContainer().getItemId(), session.getSessionId()), false);
	}

	public byte[] getAlbumArtworkAsDatabase(final long itemId, final int imageWidth, final int imageHeight) throws Exception
	{
		return RequestHelper.requestBitmap(String.format("%s/databases/%d/items/%d/extra_data/artwork?session-id=%s&mw=" + imageWidth + "&mh=" + imageHeight + "&group-type=albums", session.getRequestBase(), session.getDatabase().getItemId(), itemId, session.getSessionId()));
	}

	public byte[] getAlbumArtworkAsRemote(final long itemId, final int imageWidth, final int imageHeight) throws Exception
	{
		return getAlbumArtworkAsRemote(itemId, imageWidth, imageHeight, null);
	}

	public byte[] getAlbumArtworkAsRemote(final long itemId, final int imageWidth, final int imageHeight, final String hsgid) throws Exception
	{
		return RequestHelper.requestBitmap(String.format("%s/databases/%d/groups/%d/extra_data/artwork?session-id=%s&mw=" + imageWidth + "&mh=" + imageHeight + "&group-type=albums", session.getRequestBase(), session.getDatabase().getItemId(), itemId, session.getSessionId()));
	}

	public DatabaseItems getTestCode(final String stuff) throws Exception
	{
//		return RequestHelper.requestParsed(String.format("%s/databases/%d/containers/%d/items?session-id=%s&meta=dmap.itemname,dmap.itemid,daap.songextradata,com.apple.itunes.artworkchecksum,daap.songartworkcount,com.apple.itunes.mediakind,daap.songartist,com.apple.itunes.jukebox-vote,com.apple.itunes.jukebox-client-vote,com.apple.itunes.jukebox-score,com.apple.itunes.jukebox-current,com.apple.itunes.extended-media-kind,com.apple.itunes.adam-ids-array&type=music&sort=name&include-sort-headers=1&query=('"+stuff+")", session.getRequestBase(), session.getDatabase().getItemId(), session.getDatabase().getMasterContainer().getItemId(), session.getSessionId()), false);
		//return RequestHelper.requestParsed(String.format("%s/databases/%d/containers/%d/items?session-id=%s&meta=all&query=('"+stuff+")", session.getRequestBase(), session.getDatabase().getItemId(), session.getDatabase().getMasterContainer().getItemId(), session.getSessionId()), false);
		//return RequestHelper.requestParsed(String.format("%s/databases/%d/containers/%d/items?session-id=%s&meta=all", session.getRequestBase(), session.getDatabase().getItemId(), session.getDatabase().getMasterContainer().getItemId(), session.getSessionId()), false);
		
		//return RequestHelper.requestParsed(String.format("%s/databases/%d/containers/%d/items?session-id=%s&meta=all&type=music&sort=album&query='daap.songartist:%s'", session.getRequestBase(), session.getDatabase().getItemId(), session.getDatabase().getMasterContainer().getItemId(), session.getSessionId(), stuff), false);
		return RequestHelper.requestParsed(String.format("http://localhost:3689/databases/%d/items?session-id=%s&delta=0&type=music&meta=dmap.itemid,dmap.itemname,dmap.itemkind,dmap.persistentid,daap.songalbum,daap.songgrouping,daap.songartist,daap.songalbumartist,daap.songbitrate,daap.songbeatsperminute,daap.songcomment,daap.songcodectype,daap.songcodecsubtype,daap.songcompilation,daap.songcomposer,daap.songdateadded,daap.songdatemodified,daap.songdisccount,daap.songdiscnumber,daap.songdisabled,daap.songeqpreset,daap.songformat,daap.songgenre,daap.songdescription,daap.songrelativevolume,daap.songsamplerate,daap.songsize,daap.songstarttime,daap.songstoptime,daap.songtime,daap.songtrackcount,daap.songtracknumber,daap.songuserrating,daap.songyear,daap.songdatakind,daap.songdataurl,daap.songcontentrating,com.apple.itunes.norm-volume,com.apple.itunes.itms-songid,com.apple.itunes.itms-artistid,com.apple.itunes.itms-playlistid,com.apple.itunes.itms-composerid,com.apple.itunes.itms-genreid,com.apple.itunes.itms-storefrontid,com.apple.itunes.has-videodaap.songcategory,daap.songextradata,daap.songcontentdescription,daap.songlongcontentdescription,daap.songkeywords,com.apple.itunes.is-podcast,com.apple.itunes.mediakind,com.apple.itunes.extended-media-kind,com.apple.itunes.series-name,com.apple.itunes.network-name,com.apple.itunes.episode-num-str,com.apple.itunes.episode-sort,com.apple.itunes.season-num,daap.songgapless,com.apple.itunes.gapless-enc-del,com.apple.itunes.gapless-heur,com.apple.itunes.gapless-enc-dr,com.apple.itunes.gapless-dur,com.apple.itunes.gapless-resy,com.apple.itunes.content-rating", session.getDatabase().getItemId(), session.getSessionId()));
	}
}
