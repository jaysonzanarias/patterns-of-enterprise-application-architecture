class PersonGateway {
    public IDataReader FindAll() {
        String sql = "SELECT * FROM person";
        return new OleDbCommand(sql, DB.Connection).ExecuteReader();
    }

    public IDataReader FindWithLastName(String lastName) {
        String sql = "SELECT * FROM person WHERE lastname = ?";
        IDbCommand comm = new OleDbCommand(sql, Db.connection);
        comm.Parameters.Add(new OleDbParameter("lastname", lastName));
        return comm.ExecuteReader();
    }

    public IDataReader FindWhere(String whereClause) {
        String sql = String.Format("SELECT * FROM person where {0}", whereClause);
        return new OleDbCommand(sql, DB.Connection).ExecuteReader();
    }

    public Object[] FindRow(long key) {
        String sql = "SELECT * FROM person WHERE id = ?";
        IDbCommand comm = new OleDbCommand(sql, DB.Connection);
        comm.Parameters.Add(new OleDbCommand("key", key));
        IDataReader reader = comm.ExecuteReader();
        reader.Read();
        Object[] result = new Object[reader.FieldCount];
        reader.GetValues(result);
        reader.Close();
        return result;
    }

    public void Update(long key, String lastname, String firstname, long numberOfDependents) {
        String sql = @"
            UPDATE person
                SET lastname = ?, firstname = ?, numberOfDependents = ?
                WHERE id = ?"

        IDbCommand comm = new OleDbCommand(sql, DB.Connection);
        comm.Parameters.Add(new OleDbParameter("last", lastname));
        comm.Parameters.Add(new OleDbParameter("first", firstname));
        comm.Parameters.Add(new OleDbParameter("numDep", numberOfDependents));
        comm.Parameters.Add(new OleDbParameter("key", key));
        comm.ExecuteNonQuery();
    }

    public long Insert(String lastName, String firstName, long numberOfDependents) {
        String sql = "INSERT INTO person VALUES (?, ?, ?, ?)";
        long key = GetNextID();
        IDbCommand comm = new OleDbCommand(sql, DB.Connection);
        comm.Parameters.Add(new OleDbParameter("key", key));
        comm.Parameters.Add(new OleDbParameter("last", lastName));
        comm.Parameters.Add(new OleDbParameter("first", firstName));
        comm.Parameters.Add(new OleDbParameter("numDep", numberOfDependents));
    }

    public void Delete(long key) {
        String sql = "DELETE FROM person WHERE id = ?";
        IDbCommand comm = new OleDbCommand(sql, DB.Connection);
        comm.Parameters.Add(new OleDbParameter("key", key));
        comm.ExecuteNonQuery();
    }
}
