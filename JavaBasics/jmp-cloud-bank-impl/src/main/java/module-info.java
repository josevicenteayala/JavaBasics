module jmp.cloud.bank.impl {
    requires jmp.bank.api;
    requires jmp.dto;
    exports org.app.cloud;
    provides org.app.Bank with org.app.cloud.SecondBank;
}