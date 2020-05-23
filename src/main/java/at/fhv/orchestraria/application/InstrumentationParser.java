package at.fhv.orchestraria.application;

import at.fhv.orchestraria.domain.Imodel.ISectionInstrumentation;
import at.fhv.orchestraria.persistence.dao.JPADatabaseFacade;
import at.fhv.orchestraria.persistence.dao.DaoBase;
import at.fhv.orchestraria.domain.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class InstrumentationParser {

    /**
     * Main Method that deletes all insturmentationpositions and dutypositions from the DB and recreates them for all
     * duties
     *
     * @param args Normal params for main class
     */
    public static void main(String[] args) {
        InstrumentationParser ip = new InstrumentationParser();
        ip.createDutyPositionsAndInstrumentationPositions();
    }

    /**
     * Void Method, which deletes all DutyPositions and InstrumentationPositions from the DB and replaces them with
     * new ones for all the Duties currently in the DB
     */
    public void createDutyPositionsAndInstrumentationPositions() {
        DaoBase<DutyEntity> dutyDAO = JPADatabaseFacade.getInstance().getDAO(DutyEntity.class);
        InstrumentationParser parser = new InstrumentationParser();
        DaoBase<DutyPositionEntity> dutyPosDAO = JPADatabaseFacade.getInstance().getDAO(DutyPositionEntity.class);
        DaoBase<InstrumentationPositionEntity> instruPosDAO = JPADatabaseFacade.getInstance().getDAO(InstrumentationPositionEntity.class);
        for (DutyPositionEntity dutypos : dutyPosDAO.getAll()) {
            dutyPosDAO.delete(dutypos);
        }
        for (InstrumentationPositionEntity pos : instruPosDAO.getAll()) {
            instruPosDAO.delete(pos);
        }
        //TODO: FixMe
        for (DutyEntity duty : dutyDAO.getAll()) {
            if (duty.getSeriesOfPerformances() != null) {


                for (SeriesOfPerformancesInstrumentationEntity sopie : duty.getSeriesOfPerformances().getSeriesOfPerformancesInstrumentations()) {
                    for (SectionInstrumentationEntity sectionInstrumentationEntity : sopie.getInstrumentation().getSectionInstrumentations()){
                        createDutyPositionEntries(duty, sectionInstrumentationEntity);
                    }
                }
            }
        }
    }

    /**
     * Adds all the DutyPositionEntries to the DB for one Duty and one SectionInstrumentation
     *
     * @param de Duty, for which the DutyPositions should be created
     * @param se SectionInstrumentation, for which the DutyPositions should be created
     */
    public void createDutyPositionEntries(DutyEntity de, SectionInstrumentationEntity se) {

        Collection<InstrumentationPositionEntity> ipes = se.getInstrumentationPositions();

        if (ipes == null || ipes.isEmpty()) {

            LinkedList<InstrumentationPositionEntity> allInstrumentationPositions = createInstrumentationPositionEntries(se);

            for (InstrumentationPositionEntity ipe : allInstrumentationPositions) {
                DutyPositionEntity dpe = new DutyPositionEntity();

                dpe.setInstrumentationPosition(ipe);
                dpe.setDuty(de);
                dpe.setSection(se.getSection());

                if (ipe.getDutyPositions() == null) {
                    ipe.setDutyPositions(new LinkedList<>());
                }
                ipe.getDutyPositions().add(dpe);

                ipe.setSectionInstrumentation(se);

                se.getInstrumentationPositions().add(ipe);

                JPADatabaseFacade.getInstance().getDAO(DutyPositionEntity.class).save(dpe);
            }
        } else {
            for (InstrumentationPositionEntity ipe : ipes) {
                DutyPositionEntity dpe = new DutyPositionEntity();

                dpe.setInstrumentationPosition(ipe);
                dpe.setDuty(de);
                dpe.setSection(se.getSection());

                if (ipe.getDutyPositions() == null) {
                    ipe.setDutyPositions(new LinkedList<>());
                }
                ipe.getDutyPositions().add(dpe);

                JPADatabaseFacade.getInstance().getDAO(DutyPositionEntity.class).save(dpe);
            }
        }
    }

    /**
     * Adds the parsed InstrumentationPositionEntries to the DB and writes them in a List for further use in createDutyPositionEntries
     *
     * @param si Needed to add the sectionId to the newly made DB entries
     * @return returns a List with InstrumentationPositionEntities
     */
    public LinkedList<InstrumentationPositionEntity> createInstrumentationPositionEntries(SectionInstrumentationEntity si) {
        String[][] values = createInstrumentationPositions(si);
        String[] instruments = values[0];
        String[] numbersOfInstruments = values[1];
        LinkedList<InstrumentationPositionEntity> allAddedEntities = new LinkedList<>();
        String last = "default";
        int count = 1;
        for (int i = 0; i < instruments.length; i++) {
            String[] splits = instruments[i].split(":");
            if (last.compareToIgnoreCase(splits[0]) != 0) {
                count = 1;
            }
            for (int j = 0; j < Integer.parseInt(numbersOfInstruments[i]); j++) {
                InstrumentationPositionEntity e = new InstrumentationPositionEntity();

                e.setSectionInstrumentation(si);
                e.setInstrumentation(si.getInstrumentation());


                String description = splits[0] + " : " + count++ + "." + splits[1];
                last = splits[0];

                e.setPositionDescription(description);

                allAddedEntities.add(e);

                JPADatabaseFacade.getInstance().getDAO(InstrumentationPositionEntity.class).save(e);
            }
            if (instruments.length != 1) {
                if (i + 1 < instruments.length && splits[0].compareToIgnoreCase("Other") != 0
                        && splits[0].compareToIgnoreCase(instruments[i + 1].split(":")[0]) != 0
                        && Integer.parseInt(numbersOfInstruments[i]) != 0) {
                    allAddedEntities.add(createReplacementInstrumentationPosition(si, splits[0]));
                }
            } else {
                allAddedEntities.add(createReplacementInstrumentationPosition(si, splits[0]));
            }
        }
        return allAddedEntities;
    }

    /**
     * Creates the instrumentationpositions for the backup Musician.
     *
     * @param si   SectionInstrumentationEntity, from which the instrumantation is made
     * @param desc String containing the type of replacement, for example Fl if the replacement is for the flute subsection
     * @return returns the ready-to-process InstrumentationPositionEntity
     */
    private InstrumentationPositionEntity createReplacementInstrumentationPosition(SectionInstrumentationEntity si, String desc) {
        InstrumentationPositionEntity e = new InstrumentationPositionEntity();

        e.setSectionInstrumentation(si);
        e.setInstrumentation(si.getInstrumentation());

        String description = desc + " : Backup";

        e.setPositionDescription(description);

        JPADatabaseFacade.getInstance().getDAO(InstrumentationPositionEntity.class).save(e);
        return e;
    }


    /**
     * Method for parsing the different sections, together with how many instruments are needed.
     * Regex for predefined: 1-4 x: from x to x/x/x/x possible. () after every x and many additional instruments
     * seperated by +: x(2.asd)/x(2.asd)/x(2.asd)/x(2.asd) + asd + asd + asd ...;
     * these may have numbers too:x/x/x/x + 2(asd) + asd
     *
     * @param si The sectioninstrumentation which should be parsed
     * @return retruns a String[2][1] containting all the instruments on index 0 and how many of that instrument on index 1
     */
    public static String[][] createInstrumentationPositions(ISectionInstrumentation si) {
        String predefined = unifyInstrumentation(si.getPredefinedSectionInstrumentation());
        String[] sectionShortcuts = si.getSection().getSectionShortcut().split("/"); // gets all shortcuts and splits them into an array

        String[] instruments = predefined.split("/");
        String[] numberOfInstruments = new String[instruments.length];

        for (int i = 0; i < instruments.length; i++) {
            boolean hasNumber = false;
            if (instruments[i].toCharArray()[0] >= '0' && instruments[i].toCharArray()[0] <= '9') {
                hasNumber = true; //checks if the instrumentation starts with a number or not
            }

            String temp = instruments[i];
            String[] splittemp = new String[2];
            String[] tempsplit = temp.split("\\(");
            splittemp[0] = tempsplit[0];
            if (tempsplit.length >= 2) {
                splittemp[1] = tempsplit[1];
            }

            if (hasNumber) {
                numberOfInstruments[i] = splittemp[0].replaceAll("[^\\d]", ""); //removes all non numerics to avoid errors due to wrong DB entries
            } else {
                numberOfInstruments[i] = "1";
            }

            if (splittemp[1] == null && hasNumber) {
                if (i <= sectionShortcuts.length) {
                    instruments[i] = sectionShortcuts[i]; //for example 2
                } else {
                    instruments[i] = splittemp[0].replaceAll("[0-9]", "");  //FailSafe in case of wrongly written DB entry
                }
            } else if (splittemp[1] != null && hasNumber) {
                instruments[i] = splittemp[1];  //for example 3(3.Picc) and 2(Eh)
            } else {
                instruments[i] = splittemp[0]; //for example Bkl
            }
        }

        //This section deals with x(1.2.3.X)
        ArrayList<String> dynamicInstruments = new ArrayList<>();
        ArrayList<String> dynamicNumberOfInstruments = new ArrayList<>();
        for (int i = 0; i < instruments.length; i++) {
            if (i < sectionShortcuts.length) {
                String[] dotcheck = instruments[i].split("\\."); // 1.2.3.Picc -> {1,2,3,Picc} Fl -> {Fl}

                if (dotcheck.length > 1) { //if dots existed
                    dynamicInstruments.add(sectionShortcuts[i] + ":" + sectionShortcuts[i]);
                    dynamicNumberOfInstruments.add(Integer.toString(((Integer.parseInt(numberOfInstruments[i])) - (dotcheck.length - 1))));
                    dynamicInstruments.add(sectionShortcuts[i] + ":" + dotcheck[dotcheck.length - 1]);
                    dynamicNumberOfInstruments.add(Integer.toString(dotcheck.length - 1));
                } else {
                    dynamicInstruments.add(sectionShortcuts[i] + ":" + sectionShortcuts[i]);
                    dynamicNumberOfInstruments.add(numberOfInstruments[i]);
                }
            } else {
                dynamicInstruments.add("Other" + ":" + instruments[i]);
                dynamicNumberOfInstruments.add(numberOfInstruments[i]);
            }
        }

        String[][] returnme = new String[2][1];
        returnme[0] = dynamicInstruments.toArray(returnme[0]);
        returnme[1] = dynamicNumberOfInstruments.toArray(returnme[1]);

        return returnme;
    }

    /**
     * Takes an instrumentation String and removes the unnecessary clutter, and helps avoid inconsistencies caused
     * by wrong DB entries
     *
     * @param instrumentation String which contains a instrumentation description
     * @return returns the description in a unified format, for easier use in the parser
     */
    private static String unifyInstrumentation(String instrumentation) {
        instrumentation = instrumentation.replace(" ", ""); //removes spaces
        instrumentation = instrumentation.replace("+", "/"); //replaces + with /
        instrumentation = instrumentation.replace(")", ""); //removes ) for later convenience
        return instrumentation;
    }
}
