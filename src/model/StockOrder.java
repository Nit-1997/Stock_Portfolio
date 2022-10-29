package model;

public class StockOrder {
   private final Stock stock;
   private final int quantity;

   public StockOrder(String ticker , int qty){
      this.stock = new StockImpl(ticker);
      this.quantity = qty;
   }


   public Stock getStock() {
      return this.stock;
   }

   public int getQuantity() {
      return this.quantity;
   }
}
