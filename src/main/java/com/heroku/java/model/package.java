public class PackageModel {
    private String packID;
    private String packName;
    private String packActivity;
    private String packType;
    private double packPrice;

    // Default constructor
    public PackageModel() {}

    // Parameterized constructor
    public PackageModel(String packID, String packName, String packActivity, String packType, double packPrice) {
        this.packID = packID;
        this.packName = packName;
        this.packActivity = packActivity;
        this.packType = packType;
        this.packPrice = packPrice;
    }

    // Getter and Setter methods
    public String getPackID() {
        return packID;
    }

    public void setPackID(String packID) {
        this.packID = packID;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getPackActivity() {
        return packActivity;
    }

    public void setPackActivity(String packActivity) {
        this.packActivity = packActivity;
    }

    public String getPackType() {
        return packType;
    }

    public void setPackType(String packType) {
        this.packType = packType;
    }

    public double getPackPrice() {
        return packPrice;
    }

    public void setPackPrice(double packPrice) {
        this.packPrice = packPrice;
    }

    @Override
    public String toString() {
        return "PackageModel{" +
                "packID='" + packID + '\'' +
                ", packName='" + packName + '\'' +
                ", packActivity='" + packActivity + '\'' +
                ", packType='" + packType + '\'' +
                ", packPrice=" + packPrice +
                '}';
    }
}