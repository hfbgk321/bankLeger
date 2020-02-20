package com.company.DSHW;

import java.text.DecimalFormat;

/**
 * a class the represents the general ledger. Extends into the Transaction class.
 * @author Jacky Chen
 * SBU ID: 112704638
 * Email: Jacky.Chen.6@stonybrook.edu
 */
public class GeneralLedger extends Transaction{
    private final int MAX_TRANSACTIONS = 50; // Maximum number of transactions per ledger
    private Transaction[] ledger = new Transaction[MAX_TRANSACTIONS]; // creates an array of MAX_TRANSACTIONS that represents the ledger.
    private double totalDebitAmount; // represents the money you have at the moment
    private double totalCreditAmount; // represents how much you spend
    private int size = 0; // represents the current size of the ledger.

    /**
     * Empty constructor for the General ledger. Initializes the totalCreditAmount and totalDebitAmount to 0;
     */
    public GeneralLedger(){
        this.totalCreditAmount = 0;
        this.totalDebitAmount = 0;
    }

    /**
     * adds a transaction into the ledger based on the date the transaction was created.
     * @param newTransaction represents a Transaction object
     * @throws FullGeneralLedgerException throws this exception if the ledger is full
     * @throws InvalidTransationException throws this exception
     * if the tranaction has an amount of 0 or if its date format is invalid
     * @throws TransactionAlreadyExistsException throws an exception
     * if the transaction already exists in the ledger
     * @throws Exception throws an exception if anything else happens.
     */
    public void addTransaction(Transaction newTransaction)
            throws FullGeneralLedgerException, InvalidTransationException, TransactionAlreadyExistsException, Exception{
        int nullPos = 0;
        if(newTransaction.getAmount() == 0 || newTransaction.validateDate() == false){
            throw new InvalidTransationException();
        }
        if(this.ledger[0] == null){
            this.ledger[0] = newTransaction;
        }

        else{
            if(this.ledger[ledger.length-1] != null){
                throw new FullGeneralLedgerException();
            }
            for(int x = 0; x< ledger.length;x++){
                if(exists(newTransaction)){
                    throw new TransactionAlreadyExistsException();
                }
                if(this.ledger[x]==null){
                    this.ledger[x]= newTransaction;
                    nullPos = x;
                    break;
                }
            }
        }
        for(int x = 0; x <=nullPos; x++){
            for(int y = 0; y<=nullPos-x-1;y++){
                if(this.ledger[y].compareTo(this.ledger[y+1])>0){
                    Transaction temp = ledger[y];
                    ledger[y]= ledger[y+1];
                    ledger[y+1] = temp;
                }
            }
        }
        if(newTransaction.getAmount() <0){
            this.setTotalCreditAmount(this.totalCreditAmount+ Math.abs(newTransaction.getAmount()));
        }
        else{
            this.setTotalDebitAmount(this.totalDebitAmount+newTransaction.getAmount());
        }
        System.out.println("Transaction successfully added to the general ledger.");
        this.size++;
    }

    /**
     * removes a transaction in the ledger and reorganizes the ledger
     * @param position an integer that represents the position of
     *                 the transaction in the array starting from 1.
     * @throws InvalidLedgerPositionException throws this exception if
     * the position enter in an invalid position on the ledger
     */
    public void removeTransaction(int position) throws InvalidLedgerPositionException{
        if((position-1) > MAX_TRANSACTIONS-1){
            throw new InvalidLedgerPositionException();
        }
        else if(this.ledger[position-1] == null ){
            throw new InvalidLedgerPositionException();
        }
        else {
            Transaction storage;
            for (int x = position-1; x < ledger.length-1; x++) {
                if(this.ledger[x] == null){
                    break;
                }
                this.ledger[x] = this.ledger[x + 1];
                storage = this.ledger[x + 1];
            }
            System.out.println("Transaction has been successfully removed from the general ledger.");
            size--;
        }
    }

    /**
     * This method gets a specific transaction in the ledger
     * @param position represents the position of the transaction you want to get
     * @return returns an Object of type transaction
     * @throws InvalidLedgerPositionException throws this exception if the position entered is invalid.
     */
    public Transaction getTransaction(int position) throws InvalidLedgerPositionException{
        if(this.ledger[position-1] == null){
            throw new InvalidLedgerPositionException();
        }
        return this.ledger[position-1];
    }

    /**
     * This method filters out all transactions of a given date
     * @param generalLedger represents the ledger to which you are getting your transactions from.
     * @param date represent which date you would like for those transactions
     */
    public static void filter(GeneralLedger generalLedger, String date){
        DecimalFormat d1 = new DecimalFormat("#.##");
        System.out.println("No.     Date        Debit   Credit   Description");
        System.out.println("-------------------------------------------------");
        for(int x = 0; x< generalLedger.ledger.length;x++){
            if(generalLedger.ledger[x] == null)
                break;
            if(generalLedger.ledger[x].getDate().equals(date)){
                if(generalLedger.ledger[x].getAmount()<0) {
                    System.out.println((x+1)+"      "+generalLedger.ledger[x].getDate()+"              "+
                            d1.format(Math.abs(generalLedger.ledger[x].getAmount()))+"    "+generalLedger.ledger[x].getDescription()+".");
                }
                else{
                    System.out.println((x+1)+"      "+generalLedger.ledger[x].getDate()+"    "+
                            d1.format(generalLedger.ledger[x].getAmount())+"              "+generalLedger.ledger[x].getDescription()+".");
                }
            }
        }
    }

