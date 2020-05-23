package at.fhv.orchestraria.UserInterface.Login;

import at.fhv.orchestraria.UserInterface.MainWindow.MainWindow;
import at.fhv.orchestraria.UserInterface.Usermanagement.UserTable;
import at.fhv.orchestraria.application.PasswordManager;
import at.fhv.orchestraria.domain.Imodel.IUser;
import at.fhv.orchestraria.persistence.dao.JPADatabaseFacade;
import at.fhv.orchestraria.domain.integrationInterfaces.PasswordableUser;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javax.persistence.NoResultException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginWindowController {
    private final static Logger LOGGER = Logger.getLogger(LoginWindowController.class.getName());
    public LoginWindow _loginWindow;
    private static IUser _loggedInUser;
    private MediaPlayer _player;
    private static JPADatabaseFacade _facade;

    @FXML
    private CheckBox _checkboxKeepLoggedIn;

    @FXML
    private JFXTextField _txtUsername;

    @FXML
    private Label _txtSong;

    @FXML
    private JFXPasswordField _txtPassword;

    @FXML
    private Label _labelInvalidLogin;

    @FXML
    private JFXSlider _slider;

    @FXML
    private Button bttnPlay;

    @FXML
    private Button bttnPause;

    public void setMain(LoginWindow loginWindow) {
        _loginWindow = loginWindow;

        //load userData
        File userData = new File("nothingInterestingHere.png");
        if(userData.exists()){
            BufferedReader reader=null;
            try {
                reader = new BufferedReader(new FileReader(userData));
                _txtUsername.setText(new StringBuilder(reader.readLine()).reverse().toString());
                _txtPassword.setText(new StringBuilder(reader.readLine()).reverse().toString());

            } catch (IOException ex) {
                LOGGER.log(Level.INFO, "IOException ", ex);
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    LOGGER.log(Level.INFO, "IOException ", e);
                }
            }
        }

        //Preload Database Facade for reduced loading times later on.
        _facade = JPADatabaseFacade.getInstance();


        startMusic();

        if (_player != null) {
            _slider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable,
                                    Number oldValue, Number newValue) {
                    //Aufgrund von exponentieller Wahrnehmung von Lautstärke Verwendung einer exponentiellen Funktion
                    _player.setVolume(Math.pow(newValue.doubleValue(), 1.5) / 1000);
                }
            });
        }
    }


    /**
     * Selects a .wav file from the directory ./Music/ and plays it in the login screen.
     */
    public void startMusic() {
        File f = null;

        //Searches the ./Music/ directory for all wave files
        File dir = new File("Music");
        if (dir.isDirectory()) {
            File[] files = dir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.matches(".+\\.wav$");
                }
            });
            //Selects one piece of Music for the day
            f = files[LocalDate.now().getDayOfYear() % files.length];


            if (f != null && f.exists()) {
                _txtSong.setText(f.getPath().substring(6, f.getPath().length() - 4));
                try (AudioInputStream is = AudioSystem.getAudioInputStream(f)) {
                    Media hit = new Media(f.toURI().toString());
                    _player = new MediaPlayer(hit);
                    _player.play();
                    _player.setVolume(0.3535);

                    //Enable loop
                    _player.setStartTime(Duration.seconds(0));
                    _player.setStopTime(hit.getDuration());
                    _player.setCycleCount(MediaPlayer.INDEFINITE);
                } catch (FileNotFoundException | UnsupportedAudioFileException e) {
                    LOGGER.log(Level.INFO, "FileNotFound or unsupportedAudioFile ", e);
                } catch (IOException e) {
                    LOGGER.log(Level.INFO, "IOException ", e);
                }
            }
        }
    }

    /**
     * Plays Music
     *
     * @param event
     */
    public void playMusic(ActionEvent event) {
        _player.play();
        bttnPause.setVisible(true);
        bttnPlay.setVisible(false);
    }

    /**
     * Pauses Music
     *
     * @param event
     */
    public void pauseMusic(ActionEvent event) {
        _player.pause();
        bttnPlay.setVisible(true);
        bttnPause.setVisible(false);
    }

    /**
     * Closes the Window
     */
    public void handleClose() {
        _loginWindow.close();
    }

    /**
     * Checks the validate
     *
     * @param event
     */
    public void validateLogin(ActionEvent event) {
        try {
            Optional<PasswordableUser> user = PasswordManager.getUserIfValid(_txtUsername.getText(), _txtPassword.getText());
            if (user.isPresent()) {
                _loggedInUser = (IUser) user.get();
                _labelInvalidLogin.setVisible(false);
                safeUserData();
                pauseMusic(event);
                if (isAdministrativeAssistant()) {
                    changeScreenForAdministrativeAssistant(event);
                } else {
                    changeScreenForMusician(event);
                }
            } else {
                _labelInvalidLogin.setVisible(true);
                _checkboxKeepLoggedIn.setVisible(false);

            }

        } catch (NoResultException ex) {
            //Empty for Security-reasons
        }
    }



    public void changeScreenForAdministrativeAssistant(ActionEvent event) {
        UserTable ut = new UserTable();
        Stage undecoratedWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Stage decoratedWindow = new Stage();
        decoratedWindow.initStyle(StageStyle.DECORATED);
        Scene scene = undecoratedWindow.getScene();
        undecoratedWindow.setScene(null);
        decoratedWindow.setScene(scene);
        decoratedWindow.show();
        undecoratedWindow.hide();
        try {
            ut.start(decoratedWindow);
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Exception ", e);
            //TODO handle
        }
    }


    public void changeScreenForMusician(ActionEvent event) {
        try {
            MainWindow mw = new MainWindow();
            Stage undecoratedWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();

            //make next undecorated
            Stage decoratedWindow = new Stage();
            decoratedWindow.initStyle(StageStyle.DECORATED);
            Scene scene = undecoratedWindow.getScene();
            undecoratedWindow.setScene(null);
            decoratedWindow.setScene(scene);
            decoratedWindow.hide();
            undecoratedWindow.hide();
            mw.start(decoratedWindow);
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Exception at changeScreenForMusician ", e);
        }
    }

    /**
     * Checks if the loginuser is AdministrativeAssistant
     * @return
     */
    public boolean isAdministrativeAssistant() {
        //TODO FIX THIS FOR NOTENWART AND ORCHESTERWART
        return (!(_loggedInUser.getAdministrativeAssistant() == null));
    }

    /**
     * Saves the User of the login
     */
    private void safeUserData() {
        File file = new File("nothingInterestingHere.png");
        if (_checkboxKeepLoggedIn.isSelected()) {
            BufferedWriter buffWriter = null;
            try {
                buffWriter = new BufferedWriter(new FileWriter(file));
                buffWriter.write(new StringBuilder(_txtUsername.getText()).reverse().toString());
                buffWriter.newLine();
                buffWriter.write(new StringBuilder(_txtPassword.getText()).reverse().toString());
                buffWriter.flush();

            } catch (IOException ex) {
                LOGGER.log(Level.INFO, "IOException ", ex);
            } finally {
                try {
                    if (buffWriter != null) {
                        buffWriter.close();
                    }
                } catch (IOException e) {
                    LOGGER.log(Level.INFO, "IOException ", e);
                }

            }
        }
    }

    /**
     * Sets the User which was loged in to null
     */
    public static void logout() {
        _loggedInUser = null;
        _facade.closeSession();
    }

    public static IUser getLoggedInUser() {
        return _loggedInUser;
    }

    public JFXTextField getUsername() {
        return _txtUsername;
    }


    public void setUsername(JFXTextField username) {
        _txtUsername = username;
    }

    public JFXPasswordField getPassword() {
        return _txtPassword;
    }

    public void setPassword(JFXPasswordField password) {
        _txtPassword = password;
    }

    public JFXSlider getSlider() {
        return _slider;
    }

    public void setSlider(JFXSlider slider) {
        this._slider = slider;
    }
}
