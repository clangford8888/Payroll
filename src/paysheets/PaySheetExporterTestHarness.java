/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paysheets;

/**
 *
 * @author Casey
 */
public class PaySheetExporterTestHarness {
    
    public static void main(String[] args){
        PaySheetExporterDAO pseDAO = new PaySheetExporterDAO();
        
        String test = pseDAO.getTechName("Angel.Torres1");
        System.out.println(test);
        String test1 = pseDAO.getTechName("Jhonny.Gonzalez3");
        System.out.println(test1);
        String test2 = pseDAO.getTechName("david.rose");
        System.out.println(test2);
        String test3 = pseDAO.getTechName("allen.kerr");
        System.out.println(test3);
    }
}
