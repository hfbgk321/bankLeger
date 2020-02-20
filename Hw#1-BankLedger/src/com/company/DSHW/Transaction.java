package com.company.DSHW;

/**
 * This class contains all the methods for each transaction.
 * @author Jacky Chen
 * SBU ID: 112704638
 * Email: Jacky.Chen.6@stonybrook.edu
 */
public class Transaction implements Comparable<Transaction>{
    private String date; // contains the date the transaction was made
    private double amount; // contains the amount the transaction was worth.
    private String description; //contains description of transaction

    /**
     * It is an empty constructor
     */
    public Transaction(){}

    /**
     * A constructor with a custom date, amount, and description
     * @param date contains the date of that transaction
     * @param amount contains the amount the transaction was worth
     * @param description contains the description of that transaction
     */
    public Transaction(String date, double amount, String description){
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    /**
     * This method creates a deep copy of a specific transaction
     * @return returns the copy of that transaction.
     * Must be casted in order to access methods of that transaction.
     */
    public Object clone(){
        Transaction copy = new Transaction(this.date,this.amount, this.description);
        return copy;
    }

    /**
     *This methods determines whether or not an object is a transaction
     * and whether or not that transaction is equal to your specific transaction.
     * @param obj contains an object of type Object
     * @return returns true if obj is a transaction that is equal to your
     * specified transaction. All other cases will return false.
     */
    public boolean equals(Object obj){
        if(obj instanceof Transaction){
            if(((Transaction) obj).getDate().equals(this.date) && (
                    (Transaction) obj).getAmount() == this.amount &&
                    ((Transaction) obj).getDescription().equals(this.description)){
                return true;
            }
        }
        return false;
    }

    /**
     * This method compares transaction dates to see if they are greater than,
     * less than, or equal to specific transaction's date.
     * @param x contains an Transaction object
     * @return returns an int that symbolizes the comparison.
     * 0 being the same, 1 begin greater, and -1 being less than.
     */
    @Override
    public int compareTo(Transaction x){
        int thisYear = Integer.parseInt(this.date.substring(0,4));
        int tranYear = Integer.parseInt(x.getDate().substring(0,4));
        int thisMonth = Integer.parseInt(this.date.substring(5,7));
        int tranMonth = Integer.parseInt(x.getDate().substring(5,7));
        int thisDay =Integer.parseInt(this.date.substring(8,10));
        int tranDate =Integer.parseInt(x.getDate().substring(8,10));;
        if(thisYear<tranYear){
            return -1;
        }
        else if(thisYear>tranYear){
            return 1;
        }
        else{
            if(thisMonth < tranMonth){
                return -1;
            }
            else if(thisMonth > tranMonth){
                return 1;
            }
            else{
                if(thisDay<tranDate){
                    return -1;
                }
                else if(thisDay>tranDate){
                    return 1;
                }
                else{
                    return 0;
                }
            }
        }
    }

    /**
     * This method determines if a date is in the correct format.
     * @return returns true or false based on whether or not the date is correct.
     * @throws Exception throws an exception if the date is false;
     */
    public boolean validateDate() throws Exception{
        try {
            int thisYear = Integer.parseInt(this.getDate().substring(0, 4));
            int thisMonth = Integer.parseInt(this.getDate().substring(5, 7));
            int thisDay = Integer.parseInt(this.getDate().substring(8, 10));

            if (thisYear < 1900 || thisYear > 2050) {
                return false;
            } else if (thisMonth < 1 || thisMonth > 12) {
                return false;
            } else if (thisDay < 0 || thisDay > 30) {
                return false;
            }
            return true;
        }
        catch (Exception x){
            return false;
        }
    }

    /**
     * Getter method for the date
     * @return returns the date instance variable
     */
    public String getDate() {
        return date;
    }

    /**
     * Getter method for the amount
     * @return returns the amount instance variable
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Getter method for the description
     * @return returns the description instance variable
     */
    public String getDescription() {
        return description;
    }
}
