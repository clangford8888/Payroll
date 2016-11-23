/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.jobs;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import payroll.tasks.NonSerializedEquipmentTask;
import payroll.tasks.SHSLaborTask;
import payroll.tasks.SerializedEquipmentTask;
import payroll.tasks.StandardLaborTask;

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
    private final String techID;
    private final String jobType;
    protected Set<SerializedEquipmentTask> serializedEquipmentGroup;
    protected List<NonSerializedEquipmentTask> nonSerializedEquipmentList;
    protected List<StandardLaborTask> standardLaborList;
    protected List<SHSLaborTask> shsList;
    private int payment;
    
    public Job(String inANum, String inWONum, Date inDate, String inDesignation,
                String inTID, String inCName, String inJobType){
        
        this.accountNum = inANum;
        this.workOrderNum = inWONum;
        this.date = inDate;
        this.designation = inDesignation;
        this.techID = inTID;
        this.customerName = inCName;
        this.jobType = inJobType;
        this.serializedEquipmentGroup = new HashSet<>();
        this.nonSerializedEquipmentList = new ArrayList<>();
        this.standardLaborList = new ArrayList<>();
        this.shsList = new ArrayList<>();
        this.payment = 0;
    }
    
    // Second constructor that accepts a payment also, for reading from DB
    public Job(String inANum, String inWONum, Date inDate, String inDesignation,
                String inTID, String inCName, String inJobType, int payment){
        
        this.accountNum = inANum;
        this.workOrderNum = inWONum;
        this.date = inDate;
        this.designation = inDesignation;
        this.techID = inTID;
        this.customerName = inCName;
        this.jobType = inJobType;
        this.serializedEquipmentGroup = new HashSet<>();
        this.nonSerializedEquipmentList = new ArrayList<>();
        this.standardLaborList = new ArrayList<>();
        this.shsList = new ArrayList<>();
        this.payment = payment;
    }
    
    public abstract int calculatePay();
    
    protected int calculateSHSPay(){
        
        int pay = 0;
        
        if(shsList != null && !shsList.isEmpty()){
            //Traverse the list
           for(SHSLaborTask task : shsList){
               // Add SHS labor for each task
               pay += task.getPayment();
           }
        }
        return pay;
    }
     
    public void addEquipmentTask(SerializedEquipmentTask inTask){
        this.serializedEquipmentGroup.add(inTask);
    }
    
    public void addEquipmentTask(NonSerializedEquipmentTask inTask){
        this.nonSerializedEquipmentList.add(inTask);
    }
    
    public void addLaborTask(StandardLaborTask inTask){
        this.standardLaborList.add(inTask);
    }
    public void addLaborTask(SHSLaborTask inTask){
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
    
    public Set<SerializedEquipmentTask> getSerializedEquipmentTaskList(){
        return serializedEquipmentGroup;
    }
    
    public List<NonSerializedEquipmentTask> getNonSerializedEquipmentTaskList(){
        return nonSerializedEquipmentList;
    }
    
    public List<StandardLaborTask> getStandardLaborTaskList(){
        return standardLaborList;
    }
    
    public List<SHSLaborTask> getSHSTaskList(){
        return shsList;
    }
    
    public String getDesignation(){
        return designation;
    }
    
    public String getJobType(){
        return jobType;
    }
    
    public int getPayment(){
        return payment;
    }
    
    // Debugging method
    public void printSerializedMap(){
        for(SerializedEquipmentTask t: this.serializedEquipmentGroup){
            System.out.println(t.getModel() + " " + t.getSerialNumber());
        }
    }
    
}
