import java.io.IOException;
import java.util.Scanner;

//-----------------------------------------------------
// Example code to read from fixed length records (random access file)
//-----------------------------------------------------

public class TestDB {
  static Record record;

  public static void main(String[] args) throws IOException {

    // calls constructor
    DB db = new DB();

    // creates database from csv file 
    db.createDB("Fortune500"); // creates Fortune500_small.data

    // opens "Fortune500_small.data"
    db.open("Fortune500");

    System.out
        .println("------------- Testing readRecord ------------");

    // Reads record 0
    // Then prints the values of the 5 fields to the screen with the name of the
    // field and the values read from the record, i.e.,
    // id: 00003 experience: 3 married: no wages: 1.344461678 industry:
    // Business_and_Repair_Service

    // for part 1, hardcoded which records to read for testing
    
    int record_num = 0;
    record = db.readRecord(record_num);
    if (!record.isEmpty())
      System.out.println("RecordNum " + record_num + ": " + record.toString() + "\n\n");
    else {
      System.out.println("Could not get Record " + record_num);
      System.out.println("Record out of range");
    }

    // Reads record 9 (last record)
    record_num = 9;
    record = db.readRecord(record_num);
    if (!record.isEmpty())
      System.out.println("RecordNum " + record_num + ": " + record.toString() + "\n\n");
    else {
      System.out.println("Could not get Record " + record_num);
      System.out.println("Record out of range");
    }

    // // Reads record 5 (middle record)
    record_num = 5;
    record = db.readRecord(record_num);
    if (!record.isEmpty())
      System.out.println("RecordNum " + record_num + ": " + record.toString() + "\n\n");
    else {
      System.out.println("Could not get Record " + record_num);
      System.out.println("Record out of range");
    }

    // // Reads record -1 (out of range)
    record_num = -1;
    record = db.readRecord(record_num);
    if (!record.isEmpty())
      System.out.println("RecordNum " + record_num + ": " + record.toString() + "\n\n");
    else {
      System.out.println("Could not get Record " + record_num);
      System.out.println("Record out of range");
    }

    // // Reads record 1000 (out of range)
    record_num = 1000;
    record = db.readRecord(record_num);
    if (!record.isEmpty())
      System.out.println("RecordNum " + record_num + ": " + record.toString() + "\n\n");
    else {
      System.out.println("Could not get Record " + record_num);
      System.out.println("Record out of range");
    }

    System.out.println("\n\n" + "------------- Testing overwriteRecord ------------");

    // Overwrite the previously read middle record
    record_num = DB.NUM_RECORDS/2;
    db.updateRecord(record_num,"AES", "200", "ARLING", "VU", "22203", "19000");  // added new values fitting the new data

    // Rereads record 5 (middle record) to show that it has been overwritten
    record_num = DB.NUM_RECORDS/2;
    record = db.readRecord(record_num);
    if (!record.isEmpty())
      System.out.println("RecordNum " + record_num + ": " + record.toString() + "\n\n");
    else {
      System.out.println("Could not get Record " + record_num);
      System.out.println("Record out of range");
    }

    System.out.println("------------- Testing binarySearch ------------");

    // Find record with name 42 (should not be found)// Find record 17
    String NAME = "3M";
    record_num = db.findRecord(NAME);
    if (record_num != -1) {
      record = db.readRecord(record_num);
      System.out
          .println(
              "NAME " + NAME + " found at Record " + record_num + "\nRecordNum " + record_num + ": \n" + record.toString()
                  + "\n\n");
    } else
      System.out.println("NAME " + NAME + " not found in our records\n\n");

    // Find record with name 00000 (the first one in the file)
    NAME = "TESLA";
    record_num = db.findRecord(NAME);
    if (record_num != -1) {
      record = db.readRecord(record_num);
      System.out
          .println(
              "NAME " + NAME + " found at Record " + record_num + "\nRecordNum " + record_num + ": \n" + record.toString()
                  + "\n\n");
    } else
      System.out.println("NAME " + NAME + " not found in our records\n\n");

    // Find record with name 00015 (the last one in the file)
    NAME = "ZIMMER BIOMET HOLDINGS";
    record_num = db.findRecord(NAME);
    if (record_num != -1) {
      record = db.readRecord(record_num);
      System.out
          .println(
              "NAME " + NAME + " found at Record " + record_num + "\nRecordNum " + record_num + ": \n" + record.toString()
                  + "\n\n");
    } else
      System.out.println("NAME " + NAME + " not found in our records\n\n");

    // Find record with name 00006 (somewhere in the middle)
    NAME = "WESTROCK";
    record_num = db.findRecord(NAME);
    if (record_num != -1) {
      record = db.readRecord(record_num);
      System.out
          .println(
              "NAME " + NAME + " found at Record " + record_num + "\nRecordNum " + record_num + ": \n" + record.toString()
                  + "\n\n");
    } else
      System.out.println("NAME " + NAME + " not found in our records\n\n");

    // Allow user to search for a name
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter a NAME to Search: ");
    String userInput = scanner.nextLine().toUpperCase();  // convert to uppercase to match database entries
    record_num = db.findRecord(userInput);
    if (record_num != -1) {
      record = db.readRecord(record_num);
      System.out
          .println(
              "NAME " + userInput + " found at Record " + record_num + "\nRecordNum " + record_num + ": \n" + record.toString()
                  + "\n\n");
    } else
      System.out.println("NAME " + userInput + " not found in our records\n\n");
    
    
    scanner.close();
    // closes the database file
    db.close();
  }
}
