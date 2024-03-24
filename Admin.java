import java.util.ArrayList;
import java.util.Scanner;

class Admin extends employee{
    static Admin[] alllAdmins = new Admin[3];
    String Role = "Admin";
    Asset TempAsset;
    ArrayList <Report> Repots = new <Report> ArrayList();
    ArrayList <Report> Cleared = new <Report> ArrayList();
    Scanner AdminIn = new Scanner(System.in);
    Admin(String Name, String Password){
        super(Name, Password);
        // AssetManagement.AllRoll.set(AssetManagement.AllRoll.size() - 1,Role);
    }

    void createFromFile(String Id, ArrayList <Report> AllRepots,Asset asset){
        this.ID = Id;
        this.asset = asset;
        Repots = (ArrayList) AllRepots.clone();
        Repots.removeIf(Obj -> Obj.Cleared );
        Repots.removeIf(Obj -> !Obj.RequestedTo.equals(ID) );
        Cleared = (ArrayList) AllRepots.clone();
        Cleared.removeIf(Obj -> ((Obj.Cleared == false)));
        Cleared.removeIf(Obj -> !Obj.RequestedTo.equals(ID));
        
    }

    // void newReport(){}
    void ClearReport(){
        // System.out.println(Repots);
       Scanner AdminIn = new Scanner(System.in);

        // System.out.println("===== Unresolved Reports =====");
        

        System.out.print("Choose the Report to clear: ");
        
        int chooseReport = 0;
        try{
            chooseReport = AdminIn.nextInt();
        }
        catch(Exception e){
            System.out.print("Invalid option: ");
            ClearReport();
            return;
        }
        if(chooseReport > Repots.size() ){
            System.out.print("Invalid option: ");
            ClearReport();
            return;
        }
        Report currReport = Repots.get(chooseReport - 1);

        System.out.println("Options:");
        System.out.println("1. Resolve");
        System.out.println("2. Decline");

        int ResolveOrDecline = AdminIn.nextInt();

        for (employee testEmployee : AssetManagement.AllEmployee) {
            if (currReport.RequestedBy.equals(testEmployee.ID)) {
                TempAsset = testEmployee.asset;
            }
        }

        if (ResolveOrDecline == 1) {
            if (currReport.Type.equalsIgnoreCase("New Device")) {
                if (currReport.RequestedDevice.equals("Mobile")) {
                    TempAsset.giveMobile(Role, currReport.RequestDetails);
                } else if (currReport.RequestedDevice.equals("Laptop")) {
                    TempAsset.giveLaptop(Role, currReport.RequestDetails);
                }
            } else if (currReport.Type.equalsIgnoreCase("Software Update")) {
                TempAsset.Update(Role);
            }

            currReport.ReportStatus = 'C';
            TempAsset = null;
            Cleared.add(currReport);
            Repots.remove(currReport);
            currReport.Cleared = true;

            System.out.println("Report successfully resolved. Asset updated!");
        } else if (ResolveOrDecline == 2) {
            currReport.ReportStatus = 'D';
            Cleared.add(currReport);
            Repots.remove(currReport);
            currReport.Cleared = true;

            System.out.println("Report declined. Asset remains unchanged.");
        }
        // AdminIn.close();
    }
        
}