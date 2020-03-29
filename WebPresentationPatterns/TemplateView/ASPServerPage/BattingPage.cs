class BattingPage {
    protected String team {
        get {return Request.Params["team"];}
    }

    protected String match {
        get {return Request.Params["match"];}
    }

    protected String innings {
        get {return Request.Params["innings"];}
    }

    protected String ordinalInnings{
        get {return (innings == "1") ? "1st" : "2nd";}
    }

    override protected void prepareUI(DataSet ds) {
        DataGrid1.DataSource = ds;
        DataGrid1.DataBind();
    }
}