import java.util.ArrayList;
import java.util.Scanner;

public class Report{
    static ArrayList <Report> AllReports = new <Report> ArrayList();
    static int reportNum = 1;
    String Type;
    String RequestTitle;
    String RequestDetails;
    boolean Cleared;
    char ReportStatus;
    String ReportID;
    String RequestedBy;
    String RequestedTo;
    String RequestedDevice;
    Scanner ReportIn = new Scanner(System.in);
    // employee thisEmployee;
    void CreateReport(String Id, String Type, String RequestTitle, String RequestDetails, String Device){
        Admin freeAdmin = Admin.alllAdmins[0];
        int freeAdminReports = freeAdmin.Repots.size();

        this.Type = Type;
        this.RequestTitle = RequestTitle;
        this.RequestDetails = RequestDetails;
        this.RequestedDevice = Device;
        ReportID = reportNum+""+((long) (Math.floor(Math.random()*899999999)+1000000000)) + "";
        reportNum++;
        for (Admin checkAdmin : Admin.alllAdmins) {
            // System.out.println(freeAdmin.Name+checkAdmin.Repots.size());
            if(checkAdmin.Repots.size()<freeAdminReports){
                freeAdminReports = checkAdmin.Repots.size();
                freeAdmin = checkAdmin;
                // System.out.println(freeAdmin.Name);
            }
        }
        ReportStatus = 'P';
        freeAdmin.Repots.add(this);
        RequestedBy = Id;
        AllReports.add(this);
        RequestedTo = freeAdmin.ID;
    }

    void CreateReport(String ReportID,String Type, String RequestTitle, String RequestDetails, String Device, String RequestedTo, String RequestedBy,String ReportStatus, String Cleared){
        this.Type = Type;
        this.RequestTitle = RequestTitle;
        this.RequestDetails = RequestDetails;
        this.RequestedDevice = Device;
        this.ReportID = ReportID;
        this.ReportStatus = ReportStatus.charAt(0);
        // System.out.println(this.ReportStatus);
        this.RequestedBy = RequestedBy;
        this.RequestedTo = RequestedTo;
        this.Cleared = Cleared.equals("true");
    }
    
    public String toString(){
        String Progress = "";
        if(ReportStatus == 'P'){
            Progress = "Progress";
        }
        else if(ReportStatus == 'C'){
            Progress = "Completed";
        }
        else if(ReportStatus == 'D'){
            Progress = "Declined";
        }
        return ("Type: "+Type+"\nRequestTitle: "+RequestTitle+"\nReportID: "+ReportID+"\nStatus: "+Cleared+"\nStatus: "+Progress);
    }

    String allValuesForReport(){
        // String []allVals = {};
        return (ReportID+","+Type+","+RequestTitle+","+RequestDetails+","+RequestedDevice+","+RequestedTo+","+RequestedBy+","+ReportStatus+","+Cleared);
    }
}