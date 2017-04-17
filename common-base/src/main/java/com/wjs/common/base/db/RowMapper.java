package com.wjs.common.base.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by pqq on 2015/8/10.
 */
public interface RowMapper {

    public Object mapRow(ResultSet rs, int index) throws SQLException;
}
