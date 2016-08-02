/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paysheets;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents one job listed on a technician's pay sheet.
 * 
 * @author Casey
 */
public class PaySheetEntry {
    private final Date date;
    private final String workOrderNum;
    private final String customer;
    private final String type;
    private List<String> nonSerializedList;
    private List<String> serializedList;
    private List<String> shsList;
    private final int pay;
    private final String lastEventProvider;
    
    public PaySheetEntry(Date inDate, String inWorkOrder, String inCustomer,
                            String inType, int inPay, String inLEP){
        date = inDate;
        workOrderNum = inWorkOrder;
        customer = inCustomer;
        type = inType;
        nonSerializedList = new ArrayList<>();
        serializedList = new ArrayList<>();
        shsList = new ArrayList<>();
        pay = inPay;
        lastEventProvider = inLEP;
        
    }
    
    /*
      Getter methods for all neceassary fields
    */
    
    public Date getDate(){
        return this.date;
    }
    
    public String getWorkOrderNumber(){
        return this.workOrderNum;
    }
    
    public String getCustomer(){
        return this.customer;
    }
    
    public String getType(){
        return this.type;
    }
    
    public List<String> getNonSerializedList(){
        return this.nonSerializedList;
    }
    
    public List<String> getSerializedList(){
        return this.serializedList;
    }
    
    public List<String> getSHSList(){
        return this.shsList;
    }
    
    public int getPay(){
        return this.pay;
    }
    
    public String getLEP(){
        return this.lastEventProvider;
    }

    @Override
    public String toString(){
        String s = date.toString() + " " + workOrderNum + " " + customer + " " +
                    type + ". Pay: " + pay;
        return s;
    }
    
    // TODO: Test this method
    protected void addNonSerialized(String nonSerialized){
        if(nonSerialized != null){
            nonSerializedList.add(nonSerialized);
        }
    }
    
    // TODO: Test this method
    protected void addSerialized(String serialized){
        if(serialized != null){
            serializedList.add(serialized);
        }
    }
    
    // TODO: Test this method
    protected void addSHS(String shs){
        if(shs != null){
            shsList.add(shs);
        }
    }
    
    protected void printEquipmentLists(){
        if(!this.serializedList.isEmpty()){
            System.out.println("Serialized List:");
            for(String s : serializedList){
                System.out.println(s);
            }
        }
        else{
            System.out.println("No serialized equipment.");
        }
        // Print the Non-serialized list
        if(!this.nonSerializedList.isEmpty()){
            System.out.println("Non-Serialized List:");
            for(String s : nonSerializedList){
                System.out.println(s);
            }
        }
        else{
            System.out.println("No Non-Serialized equipment.");
        }
    }
}
