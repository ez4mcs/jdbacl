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

import org.databene.commons.Named;
import org.databene.commons.depend.Dependent;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a database table.<br/><br/>
 * Created: 06.01.2007 08:58:49
 * @author Volker Bergmann
 */
public interface DBTable extends Dependent<DBTable>, Named, Serializable {

    public String getName();
    public String getDoc();
    public DBCatalog getCatalog();
    public DBSchema getSchema();
    public DBPrimaryKeyConstraint getPrimaryKeyConstraint();

    public List<DBColumn> getColumns();
    public DBColumn[] getColumns(List<String> columnNames);
    public DBColumn getColumn(String columnName);
    public List<DBIndex> getIndexes();
    public DBIndex getIndex(String indexName);

	public String[] getPKColumnNames();
    public List<DBUniqueConstraint> getUniqueConstraints();
    public List<DBForeignKeyConstraint> getForeignKeyConstraints();
    public Collection<DBTable> getReferrers();
	
	public long getRowCount(Connection connection);
    public Iterator<DBRow> allRows(Connection connection) throws SQLException;
    public DBRow queryById(Object[] idParts, Connection connection) throws SQLException;
    public Iterator<DBRow> queryByColumnValues(String[] columnNames, Object[] values, Connection connection) throws SQLException;
    
}
