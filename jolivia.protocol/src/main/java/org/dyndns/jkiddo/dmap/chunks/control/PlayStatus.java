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
package org.dyndns.jkiddo.dmap.chunks.control;

import org.dyndns.jkiddo.dmap.chunks.UByteChunk;

public class PlayStatus extends UByteChunk
{
	public static int PLAYING = 4;
	public static int PAUSED = 3;
	public PlayStatus()
	{
		this(0);
	}

	public PlayStatus(int value)
	{
		// # play status: 4=playing, 3=paused
		super("caps", "dacp.playerstate", value);
	}

}
