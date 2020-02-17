class DataSetHolder {
    public DataSet Data = new DataSet();
    private Hashtable DataAdapters = new Hashtable();

    public void FillData(String query, String tableName) {
        if(DataAdapters.Contains(tableName))
            throw new MultipleLoadException();

        OleDbDataAdapter da = new OleDbDataAdapter(query, DB.Connection);
        OleDbCommandBuilder builder = new OleDbCommandBuilder(da);
        da.Fill(Data, tableName);
        DataAdapters.Add(tableName, da);
    }
}