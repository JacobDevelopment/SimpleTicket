package io.jacobking.simpleticket.core;

public class Version {
    private static final String FX_VERSION = "20.0.2";

    private static final Version current = new Version(1, 0, 0);

    private static final Version instance = new Version();

    private final int major;
    private final int minor;
    private final int patch;

    private Version(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    private Version() {
        this.major = 0;
        this.minor = 0;
        this.patch = 0;
    }

    public static Version getInstance() {
        if (instance == null)
            return new Version();
        return instance;
    }

    private String asString() {
        return String.format("%d.%d.%d", major, minor, patch);
    }

    public static String getCurrent() {
        return current.asString();
    }

    public static String getJDKVersion() {
        return System.getProperty("java.version");
    }

    public static String getFXVersion() {
        return FX_VERSION;
    }
}
