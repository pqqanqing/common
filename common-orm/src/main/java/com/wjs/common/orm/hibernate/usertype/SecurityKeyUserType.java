package com.wjs.common.orm.hibernate.usertype;

import com.wjs.common.base.security.SecurityKey;
import com.wjs.common.base.security.SecurityKeyFactory;
import com.wjs.common.orm.hibernate.base.BaseUserType;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StringType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import static java.util.Objects.isNull;

/**
 * Created by panqingqing on 16/8/10.
 */
public class SecurityKeyUserType extends BaseUserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.VARCHAR};
    }

    @Override
    public Class returnedClass() {
        return SecurityKey.class;
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor sessionImplementor, Object object) throws HibernateException, SQLException {
        return SecurityKeyFactory.getSecurityKey(resultSet.getString(names[0]));
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        SecurityKey securityKey = (SecurityKey) value;
        if (isNull(securityKey)) return;
        StringType.INSTANCE.nullSafeSet(preparedStatement, securityKey.getKey(), index, sessionImplementor);
    }
}
