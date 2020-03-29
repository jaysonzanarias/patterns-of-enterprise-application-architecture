class CricketPage {
    protected void Page_Load(object sender, System.EventArgs e) {
        db = new OleDbConnection(DB.ConnectionString);

        if (hasMissingParameters())
            errorTransfer (missingParameterMessage);
        DataSet ds = getData();

        if (hasNoData (ds))
            errorTransfer ("No data matches your request");

        applyDomainLogic (ds);
        DataBind();
        prepareUI(ds);
    }
}