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
import java.util.Map;

/**
 * Created by panqingqing on 16/8/12.
 */
public class Json4MapUserType extends BaseUserType {
    @Override
    public int[] sqlTypes() {
        return new int[]{Types.VARCHAR};
    }

    @Override
    public Class returnedClass() {
        return Map.class;
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor sessionImplementor, Object object) throws HibernateException, SQLException {
        return StringUtil.parseJsonString2Map(resultSet.getString(names[0]));
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object object, int index, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        String value = StringUtil.parseMap2JsonString(object);
        StringType.INSTANCE.nullSafeSet(preparedStatement, value, index, sessionImplementor);
    }


}
