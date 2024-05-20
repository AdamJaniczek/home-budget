package pl.com.itsystems.homeBudget;

public enum TransactionType {
    INCOME("przychód"), EXPENSE("wydatek");
    String description;

    TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
