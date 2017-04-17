package com.wjs.common.orm.hibernate.usertype;

import com.wjs.common.base.money.Money;
import com.wjs.common.orm.hibernate.base.BaseUserType;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StringType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Types.VARCHAR;
import static java.util.Objects.isNull;

/**
 * Created by panqingqing on 16/10/18.
 */
public class MoneyUserType extends BaseUserType {
    @Override
    public int[] sqlTypes() {
        return new int[]{VARCHAR};
    }

    @Override
    public Class returnedClass() {
        return Money.class;
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor sessionImplementor, Object object) throws HibernateException, SQLException {
        String result = resultSet.getString(names[0]);
        return new Money(result);
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object object, int index, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        String value = getMoney(object);
        StringType.INSTANCE.nullSafeSet(preparedStatement, value, index, sessionImplementor);
    }

    private String getMoney(Object object) {
        Money money = (Money) object;
        return isNull(money) ? null : money.getMoney().toString();
    }
}
