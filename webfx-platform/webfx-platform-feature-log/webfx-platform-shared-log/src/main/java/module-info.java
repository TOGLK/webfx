// Generated by WebFx

module webfx.platform.shared.log {

    // Direct dependencies modules
    requires java.base;
    requires webfx.platform.shared.util;

    // Exported packages
    exports dev.webfx.platform.shared.services.log;
    exports dev.webfx.platform.shared.services.log.spi;

    // Used services
    uses dev.webfx.platform.shared.services.log.spi.LoggerProvider;

}