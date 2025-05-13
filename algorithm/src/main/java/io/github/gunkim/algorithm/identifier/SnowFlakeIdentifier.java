package io.github.gunkim.algorithm.identifier;

public class SnowFlakeIdentifier {
    private static final int DATACENTER_ID_BITS = 5;
    private static final int WORKER_ID_BITS = 5;
    private static final int SEQUENCE_BITS = 12;

    private static final int WORKER_ID_SHIFT = SEQUENCE_BITS;
    private static final int DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private static final int TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

    private long timestamp;
    private long datacenterId;
    private long workerId;
    private long sequence;

    public SnowFlakeIdentifier(long timestamp, long datacenterId, long workerId, long sequence) {
        this.timestamp = timestamp;
        this.datacenterId = datacenterId;
        this.workerId = workerId;
        this.sequence = sequence;
    }

    public long getValue() {
        return (timestamp << TIMESTAMP_SHIFT) |
                (datacenterId << DATACENTER_ID_SHIFT) |
                (workerId << WORKER_ID_SHIFT) |
                sequence;
    }
}
