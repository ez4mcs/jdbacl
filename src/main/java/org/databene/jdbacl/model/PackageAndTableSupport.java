/*
 * (c) Copyright 2011 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License (GPL).
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.databene.commons.collection.OrderedNameMap;

/**
 * TODO Document class.<br/><br/>
 * Created: 30.05.2011 09:34:30
 * @since TODO version
 * @author Volker Bergmann
 */
public class PackageAndTableSupport {

	private OrderedNameMap<DBPackage> packages;
	private OrderedNameMap<DBTable> tables;
	private OrderedNameMap<DBSequence> sequences;
	
    public PackageAndTableSupport() {
    	this.packages = OrderedNameMap.createCaseInsensitiveMap();
		this.tables = OrderedNameMap.createCaseInsensitiveMap();
		this.sequences = OrderedNameMap.createCaseInsensitiveMap();
    }

    // package operations ----------------------------------------------------------------------------------------------

    public void addPackage(DBPackage dbPackage) {
		packages.put(dbPackage.getName(), dbPackage);
	}

	public Collection<DBPackage> getPackages() {
		return packages.values();
	}
    
    // table operations ------------------------------------------------------------------------------------------------

    public List<DBTable> getTables() {
        return getTables(false);
    }

    public List<DBTable> getTables(boolean recursive) {
		return getTables(recursive, new ArrayList<DBTable>());
    }

    public List<DBTable> getTables(boolean recursive, List<DBTable> result) {
    	result.addAll(tables.values());
    	if (recursive)
    		for (DBPackage subPackage : packages.values())
    			subPackage.getTables(recursive, result);
		return result;
    }

    public DBTable getTable(String tableName) {
        return tables.get(tableName);
    }

    public void addTable(DBTable table) {
        tables.put(table.getName(), table);
    }

    public void removeTable(DBTable table) {
        tables.remove(table.getName());
    }
    
    // sequence operations ---------------------------------------------------------------------------------------------

    public void addSequence(DBSequence sequence) {
    	this.sequences.put(sequence.getName(), sequence);
    }
    
	public List<DBSequence> getSequences(boolean recursive) {
		return getSequences(recursive, new ArrayList<DBSequence>());
	}

    public List<DBSequence> getSequences(boolean recursive, List<DBSequence> result) {
    	result.addAll(sequences.values());
    	if (recursive)
    		for (DBPackage subPackage : packages.values())
    			subPackage.getSequences(recursive, result);
		return result;
    }

}
