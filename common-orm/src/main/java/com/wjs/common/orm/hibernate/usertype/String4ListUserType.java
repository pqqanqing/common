package com.wjs.common.orm.hibernate.usertype;

import com.wjs.common.base.util.StringUtil;
import com.wjs.common.orm.hibernate.base.BaseUserType;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StringType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.split;

/**
 * Created by panqingqing on 16/7/9.
 */
public class String4ListUserType extends BaseUserType {

    private static final String SPLIT = ";";

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.VARCHAR};
    }

    @Override
    public Class returnedClass() {
        return List.class;
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor sessionImplementor, Object object) throws HibernateException, SQLException {
        String result = resultSet.getString(names[0]);
        return isNull(result) ? null : asList(split(result, SPLIT));
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object object, int index, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        String value = StringUtil.parseList2String(object, SPLIT);
        StringType.INSTANCE.nullSafeSet(preparedStatement, value, index, sessionImplementor);
    }

}
