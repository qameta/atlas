package io.qameta.atlas.webdriver.util.http.url;

import java.io.EOFException;
import java.nio.charset.Charset;


/**
 * A collection of bytes in memory.
 * Main functional from Okhttp.
 */
//CHECKSTYLE:OFF
@SuppressWarnings("PMD")
public final class Buffer {
    private static final byte[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    Segment head;
    long size;

    public Buffer() {
    }

    /** Returns the number of bytes currently in this buffer. */
    public final long size() {
        return size;
    }


    public boolean exhausted() {
        return size == 0;
    }



    public byte readByte() {
        if (size == 0) throw new IllegalStateException("size == 0");

        Segment segment = head;
        int pos = segment.pos;
        int limit = segment.limit;

        byte[] data = segment.data;
        byte b = data[pos++];
        size -= 1;

        if (pos == limit) {
            head = segment.pop();
            SegmentPool.recycle(segment);
        } else {
            segment.pos = pos;
        }

        return b;
    }






    public String readUtf8() {
        try {
            return readString(size, UTF_8);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }




    private String readString(long byteCount, Charset charset) throws EOFException {
        if (charset == null) throw new IllegalArgumentException("charset == null");
        if (byteCount > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + byteCount);
        }
        if (byteCount == 0) return "";

        Segment s = head;
        if (s.pos + byteCount > s.limit) {
            // If the string spans multiple segments, delegate to readBytes().
            return new String(readByteArray(byteCount), charset);
        }

        String result = new String(s.data, s.pos, (int) byteCount, charset);
        s.pos += byteCount;
        size -= byteCount;

        if (s.pos == s.limit) {
            head = s.pop();
            SegmentPool.recycle(s);
        }

        return result;
    }




     private byte[] readByteArray(long byteCount) throws EOFException {
        if (byteCount > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + byteCount);
        }

        byte[] result = new byte[(int) byteCount];
        readFully(result);
        return result;
    }



    private void readFully(byte[] sink) throws EOFException {
        int offset = 0;
        while (offset < sink.length) {
            int read = read(sink, offset, sink.length - offset);
            if (read == -1) throw new EOFException();
            offset += read;
        }
    }

    private int read(byte[] sink, int offset, int byteCount) {

        Segment s = head;
        if (s == null) return -1;
        int toCopy = Math.min(byteCount, s.limit - s.pos);
        System.arraycopy(s.data, s.pos, sink, offset, toCopy);

        s.pos += toCopy;
        size -= toCopy;

        if (s.pos == s.limit) {
            head = s.pop();
            SegmentPool.recycle(s);
        }

        return toCopy;
    }





     public Buffer writeUtf8(String string) {
        return writeUtf8(string, 0, string.length());
    }

