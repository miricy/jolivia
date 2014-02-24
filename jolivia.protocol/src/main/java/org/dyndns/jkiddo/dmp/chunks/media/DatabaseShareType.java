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
package org.dyndns.jkiddo.dmp.chunks.media;

import org.dyndns.jkiddo.dmp.chunks.UIntChunk;

import org.dyndns.jkiddo.dmp.IDmapProtocolDefinition.DmapProtocolDefinition;
import org.dyndns.jkiddo.dmp.DMAPAnnotation;

@DMAPAnnotation(type=DmapProtocolDefinition.mdbk)
public class DatabaseShareType extends UIntChunk
{
	public static final int LOCAL = 0x01;
	public static final int SHARED = 0x02;
	public static final int RADIO = 0x64;

	public DatabaseShareType()
	{
		this(0);
	}

	public DatabaseShareType(long type)
	{
		super("mdbk", "dmap.databasesharetype", type);
	}
}
