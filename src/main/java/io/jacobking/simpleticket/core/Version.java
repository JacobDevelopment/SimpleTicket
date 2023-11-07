package io.jacobking.simpleticket.core;


public class Version implements Comparable<Version> {
    private static final String FX_VERSION = "20.0.2";

    private static final Version current = new Version(0, 1, 2, "beta");

    private static final Version database = new Version(0, 0, 0, null);

    private int major;
    private int minor;
    private int patch;
    private String suffix;

    public Version(int major, int minor, int patch, String suffix) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.suffix = suffix;
    }

    public Version() {
        this.major = 0;
        this.minor = 0;
        this.patch = 0;
        this.suffix = null;
    }

    public static void setDatabase(final Version version) {
        database.setMajor(version.major);
        database.setMinor(version.minor);
        database.setPatch(version.patch);
        database.setSuffix(version.suffix);
    }


    public String asString() {
        if (suffix == null || suffix.isEmpty())
            return String.format("%d.%d.%d", major, minor, patch);
        return String.format("%d.%d.%d-%s", major, minor, patch, suffix);
    }

    public static String getCurrentAsString() {
        return current.asString();
    }

    public static Version getCurrent() {
        return current;
    }

    public static String getJDKVersion() {
        return System.getProperty("java.version");
    }

    public static Version getDatabase() {
        return database;
    }

    public static String getFXVersion() {
        return FX_VERSION;
    }


    public void setMajor(int major) {
        this.major = major;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public void setPatch(int patch) {
        this.patch = patch;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public int compareTo(Version o) {
        final int majorDiff = (this.major - o.major);
        if (majorDiff != 0)
            return major;

        final int minorDiff = (this.minor - o.minor);
        if (minorDiff != 0)
            return minorDiff;

        return (this.patch - o.patch);
    }
}
