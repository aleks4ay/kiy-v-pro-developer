package org.aleks4ay.developer.model;

public class Page {
    private long position;
    private long maxPosition;
    private final long positionOnPage;

    public Page(long positionOnPage) {
        this.positionOnPage = positionOnPage;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public long getMaxPosition() {
        return maxPosition;
    }

    public void setMaxPosition(long maxPosition) {
        this.maxPosition = maxPosition;
    }

    public long getPositionOnPage() {
        return positionOnPage;
    }

    public boolean isLast() {
        return position == maxPosition;
    }

    public boolean isFirst() {
        return position == 0;
    }

    public boolean next() {
        if (isLast()) {
            return false;
        }
        position ++;
        return true;
    }

    public boolean previous() {
        if (isFirst()) {
            return false;
        }
        position --;
        return true;
    }
}
