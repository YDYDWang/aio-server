package com.github.ydydwang.aio.util;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;

public class SSLHandshakeUtils {
	private static final String STRING_HEARTBEAT_STATUS_EXCEPTION = "Wrong heartbeat status: %s";
	private static final String STRING_SOCKET_CLOSED_WHILE_HANDSHAKE = "Socket closed while ssl handshake";

	public static SSLException newWrongHearbeatStatus(HandshakeStatus status) {
		return new SSLException(String.format(STRING_HEARTBEAT_STATUS_EXCEPTION, status));
	}

	public static SSLException newSocketClosedWhileHandshake() {
		return new SSLException(String.format(STRING_SOCKET_CLOSED_WHILE_HANDSHAKE));
	}
}
