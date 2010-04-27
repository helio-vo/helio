package org.egso.common.votable.rdbloader.fieldhandler;

import java.sql.Types;

/**
 * Default FieldHandler for double
 * @author Marco Soldati
 * @created 23.02.2005
 */    
public class DoubleField extends FieldHandler
{
    /** 
     * @see org.egso.consumer.ssr.FieldHandler#format(java.lang.String)
     */
    public Object format(String content)
    {
        if (isArray)
            return content;
        return new Double(content);
    }
    
    /**
     * 
     * @see org.egso.consumer.ssr.FieldHandler#getJDBCType()
     */
    public int getJDBCType()
    {
        if (isArray)
            return Types.VARCHAR;
        else
            return Types.DOUBLE;
    }
}
