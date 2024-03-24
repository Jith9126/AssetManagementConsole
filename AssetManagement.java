import java.util.ArrayList;
import java.util.Scanner;
import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;

import java.nio.file.Paths;



public class AssetManagement {
    static ArrayList <employee> AllEmployee = new <employee> ArrayList();
    static ArrayList <String> AllID = new ArrayList<String>();
    static ArrayList <String> AllPass = new ArrayList<String>();
    static ArrayList <String> AllRoll = new ArrayList<String>();

    public static void main(String[] args) {
        Scanner inputObj = new Scanner(System.in);
        AssetUtil.CreatingFromFile();
        if(AllEmployee.isEmpty()){
            employee Rabi = new employee("Rabi", "12553");
            AllEmployee.add(Rabi);
        }
        Admin currAdmin = Admin.alllAdmins[0];
        employee currEmployee = AllEmployee.get(0);
        // System.out.println(AllID);
        // System.out.println(Rabi.asset.showAsset());
        // Rabi.newReport();
        // CreatingReport(Rabi);
        EntryPage:
        while (true) {
            // System.out.println(AllID+"\n"+AllPass+"\n"+AllRoll);
            System.out.println("+----------------------------------------+");
            System.out.println("|                Main Menu               |");
            System.out.println("+----------------------------------------+");
            System.out.println("| 1. Login                               |");
            System.out.println("| 2. New Employee                        |");
            System.out.println("| 3. Exit                                |");
            System.out.println("+----------------------------------------+");
            System.out.print("Enter your choice: ");
            int loginOpr = 3;
            String logOrNew = inputObj.next();
            
            LoginPage:
            while (true) {
                if (logOrNew.equals("1") || logOrNew.equalsIgnoreCase("Login")) {
                    System.out.print("Enter Your ID: ");
                    inputObj.nextLine(); 
                    String LogId = inputObj.nextLine();
                    int userIndex = AllID.indexOf(LogId);
                
                    if (userIndex == -1) {
                        System.out.println("Invalid ID. Please enter a correct ID.");
                        continue;
                    }
                
                    System.out.print("Enter Your Password: ");
                    String LogPassWord = AssetUtil.readPassword();
                
                    if (AllPass.get(userIndex).equals(LogPassWord)) {
                        if (AllRoll.get(userIndex).equals("Admin")) {
                            for (Admin cAdmin : Admin.alllAdmins) {
                                if (cAdmin.ID.equals(LogId)) {
                                    currAdmin = cAdmin;
                                    loginOpr = 2;
                                    break LoginPage;
                                }
                            }
                        } else {
                            for (employee cEmployee : AllEmployee) {
                                if (cEmployee.ID.equals(LogId)) {
                                    currEmployee = cEmployee;
                                    loginOpr = 1;
                                    break LoginPage;
                                }
                            }
                        }
                    } else {
                        System.out.println("Incorrect Password. Please try again.");
                        continue;
                    }
                } else if (logOrNew.equals("2") || logOrNew.equalsIgnoreCase("new employee")) {
                    System.out.print("Enter Your Name: ");
                    inputObj.nextLine(); 
                    String name = inputObj.nextLine();

                    System.out.print("Enter Your Password: ");
                    String pass = AssetUtil.readPassword();

                    while (pass.length() < 8) {
                        System.out.print("Password too weak. Please enter a stronger password (at least 8 characters): ");
                        pass = AssetUtil.readPassword();
                    }

                    // Confirm password
                    System.out.print("Confirm Your Password: ");
                    String confirmPassword = AssetUtil.readPassword();

                    while (!confirmPassword.equals(pass)) {
                        System.out.print("Passwords do not match. Please confirm your password: ");
                        confirmPassword = AssetUtil.readPassword();
                    }

                    System.out.println("Account created successfully!");
                
                    employee newEmployee = new employee(name, pass);
                    AllEmployee.add(newEmployee);
                    AllID.add(newEmployee.ID);
                    AllRoll.add(newEmployee.Role);
                    AllPass.add(newEmployee.Password);
                    currEmployee = newEmployee;
                    AssetUtil.SavingFile();
                    loginOpr = 1;
                }
                else if(!(logOrNew.equals("3"))){
                    continue;
                }
                System.out.print("\033[H\033[2J");
                break;
                
            }
            if(loginOpr == 1){
                MainPage:
                while (true) {
                    System.out.println("+" + "-".repeat(79) + "+");
                    System.out.printf("| %-77s |\n","Employee Information");
                    System.out.println("+" + "-".repeat(79) + "+");
                    System.out.printf("| %-40s | ID: %-30s |\n", currEmployee.Name, currEmployee.ID);
                    System.out.println("+" + "-".repeat(79) + "+");




                    System.out.println("1.Asset Details\n2.Rise a Ticket\n3.Check Status\n4.Logout");
                    int userOpr = inputObj.nextInt();
                    switch (userOpr) {
                        case 1:
                            System.out.println();
                            System.out.println();
                            System.out.println(currEmployee.asset.showAsset());
                            System.out.println("Press Enter to go back:");
                            inputObj.nextLine();
                            inputObj.nextLine();
                            System.out.print("\033[H\033[2J");
                            // System.out.println(currEmployee.asset.showAsset());
                            
                        break;
                    
                        case 2:
                            System.out.println();
                            System.out.println();
                            CreatingReport(currEmployee);
                            AssetUtil.SavingFile();
                            break;
                        case 3:
                            // System.out.println(currEmployee.MyRepots);
                            System.out.println();
                            System.out.println();
                            int srNum = 0;

                            System.out.printf("%-7s %-15s %-20s %-30s %-20s %-20s %-20s\n", "S.No", "Report ID", "Type", "Title", "Requested to", "Admin Name","Status");

                            for (Report rep : currEmployee.MyRepots) {
                                srNum++;
                                String ReportID = rep.ReportID;
                                String Type = (rep.Type.equals("New Device")) ? rep.RequestedDevice : rep.Type;
                                String Title = rep.RequestTitle;
                                String details = rep.RequestDetails;
                                String To = rep.RequestedTo;
                                String Whom = "";
                                String Status = "";
                                if(rep.ReportStatus == 'P'){
                                    Status = "Progress";
                                }
                                else if(rep.ReportStatus == 'D'){
                                    Status = "Declined";
                                }
                                else if(rep.ReportStatus == 'C'){
                                    Status = "Completed";
                                }
                                for (Admin name : Admin.alllAdmins) {
                                    if (name.ID.equals(To)) {
                                        Whom = name.Name;
                                    }
                                }
                                System.out.printf("%-7s %-15s %-20s %-30s %-20s %-20s %-20s\n", srNum, ReportID, Type, Title, To, Whom, Status);
                            }
                            if (currEmployee.MyRepots.isEmpty()) {
                                System.out.println("No Reports");
                             }
                            System.out.println("Press Enter to go back:");
                            inputObj.nextLine();
                            inputObj.nextLine();
                            System.out.print("\033[H\033[2J");
                            break;
                        case 4:
                            System.out.print("\033[H\033[2J");
                            break MainPage;
                    }
                }
            }
            else if(loginOpr == 2){
                MainPage:
                while (true) {
                    String reportCount = (currAdmin.Repots.size() > 0)?"("+currAdmin.Repots.size()+")":"";
                    System.out.println();
                    System.out.println();
                    System.out.println("+" + "-".repeat(79) + "+");
                    System.out.printf("| %-77s |\n","Employee Information");
                    System.out.println("+" + "-".repeat(79) + "+");
                    System.out.printf("| %-40s | ID: %-30s |\n", currAdmin.Name, currAdmin.ID);
                    System.out.println("+" + "-".repeat(79) + "+");
                    
                    System.out.println("Options:");
                    System.out.println("1. Asset Details");
                    System.out.println("2. Unresolved Reports"+reportCount);
                    System.out.println("3. Resolved Reports");
                    System.out.println("4. Logout");
                    System.out.println("-------------------------------------------------------------------------------------------");
                    
                    int userOpr = inputObj.nextInt();
                    switch (userOpr) {
                        case 1:
                            System.out.println();
                            System.out.println();
                            System.out.println(currAdmin.asset.showAsset());
                            System.out.println("Press Enter to go back:");
                            inputObj.nextLine();
                            inputObj.nextLine();
                            System.out.print("\033[H\033[2J");
                        break;
                    
                        case 2:
                            // System.out.println(Admin.alllAdmins[0].Repots);
                            System.out.println();
                            System.out.println();
                            int srNo = 0;
                            System.out.println("===== Unresolved Reports =====\n");
                            System.out.printf("%-7s %-15s %-20s %-30s %-20s %-20s %-20s\n", "S.No", "Report ID", "Type", "Title", "Requested By", "Employee Name","Status");

                            for (Report rep : currAdmin.Repots) {
                                srNo++;
                                String ReportID = rep.ReportID;
                                String Type = (rep.Type.equals("New Device")) ? rep.RequestedDevice : rep.Type;
                                String Title = rep.RequestTitle;
                                String details = rep.RequestDetails;
                                String By = rep.RequestedBy;
                                System.out.println();
                                String Whom = "";
                                String Status = "";
                                if(rep.ReportStatus == 'P'){
                                    Status = "Progress";
                                }
                                else if(rep.ReportStatus == 'D'){
                                    Status = "Declined";
                                }
                                else if(rep.ReportStatus == 'C'){
                                    Status = "Completed";
                                }
                                for (employee name : AllEmployee) {
                                    if (name.ID.equals(By)) {
                                        Whom = name.Name;
                                    }
                                }
                                System.out.printf("%-7s %-15s %-20s %-30s %-20s %-20s %-20s\n", srNo, ReportID, Type, Title, By, Whom, Status);
                            }
                            if (currAdmin.Repots.isEmpty()) {
                                System.out.println("No Reports");
                                System.out.println("Press Enter to go back:");
                                inputObj.nextLine();
                                inputObj.nextLine();
                            }
                            else{
                                currAdmin.ClearReport();
                                AssetUtil.SavingFile();
                            }
                            System.out.print("\033[H\033[2J");
                            
                            
                            break;
                        case 3:
                            System.out.println();
                            System.out.println();
                            // System.out.println(currAdmin.Cleared);

                            // System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n   S.no |      ReportID      |       Type       |                 Title                 |                 Details                 |    Requested By   ");
                            System.out.println();
                            System.out.println();
                            int sNo = 0;

                            System.out.printf("%-7s %-15s %-20s %-30s %-20s %-20s %-20s\n", "S.No", "Report ID", "Type", "Title", "Requested By", "Employee Name","Status");

                            for (Report rep : currAdmin.Cleared) {
                                sNo++;
                                String ReportID = rep.ReportID;
                                String Type = (rep.Type.equals("New Device")) ? rep.RequestedDevice : rep.Type;
                                String Title = rep.RequestTitle;
                                String details = rep.RequestDetails;
                                String By = rep.RequestedBy;
                                // System.out.println(rep.RequestedBy+"By:To"+rep.RequestedTo);
                                String Whom = "";
                                String Status = "";
                                if(rep.ReportStatus == 'P'){
                                    Status = "Progress";
                                }
                                else if(rep.ReportStatus == 'D'){
                                    Status = "Declined";
                                }
                                else if(rep.ReportStatus == 'C'){
                                    Status = "Completed";
                                }
                                for (employee name : AllEmployee) {
                                    if (name.ID.equals(By)) {
                                        Whom = name.Name;
                                    }
                                }
                                System.out.printf("%-7s %-15s %-20s %-30s %-20s %-20s %-20s\n", sNo, ReportID, Type, Title, By, Whom, Status);
                            }
                             if (currAdmin.Cleared.isEmpty()) {
                                System.out.println("No Reports");
                             }
                            System.out.println("Press Enter to go back:");
                            inputObj.nextLine();
                            inputObj.nextLine();
                            System.out.print("\033[H\033[2J");
                            break;
                        case 4:
                            break MainPage;
                    }
                }
            }
            else if(loginOpr == 3){
                break EntryPage;
            }
        }
        AssetUtil.SavingFile();
        // inputObj.close();
    }

