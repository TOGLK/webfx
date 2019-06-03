// Generated by WebFx

module mongoose.backend.activities.bookings {

    // Direct dependencies modules
    requires java.base;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires mongoose.backend.activities.cloneevent.routing;
    requires mongoose.client.activity;
    requires mongoose.client.aggregates;
    requires mongoose.client.personaldetails;
    requires mongoose.client.util;
    requires mongoose.frontend.activities.fees;
    requires mongoose.shared.domain;
    requires mongoose.shared.entities;
    requires webfx.framework.client.action;
    requires webfx.framework.client.activity;
    requires webfx.framework.client.controls;
    requires webfx.framework.client.domain;
    requires webfx.framework.client.layouts;
    requires webfx.framework.client.uifilter;
    requires webfx.framework.client.uirouter;
    requires webfx.framework.shared.domain;
    requires webfx.framework.shared.entity;
    requires webfx.framework.shared.expression;
    requires webfx.framework.shared.operation;
    requires webfx.framework.shared.router;
    requires webfx.fxkit.extracontrols;
    requires webfx.fxkit.extratype;
    requires webfx.fxkit.util;
    requires webfx.platform.client.windowhistory;
    requires webfx.platform.shared.json;
    requires webfx.platform.shared.util;

    // Exported packages
    exports mongoose.backend.activities.bookings;
    exports mongoose.backend.activities.bookings.routing;
    exports mongoose.backend.operations.bookings;

    // Provided services
    provides webfx.framework.client.operations.route.RouteRequestEmitter with mongoose.backend.activities.bookings.RouteToBookingsRequestEmitter;
    provides webfx.framework.client.ui.uirouter.UiRoute with mongoose.backend.activities.bookings.BookingsUiRoute;

}