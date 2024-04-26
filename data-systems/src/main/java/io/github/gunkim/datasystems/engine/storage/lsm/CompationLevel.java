package io.github.gunkim.datasystems.engine.storage.lsm;

import java.util.Arrays;

enum CompationLevel {
    LEVEL_1(1, 2),
    LEVEL_2(2, 4),
    LEVEL_3(3, 8),
    LEVEL_4(4, 16),
    LEVEL_5(5, 32),
    LEVEL_6(6, 64);

    private final int level;
    private final int threshold;

    CompationLevel(int level, int threshold) {
        this.level = level;
        this.threshold = threshold;
    }

    public static CompationLevel valueOf(int levelValue) {
        return Arrays.stream(values())
                .filter(level -> level.value() == levelValue)
                .findAny()
                .orElseThrow();
    }

    public static CompationLevel maxLevel() {
        return LEVEL_6;
    }

    public int nextLevel() {
        if (this == maxLevel()) {
            return maxLevel().value();
        }
        return level + 1;
    }

    public int value() {
        return level;
    }

    public String ssTablePath(String basePath) {
        return String.format(basePath, level);
    }

    public boolean isCompactionRequired(long ssTableCount) {
        return ssTableCount >= threshold;
    }
}