class TableModule {
    protected DataTable table;
    protected TableModule(DataSet ds, String tableName) {
        table = ds.Tables[tableName];
    }
}

