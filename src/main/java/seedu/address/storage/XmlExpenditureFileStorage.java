package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores expendituretracker data in an XML file
 */
public class XmlExpenditureFileStorage {
    /**
     * Saves the given expendituretracker data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableExpenditureTracker expenditureTracker)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, expenditureTracker);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns address book in the file or an empty address book
     */
    public static XmlSerializableExpenditureTracker loadDataFromSaveFile(Path file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableExpenditureTracker.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
