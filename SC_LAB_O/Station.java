public class Station {
    private String code;
    private String name;
    private String city;
    private int distanceIndex;
    
    public Station(String code, String name, String city, int distanceIndex) {
        this.code = code;
        this.name = name;
        this.city = city;
        this.distanceIndex = distanceIndex;
    }
    
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public int getDistanceIndex() { return distanceIndex; }
    
    public int getDistanceTo(Station other) {
        return Math.abs(this.distanceIndex - other.distanceIndex);
    }
    
    public String toString() { return name + " (" + code + ")"; }
}
