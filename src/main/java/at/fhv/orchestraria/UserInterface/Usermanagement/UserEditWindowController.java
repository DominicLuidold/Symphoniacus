package at.fhv.orchestraria.UserInterface.Usermanagement;

import at.fhv.orchestraria.application.FormValidator;
import at.fhv.orchestraria.application.UserManagementController;
import at.fhv.orchestraria.domain.Imodel.IMusicianRole;
import at.fhv.teamb.symphoniacus.application.type.AdministrativeAssistantType;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IAdministrativeAssistantEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianRoleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IUserEntity;
import at.fhv.teamb.symphoniacus.presentation.TabPaneController;
import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;
import at.fhv.teamb.symphoniacus.presentation.internal.TabPaneEntry;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class UserEditWindowController implements Parentable<TabPaneController> {
    private IUserEntity userToEdit;
    private Collection<IMusicianRole> allMusicianRoles;
    private Collection<IInstrumentCategoryEntity> allInstrumentCategories;
    private int listviewindex;
    private UserTableWindowController parentTreeTable;
    private boolean isNewUser;
    boolean validateForm = true;
    private final static Logger LOGGER = Logger.getLogger(UserEditWindowController.class.getName());
    private UserManagementController uManagementController;

    private TabPaneController parentController;

    @FXML
    private Label _user1;

    @FXML
    private Label _userLabel;

    @FXML
    private JFXTextField fnameField;

    @FXML
    private JFXTextField lnameField;

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXTextField phoneField;

    @FXML
    private JFXTextField countryField;

    @FXML
    private JFXTextField cityField;

    @FXML
    private JFXTextField zipField;

    @FXML
    private JFXTextField streetField;

    @FXML
    private JFXTextField streetNrField;

    @FXML
    private JFXToggleButton musicianAdministrativeToggle;

    @FXML
    private VBox _musicianVBox;

    @FXML
    private MenuButton roleDropDown;

    @FXML
    private MenuButton instrumentDropDown;

    @FXML
    private JFXDatePicker contractStartPicker;

    @FXML
    private JFXDatePicker contractEndPicker;

    @FXML
    private JFXTextField pointsPerMonthField;

    @FXML
    private JFXComboBox<String> sectionDropDown;

    @FXML
    private JFXTextField specialField;

    @FXML
    private VBox _administrativeVBox;

    @FXML
    private JFXComboBox<String> adminRoleDropDown;

    @FXML
    void maatoggle(ActionEvent event) {
        if (_musicianVBox.isVisible()) {
            _musicianVBox.setVisible(false);
            _administrativeVBox.setVisible(true);
        } else {
            _musicianVBox.setVisible(true);
            _administrativeVBox.setVisible(false);
        }
    }


    /**
     * init initialize the UI with all the used content and also
     * set the propmt texts of the textfields and adds the Change Listeners
     * when editing a user it also fills the existing fields
     */
    public void init() {
        _musicianVBox.setVisible(true);
        _administrativeVBox.setVisible(false);
        setPromtTexts();
        setInputChangeListener();

        if (isNewUser) {
            fillFields();
            fillFieldsNew();
        } else {
            fillFields();
            fillFieldsExisting();
        }
    }

    private void fillFieldsNew() {
        _userLabel.setText("");
        _user1.setText("Create new user:");
    }

    private void fillFieldsExisting() {
        _userLabel.setText(userToEdit.getLastName() + " " + userToEdit.getFirstName() + " (" +
            userToEdit.getShortcut() + ")");
        fnameField.setText(userToEdit.getFirstName());
        lnameField.setText(userToEdit.getLastName());
        emailField.setText(userToEdit.getEmail());
        phoneField.setText(userToEdit.getPhone());
        countryField.setText(userToEdit.getCountry());
        cityField.setText(userToEdit.getCity());
        zipField.setText(userToEdit.getZipCode());
        streetField.setText(userToEdit.getStreet());
        streetNrField.setText(userToEdit.getStreetNumber());

        if (userToEdit.getMusician() != null) {
            _musicianVBox.setVisible(true);
            _administrativeVBox.setVisible(false);
            musicianAdministrativeToggle.setSelected(false);

            for (IMusicianRoleEntity mrme : userToEdit
                .getMusician().getMusicianRoles()) {
                for (MenuItem mi : roleDropDown.getItems()) {
                    CustomMenuItem item = (CustomMenuItem) mi;
                    CheckBox cb = (CheckBox) item.getContent();
                    if (mrme.getDescription().toString().compareToIgnoreCase(cb.getText()) == 0) {
                        cb.setSelected(true);
                    }
                }
            }
            for (IInstrumentCategoryEntity icme : userToEdit.getMusician()
                .getInstrumentCategories()) {
                for (MenuItem mi : instrumentDropDown.getItems()) {
                    CustomMenuItem item = (CustomMenuItem) mi;
                    CheckBox cb = (CheckBox) item.getContent();
                    if (icme.getDescription().compareToIgnoreCase(cb.getText()) == 0) {
                        cb.setSelected(true);
                    }
                }
            }
            for (IContractualObligationEntity coe : userToEdit.getMusician()
                .getContractualObligations()) {
                contractEndPicker.setValue(coe.getEndDate());
                pointsPerMonthField.setText(Integer.toString(coe.getPointsPerMonth()));
                specialField.setText(coe.getPosition());
                contractStartPicker.setValue(coe.getStartDate());
            }
            sectionDropDown.setValue(userToEdit.getMusician().getSection().getDescription());
        } else {
            musicianAdministrativeToggle.setSelected(true);
            _administrativeVBox.setVisible(true);
            _musicianVBox.setVisible(false);
            for (IAdministrativeAssistantEntity ass : userToEdit.getAdministrativeAssistants()) {
                adminRoleDropDown.getItems().add(ass.getDescription().toString());
            }

        }
        setMenuButtonPrompt(roleDropDown);
        setMenuButtonPrompt(instrumentDropDown);
        musicianAdministrativeToggle.setDisable(true);
    }

    private void fillFields() {
        ObservableList<String> adminRoles = FXCollections
            .observableArrayList(AdministrativeAssistantType.ORGANIZATIONAL_OFFICER.toString(),
                AdministrativeAssistantType.ORCHESTRA_LIBRARIAN.toString(),
                AdministrativeAssistantType.MUSIC_LIBRARIAN.toString());
        adminRoleDropDown.setItems(adminRoles);
        for (ISectionEntity se : uManagementController.getISections()) {
            sectionDropDown.getItems().addAll(se.getDescription());
        }
        List<CustomMenuItem> musicianRoles = FXCollections.observableArrayList();
        for (IMusicianRole mre : allMusicianRoles) {
            CheckBox cBox;
            if (mre != null && mre.getDescription() != null) {
                cBox = new CheckBox(mre.getDescription());
            } else {
                cBox = new CheckBox("-");
            }
            cBox.focusedProperty().addListener(((observable, oldValue, newValue) -> {
                setMenuButtonPrompt(roleDropDown);
            }));
            CustomMenuItem cm = new CustomMenuItem(cBox);
            cm.setHideOnClick(false);
            musicianRoles.add(cm);
        }
        roleDropDown.getItems().addAll(musicianRoles);
        List<CustomMenuItem> instrumentCategories = FXCollections.observableArrayList();
        for (IInstrumentCategoryEntity ice : allInstrumentCategories) {
            CheckBox cBox = new CheckBox(ice.getDescription());
            cBox.focusedProperty().addListener(((observable, oldValue, newValue) -> {
                setMenuButtonPrompt(instrumentDropDown);
            }));
            CustomMenuItem cm = new CustomMenuItem(cBox);
            cm.setHideOnClick(false);
            instrumentCategories.add(cm);
        }
        instrumentDropDown.getItems().addAll(instrumentCategories);
    }

    public void setParameter(IUserEntity ue, int listindex,
                             UserTableWindowController _parentTreeTable, boolean _newUser) {
        tearDown();
        uManagementController =
            new UserManagementController(); //TODO: Verletzung der SchichtenRegeln: UI kennt nicht persistenz
        userToEdit = ue;
        listviewindex = listindex;
        allInstrumentCategories = uManagementController.getIInstrumentCategory();
        allMusicianRoles = uManagementController.getIMusicianRole();
        parentTreeTable = _parentTreeTable;
        isNewUser = _newUser;
    }

    /**
     * Method for Integration.
     */
    public void tearDown() {
        if (this.allMusicianRoles != null && this.allInstrumentCategories != null) {
            this.allMusicianRoles = new LinkedList<>();
            this.allInstrumentCategories = new LinkedList<>();
        }
        this.sectionDropDown.getItems().removeAll(this.sectionDropDown.getItems());
        this.adminRoleDropDown.getItems().removeAll(this.adminRoleDropDown.getItems());
        this.instrumentDropDown.getItems().removeAll(this.instrumentDropDown.getItems());
        this.roleDropDown.getItems().removeAll(this.roleDropDown.getItems());

        fnameField.clear();
        lnameField.clear();
        emailField.clear();
        phoneField.clear();
        countryField.clear();
        cityField.clear();
        zipField.clear();
        streetField.clear();
        streetNrField.clear();
        contractStartPicker.setValue(null);
        contractEndPicker.setValue(null);
        pointsPerMonthField.clear();
        musicianAdministrativeToggle.setDisable(false);
    }

    /**
     * setting the predefined promptextes to
     */
    private void setPromtTexts() {
        fnameField.setPromptText("Enter the firstname");
        lnameField.setPromptText("Enter the lastname");
        emailField.setPromptText("Enter the e-mail");
        phoneField.setPromptText("Enter the phone number");
        countryField.setPromptText("Enter the name of the country");
        cityField.setPromptText("Enter the name of the city");
        zipField.setPromptText("Enter the zip code of the city");
        streetField.setPromptText("Enter the street name");
        streetNrField.setPromptText("Enter the number of the street");
        roleDropDown.setText("Select one or many");
        sectionDropDown.setPromptText("Choose one section");
        instrumentDropDown.setText("Select one or many");
        pointsPerMonthField.setPromptText("Enter the needed points per month");
        specialField.setPromptText("Optional");
        contractStartPicker.setPromptText("MM/DD/YYYY");
        contractEndPicker.setPromptText("MM/DD/YYYY");
    }

    /**
     * refreshes the PromptText of a Menu Button with the
     * selected checkboxes in it
     *
     * @param menu
     */
    public void setMenuButtonPrompt(MenuButton menu) {
        menu.setText("");
        for (MenuItem mi : menu.getItems()) {
            CustomMenuItem item = (CustomMenuItem) mi;
            CheckBox cb = (CheckBox) item.getContent();
            if (cb.isSelected()) {
                menu.setText(menu.getText() + " " + cb.getText());
            }
        }
    }

    /**
     * this method sets a changelistener on all the textfield on the form
     * and adds a failure message to them if they got no new input
     */
    public void setInputChangeListener() {
        // FormValidator checks the TextFieldInput with Regular Expressions
        FormValidator formValidator = new FormValidator();
        // RequiredFieldValidator checks if there is any Input in the TextField
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("No Input Given");

        // Override ValidatorBase for email and phone TextFields
        ValidatorBase emailValidator = new ValidatorBase() {
            @Override
            protected void eval() {
                evalEmail();
            }

            private void evalEmail() {
                if (formValidator.validateEmail(emailField.getText())) {
                    hasErrors.set(false);
                } else {
                    hasErrors.set(true);
                }
            }
        };

        ValidatorBase phoneValidator = new ValidatorBase() {
            @Override
            protected void eval() {
                evalPhone();
            }

            private void evalPhone() {
                if (formValidator.validatePhone(phoneField.getText())) {
                    hasErrors.set(false);
                } else {
                    hasErrors.set(true);
                }
            }
        };

        ObservableList<JFXTextField> textFieldList = FXCollections.<JFXTextField>observableArrayList
            (fnameField, lnameField, emailField, phoneField, countryField, cityField, zipField,
                streetField,
                streetNrField, pointsPerMonthField);

        for (JFXTextField textField : textFieldList) {
            textField.getValidators().add(validator);
            textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    validator.setMessage("No Input Given");
                    textField.validate();
                }
            });
            if (textField == emailField) {
                textField.getValidators().add(emailValidator);
                textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue) {
                        emailValidator.setMessage("Not a valid email");
                        textField.validate();
                    }
                });
            }
            if (textField == phoneField) {
                textField.getValidators().add(phoneValidator);
                textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue) {
                        phoneValidator.setMessage("Not a valid phone number");
                        textField.validate();
                    }
                });
            }
        }
    }

    /**
     * Checks if all mandatory Text-fields are filled out
     */
    public void checkGivenInput() {
        ObservableList<JFXTextField> textFieldList = FXCollections.<JFXTextField>observableArrayList
            (fnameField, lnameField, emailField, phoneField, countryField, cityField, zipField,
                streetField,
                streetNrField, pointsPerMonthField);

        for (JFXTextField textField : textFieldList) {
            textField.validate();
        }
    }

    /**
     * validate all the TextFields with Regular Expressions
     * TextField get checked by Regular Expressions
     * of our implemented FormValidator Class
     */
    public void validateRegex() {
        FormValidator formValidator = new FormValidator();
        ArrayList<String> alerts = new ArrayList<String>();
        validateForm = true;

        if (!formValidator.validateFname(fnameField.getText())) {
            alerts.add("- First Name should start with capital letter\n\n");
            setIsNotValidatedColor(fnameField);
            validateForm = false;
        } else {
            setIsValidatedColor(fnameField);
        }
        if (!formValidator.validateLname(lnameField.getText())) {
            alerts.add("- Last Name should start with capital letter\n\n");
            setIsNotValidatedColor(lnameField);
            validateForm = false;
        } else {
            setIsValidatedColor(lnameField);
        }
        if (!formValidator.validateEmail(emailField.getText())) {
            alerts.add("- The given E-Mail is not valid\n\n");
            setIsNotValidatedColor(emailField);
            validateForm = false;
        } else {
            setIsValidatedColor(emailField);
        }
        if (!formValidator.validatePhone(phoneField.getText())) {
            alerts.add("- Phone number is not in correct syntax\n" +
                "  Please start with prefix '+'\n\n");
            setIsNotValidatedColor(phoneField);
            validateForm = false;
        } else {
            setIsValidatedColor(phoneField);
        }
        if (!formValidator.validateCountry(countryField.getText())) {
            alerts.add("- Please check if the Country Name is correct: no digits!\n\n");
            setIsNotValidatedColor(countryField);
            validateForm = false;
        } else {
            setIsValidatedColor(countryField);
        }
        if (!formValidator.validateCity(cityField.getText())) {
            alerts.add("- Please check if the City Name is correct: no digits!\n\n");
            setIsNotValidatedColor(cityField);
            validateForm = false;
        } else {
            setIsValidatedColor(cityField);
        }
        if (!formValidator.validateZip(zipField.getText())) {
            alerts.add("- Please check the Zip-Code: only digits\n\n");
            setIsNotValidatedColor(zipField);
            validateForm = false;
        } else {
            setIsValidatedColor(zipField);
        }
        if (!formValidator.validateStreet(streetField.getText())) {
            alerts.add("- Please check if the Street Name is correct: no digits!\n\n");
            setIsNotValidatedColor(streetField);
            validateForm = false;
        } else {
            setIsValidatedColor(streetField);
        }
        if (!formValidator.validateStreetNr(streetNrField.getText())) {
            alerts.add("- Please check if the Street number is correct: only digits!\n\n");
            setIsNotValidatedColor(streetNrField);
            validateForm = false;
        } else {
            setIsValidatedColor(streetNrField);
        }
        if (!musicianAdministrativeToggle.isSelected()) {
            boolean cbSelect = false;
            for (MenuItem mi : roleDropDown.getItems()) {
                CustomMenuItem cmi = (CustomMenuItem) mi;
                CheckBox checkbox = (CheckBox) cmi.getContent();
                if (checkbox.isSelected()) {
                    cbSelect = true;
                    roleDropDown.setStyle("-fx-border-color:green");
                }
            }
            if (!cbSelect) {
                validateForm = false;
                roleDropDown.setStyle("-fx-border-color:red");
                alerts.add("- Please select a musician role!\n\n");
            }

            if (sectionDropDown.getSelectionModel().isEmpty()) {
                alerts.add("- Please select a section!\n\n");
                sectionDropDown.setFocusColor(Color.RED);
                sectionDropDown.setUnFocusColor(Color.RED);
                validateForm = false;
            } else {
                sectionDropDown.setFocusColor(Color.GREEN);
                sectionDropDown.setUnFocusColor(Color.GREEN);
            }
            cbSelect = false;
            for (MenuItem mi : instrumentDropDown.getItems()) {
                CustomMenuItem cmi = (CustomMenuItem) mi;
                CheckBox checkbox = (CheckBox) cmi.getContent();
                if (checkbox.isSelected()) {
                    cbSelect = true;
                    instrumentDropDown.setStyle("-fx-border-color:green");
                }
            }
            if (!cbSelect) {
                validateForm = false;
                instrumentDropDown.setStyle("-fx-border-color:red");
                alerts.add("- Please select an instrument!\n\n");
            }

            if (!formValidator.validateDate(contractStartPicker.getEditor().getText())) {
                alerts.add("- Please select contract Start Date!\n\n");
                contractStartPicker.setDefaultColor(Color.RED);
                validateForm = false;
            } else {
                contractStartPicker.setDefaultColor(Color.GREEN);
            }

            if (!formValidator.validateDate(contractEndPicker.getEditor().getText())) {
                alerts.add("- Please select contract End Date!\n\n");
                contractEndPicker.setDefaultColor(Color.RED);
                validateForm = false;
            } else {
                contractEndPicker.setDefaultColor(Color.GREEN);
            }

            if (!formValidator.validatePoints(pointsPerMonthField.getText())) {
                alerts.add("- Please specify the needed points: only digits!\n\n");
                setIsNotValidatedColor(pointsPerMonthField);
                validateForm = false;
            } else {
                pointsPerMonthField.setFocusColor(Color.GREEN);
                pointsPerMonthField.setUnFocusColor(Color.GREEN);
            }
        }
        if (musicianAdministrativeToggle.isSelected()) {
            if (adminRoleDropDown.getSelectionModel().isEmpty()) {
                alerts.add("- Please select an Administrative Role!\n\n");
                adminRoleDropDown.setFocusColor(Color.RED);
                adminRoleDropDown.setUnFocusColor(Color.RED);
                validateForm = false;
            } else {
                adminRoleDropDown.setFocusColor(Color.GREEN);
                adminRoleDropDown.setUnFocusColor(Color.GREEN);
            }
        }

        if (!validateForm) {
            Alert warningAlert = new Alert(Alert.AlertType.ERROR);
            warningAlert.setTitle("Error Dialog");
            warningAlert.setHeaderText("Errors appeared, you can't save");
            StringBuilder sb = new StringBuilder();
            for (String alert : alerts) {
                sb.append(alert);
            }
            warningAlert.setContentText(sb.toString());
            warningAlert.showAndWait();
        } else {
            setIsValidatedColor(fnameField);
        }
    }


    /**
     * saveMusiciansAssignment validates the form and checks if its a new or
     * existing user and then saves in to the database
     *
     * @param actionEvent is an event which happen by clicking on the save button
     */
    @FXML
    public void saveMusiciansAssignment(javafx.event.ActionEvent actionEvent) {
        checkGivenInput();
        validateRegex();

        if (validateForm) {
            saveGeneral();
            if (isNewUser) {
                parentTreeTable.insertNewRow(userToEdit);
            } else {
                parentTreeTable.updateRow(listviewindex, userToEdit);
            }

            this.parentController.removeTab(TabPaneEntry.USER_EDIT);
            this.parentController.selectTab(TabPaneEntry.USER_MANAGEMENT);

            /*
            Not Integrated by Team - B
            */
            /*
            Node source = (Node) actionEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
             */
        }
    }

    private void saveGeneral() {

        Collection<String> roles = new LinkedList<>();

        for (MenuItem mi : roleDropDown.getItems()) {
            CustomMenuItem item = (CustomMenuItem) mi;
            CheckBox cb = (CheckBox) item.getContent();
            if (cb.isSelected()) {
                roles.add(cb.getText());
            }
        }

        Collection<String> instruments = new LinkedList<>();

        for (MenuItem mi : instrumentDropDown.getItems()) {
            CustomMenuItem item = (CustomMenuItem) mi;
            CheckBox cb = (CheckBox) item.getContent();
            if (cb.isSelected()) {
                instruments.add(cb.getText());
            }
        }

        UserDTO userDTO =
            new UserDTO(isNewUser, !musicianAdministrativeToggle.isSelected(), fnameField.getText(),
                lnameField.getText(),
                emailField.getText(), phoneField.getText(), cityField.getText(), zipField.getText(),
                countryField.getText(),
                streetField.getText(), streetNrField.getText(), adminRoleDropDown.getValue(),
                sectionDropDown.getValue(), roles,
                instruments, pointsPerMonthField.getText(), specialField.getText(),
                contractStartPicker.getValue(), contractEndPicker.getValue());

        userToEdit = (IUserEntity) uManagementController.saveGeneral(userToEdit, userDTO);

    }


    private void setIsNotValidatedColor(JFXTextField tfield) {
        tfield.setFocusColor(Color.RED);
        tfield.setUnFocusColor(Color.RED);
    }

    private void setIsValidatedColor(JFXTextField tfield) {
        tfield.setFocusColor(Color.GREEN);
        tfield.setUnFocusColor(Color.GREEN);
    }

    /**
     * Sets this controller's parent controller.
     *
     * @param controller The controller to be set as parent
     */
    @Override
    public void setParentController(TabPaneController controller) {
        this.parentController = controller;
    }

    /**
     * Returns this controller's parent controller.
     *
     * @return Parent controller
     */
    @Override
    public TabPaneController getParentController() {
        return this.parentController;
    }

    /**
     * Calls the controller initialization AFTER the parent controller has been set by
     * {@link TabPaneController}.
     */
    @Override
    public void initializeWithParent() {
        //this.init();
    }
}