    static void CreatingReport(employee currEmployee){
        Scanner ReportIn = new Scanner(System.in);
        String[] Mobiles = {"Samsung S23", "Pixel 8", "Oneplus 11", "Iphone 15","Samsung Galaxy S23 Ultra","Apple iPhone 14 Pro Max","Google Pixel 7 Pro","OnePlus 11 Pro","Xiaomi 13 Pro","OPPO Find X6 Pro","vivo X90 Pro Plus"};
        String[] Laptops = {"Macbook pro m3 14", "Dell Inspiron 16 Plus Laptop", "ASUS ROG Strix G16", "ASUS Creator Series Vivobook Pro 16 OLED"};
        String RequestTitle = "";
        String RequestDetails = "";
        String RequestedDevice = null;

        System.out.println("=== Create Ticket ===");
        System.out.println("Choose an option:");
        System.out.println("1. Request New Device");
        System.out.println("2. Repair");
        System.out.println("3. Software Update");
        System.out.print("Enter your choice: ");
        String Type = ReportIn.nextLine();

        if (Type.equals("SoftwareUpdate") || Type.equals("3")) {
            Type = "Software Update";
            System.out.print("Enter the Subject: ");
            RequestTitle = ReportIn.nextLine();
            RequestDetails = "Request for software update to the latest version.";
        } else if (Type.equals("Repair") || Type.equals("2")) {
            Type = "Repair";
            System.out.print("Enter the Subject: ");
            RequestTitle = ReportIn.nextLine();
            System.out.print("Enter the Description of the issue: ");
            RequestDetails = ReportIn.nextLine();
        } else if (Type.equals("New Device") || Type.equals("1")) {
            Type = "New Device";
            System.out.println("Select the type of device:");
            System.out.println("1. Laptop");
            System.out.println("2. Mobile");
            System.out.print("Your choice: ");
            switch (ReportIn.nextInt()) {
                case 1:
                    RequestedDevice = "Laptop";
                    System.out.print("Enter the Subject: ");
                    ReportIn.nextLine(); 
                    RequestTitle = ReportIn.nextLine();
                    System.out.println("Choose your Laptop:");
                    for (int i = 0; i < Laptops.length; i++) {
                        System.out.println((i + 1) + ". " + Laptops[i]);
                    }
                    System.out.print("Your choice: ");
                    int laptopChoice = ReportIn.nextInt();
                    while (laptopChoice > Laptops.length) {
                        System.out.println("Invalid choice, please select a valid device number.");
                        System.out.print("Your choice: ");
                        laptopChoice = ReportIn.nextInt();
                    }
                    RequestDetails = Laptops[laptopChoice - 1];
                    break;
                case 2:
                    RequestedDevice = "Mobile";
                    System.out.print("Enter the Subject: ");
                    ReportIn.nextLine();
                    RequestTitle = ReportIn.nextLine();
                    System.out.println("Choose your Mobile:");
                    for (int i = 0; i < Mobiles.length; i++) {
                        System.out.println((i + 1) + ". " + Mobiles[i]);
                    }
                    System.out.print("Your choice: ");
                    int mobileChoice = ReportIn.nextInt();
                    while (mobileChoice > Mobiles.length) {
                        System.out.println("Invalid choice, please select a valid device number.");
                        System.out.print("Your choice: ");
                        mobileChoice = ReportIn.nextInt();
                    }
                    RequestDetails = Mobiles[mobileChoice - 1];
                    break;
            }
        }
        currEmployee.newReport(Type, RequestTitle, RequestDetails, RequestedDevice);

    }

    
    
}
