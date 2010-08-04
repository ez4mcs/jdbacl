/*
 * (c) Copyright 2006-2010 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License.
 *
 * For redistributing this software or a derivative work under a license other
 * than the GPL-compatible Free Software License as defined by the Free
 * Software Foundation or approved by OSI, you must first obtain a commercial
 * license to this software product from Volker Bergmann.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * WITHOUT A WARRANTY OF ANY KIND. ALL EXPRESS OR IMPLIED CONDITIONS,
 * REPRESENTATIONS AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE
 * HEREBY EXCLUDED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.databene.jdbacl.model;

import org.databene.commons.CollectionUtil;
import org.databene.commons.ObjectNotFoundException;

import java.util.List;

/**
 * Represents a foreign key constraint.<br/><br/>
 * Created: 06.01.2007 09:00:59
 * @author Volker Bergmann
 */
public class DBForeignKeyConstraint extends DBConstraint {

    private List<DBForeignKeyColumn> foreignKeyColumns;

    public DBForeignKeyConstraint(String name, DBForeignKeyColumn ... foreignKeyColumns) {
        super(name);
        this.foreignKeyColumns = CollectionUtil.toList(foreignKeyColumns);
    }

    public List<DBForeignKeyColumn> getForeignKeyColumns() {
        return foreignKeyColumns;
    }

    public void addForeignKeyColumn(DBForeignKeyColumn foreignKeyColumn) {
        this.foreignKeyColumns.add(foreignKeyColumn);
    }
    
    public DBColumn columnReferencedBy(DBColumn fkColumn) {
    	return columnReferencedBy(fkColumn, true);
    }

    public DBColumn columnReferencedBy(DBColumn fkColumn, boolean required) {
    	for (DBForeignKeyColumn column : foreignKeyColumns) {
    		if (column.getForeignKeyColumn().equals(fkColumn))
    			return column.getTargetColumn();
    	}
    	if (required)
    		throw new ObjectNotFoundException(fkColumn.getName() + " is not a foreign key column " +
    				"in table " + getOwner().getName());
    	else
    		return null;
    }

    public DBTable getForeignTable() {
        return foreignKeyColumns.get(0).getTargetColumn().getTable();
    }

    @Override
    public DBTable getOwner() {
        return foreignKeyColumns.get(0).getForeignKeyColumn().getTable();
    }

    @Override
    public DBColumn[] getColumns() {
        DBColumn[] columns = new DBColumn[foreignKeyColumns.size()];
        for (int i = 0; i < foreignKeyColumns.size(); i++)
            columns[i] = foreignKeyColumns.get(i).getForeignKeyColumn();
        return columns;
    }

    @Override
    public String toString() {
    	StringBuilder builder = new StringBuilder("(");
		builder.append(foreignKeyColumns.get(0).getForeignKeyColumn().getName());
    	for (int i = 1; i < foreignKeyColumns.size(); i++) {
    		DBForeignKeyColumn fkc = foreignKeyColumns.get(i);
    		builder.append(", ").append(fkc.getForeignKeyColumn().getName());
    	}
    	builder.append(") -> ").append(foreignKeyColumns.get(0).getTargetColumn().getTable().getName()).append("(");
		builder.append(foreignKeyColumns.get(0).getTargetColumn().getName());
    	for (int i = 1; i < foreignKeyColumns.size(); i++) {
    		DBForeignKeyColumn fkc = foreignKeyColumns.get(i);
    		builder.append(", ").append(fkc.getTargetColumn().getName());
    	}
        return builder.append(")").toString();
    }

	public DBColumn[] getRefereeColumns() {
		DBColumn[] result = new DBColumn[foreignKeyColumns.size()];
		for (int i = 0; i < foreignKeyColumns.size(); i++)
			result[i] = foreignKeyColumns.get(i).getTargetColumn();
		return result;
    }
    
}
