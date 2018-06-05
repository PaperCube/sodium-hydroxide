package naoh.client;

public enum Predifined {
    defaultLogPath("C:/ProgramData/Local/SodiumHydroxide/Logs/Log-%s.txt"),
    @Deprecated defaultBridgePath("C:\\ProgramData\\AccessBridge\\bridge.jar"),
    defaultStartUpPath("C:/ProgramData/Microsoft/Windows/Start Menu/Programs/StartUp/"),
    defaultStartUpPathXP("C:\\Documents and Settings\\All Users\\「开始」菜单\\程序\\启动"),
    defaultInstallationPath("C:/ProgramData/Local/SodiumHydroxide/"),
    version("v1");


    public final String value;

    Predifined(String str) {
        value = str;
    }


}

