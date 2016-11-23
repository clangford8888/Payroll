/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.gui;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

import paysheets.PaySheet;
import paysheets.PaySheetCreator;
import paysheets.PaySheetExporter;

/**
 *
 * @author Casey
 */
public class Index extends javax.swing.JFrame {

    /**
     * Creates new form Index
     */
    public Index() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        exportPaySheetsTabbedPane = new javax.swing.JTabbedPane();
        exportPaySheetsTab = new javax.swing.JPanel();
        resetFormButton = new javax.swing.JButton();
        exportPaySheetsButton = new javax.swing.JButton();
        chooseDirectoryButton = new javax.swing.JButton();
        outputDirectoryLabel = new javax.swing.JLabel();
        outputDirectoryTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        DefaultListModel<TechCheckListItem> checkListModel = new DefaultListModel<>();
        checkListModel = populateListModel(checkListModel);
        techJList = new javax.swing.JList<>(checkListModel);
        // Set the cell renderer to use checkboxes
        techJList.setCellRenderer(new CheckboxListRenderer());
        lblSelectTechnicians = new javax.swing.JLabel();
        lblStartDate = new javax.swing.JLabel();
        lblEndDate = new javax.swing.JLabel();
        jdcStartDate = new com.toedter.calendar.JDateChooser();
        jdcEndDate = new com.toedter.calendar.JDateChooser();
        btnCheckAll = new javax.swing.JButton();
        btnUncheckAll = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        resetFormButton.setText("Reset Form");

