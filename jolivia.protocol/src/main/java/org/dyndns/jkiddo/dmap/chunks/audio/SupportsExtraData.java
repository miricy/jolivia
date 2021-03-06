package org.dyndns.jkiddo.dmap.chunks.audio;

import org.dyndns.jkiddo.dmp.chunks.UShortChunk;

import org.dyndns.jkiddo.dmp.IDmapProtocolDefinition.DmapChunkDefinition;
import org.dyndns.jkiddo.dmp.DMAPAnnotation;

@DMAPAnnotation(type=DmapChunkDefinition.ated)
public class SupportsExtraData extends UShortChunk
{
	public SupportsExtraData()
	{
		this(0);
	}

	public SupportsExtraData(int i)
	{
		super("ated", "daap.supportsextradata", i);
	}
}
