package ObjectRelationalStructuralPatterns.EmbeddedValue;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductOffering {
    private Product product;
    private Money baseCost;
    private Integer ID;

    public static ProductOffering load(ResultSet rs) {
        try {
            Integer id = (Integer) rs.getObject("ID");
            BigDecimal baseCostAmount = rs.getBigDecimal("base_cost_amount");
            Currency baseCostCurrency = Registry.getCurrency(rs.getString("base_cost_currency"));
            Money baseCost = new Money(baseCostAmount, baseCostCurrency);
            Integer productID = (Integer) rs.getObject("product");
            Product product = Product.find((Integer) rs.getObject("product"));

            return new ProductOffering(id, product, baseCost);
        } catch (SQLException e) {
            throw new ApplicationException(e);
        }
    }

    public void update() {
        PreparedStatement stmt = null;
        try {
            stmt = DB.prepare(updateStatementString);
            stmt.setBigDecimal(1, baseCost.amount());
            stmt.setString(2, baseCost.currency().code());
            stmt.setInt(3, ID.intValue());
            stmt.execute();
        } catch (Exception e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(stmt);
        }
    }

    private String updateStatementString =
        "UPDATE product_offering SET base_cost_amount = ?, base_cost_currency = ? WHERE id = ?";
}