        exportPaySheetsButton.setText("Export Pay Sheets");
        exportPaySheetsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportPaySheetsButtonActionPerformed(evt);
            }
        });

        chooseDirectoryButton.setText("Choose Directory");
        chooseDirectoryButton.setMaximumSize(new java.awt.Dimension(134, 32));
        chooseDirectoryButton.setMinimumSize(new java.awt.Dimension(134, 32));
        chooseDirectoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseDirectoryButtonActionPerformed(evt);
            }
        });

        outputDirectoryLabel.setText("Output Directory:");

        outputDirectoryTextField.setEditable(false);

        techJList.setModel(checkListModel);
        techJList.setSelectionModel(new MultipleSelectionModel());
        techJList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                techJListMouseClicked(evt);
            }
        });
        techJList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                techJListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(techJList);
        selectAllJListItems();

        lblSelectTechnicians.setText("Select Technicians:");

        lblStartDate.setText("Select Start Date:");

        lblEndDate.setText("Select End Date:");

        btnCheckAll.setText("Check All");
        btnCheckAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckAllActionPerformed(evt);
            }
        });

        btnUncheckAll.setText("Uncheck All");
        btnUncheckAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUncheckAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout exportPaySheetsTabLayout = new javax.swing.GroupLayout(exportPaySheetsTab);
        exportPaySheetsTab.setLayout(exportPaySheetsTabLayout);
        exportPaySheetsTabLayout.setHorizontalGroup(
            exportPaySheetsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exportPaySheetsTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(exportPaySheetsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, exportPaySheetsTabLayout.createSequentialGroup()
                        .addComponent(btnCheckAll, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUncheckAll))
                    .addGroup(exportPaySheetsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblSelectTechnicians)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(exportPaySheetsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(exportPaySheetsButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exportPaySheetsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jdcStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblStartDate)
                        .addComponent(lblEndDate)
                        .addComponent(jdcEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(outputDirectoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(outputDirectoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(chooseDirectoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(resetFormButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 17, Short.MAX_VALUE))
        );
        exportPaySheetsTabLayout.setVerticalGroup(
            exportPaySheetsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exportPaySheetsTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(exportPaySheetsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSelectTechnicians)
                    .addComponent(lblStartDate))
                .addGap(5, 5, 5)
                .addGroup(exportPaySheetsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(exportPaySheetsTabLayout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(exportPaySheetsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCheckAll)
                            .addComponent(btnUncheckAll))
                        .addGap(6, 6, 6))
                    .addGroup(exportPaySheetsTabLayout.createSequentialGroup()
                        .addComponent(jdcStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblEndDate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdcEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(outputDirectoryLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outputDirectoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chooseDirectoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                        .addComponent(exportPaySheetsButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resetFormButton)))
                .addContainerGap())
        );

        exportPaySheetsTabbedPane.addTab("Export Pay Sheets", null, exportPaySheetsTab, "Export Pay Sheets");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(exportPaySheetsTabbedPane)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(exportPaySheetsTabbedPane, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private List<TechCheckListItem> getTechnicianListFromDatabase(){
        List<TechCheckListItem> techList;
        GuiDAO guiDAO = new GuiDAO();
        techList = guiDAO.getActiveTechList();
        return techList;
    }
    
    private DefaultListModel<TechCheckListItem> populateListModel(DefaultListModel<TechCheckListItem> listModel){
        List<TechCheckListItem> technicianCheckList = getTechnicianListFromDatabase();
        for(TechCheckListItem item : technicianCheckList){
            listModel.addElement(item);
        }
        return listModel;
    }

    private void chooseDirectoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseDirectoryButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser("C:/Users/Casey/Documents/Netbeans Projects/Payroll/test/Output Files");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();
            setOutputDirectoryLabel(file);
        }
        else{
            JOptionPane.showMessageDialog(this, "You did not select a directory.");
            // Forces user to select a directory
            chooseDirectoryButtonActionPerformed(evt);
        }
    }//GEN-LAST:event_chooseDirectoryButtonActionPerformed
    
    private void setOutputDirectoryLabel(File file){
        String directoryLocation = file.getAbsolutePath();
        outputDirectoryTextField.setText(directoryLocation);
    }
    
    /*
        Flip the selected state of a check box when it is clicked. Repaint the
        JList when finished.
    */
    private void techJListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_techJListMouseClicked
        JList<TechCheckListItem> list = (JList<TechCheckListItem>)evt.getSource();
        // Get index of item clicked
        int index = list.locationToIndex(evt.getPoint());
        if(index != -1){
            TechCheckListItem checkbox = (TechCheckListItem)list.getModel().getElementAt(index);
            checkbox.setSelected(!checkbox.isSelected());
            list.repaint();
        }
    }//GEN-LAST:event_techJListMouseClicked

    private void exportPaySheetsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportPaySheetsButtonActionPerformed
        // TODO add your handling code here:
        Date startDate = jdcStartDate.getDate();
        Date endDate = jdcEndDate.getDate();
        boolean validDates = checkValidDateSelections(startDate, endDate);
        boolean validTechs = checkValidTechnicianSelection();
        boolean validDirectory = checkValidDirectorySelected();
        if(validDates && validTechs && validDirectory){
            exportPaySheets(startDate, endDate);
        }
    }//GEN-LAST:event_exportPaySheetsButtonActionPerformed
    
    private void exportPaySheets(Date startDate, Date endDate){
        System.out.println("EXPORTING PAY SHEETS\n");
        List<TechCheckListItem> list = techJList.getSelectedValuesList();
        for(TechCheckListItem item : list){
            try {
                String techID = item.getTechID();
                PaySheetCreator psc = new PaySheetCreator();
                PaySheet createdSheet = psc.createPaySheet(techID, startDate, endDate);
                PaySheetExporter exporter = new PaySheetExporter();
                //FileOutputStream outputStream = new FileOutputStream();
                String fileName = exporter.createFileName(createdSheet);
                System.out.println("Filename Created: " + fileName);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    private boolean checkValidDateSelections(Date startDate, Date endDate){
        if(startDate == null || endDate == null){
            return false;
        }
        Calendar currentDate = Calendar.getInstance();
        Date maxSelectableDate = currentDate.getTime();
        if(endDate.after(maxSelectableDate)){
            JOptionPane.showMessageDialog(this, "You selected an end date in the future!",
                                                "Alert!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        /* Magic value is the first date of the year payroll was tracked. */
        Calendar minCalendar = Calendar.getInstance();
        minCalendar.set(2015, 0, 0);
        Date minSelectableDate = minCalendar.getTime();
        if(startDate.before(minSelectableDate)){
            JOptionPane.showMessageDialog(this, "Start date before 01/01/2015!",
                                                "Alert!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(startDate.after(endDate)){
            JOptionPane.showMessageDialog(this, "Start date before end date!",
                                                "Alert!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // If everything passes, the dates are valid.
        return true;
    }
    
    private boolean checkValidDirectorySelected(){
        String directoryPath = outputDirectoryTextField.getText();
        if(directoryPath.equals("")){
            JOptionPane.showMessageDialog(this, "No output directory selected!",
                                                "Alert!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        File outputDirectory = new File(directoryPath);
        if(!outputDirectory.exists()){
            JOptionPane.showMessageDialog(this, "Output directory does not exist!",
                                                "Alert!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    private boolean checkValidTechnicianSelection(){
        if(techJList == null){
            return false;
        }
        List<TechCheckListItem> checkedList = techJList.getSelectedValuesList();
        if(checkedList.isEmpty()){
            JOptionPane.showMessageDialog(null, "Alert!", "No technicians selected!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void techJListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_techJListValueChanged
        // TODO add your handling code here:
        
        //System.out.println("Value Changed " + )
    }//GEN-LAST:event_techJListValueChanged

    private void btnCheckAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckAllActionPerformed
        // TODO add your handling code here:
        selectAllJListItems();
    }//GEN-LAST:event_btnCheckAllActionPerformed

    // Sets all items in the JList to selected
    private void selectAllJListItems(){
        ListModel<TechCheckListItem> model = techJList.getModel();
        int lastElementIndex = model.getSize() - 1;
        techJList.addSelectionInterval(0, lastElementIndex);
        for(int i = 0; i <= lastElementIndex; i++){
            TechCheckListItem item = model.getElementAt(i);
            item.setSelected(true);
            techJList.repaint();
        }
    }
    
    private void btnUncheckAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUncheckAllActionPerformed
        // TODO add your handling code here:
        clearAllSelectedJListItems();
    }//GEN-LAST:event_btnUncheckAllActionPerformed
    
    private void clearAllSelectedJListItems(){
        techJList.clearSelection();
        ListModel<TechCheckListItem> model = techJList.getModel();
        int lastElementIndex = model.getSize() - 1;
        for(int i = 0; i <= lastElementIndex; i++){
            TechCheckListItem item = model.getElementAt(i);
            item.setSelected(false);
            techJList.repaint();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Index().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCheckAll;
    private javax.swing.JButton btnUncheckAll;
    private javax.swing.JButton chooseDirectoryButton;
    private javax.swing.JButton exportPaySheetsButton;
    private javax.swing.JPanel exportPaySheetsTab;
    private javax.swing.JTabbedPane exportPaySheetsTabbedPane;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdcEndDate;
    private com.toedter.calendar.JDateChooser jdcStartDate;
    private javax.swing.JLabel lblEndDate;
    private javax.swing.JLabel lblSelectTechnicians;
    private javax.swing.JLabel lblStartDate;
    private javax.swing.JLabel outputDirectoryLabel;
    private javax.swing.JTextField outputDirectoryTextField;
    private javax.swing.JButton resetFormButton;
    private javax.swing.JList<TechCheckListItem> techJList;
    // End of variables declaration//GEN-END:variables
}
