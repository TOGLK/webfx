package naga.framework.orm.domainmodel.lciimpl;

import naga.framework.orm.domainmodel.DomainClass;
import naga.framework.orm.domainmodel.DomainField;
import naga.framework.expression.Expression;
import naga.framework.expression.terms.IdExpression;
import naga.framework.expression.lci.CompilerDomainModelReader;
import naga.framework.orm.domainmodel.DomainModel;

/**
 * @author Bruno Salmon
 */
public class CompilerDomainModelReaderImpl implements CompilerDomainModelReader {

    private final DomainModel domainModel;

    public CompilerDomainModelReaderImpl(DomainModel domainModel) {
        this.domainModel = domainModel;
    }

    private DomainClass getDomainClass(Object domainClass) {
        if (domainClass instanceof DomainClass)
            return (DomainClass) domainClass;
        return domainModel.getClass(domainClass);
    }

    @Override
    public String getDomainClassSqlTableName(Object domainClass) {
        return getDomainClass(domainClass).getSqlTableName();
    }

    @Override
    public String getDomainClassPrimaryKeySqlColumnName(Object domainClass) {
        return getDomainClass(domainClass).getIdColumnName();
    }

    @Override
    public Object getSymbolForeignDomainClass(Object symbolDomainClass, Expression symbol, boolean required) {
        if (symbol instanceof DomainField)
            return ((DomainField) symbol).getForeignClass();
        return null;
    }

    @Override
    public Expression getDomainClassDefaultForeignFields(Object domainClass) {
        return getDomainClass(domainClass).getForeignFields();
    }

    @Override
    public String getSymbolSqlColumnName(Object symbolDomainClass, Expression symbol) {
        if (symbol instanceof DomainField)
            return ((DomainField) symbol).getSqlColumnName();
        if (symbol instanceof IdExpression)
            return getDomainClassPrimaryKeySqlColumnName(symbolDomainClass);
        return null;
    }
}
