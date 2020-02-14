package DomainLogicPatterns.DomainModel;

public class Tester {
    private Product word = Product.newWordProcessor("Thinking Word");
    private Product calc = Product.newSpreadsheet("Thinking Calc");
    private Product db = Product.newDatabase("Thinking DB");

    public static void main(String[] args) {
        Contract c1 = new Contract(word, 123, "20200211");
        Contract c2 = new Contract(calc, 456, "20200212");
        Contract c3 = new Contract(db, 789, "20200213");

        c1.calculateRecognitions();
        c2.calculateRecognitions();
        c3.calculateRecognitions();

        c1.recognizedRevenue("20200211");
        c2.recognizedRevenue("20200212");
        c3.recognizedRevenue("20200213");
    }
}
