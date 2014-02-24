package org.dyndns.jkiddo.dmap.chunks.audio.extension;

import org.dyndns.jkiddo.dmp.chunks.ULongChunk;

import org.dyndns.jkiddo.dmp.IDmapProtocolDefinition.DmapProtocolDefinition;
import org.dyndns.jkiddo.dmp.DMAPAnnotation;

@DMAPAnnotation(type=DmapProtocolDefinition.aeCF)
public class CloudFlavorID extends ULongChunk
{
	public CloudFlavorID()
	{
		this(0);
	}

	public CloudFlavorID(int i)
	{
		super("aeCF", "com.apple.itunes.cloud-flavor-id", i);
	}
}
