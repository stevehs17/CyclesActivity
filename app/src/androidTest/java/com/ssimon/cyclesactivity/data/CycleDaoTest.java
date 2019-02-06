package com.ssimon.cyclesactivity.data;

import org.junit.Test;

import dalvik.annotation.TestTarget;

public class CycleDaoTest {
    @Test
    public void test() {
        DatabaseHelperTest dht = new DatabaseHelperTest();
        dht.test_create_tables_and_open_db_Success();
    }
}
