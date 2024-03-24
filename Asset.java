public class Asset{
    boolean mobile;
    boolean laptop = true;
    String mobileDetails;
    String laptopDetails = "ASUS Creator Series Vivobook Pro 16 OLED";
    String LaptopSoftwareVersion = "10.2.3";
    void giveLaptop(String Role, String LaptopDetails){
        if(Role.equals("Admin")){
            laptopDetails = LaptopDetails;
            laptop = true;
        }
        else {
            System.out.println("Report to the admin");
        }
    }
    void createFromFile(String mobilie, String mobileDetails,String laptop, String LaptopDetails, String LaptopSoftwareVersion){
        if(mobilie.equals("true")){
            this.mobile = true;
            this.mobileDetails = mobileDetails;
        }
        if(laptop.equals("true")){
            this.laptop = true;
            this.laptopDetails = LaptopDetails;
            this.LaptopSoftwareVersion = LaptopSoftwareVersion;
        }
    }
    void Update(String Role){
        if(Role.equals("Admin")){
            String[] splitedSoftware = LaptopSoftwareVersion.split("\\.");
            splitedSoftware[0] = (Integer.parseInt(splitedSoftware[0])+1)+"";
            // System.out.println("After: "+);
            splitedSoftware[1] = ((int) (Math.random()*11))+"";
            splitedSoftware[2] = ((int) (Math.random()*11))+"";
            LaptopSoftwareVersion = splitedSoftware[0]+"."+splitedSoftware[1]+"."+splitedSoftware[2];

        }
            
    }

    void giveMobile(String Role, String mobileDetails){
        if(Role.equals("Admin")){
            this.mobileDetails = mobileDetails;
            mobile = true;
        }
        else {
            System.out.println("Report to the admin");
        }
    }

    String showMobile() {
        String mobileStatus = mobile ? "Available" : "Not Available";
        String mobileDetail = mobile ? mobileDetails : "Request from admin";
        return String.format("| %-13s | %-70s |", mobileStatus, mobileDetail);
    }

    String showLap() {
        String laptopStatus = laptop ? "Available" : "Not Available";
        String laptopDetail = laptop ? (laptopDetails + ", SW Version: " + LaptopSoftwareVersion) : "Request from admin";
        return String.format("| %-13s | %-70s |", laptopStatus, laptopDetail);
    }

    String showAsset() {
        String assetHeader = String.format("| %-20s | %-86s |", "Asset Status", "Details");
        String separator = String.format("+%s+%s+", "-".repeat(22), "-".repeat(88));
        
        // int laptopWidth = Math.max("Laptop".length(), showLap().length());
        // int mobileWidth = Math.max("Mobile".length(), showMobile().length());

        StringBuilder assetDetails = new StringBuilder();
        assetDetails.append(separator).append("\n");
        assetDetails.append(assetHeader).append("\n");
        assetDetails.append(separator).append("\n");
        assetDetails.append(String.format("| %-"+21+"s", "Laptop")).append(showLap()).append("\n");
        assetDetails.append(separator).append("\n");
        assetDetails.append(String.format("| %-"+21+"s", "Mobile")).append(showMobile()).append("\n");
        assetDetails.append(separator);
        
        return assetDetails.toString();
    }
}