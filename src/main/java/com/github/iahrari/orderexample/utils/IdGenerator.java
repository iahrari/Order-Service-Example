package com.github.iahrari.orderexample.utils;

import java.util.UUID;

public class IdGenerator {
    public static Long generateId() {
        String unique = "" + (UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
		String id = "1400" + unique.substring(4);
        return Long.parseLong(id);
    }
}
