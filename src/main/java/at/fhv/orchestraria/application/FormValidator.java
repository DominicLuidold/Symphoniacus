package at.fhv.orchestraria.application;

import java.util.regex.Pattern;

/**
 * FormValidator to validate the Form to create new user or edit them
 * Validation with regex and matching
 */
public class FormValidator {

//    First Name: keine Zahlen
//    Last Name: keine Zahlen
//    Section: DropDown
//    Role: DropDown
//    End of Contract: Datepicker
//    E-Mail: regex
//    Phone: regex
//    Password: regex
//    City: keine zahlen und sonderzeichen
//    Zip code: nur zahlen?
//    Country: nur Buchstaben
//    street: nur Buchstaben
//    streetnumber: / und number

    //Regex
    private final String FNAME_REGEX = "^[A-Z\u00c4\u00d6\u00dcÖÄÜ][öäüÖÄÜßa-zA-Z\\-' \u00c4\u00e4\u00d6\u00f6\u00dc\u00fc\u00df]{1,20}";
    private final String LNAME_REGEX = "^[A-Z\u00c4\u00d6\u00dcÖÄÜ][öäüÖÄÜß\u00c4\u00e4\u00d6\u00f6\u00dc\u00fc\u00dfa-zA-Z\\-' ]{1,25}";
    private final String CITY_REGEX = "^[A-Z\u00c4\u00d6\u00dcÖÄÜ][öäüÖÄÜß\u00c4\u00e4\u00d6\u00f6\u00dc\u00fc\u00dfa-zA-Z\\-' .]{1,30}";
    private final String ZIP_REGEX = "\\d{4,8}";
    private final String COUNTRY_REGEX = "^[A-Z\u00c4\u00d6\u00dcÖÄÜ][öäüÖÄÜß\u00c4\u00e4\u00d6\u00f6\u00dc\u00fc\u00dfa-zA-Z\\- ]{1,12}";
    private final String STREET_REGEX = "^[\\dA-Z\u00c4\u00d6\u00dcÖÄÜ][\\döäüÖÄÜß\u00c4\u00e4\u00d6\u00f6\u00dc\u00fc\u00dfa-zA-Z\\-\' ]{1,25}";
    private final String STREET_NR_REGEX = "[\\d/]{1,5}";
    private final String EMAIL_REGEX = "^[a-zA-Z][\\.0-9a-zA-Z_\\-]+[^.]@[0-9a-zA-Z_\\-]+[\\.]{1}([0-9a-zA-Z_\\-]+[\\.])*[0-9a-zA-Z_\\-]+$";
    private final String PHONE_REGEX = "^\\+(?:[0-9] ?){6,14}[0-9]$";
    private final String POINTS_REGEX = "\\d{1,3}";
    private final String DATE_REGEX = "^\\d{1,2}/\\d{1,2}/\\d{4}";

    //Patterns
    private final Pattern FNAME_PATTERN = Pattern.compile(FNAME_REGEX);
    private final Pattern LNAME_PATTERN = Pattern.compile(LNAME_REGEX);
    private final Pattern CITY_PATTERN = Pattern.compile(CITY_REGEX);
    private final Pattern ZIP_PATTERN = Pattern.compile(ZIP_REGEX);
    private final Pattern COUNTRY_PATTERN = Pattern.compile(COUNTRY_REGEX);
    private final Pattern STREET_PATTERN = Pattern.compile(STREET_REGEX);
    private final Pattern STREET_NR_PATTERN = Pattern.compile(STREET_NR_REGEX);
    private final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);
    private final Pattern POINTS_PATTERN = Pattern.compile(POINTS_REGEX);
    private final Pattern DATE_PATTERN = Pattern.compile(DATE_REGEX);


    /**
     * boolean method which returns wheter a form is valid or not
     * @param fname
     * @param lname
     * @param city
     * @param zip
     * @param country
     * @param street
     * @param streetNr
     * @param email
     * @param phone
     * @return
     */
    public boolean validateAll(String fname, String lname, String city, String zip, String country, String street,
                            String streetNr, String email, String phone) {
        if (validateFname(fname) && validateLname(lname) &&
               validateCity(city) && validateZip(zip) &&
                validateCountry(country) && validateStreet(street) && validateStreetNr(streetNr) &&
                validateEmail(email) && validatePhone(phone)) {
            return true;
        }
        return false;
    }

    public boolean validateFname(String fname){
        if (FNAME_PATTERN.matcher(fname).matches()){
            return true;
        }
        return false;
    }

    public boolean validateLname(String lname){
        if (LNAME_PATTERN.matcher(lname).matches()){
            return true;
        }
        return false;
    }

    public boolean validateCity(String city){
        if (CITY_PATTERN.matcher(city).matches()){
            return true;
        }
        return false;
    }

    public boolean validateZip(String zip){
        if (ZIP_PATTERN.matcher(zip).matches()){
            return true;
        }
        return false;
    }

    public boolean validateCountry(String country){
        if(COUNTRY_PATTERN.matcher(country).matches()){
            return true;
        }else{
            return false;
        }
    }

    public boolean validateStreet(String street){
        if(STREET_PATTERN.matcher(street).matches()){
            return true;
        }else{
            return false;
        }
    }

    public boolean validateStreetNr(String streetNr){
        if(STREET_NR_PATTERN.matcher(streetNr).matches()){
            return true;
        }else{
            return false;
        }
    }

    public boolean validateEmail(String email){
        if(EMAIL_PATTERN.matcher(email).matches()){
            return true;
        }else{
            return false;
        }
    }

    public boolean validatePhone(String phone){
        if(PHONE_PATTERN.matcher(phone).matches()){
            return true;
        }else{
            return false;
        }
    }

    public boolean validatePoints(String points){
        if(POINTS_PATTERN.matcher(points).matches()){
            return true;
        }else{
            return false;
        }
    }

    public boolean validateDate(String date){
        if(DATE_PATTERN.matcher(date).matches()){
            return true;
        }else{
            return false;
        }
    }
}
