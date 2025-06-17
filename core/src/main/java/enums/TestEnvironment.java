package enums;

public enum TestEnvironment {
    ENVIRONMENT_BASE_URL {
        @Override
        public String getUrl() {
            if (System.getProperty("eut") != null) {
                switch (System.getProperty("eut")) {
                    case PRODUCTION:
                        return "";
                    case UAT:
                        return "";
                    case STAGING:
                        return "";
                    case BUGFIX:
                        return "";
                    default:
                        return "";
                }
            } else {
                return "https://automationintesting.online/";
            }
        }
    };

    public static final String PRODUCTION = "production";
    public static final String BUGFIX = "bugfix";
    public static final String UAT = "uat";
    public static final String STAGING = "staging";

    public static String[] getEnvironmentList() {
        return new String[]{PRODUCTION, UAT, STAGING, BUGFIX};
    }

    public String getUrl() {
        return "";
    }
}
