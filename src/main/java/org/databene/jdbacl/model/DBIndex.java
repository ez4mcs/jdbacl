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

import java.io.Serializable;
import java.util.Arrays;

import org.databene.commons.ArrayFormat;
import org.databene.commons.Named;
import org.databene.commons.NullSafeComparator;

/**
 * Represents a database index.<br/><br/>
 * Created: 06.01.2007 08:58:49
 * @author Volker Bergmann
 */
public abstract class DBIndex extends AbstractDBTableComponent implements Named, Serializable {

	private static final long serialVersionUID = -1656761838194962745L;
	
    public DBIndex() {
        this(null, null);
    }

    public DBIndex(String name, DBTable table) {
        super(name, table);
        table.addIndex(this);
    }

    public abstract boolean isUnique();

    public abstract String[] getColumnNames();

    // properties ------------------------------------------------------------------------------------------------------

    public String getName() {
        return name;
    }

    // java.lang.Object overrides --------------------------------------------------------------------------------------

    @Override
    public boolean equals(Object obj) {
    	if (this == obj)
    		return true;
    	if (obj == null || obj.getClass() != this.getClass())
    		return false;
    	DBIndex that = (DBIndex) obj;
    	return NullSafeComparator.equals(this.getName(), that.getName())
			&& NullSafeComparator.equals(this.getTable(), that.getTable())
			&& NullSafeComparator.equals(this.isUnique(), that.isUnique())
			&& Arrays.equals(this.getColumnNames(), that.getColumnNames());
    }
    
    @Override
    public int hashCode() {
    	throw new UnsupportedOperationException("DBIndex.hashCode() is not implemented"); // TODO implement DBIndex.hashCode
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(name);
        builder.append(" (");
        builder.append(ArrayFormat.format(getColumnNames()));
        builder.append(')');
        builder.append(isUnique() ? " unique" : "");
        return builder.toString();
    }
    
}
