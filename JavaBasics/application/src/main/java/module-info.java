module application {
    requires jmp.dto;
    requires jmp.service.api;
    requires jmp.cloud.service.impl;
    requires jmp.service.impl;
    uses org.app.custom.service.AppService;
}