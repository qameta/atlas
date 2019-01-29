package io.qameta.atlas.webdriver.util.http.url;


//CHECKSTYLE:OFF
@SuppressWarnings("PMD")
final class Segment {
    /** The size of all segments in bytes. */
    static final int SIZE = 8192;

    /** Segments will be shared when doing so avoids {@code arraycopy()} of this many bytes. */
    static final int SHARE_MINIMUM = 1024;

    final byte[] data;

    /** The next byte of application data byte to read in this segment. */
    int pos;

    /** The first byte of available data ready to be written to. */
    int limit;

    /** True if other segments or byte strings use the same byte array. */
    boolean shared;

    /** True if this segment owns the byte array and can append to it, extending {@code limit}. */
    boolean owner;

    /** Next segment in a linked or circularly-linked list. */
    Segment next;

    /** Previous segment in a circularly-linked list. */
    Segment prev;

    Segment() {
        this.data = new byte[SIZE];
        this.owner = true;
        this.shared = false;
    }

    Segment(byte[] data, int pos, int limit, boolean shared, boolean owner) {
        this.data = data;
        this.pos = pos;
        this.limit = limit;
        this.shared = shared;
        this.owner = owner;
    }

    /**
     * Returns a new segment that shares the underlying byte array with this. Adjusting pos and limit
     * are safe but writes are forbidden. This also marks the current segment as shared, which
     * prevents it from being pooled.
     */
    Segment sharedCopy() {
        shared = true;
        return new Segment(data, pos, limit, true, false);
    }


    /**
     * Removes this segment of a circularly-linked list and returns its successor.
     * Returns null if the list is now empty.
     */
    Segment pop() {
        final Segment result = next != this ? next : null;
        prev.next = next;
        next.prev = prev;
        next = null;
        prev = null;
        return result;
    }

    /**
     * Appends {@code segment} after this segment in the circularly-linked list.
     * Returns the pushed segment.
     */
    public Segment push(final Segment segment) {
        segment.prev = this;
        segment.next = next;
        next.prev = segment;
        next = segment;
        return segment;
    }


    public Segment split(final int byteCount) {
        if (byteCount <= 0 || byteCount > limit - pos) {
            throw new IllegalArgumentException();
        }
        final Segment prefix;

        if (byteCount >= SHARE_MINIMUM) {
            prefix = sharedCopy();
        } else {
            prefix = SegmentPool.take();
            System.arraycopy(data, pos, prefix.data, 0, byteCount);
        }

        prefix.limit = prefix.pos + byteCount;
        pos += byteCount;
        prev.push(prefix);
        return prefix;
    }
}
//CHECKSTYLE:ON
