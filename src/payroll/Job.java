/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Casey
 */
public abstract class Job {
    
    private final String accountNum;
    private final String workOrderNum;
    private final Date date;
    private String designation;
    private final String customerName;
    private final String techID; // Want DB to have TID, get name from there??
    private ArrayList<EquipmentTask> serializedEquipmentList;
    private ArrayList<EquipmentTask> nonSerializedEquipmentList;
    private ArrayList<StandardLaborTask> standardLaborList;
    private ArrayList<SHSLaborTask> shsList;
    private int payment;
    
    public Job(String inANum, String inWONum, Date inDate, String inDesignation,
                String inTID, String inCName){
        
        this.accountNum = inANum;
        this.workOrderNum = inWONum;
        this.date = inDate;
        this.designation = inDesignation;
        this.techID = inTID;
        this.customerName = inCName;
        this.serializedEquipmentList = new ArrayList<>();
        this.nonSerializedEquipmentList = new ArrayList<>();
        this.standardLaborList = new ArrayList<>();
        this.shsList = new ArrayList<>();
        this.payment = 0;
    }
    
    public abstract int calculatePay();
    
    protected int calculateSHSPay(){
        
        int pay = 0;
        
        if(shsList != null && !shsList.isEmpty()){
            //Traverse the list
           for(SHSLaborTask task : shsList){
               // Look up the SHS payment and add it to pay
               pay += task.lookupLaborPayment();
           }
        }
        return pay;
    }
    
    // ***********************************************************
    // TRYING THIS METHOD OF ADDING TASKS, SO YOU DON'T HAVE TO PASS 
    // A WHOLE LIST IN THE CONSTRUCTOR. GOOD IDEA? WHO KNOWS!
    public void addSerializedEquipmentTask(EquipmentTask inTask){
        this.serializedEquipmentList.add(inTask);
    }
    
    public void addNonSerializedEquipmentTask(EquipmentTask inTask){
        this.nonSerializedEquipmentList.add(inTask);
    }
    
    public void addStandardLaborTask(StandardLaborTask inTask){
        this.standardLaborList.add(inTask);
    }
    public void addSHSLaborTask(SHSLaborTask inTask){
        this.shsList.add(inTask);
    }
    
    public void setPayment(int inPay){
        this.payment = inPay;
    }
    
    /* 
    Getter methods for Job attributes
    */
    public String getAccountNumber(){
        return accountNum;
    }
    
    public String getWorkOrderNumber(){
        return workOrderNum;
    }
    
    public Date getDate(){
        return date;
    }
        
    public String getTechID(){
        return techID;
    }
    
    public String getCustomerName(){
        return customerName;
    }
    
    public ArrayList<EquipmentTask> getNonSerializedEquipmentTaskList(){
        return serializedEquipmentList;
    }
    
    public ArrayList<EquipmentTask> getSerializedEquipmentTaskList(){
        return nonSerializedEquipmentList;
    }
    
    public ArrayList<StandardLaborTask> getStandardLaborTaskList(){
        return standardLaborList;
    }
    
    public ArrayList<SHSLaborTask> getSHSTaskList(){
        return shsList;
    }
    
    public String getDesignation(){
        return designation;
    }
    
    public int getPayment(){
        return payment;
    }
    
    
}