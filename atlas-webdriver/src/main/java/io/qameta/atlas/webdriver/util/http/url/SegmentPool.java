package io.qameta.atlas.webdriver.util.http.url;


/**
 * A collection of unused segments, necessary to avoid GC churn and zero-fill.
 * This pool is a thread-safe static singleton.
 */
@SuppressWarnings("PMD")
final class SegmentPool {
    /**
     * The maximum number of bytes to pool.
     */

    private static final long MAX_SIZE = 64 * 1024;

    /**
     * Singly-linked list of segments.
     */
    private static Segment next;

    /**
     * Total bytes in this pool.
     */
    private static long byteCount;

    private SegmentPool() {
    }

    static Segment take() {
        synchronized (SegmentPool.class) {
            if (next != null) {
                final Segment result = next;
                next = result.next;
                result.next = null;
                byteCount -= Segment.SIZE;
                return result;
            }
        }
        return new Segment();
    }

    static void recycle(Segment segment) {
        if (segment.next != null || segment.prev != null) {
            throw new IllegalArgumentException();
        }

        synchronized (SegmentPool.class) {
            if (byteCount + Segment.SIZE > MAX_SIZE || segment.shared) {
                return;
            }
            byteCount += Segment.SIZE;
            segment.next = next;
            segment.limit = 0;
            segment.pos = segment.limit;
            next = segment;
        }
    }
}
