package org.dyndns.jkiddo.dmap.chunks.audio.extension;

import org.dyndns.jkiddo.dmp.chunks.StringChunk;

import org.dyndns.jkiddo.dmp.IDmapProtocolDefinition.DmapChunkDefinition;
import org.dyndns.jkiddo.dmp.DMAPAnnotation;

@DMAPAnnotation(type=DmapChunkDefinition.aeNN)
public class NetworkName extends StringChunk
{
	public NetworkName()
	{
		this("");
	}

	public NetworkName(String s)
	{
		super("aeNN", "com.apple.itunes.network-name", s);
	}
}
