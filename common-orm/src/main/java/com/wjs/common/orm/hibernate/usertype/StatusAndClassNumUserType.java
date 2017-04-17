package com.wjs.common.orm.hibernate.usertype;

import com.wjs.common.base.annotation.StatusAndClassNum;
import com.wjs.common.base.util.ScanClassUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StringType;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.springframework.core.annotation.AnnotationUtils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import static java.util.Objects.isNull;

/**
 * Created by panqingqing on 16/2/18.
 */
public class StatusAndClassNumUserType implements UserType, ParameterizedType, Serializable {

    private Class<?> superClass;

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.VARCHAR};
    }

    @Override
    public Class returnedClass() {
        return superClass;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) return true;
        if (x == null || y == null) return false;
        return new EqualsBuilder().append(x, y).isEquals();
    }

    @Override
    public int hashCode(Object object) throws HibernateException {
        return object.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor sessionImplementor, Object object) throws HibernateException, SQLException {
        String result = resultSet.getString(names[0]);
        if (isNull(result)) return null;
        return ScanClassUtil.getObject(superClass + result);
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object object, int index, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        if (isNull(object)) return;
        String number = AnnotationUtils.findAnnotation(object.getClass(), StatusAndClassNum.class).number();
        StringType.INSTANCE.nullSafeSet(preparedStatement, number, index, sessionImplementor);
    }

    @Override
    public Object deepCopy(Object object) throws HibernateException {
        return object;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object object) throws HibernateException {
        return (Serializable) object;
    }

    @Override
    public Object assemble(Serializable serializable, Object object) throws HibernateException {
        return object;
    }

    @Override
    public Object replace(Object object, Object object1, Object object2) throws HibernateException {
        return object;
    }

    @Override
    public void setParameterValues(Properties properties) {
        String statusType = properties.getProperty("usertype");
        if (StringUtils.isBlank(statusType))
            throw new RuntimeException("请为[" + StatusAndClassNumUserType.class + "]设置参数[statusType]的值为目标状态类型!");
        try {
            superClass = Class.forName(statusType);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("无法找到状态类型信息[" + statusType + "]", e);
        }
    }
}
