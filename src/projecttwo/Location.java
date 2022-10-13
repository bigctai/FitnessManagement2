package projecttwo;

/**
 * Creates a Location constant  of the town a member lives in with a zip code and county
 *
 * @author Chris Tai, Shreyank Yelagoila
 */
public enum Location {
    BRIDGEWATER("08807", "SOMERSET"),
    EDISON("08837", "MIDDLESEX"),
    FRANKLIN("08873", "SOMERSET"),
    PISCATAWAY("08854", "MIDDLESEX"),
    SOMERVILLE("08876", "SOMERSET");

    private final String zip;
    private final String county;

    /**
     * Constructs a Location instance that is a town the gym is located in
     *
     * @param zip    the zip code of the location
     * @param county the county that the town of the gym is located in
     */
    Location(String zip, String county) {
        this.zip = zip;
        this.county = county;
    }

    @Override
    /**
     * Turns the Location into a String
     *
     * @return the name of the town, followed by the zip code, followed by the county
     */
    public String toString() {
        return this.name() + ", " + this.zip + ", " + this.county;
    }

    /**
     * Gets the county of the town that the gym is in
     *
     * @return county as a String
     */
    public String county() {
        return this.county;
    }

    /**
     * Gets the zip code of the town that the gym is in
     *
     * @return zipCode as a String
     */
    public String zipCode() {
        return this.zip;
    }
}