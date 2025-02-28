package com.demoproject.framework.db;

import java.sql.Connection;

public interface ConnectionIF {

    Connection borrow();

    void giveBack(final Connection p0);

    void invalidate(final Connection p0);
}
