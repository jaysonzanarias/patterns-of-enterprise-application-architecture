class DataGateway {
    public DataSetHolder Holder;

    protected DataGateway() {
        Holder = new DataSetHolder();
    }

    protected DataGateway(DataSetHolder holder) {
        this.Holder = holder;
    }

    public DataSet Data {
        get {return Holder.Data;}
    }

    public void LoadAll() {
        String commandString = String.Format("SELECT * FROM {0}", TableName);
        Holder.FillData(commandString, TableName);
    }

    public void LoadWhere(String whereClause) {
        String commandString = String.Format("SELECT * FROM {0} WHERE {1}", TableName, whereClause);
        Holder.FillData(commandString, TableName);
    }

    public DataRow this[long key] {
        get {
            String filter = String.Format("id = {0}", key);
            return Table.Select(filter)[0];
        }
    }

    public void Update() {
        foreach (String table in DataAdapter.Keys) {
            ((OleDbDataAdapter) DataAdapters[table]).Update(Data, table);
        }
    }

    public DataTable this[String tableName] {
        get {return Data.Tables[tableName];}
    }

    public long Insert(String lastName, String firstname, int numberOfDependents) {
        long key = new PersonGatewayDS().GetNextID();
        DataRow newRow = Table.NewRow();
        newRow["id"] = key;
        newRow["lastName"] = lastName;
        newRow["firstName"] = firstname;
        newRow["numberOfDependents"] = numberOfDependents;
        Table.Rows.Add(newRow);
        return key;
    }

    public override DataTable Table {
        get { return Data.Tables[TableName]};
    }

    abstract public String TableName(get;)
}