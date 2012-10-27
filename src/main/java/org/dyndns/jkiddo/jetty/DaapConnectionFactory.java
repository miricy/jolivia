package org.dyndns.jkiddo.jetty;

import org.eclipse.jetty.http.HttpParser;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpChannelConfig;
import org.eclipse.jetty.server.HttpConnection;
import org.eclipse.jetty.server.HttpConnectionFactory;

public class DaapConnectionFactory extends HttpConnectionFactory
{
	public DaapConnectionFactory()
	{
		super();
	}

	@Override
	public Connection newConnection(Connector connector, EndPoint endPoint)
	{
		return configure(new DaapConnection(getHttpChannelConfig(), connector, endPoint), connector, endPoint);
	}

	class DaapConnection extends HttpConnection
	{
		public DaapConnection(HttpChannelConfig config, Connector connector, EndPoint endPoint)
		{
			super(config, connector, endPoint);
		}

		@Override
		protected HttpParser newHttpParser()
		{
			return new DaapParser(newRequestHandler(), getHttpChannelConfig().getRequestHeaderSize());
		}
	}
}
