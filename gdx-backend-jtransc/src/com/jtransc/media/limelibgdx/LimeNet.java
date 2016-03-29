package com.jtransc.media.limelibgdx;

import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

public class LimeNet implements Net {
	@Override
	public void sendHttpRequest(HttpRequest httpRequest, HttpResponseListener httpResponseListener) {
		throw new NotImplementedException();
	}

	@Override
	public void cancelHttpRequest(HttpRequest httpRequest) {
		throw new NotImplementedException();
	}

	@Override
	public ServerSocket newServerSocket(Protocol protocol, String s, int i, ServerSocketHints serverSocketHints) {
		throw new NotImplementedException();
	}

	@Override
	public ServerSocket newServerSocket(Protocol protocol, int i, ServerSocketHints serverSocketHints) {
		throw new NotImplementedException();
	}

	@Override
	public Socket newClientSocket(Protocol protocol, String s, int i, SocketHints socketHints) {
		throw new NotImplementedException();
	}

	@Override
	public boolean openURI(String s) {
		throw new NotImplementedException();
	}
}
