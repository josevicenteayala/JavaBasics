module jmp.service.impl {
    exports org.app.custom.service;
    requires jmp.dto;
    requires jmp.service.api;
    provides org.app.service.api.Service with org.app.custom.service.AppService;
}