     public Buffer writeUtf8(String string, int beginIndex, int endIndex) {
        if (string == null) throw new IllegalArgumentException("string == null");
        if (beginIndex < 0) throw new IllegalArgumentException("beginIndex < 0: " + beginIndex);
        if (endIndex < beginIndex) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + endIndex + " < " + beginIndex);
        }
        if (endIndex > string.length()) {
            throw new IllegalArgumentException(
                    "endIndex > string.length: " + endIndex + " > " + string.length());
        }

        // Transcode a UTF-16 Java String to UTF-8 bytes.
        for (int i = beginIndex; i < endIndex;) {
            int c = string.charAt(i);

            if (c < 0x80) {
                Segment tail = writableSegment(1);
                byte[] data = tail.data;
                int segmentOffset = tail.limit - i;
                int runLimit = Math.min(endIndex, Segment.SIZE - segmentOffset);

                // Emit a 7-bit character with 1 byte.
                data[segmentOffset + i++] = (byte) c; // 0xxxxxxx

                // Fast-path contiguous runs of ASCII characters. This is ugly, but yields a ~4x performance
                // improvement over independent calls to writeByte().
                while (i < runLimit) {
                    c = string.charAt(i);
                    if (c >= 0x80) break;
                    data[segmentOffset + i++] = (byte) c; // 0xxxxxxx
                }

                int runSize = i + segmentOffset - tail.limit; // Equivalent to i - (previous i).
                tail.limit += runSize;
                size += runSize;

            } else if (c < 0x800) {
                // Emit a 11-bit character with 2 bytes.
                writeByte(c >>  6        | 0xc0); // 110xxxxx
                writeByte(c       & 0x3f | 0x80); // 10xxxxxx
                i++;

            } else if (c < 0xd800 || c > 0xdfff) {
                // Emit a 16-bit character with 3 bytes.
                writeByte(c >> 12        | 0xe0); // 1110xxxx
                writeByte(c >>  6 & 0x3f | 0x80); // 10xxxxxx
                writeByte(c       & 0x3f | 0x80); // 10xxxxxx
                i++;

            } else {
                // c is a surrogate. Make sure it is a high surrogate & that its successor is a low
                // surrogate. If not, the UTF-16 is invalid, in which case we emit a replacement character.
                int low = i + 1 < endIndex ? string.charAt(i + 1) : 0;
                if (c > 0xdbff || low < 0xdc00 || low > 0xdfff) {
                    writeByte('?');
                    i++;
                    continue;
                }

                // UTF-16 high surrogate: 110110xxxxxxxxxx (10 bits)
                // UTF-16 low surrogate:  110111yyyyyyyyyy (10 bits)
                // Unicode code point:    00010000000000000000 + xxxxxxxxxxyyyyyyyyyy (21 bits)
                int codePoint = 0x010000 + ((c & ~0xd800) << 10 | low & ~0xdc00);

                // Emit a 21-bit character with 4 bytes.
                writeByte(codePoint >> 18        | 0xf0); // 11110xxx
                writeByte(codePoint >> 12 & 0x3f | 0x80); // 10xxxxxx
                writeByte(codePoint >>  6 & 0x3f | 0x80); // 10xxyyyy
                writeByte(codePoint       & 0x3f | 0x80); // 10yyyyyy
                i += 2;
            }
        }

        return this;
    }

     public Buffer writeUtf8CodePoint(int codePoint) {
        if (codePoint < 0x80) {
            // Emit a 7-bit code point with 1 byte.
            writeByte(codePoint);

        } else if (codePoint < 0x800) {
            // Emit a 11-bit code point with 2 bytes.
            writeByte(codePoint >>  6        | 0xc0); // 110xxxxx
            writeByte(codePoint       & 0x3f | 0x80); // 10xxxxxx

        } else if (codePoint < 0x10000) {
            if (codePoint >= 0xd800 && codePoint <= 0xdfff) {
                // Emit a replacement character for a partial surrogate.
                writeByte('?');
            } else {
                // Emit a 16-bit code point with 3 bytes.
                writeByte(codePoint >> 12        | 0xe0); // 1110xxxx
                writeByte(codePoint >>  6 & 0x3f | 0x80); // 10xxxxxx
                writeByte(codePoint       & 0x3f | 0x80); // 10xxxxxx
            }

        } else if (codePoint <= 0x10ffff) {
            // Emit a 21-bit code point with 4 bytes.
            writeByte(codePoint >> 18        | 0xf0); // 11110xxx
            writeByte(codePoint >> 12 & 0x3f | 0x80); // 10xxxxxx
            writeByte(codePoint >>  6 & 0x3f | 0x80); // 10xxxxxx
            writeByte(codePoint       & 0x3f | 0x80); // 10xxxxxx

        } else {
            throw new IllegalArgumentException(
                    "Unexpected code point: " + Integer.toHexString(codePoint));
        }

        return this;
    }




    public Buffer writeString(String string, int beginIndex, int endIndex, Charset charset) {
        if (string == null) throw new IllegalArgumentException("string == null");
        if (beginIndex < 0) throw new IllegalAccessError("beginIndex < 0: " + beginIndex);
        if (endIndex < beginIndex) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + endIndex + " < " + beginIndex);
        }
        if (endIndex > string.length()) {
            throw new IllegalArgumentException(
                    "endIndex > string.length: " + endIndex + " > " + string.length());
        }
        if (charset == null) throw new IllegalArgumentException("charset == null");
        if (charset.equals(UTF_8)) return writeUtf8(string, beginIndex, endIndex);
        byte[] data = string.substring(beginIndex, endIndex).getBytes(charset);
        return write(data, 0, data.length);
    }



     public Buffer write(byte[] source, int offset, int byteCount) {
        if (source == null) throw new IllegalArgumentException("source == null");

        int limit = offset + byteCount;
        while (offset < limit) {
            Segment tail = writableSegment(1);

            int toCopy = Math.min(limit - offset, Segment.SIZE - tail.limit);
            System.arraycopy(source, offset, tail.data, tail.limit, toCopy);

            offset += toCopy;
            tail.limit += toCopy;
        }

        size += byteCount;
        return this;
    }



     public Buffer writeByte(int b) {
        Segment tail = writableSegment(1);
        tail.data[tail.limit++] = (byte) b;
        size += 1;
        return this;
    }








     public Buffer writeHexadecimalUnsignedLong(long v) {
        if (v == 0) {
            // Both a shortcut and required since the following code can't handle zero.
            return writeByte('0');
        }

        int width = Long.numberOfTrailingZeros(Long.highestOneBit(v)) / 4 + 1;

        Segment tail = writableSegment(width);
        byte[] data = tail.data;
        for (int pos = tail.limit + width - 1, start = tail.limit; pos >= start; pos--) {
            data[pos] = DIGITS[(int) (v & 0xF)];
            v >>>= 4;
        }
        tail.limit += width;
        size += width;
        return this;
    }

    /**
     * Returns a tail segment that we can write at least {@code minimumCapacity}
     * bytes to, creating it if necessary.
     */
    Segment writableSegment(int minimumCapacity) {
        if (minimumCapacity < 1 || minimumCapacity > Segment.SIZE) throw new IllegalArgumentException();

        if (head == null) {
            head = SegmentPool.take(); // Acquire a first segment.
            return head.next = head.prev = head;
        }

        Segment tail = head.prev;
        if (tail.limit + minimumCapacity > Segment.SIZE || !tail.owner) {
            tail = tail.push(SegmentPool.take()); // Append a new empty segment to fill up.
        }
        return tail;
    }
}
//CHECKSTYLE:ON
