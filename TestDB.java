import java.io.IOException;
import java.util.Scanner;

//-----------------------------------------------------
// Example code to read from fixed length records (random access file)
//-----------------------------------------------------

public class TestDB
{
  static Record record;

  public static void main(String[] args) throws IOException
  {

    //Calls constructor
    DB db = new DB();

    //Creates database from csv file 
    //db.createDB("Fortune500"); // creates Fortune500_small.data

    //Opens "Fortune500_small.data"
    //db.open("Fortune500");

    //Making the database start as closed
    //boolean dbOpen = false;

    System.out.println("------------- Testing readRecord ------------");

    // Reads record 0
    // Then prints the values of the 5 fields to the screen with the name of the
    // field and the values read from the record, i.e.,
    // id: 00003 experience: 3 married: no wages: 1.344461678 industry:
    // Business_and_Repair_Service

    //for part 1, hardcoded which records to read for testing
    
    
    // int record_num = 0;
    // record = db.readRecord(record_num);
    // if (!record.isEmpty())
    //   System.out.println("RecordNum " + record_num + ": " + record.toString() + "\n\n");
    // else {
    //   System.out.println("Could not get Record " + record_num);
    //   System.out.println("Record out of range");
    // }

    // // Reads record 9 (last record)
    // record_num = 9;
    // record = db.readRecord(record_num);
    // if (!record.isEmpty())
    //   System.out.println("RecordNum " + record_num + ": " + record.toString() + "\n\n");
    // else {
    //   System.out.println("Could not get Record " + record_num);
    //   System.out.println("Record out of range");
    // }

    // // // Reads record 5 (middle record)
    // record_num = 5;
    // record = db.readRecord(record_num);
    // if (!record.isEmpty())
    //   System.out.println("RecordNum " + record_num + ": " + record.toString() + "\n\n");
    // else {
    //   System.out.println("Could not get Record " + record_num);
    //   System.out.println("Record out of range");
    // }

    // // // Reads record -1 (out of range)
    // record_num = -1;
    // record = db.readRecord(record_num);
    // if (!record.isEmpty())
    //   System.out.println("RecordNum " + record_num + ": " + record.toString() + "\n\n");
    // else {
    //   System.out.println("Could not get Record " + record_num);
    //   System.out.println("Record out of range");
    // }

    // // // Reads record 1000 (out of range)
    // record_num = 1000;
    // record = db.readRecord(record_num);
    // if (!record.isEmpty())
    //   System.out.println("RecordNum " + record_num + ": " + record.toString() + "\n\n");
    // else {
    //   System.out.println("Could not get Record " + record_num);
    //   System.out.println("Record out of range");
    // }

    // System.out.println("\n\n" + "------------- Testing overwriteRecord ------------");

    // // Overwrite the previously read middle record
    // record_num = DB.NUM_RECORDS/2;
    // db.updateRecord(record_num,"AES", "200", "ARLING", "VU", "22203", "19000");  // added new values fitting the new data

    // // Rereads record 5 (middle record) to show that it has been overwritten
    // record_num = DB.NUM_RECORDS/2;
    // record = db.readRecord(record_num);
    // if (!record.isEmpty())
    //   System.out.println("RecordNum " + record_num + ": " + record.toString() + "\n\n");
    // else {
    //   System.out.println("Could not get Record " + record_num);
    //   System.out.println("Record out of range");
    // }

    // System.out.println("------------- Testing binarySearch ------------");

    // // Find record with name 42 (should not be found)// Find record 17
    // String NAME = "3M";
    // record_num = db.findRecord(NAME);
    // if (record_num != -1) {
    //   record = db.readRecord(record_num);
    //   System.out
    //       .println(
    //           "NAME " + NAME + " found at Record " + record_num + "\nRecordNum " + record_num + ": \n" + record.toString()
    //               + "\n\n");
    // } else
    //   System.out.println("NAME " + NAME + " not found in our records\n\n");

    // // Find record with name 00000 (the first one in the file)
    // NAME = "TESLA";
    // record_num = db.findRecord(NAME);
    // if (record_num != -1) {
    //   record = db.readRecord(record_num);
    //   System.out
    //       .println(
    //           "NAME " + NAME + " found at Record " + record_num + "\nRecordNum " + record_num + ": \n" + record.toString()
    //               + "\n\n");
    // } else
    //   System.out.println("NAME " + NAME + " not found in our records\n\n");

    // // Find record with name 00015 (the last one in the file)
    // NAME = "ZIMMER BIOMET HOLDINGS";
    // record_num = db.findRecord(NAME);
    // if (record_num != -1) {
    //   record = db.readRecord(record_num);
    //   System.out
    //       .println(
    //           "NAME " + NAME + " found at Record " + record_num + "\nRecordNum " + record_num + ": \n" + record.toString()
    //               + "\n\n");
    // } else
    //   System.out.println("NAME " + NAME + " not found in our records\n\n");

    // // Find record with name 00006 (somewhere in the middle)
    // NAME = "WESTROCK";
    // record_num = db.findRecord(NAME);
    // if (record_num != -1) {
    //   record = db.readRecord(record_num);
    //   System.out
    //       .println(
    //           "NAME " + NAME + " found at Record " + record_num + "\nRecordNum " + record_num + ": \n" + record.toString()
    //               + "\n\n");
    // } else
    //   System.out.println("NAME " + NAME + " not found in our records\n\n");

    while(true)
    {
        //Allow user to search for a name
        Scanner scanner = new Scanner(System.in);
        //System.out.println("------------- User Search ------------");
        System.out.println("MENU OF OPERATIONS:");
        System.out.println("1. Create a new database\n");
        System.out.println("2. Open a database\n");
        System.out.println("3. Close a database\n");
        System.out.println("4. Display a record\n");
        System.out.println("5. Update a record\n");
        System.out.println("6. Print a report\n");
        System.out.println("7. Add a record\n");
        System.out.println("8. Delete a record\n");
        System.out.println("9. Quit\n");
        System.out.println("Enter your choice (1-9): ");

        int choice;
        //String prefix;
        //String key;
        //int idx;
        //Error checking in case a non-number character is entered.
        while (!scanner.hasNextInt())
        {
            System.out.println("You need to enter a number.\n Enter the number that matches your desired option.");
            scanner.nextLine();
            continue;
        }
        choice = scanner.nextInt();
        scanner.nextLine();  

        try
        {
            
            switch (choice)
            {
                case 1: //Creating a new DB
                    System.out.println("What do you want to name your database?");
                    String prefix = scanner.nextLine().trim();
                    db.createDB(prefix);
                    //Creating does NOT necessarily mean "open"
                    System.out.println("Database files created for: " + prefix);
                    break;
                case 2: //Open the DB
                    if (db.isOpen()) {
                        System.out.println("Your database is already open. Close it first.");
                        break;
                    }
                    System.out.print("Input your database prefix to open it (ex: Fortune500): ");
                    prefix = scanner.nextLine().trim();
                    db.open(prefix);
                    //db.isOpen() = true;
                    if (db.open(prefix))
                        System.out.println("Opened database: " + prefix);
                    else
                        System.out.println("Failed to open database " + prefix);
                    break;

                case 3: //Closing the DB
                    if (!db.isOpen()) {
                        System.out.println("No database is currently open.");
                        break;
                    }
                    db.close();
                    //dbOpen = false;
                    System.out.println("Database closed.");
                    break;

                case 4: //Displaying record
                    if (!db.isOpen()) {
                        System.out.println("Open a database first.");
                        break;
                    }
                    System.out.print("Enter company name (primary key): ");
                    String key = scanner.nextLine();
                    db.displayRecord(key);/* 
                    int idx = db.findRecord(key);
                    if (idx == -1) {
                        System.out.println("Record not found.");
                    } else {
                        Record r = db.readRecord(idx);
                        System.out.println("Found at record #" + idx);
                        System.out.println("Name: " + r.Name);
                        System.out.println("Rank: " + r.Rank);
                        System.out.println("City: " + r.City);
                        System.out.println("State: " + r.State);
                        System.out.println("Zip: " + r.Zip);
                        System.out.println("Employees: " + r.Employees);
                    }*/
                    break;

                case 5: //Updating records
                    if (!db.isOpen()) {
                        System.out.println("Open a database first.");
                        break;
                    }
                    System.out.print("Enter company name (primary key): ");
                    key = scanner.nextLine();
                    db.displayRecord(key);/*/
                    idx = db.findRecord(key);
                    if (idx == -1) {
                        System.out.println("Record not found.");
                        break;
                    }

                    Record old = db.readRecord(idx);
                    System.out.println("Current values:");
                    System.out.println("Name: " + old.Name);
                    System.out.println("Rank: " + old.Rank);
                    System.out.println("City: " + old.City);
                    System.out.println("State: " + old.State);
                    System.out.println("Zip: " + old.Zip);
                    System.out.println("Employees: " + old.Employees);*/

                    System.out.println("\nEnter new values (leave blank to keep existing):");
                    System.out.print("Rank: ");
                    String rank = scanner.nextLine();
                    System.out.print("City: ");
                    String city = scanner.nextLine();
                    System.out.print("State: ");
                    String state = scanner.nextLine();
                    System.out.print("Zip: ");
                    String zip = scanner.nextLine();
                    System.out.print("Employees: ");
                    String employees = scanner.nextLine();

                    boolean ok = db.updateRecordByName(key, rank, city, state, zip, employees);
                    System.out.println(ok ? "Record updated." : "Update failed.");
                    break;

                case 6: //Printing out a report
                    if (!db.isOpen()) {
                        System.out.println("Open a database first.");
                        break;
                    }
                    db.printReport();
                    break;

                case 7: //Adding a record
                    if (!db.isOpen()) {
                        System.out.println("Open a database first.");
                        break;
                    }
                    System.out.println("Enter values for new record:");
                    System.out.print("Name (primary key): ");
                    String name = scanner.nextLine();
                    System.out.print("Rank: ");
                    rank = scanner.nextLine();
                    System.out.print("City: ");
                    city = scanner.nextLine();
                    System.out.print("State: ");
                    state = scanner.nextLine();
                    System.out.print("Zip: ");
                    zip = scanner.nextLine();
                    System.out.print("Employees: ");
                    employees = scanner.nextLine();

                    ok = db.addRecord(name, rank, city, state, zip, employees);
                    System.out.println(ok ? "Record added (appended)."
                                        : "Add failed.");
                    break;

                case 8: //Deleting a record
                    if (!db.isOpen()) {
                        System.out.println("Open a database first.");
                        break;
                    }
                    System.out.print("Enter company name (primary key) to delete: ");
                    key = scanner.nextLine();
                    ok = db.deleteRecord(key);
                    System.out.println(ok ? "Record deleted (logically)." : "Delete failed / not found.");
                    break;

                case 9: //Quitting the database 
                    if (db.isOpen())
                        db.close();
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please pick 1-9.");
                }
            }
            catch (IOException e) {
                // If your DB methods throw IOException, this keeps the menu alive
                System.out.println("I/O error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            
            // System.out.println("Enter a NAME to Search: ");
            // String userInput = scanner.nextLine().toUpperCase();  // convert to uppercase to match database entries
            // record_num = db.findRecord(userInput);
            // if (record_num != -1) {
            //   record = db.readRecord(record_num);
            //   System.out
            //       .println(
            //           "NAME " + userInput + " found at Record " + record_num + "\nRecordNum " + record_num + ": \n" + record.toString()
            //               + "\n\n");
            // } else
            //   System.out.println("NAME " + userInput + " not found in our records\n\n");
            
            
            //scanner.close();
            // closes the database file
            //db.close();
        }
    }
}
