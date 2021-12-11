package org.aleks4ay.developer.model;

public class Page {
    private long position;
    private long size;
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
        return size / getPositionOnPage();
    }

    public long getPositionOnPage() {
        return positionOnPage;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean isLast() {
        return position == getMaxPosition();
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
