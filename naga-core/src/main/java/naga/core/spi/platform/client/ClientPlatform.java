package naga.core.spi.platform.client;

import naga.core.spi.bus.BusFactory;
import naga.core.spi.bus.client.ClientBusFactory;
import naga.core.spi.bus.client.WebSocketBusOptions;
import naga.core.spi.json.JsonObject;
import naga.core.spi.platform.Platform;

/**
 * @author Bruno Salmon
 */
public abstract class ClientPlatform extends Platform {

    public BusFactory busFactory() { return ClientBusFactory.SINGLETON; }

    @Override
    public WebSocketBusOptions createBusOptions() {
        return new WebSocketBusOptions();
    }

    public abstract WebSocketFactory webSocketFactory();

    /*** Static access ***/

    public static ClientPlatform get() {
        return (ClientPlatform) Platform.get();
    }

    /*** Static helper methods ***/
    // WebSocketFactory method

    public static WebSocket createWebSocket(String url, JsonObject options) {
        return get().webSocketFactory().createWebSocket(url, options);
    }
}