    /**
     * method creates a deep copy of a general ledger of your choice.
     * @return returns an Object.
     */
    public Object clone(){
        GeneralLedger clone = new GeneralLedger();
        double credit = this.totalCreditAmount;
        double debit = this.totalDebitAmount;
        int size = this.size;
        clone.setTotalCreditAmount(credit);
        clone.setTotalDebitAmount(debit);
        clone.setSize(size);
        for(int x = 0; x< this.ledger.length;x++){
            if(this.ledger[x] == null){
                break;
            }
            clone.ledger[x] = (Transaction) this.ledger[x].clone();
        }
        return clone;
    }

    /**
     * method that allows the user to know if a transaction exists in the ledger.
     * @param transaction represents a transaction object
     * @return returns true or false if the transaction exists in the ledger or not.
     */
    public boolean exists(Transaction transaction){
        for(int x = 0; x< this.ledger.length;x++){
            if(this.ledger[x]== null){
                break;
            }
            if(this.ledger[x].getDate().equals(transaction.getDate()) && this.ledger[x].getAmount() == transaction.getAmount() &&
                    this.ledger[x].getDescription().equals(transaction.getDescription())){
                return true;
            }
        }
        return false;
    }

    /**
     * the methods allows users to see the current size of the ledger.
     * @return returns the size instance variable.
     */
    public int size(){
        return this.size;
    }

    /**
     * this method prints out all the tranasctions in a given ledger.
     */
    public void printAllTransactions(){
        DecimalFormat d1 = new DecimalFormat("#.##");
        System.out.println("No.    Date          Debit    Credit    Description");
        System.out.println("---------------------------------------------------------------------------------------------------");
        for(int x = 0; x< this.ledger.length;x++){
            if(this.ledger[x] == null){
                break;
            }
            if(this.ledger[x].getAmount()<0) {
                System.out.println((x+1)+"      "+this.ledger[x].getDate()+"              "+
                        d1.format(Math.abs(this.ledger[x].getAmount()))+"    "+this.ledger[x].getDescription()+".");
            }
            else{
                System.out.println((x+1)+"      "+this.ledger[x].getDate()+"    "+
                        d1.format(this.ledger[x].getAmount())+"              "+this.ledger[x].getDescription()+".");
            }
        }
    }

    /**
     * This method returns a String with all the transactions in your ledger
     * @return a String representation of ledger
     */
    public String toString(){
        DecimalFormat d1 = new DecimalFormat("#.##");
        String hello = "No.    Date          Debit    Credit    Description\n---------------------------------------------------------------------------------------------------\n";
        for(int x = 0; x< this.ledger.length;x++){
            if(this.ledger[x] == null){
                break;
            }
            if(this.ledger[x].getAmount()<0) {
                hello+=(x+1)+"      "+this.ledger[x].getDate()+"              "+
                        d1.format(Math.abs(this.ledger[x].getAmount()))+"    "+this.ledger[x].getDescription()+".\n";
            }
            else{
                hello+=(x+1)+"      "+this.ledger[x].getDate()+"    "+
                        d1.format(this.ledger[x].getAmount())+"              "+this.ledger[x].getDescription()+".\n";
            }
        }
        return hello;
    }


    /**
     * Getter method for the ledger
     * @return return the ledger instance variable
     */
    public Transaction[] getLedger() {
        return ledger;
    }

    /**
     * this method allows users to access the total debit amount in a given ledger.
     * @return returns the totalDebitAmount instance variable.
     */
    public double getTotalDebitAmount() {
        return totalDebitAmount;
    }

    /**
     * this method allows users to access the total credit amount in a given ledger.
     * @return returns the totalCreditAmount instance variable.
     */
    public double getTotalCreditAmount() {
        return totalCreditAmount;
    }

    /**
     * Setter method for a given ledger.
     * @param ledger an array of type tranaction.
     */
    public void setLedger(Transaction[] ledger) {
        this.ledger = ledger;
    }

    /**
     * Setter method for size instance variable
     * @param size integer representing the size of the ledger.
     */
    public void setSize(int size){
        this.size = size;
    }

    /**
     * Setter method for the totalDebitAMount instance Variable
     * @param totalDebitAmount  double representing the value you want to set the debit amount to.
     */
    public void setTotalDebitAmount(double totalDebitAmount) {
        this.totalDebitAmount = totalDebitAmount;
    }

    /**
     * Setter method for the totalCreditAMount instance Variable
     * @param totalCreditAmount a double representing the new totalCreditAmount
     */
    public void setTotalCreditAmount(double totalCreditAmount) {
        this.totalCreditAmount = totalCreditAmount;
    }
}

/**
 * a class for the FullGeneralLedgerException
 */
class FullGeneralLedgerException extends Exception{
    public FullGeneralLedgerException(){
        super("You have made the max number of transations");
    }
}

/**
 * a class for the InvalidTransationException
 */
class InvalidTransationException extends Exception{
    InvalidTransationException(){
        super("Your Transaction has an Error");
    }
}

/**
 * a class for the TransactionAlreadyExistsException
 */
class TransactionAlreadyExistsException extends Exception{
    TransactionAlreadyExistsException(){
        super("Transaction already exists");
    }
}

/**
 * a class for the InvalidLedgerPositionException
 */
class InvalidLedgerPositionException extends Exception{
    public InvalidLedgerPositionException(){
        super("The Position is invalid");
    }
}
