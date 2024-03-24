import java.util.ArrayList;

public class employee{
    
    static int IdNum = 12;
    String Name;
    String ID;
    Asset asset = new Asset();
    String Role = "Employee";
    String Password;
    ArrayList <Report> MyRepots = new <Report> ArrayList();
    employee(String Name, String Password){
        this.Name = Name;
        this.Password = Password;
        // char[] smallArr = {(char) (Math.floor(Math.random()*25)+65),(char) (Math.floor(Math.random()*25)+65),(char) (Math.floor(Math.random()*25)+65),(char) (Math.floor(Math.random()*25)+65)};
        // ID = new String(smallArr);
        // Id = 
        // ID += IdNum+"";
        ID = "IJC"+IdNum;
        // System.out.println("This is Your ID\n"+ID);
        IdNum++;
        
        // System.out.println("ID: "+ID+" Pass: "+Password+" Role: "+Role);
    }

    employee(String Name, String Password,String Id, Asset asset, ArrayList <Report> MyRepots){
        this.Name = Name;
        this.Password = Password;
        this.ID = Id;
        this.asset = asset;
        this.MyRepots = MyRepots;
        // System.out.println("ID: "+ID+" Pass: "+Password+" Role: "+Role);
    }

    //Creating a new report from the Employee
    void newReport(String Type, String RequestTitle, String RequestDetails, String Device){
        //
        Report report = new Report();
        report.CreateReport(ID, Type, RequestTitle, RequestDetails, Device);
        MyRepots.add(report);
    }

    String ValuesForEmployee(){
        return (ID+","+Name+","+Password+","+asset.mobile+","+asset.mobileDetails+","+asset.laptop+","+asset.laptopDetails+","+asset.LaptopSoftwareVersion);
    }

    
}