module jmp.cloud.service.impl {
    requires jmp.service.api;
    requires jmp.dto;
    requires java.logging;
    exports org.app.cloud.service;
    provides org.app.service.api.Service with org.app.cloud.service.AppService;
}