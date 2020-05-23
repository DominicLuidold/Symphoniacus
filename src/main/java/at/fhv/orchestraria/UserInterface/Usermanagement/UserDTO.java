package at.fhv.orchestraria.UserInterface.Usermanagement;

import at.fhv.orchestraria.domain.Imodel.IContractualObligation;
import at.fhv.orchestraria.domain.Imodel.IInstrumentCategoryMusician;
import at.fhv.orchestraria.domain.Imodel.IMusicianRoleMusician;

import java.time.LocalDate;
import java.util.Collection;

public class UserDTO {

    private boolean isNewUser;
    private boolean isMusician;
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String city;
    private String zipCode;
    private String country;
    private String street;
    private String streetNumber;
    private String adminRole;
    private String section;

    private Collection<String> selectedRoles;
    private Collection<IContractualObligation> contractualObligations;
    private Collection<IInstrumentCategoryMusician> instrumentCategoryMusicians;
    private Collection<IMusicianRoleMusician> musicianRoleMusicians;
    private Collection<String> selectedInstruments;
    private String pointsOfMonth;
    private String special;
    private LocalDate startDate;
    private LocalDate endDate;

    public UserDTO(boolean isNewUser, boolean isMusician, String firstName, String lastName,
                   String email, String phone, String city, String zipCode,
                   String country, String street, String streetNumber, String adminRole,
                   String section, Collection<String> selectedRoles,
                   Collection<String> selectedInstruments, String pointsOfMonth,
                   String special, LocalDate startDate, LocalDate endDate) {
        this.isNewUser = isNewUser;
        this.isMusician = isMusician;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
        this.street = street;
        this.streetNumber = streetNumber;
        this.adminRole = adminRole;
        this.section = section;
        this.selectedRoles = selectedRoles;
        this.selectedInstruments = selectedInstruments;
        this.pointsOfMonth = pointsOfMonth;
        this.special = special;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getSection() {
        return section;
    }

    public boolean isNewUser() {
        return isNewUser;
    }

    public String getAdminRole() {
        return adminRole;
    }

    public boolean isMusician() {
        return isMusician;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public Collection<String> getSelectedRoles() {
        return selectedRoles;
    }

    public Collection<String> getSelectedInstrumentCats() {
        return selectedInstruments;
    }

    public String getPointsOfMonth() {
        return pointsOfMonth;
    }

    public String getSpecial() {
        return special;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
