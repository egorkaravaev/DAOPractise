package util;


public class Configuration {
    private int minPoolSize;
    private int maxPoolSize;
    private long idleTimeLimit;
    private double loadFactor;
    private int incrementPortion;

    public int getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(int minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public long getIdleTimeLimit() {
        return idleTimeLimit;
    }

    public void setIdleTimeLimit(long idleTimeLimit) {
        this.idleTimeLimit = idleTimeLimit;
    }

    public double getLoadFactor() {
        return loadFactor;
    }

    public void setLoadFactor(double loadFactor) {
        this.loadFactor = loadFactor;
    }

    public int getIncrementPortion() {
        return incrementPortion;
    }

    public void setIncrementPortion(int incrementPortion) {
        this.incrementPortion = incrementPortion;
    }
}
