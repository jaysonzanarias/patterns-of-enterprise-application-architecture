class Contract {
    public Contract(DataSet ds) : base (ds, "Contracts") {}

    public DataRow this [long key] {
        get {
            String filter = String.Format("ID = {0}", key);
            return table.Select(filter)[0];
        }
    }

    public void CalculateRecognitions(long contractID) {
        DataRow contractRow = this[contractID];
        Decimal amount = (Decimal) contractRow["amount"];
        RevenueRecognition rr = new RevenueRecognition(table.DataSet);
        Product prod = new Product(table.DataSet);
        long prodID = GetProductId(contractID);

        if(prod.GetProductType(prodID) == ProductType.WP){
            rr.Insert(contractID, amount, (DateTime) GetWhenSigned(contractID));
        } else if (prod.GetProductType(prodID) == ProductType.SS) {
            Decimal[] allocation = allocate(amount, 3);
            rr.Insert(contractID, allocation[0], (DateTime) GetWhenSigned(contractID));
            rr.Insert(contractID, allocation[1], (DateTime) GetWhenSigned(contractID).AddDays(60));
            rr.Insert(contractID, allocation[2], (DateTime) GetWhenSigned(contractID).AddDays(90));
        } else if (prod.GetProductType(prodID) == ProductType.DB) {
            Decimal[] allocation = allocate(amount, 3);
            rr.Insert(contractID, allocation[0], (DateTime) GetWhenSigned(contractID));
            rr.Insert(contractID, allocation[1], (DateTime) GetWhenSigned(contractID).AddDays(30));
            rr.Insert(contractID, allocation[2], (DateTime) GetWhenSigned(contractID).AddDays(60));
        } else throw new Exception("invalid product id");
    }

    private Decimal[] allocate(Decimal amount, int by) {
        Decimal lowResult = amount / by;
        lowResult = Decimal.Round(lowResult, 2);
        Decimal highResult = lowResult + 0.01m;
        Decimal[] results = new Decimal[by];
        int remainder = (int) amount % by;
        for(int i=0; i<remainder; i++) results[i] = highResult;
        for(int i=remainder; i<by; i++) results[i] = lowResult;
        return  results;
    }
}
