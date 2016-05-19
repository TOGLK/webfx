package naga.core.spi.platform.client.web;

import naga.core.spi.bus.BusOptions;
import naga.core.client.bus.WebSocketBusOptions;
import naga.core.spi.platform.client.ClientPlatform;

/**
 * @author Bruno Salmon
 */
public abstract class WebPlatform extends ClientPlatform {

    @Override
    public void setPlatformBusOptions(BusOptions options) {
        WebSocketBusOptions socketBusOptions = (WebSocketBusOptions) options;
        // Setting protocol to HTTP (unless already explicitly set by the application)
        if (socketBusOptions.getProtocol() == null)
            socketBusOptions.setProtocol(WebSocketBusOptions.Protocol.HTTP);
        // Setting protocol to Web Socket (unless already explicitly set by the application)
        WebLocation webLocation = getCurrentLocation();
        if (socketBusOptions.getServerHost() == null)
            socketBusOptions.setServerHost(webLocation.getHostName());
        if (socketBusOptions.getServerPort() == null) {
            Integer port = webLocation.getPort();
            if (port != null && port == 63342) // Port used by IntelliJ IDEA to serve web pages when testing directly in IDEA
                port = 80; // But the actual naga server web port on the development local machine is 80 in this case
            socketBusOptions.setServerPort(port);
        }
        super.setPlatformBusOptions(options);
    }

    public abstract WebLocation getCurrentLocation();
}
