package me.figo.internal;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Proxies SSLSocketFactory to force use of specific SSL protocols
 */
public class FigoSocketFactory extends SSLSocketFactory {

    private static final String[] ENABLED_PROTOCOLS = {"TLSv1.2"};

    private SSLSocketFactory isf;

    public FigoSocketFactory(SSLSocketFactory factory) {
        isf = factory;
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return isf.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return isf.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
        return enableProtocols(isf.createSocket(socket, host, port, autoClose));
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return enableProtocols(isf.createSocket(host, port));
    }

    @Override
    public Socket createSocket(String remoteHost, int remotePort, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
        return enableProtocols(isf.createSocket(remoteHost, remotePort, localHost, localPort));
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return enableProtocols(isf.createSocket(host, port));
    }

    @Override
    public Socket createSocket(InetAddress remoteHost, int remotePort, InetAddress localHost, int localPort) throws IOException {
        return enableProtocols(isf.createSocket(remoteHost, remotePort, localHost, localPort));
    }

    private Socket enableProtocols(Socket socket) {
        if (socket != null && socket instanceof SSLSocket) {
            ((SSLSocket) socket).setEnabledProtocols(ENABLED_PROTOCOLS);
        }
        return socket;
    }
}
