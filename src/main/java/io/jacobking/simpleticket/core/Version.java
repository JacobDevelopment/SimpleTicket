package io.jacobking.simpleticket.core;

public class Version {
    private static final String FX_VERSION = "20.0.2";

    private static final Version current = new Version(1, 1, 1, "beta");

    private static final Version instance = new Version();

    private final int major;
    private final int minor;
    private final int patch;
    private final String suffix;

    private Version(int major, int minor, int patch, String suffix) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.suffix = suffix;
    }

    private Version() {
        this.major = 0;
        this.minor = 0;
        this.patch = 0;
        this.suffix = null;
    }

    public static Version getInstance() {
        if (instance == null)
            return new Version();
        return instance;
    }

    private String asString() {
        if (suffix == null || suffix.isEmpty())
            return String.format("%d.%d.%d", major, minor, patch);
        return String.format("%d.%d.%d-%s", major, minor, patch, suffix);
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
