import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AssetUtil{
    
    static String readPassword(){
        Console console = System.console();

        char[] passwordChars = console.readPassword();
        String passwordString = new String(passwordChars);

        return passwordString;
    }

    static void SavingFile(){
        try {
            File ReportFile = new File("Reports.csv");
            FileWriter ReportWrite = new FileWriter(ReportFile);

            File EmployeeFile = new File("Employees.csv"); 
            FileWriter EmployeeWrite = new FileWriter(EmployeeFile);

            File AdminFile = new File("Admin.csv");
            FileWriter AdminWrite = new FileWriter(AdminFile);

            for (Report report2 : Report.AllReports) {
                ReportWrite.append(report2.allValuesForReport()+"\n");
            }
            ReportWrite.close();
            for (employee fileEmployee : AssetManagement.AllEmployee){
                EmployeeWrite.append(fileEmployee.ValuesForEmployee()+"\n");
            }
            EmployeeWrite.close();

            for (Admin admin : Admin.alllAdmins){
                AdminWrite.append(admin.ValuesForEmployee()+"\n");
            }
            AdminWrite.close();

        } catch (Exception e) {
            // e.printStackTrace();-+
        }
    }

    static void CreatingFromFile(){
        try {
            File ReportFile = new File("Reports.csv");
            File EmployeeFile = new File("Employees.csv");
            File AdminFile = new File("Admin.csv");
            int testVar = 0;
            for ( String lin : Files.readAllLines(Paths.get(ReportFile.getPath()))) {
                Report fromFileReport = new Report();
                String []ValForRep = lin.split(",");
                fromFileReport.CreateReport(ValForRep[0],ValForRep[1],ValForRep[2],ValForRep[3],ValForRep[4],ValForRep[5],ValForRep[6],ValForRep[7],ValForRep[8]);
                Report.AllReports.add(fromFileReport);
            }
            for (String lineInEmp : Files.readAllLines(Paths.get(EmployeeFile.getPath()))) {
                String []ValForEmp = lineInEmp.split(",");
                // System.out.println(ValForEmp.length);
                Asset tempAssetForEmp = new Asset();
                // System.out.println(lineInEmp);
                tempAssetForEmp.createFromFile(ValForEmp[3], ValForEmp[4], ValForEmp[5], ValForEmp[6], ValForEmp[7]);
                ArrayList <Report>tempRep = (ArrayList) Report.AllReports.clone();
                tempRep.removeIf(val -> !(val.RequestedBy.equals(ValForEmp[0])));
                employee crEmployee = new employee(ValForEmp[1], ValForEmp[2], ValForEmp[0], tempAssetForEmp, tempRep);
                AssetManagement.AllID.add(crEmployee.ID);
                AssetManagement.AllPass.add(crEmployee.Password);
                AssetManagement.AllRoll.add(crEmployee.Role);
                // System.out.println(crEmployee.ValuesForEmployee());
                AssetManagement.AllEmployee.add(crEmployee);
            }
            for (String lineInEmp : Files.readAllLines(Paths.get(AdminFile.getPath()))) {
                String []ValForEmp = lineInEmp.split(",");
                // System.out.println(ValForEmp.length);
                Asset tempAssetForEmp = new Asset();
                tempAssetForEmp.createFromFile(ValForEmp[3], ValForEmp[4], ValForEmp[5], ValForEmp[6], ValForEmp[7]);
                Admin creAdmin = new Admin(ValForEmp[1],ValForEmp[2]);
                creAdmin.createFromFile(ValForEmp[0], Report.AllReports, tempAssetForEmp);
                AssetManagement.AllID.add(creAdmin.ID);
                AssetManagement.AllPass.add(creAdmin.Password);
                AssetManagement.AllRoll.add(creAdmin.Role);
                Admin.alllAdmins[testVar] = creAdmin;
                // System.out.println(creAdmin.ValuesForEmployee());
                testVar++;
            }
            Report.reportNum = Report.AllReports.size();
            employee.IdNum = Admin.alllAdmins.length + AssetManagement.AllEmployee.size() + 12;
            // System.out.println(testVar);
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }
}