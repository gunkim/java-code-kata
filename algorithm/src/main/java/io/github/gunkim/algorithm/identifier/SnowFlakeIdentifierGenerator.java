package io.github.gunkim.algorithm.identifier;

/**
 * 트위터 Snowflake ID 생성기
 * 총 64비트 구조
 * - sign 1비트 : 부호 비트, 음수값으로 사용되지 않음
 * - timestamp 41비트 : 41비트로 표현되는 타임스탬프 (밀리초 단위)
 * - worker id 5비트 : 서버 ID, 최대 32(2^5)개 서버 지원
 * - datacenter id 5비트 : 데이터센터 ID, 최대 32(2^5)개 데이터센터 지원
 * - sequence 12비트 : 일련번호, 1밀리초 내에 최대 4096까지 증가하며 매 밀리초마다 초기화
 * <p>
 * Snowflake ID는 64비트 크기로 **정렬 가능**하며, **전 세계 어디에서나 독립적으로 유니크한 ID**를 생성할 수 있다.
 * 이론적으로, 최대 32개의 데이터센터와 각 데이터센터마다 32개의 서버를 사용할 수 있다.
 */
public final class SnowFlakeIdentifierGenerator {
    private static final int DATACENTER_ID_BITS = 5;
    private static final int WORKER_ID_BITS = 5;
    private static final int SEQUENCE_BITS = 12;

    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    private static final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);

    private final long epoch;
    private final long workerId;
    private final long datacenterId;

    private long lastTimestamp = -1L;
    private long sequence = 0L;

    public SnowFlakeIdentifierGenerator(long epoch, long workerId, long datacenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException("Worker ID는 " + MAX_WORKER_ID + "를 초과할 수 없습니다");
        }
        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException("Datacenter ID는 " + MAX_DATACENTER_ID + "를 초과할 수 없습니다");
        }

        this.epoch = epoch;
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized SnowFlakeIdentifier nextId() {
        long timestamp = System.currentTimeMillis() - epoch;

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("시스템 시계가 이전 시간으로 되돌아갔습니다");
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;
        return new SnowFlakeIdentifier(timestamp, datacenterId, workerId, sequence);
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis() - epoch;
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis() - epoch;
        }
        return timestamp;
    }
}