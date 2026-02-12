import java.io.IOException;
import java.util.Scanner;

//-----------------------------------------------------
// Example code to read from fixed length records (random access file)
//-----------------------------------------------------

public class TestDB
{

  public static void main(String[] args)
  {

    //Calls constructor
    DB db = new DB();
    Scanner scanner = new Scanner(System.in);

    System.out.println("------------- Testing readRecord ------------");
    while(true)
    {
        //Allow user to search for a name
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

        //Error checking in case a non-number character is entered.
        while (!scanner.hasNextInt()) {
            System.out.println("You need to enter a number.\n Enter the number that matches your desired option.");
            scanner.nextLine();
        }
        int choice = scanner.nextInt();
        scanner.nextLine();  

        try
        {
            switch (choice)
            {
                case 1: //Creating a new DB
                    System.out.println("What do you want to name your database?");
                    String prefix = scanner.nextLine().trim();
                    db.createDB(prefix);
                    System.out.println("Database files created for: " + prefix);
                    break;
                case 2: //Opening the DB
                    if (db.isOpen()) {
                        System.out.println("Your database is already open. Close it first.");
                        break;
                    }
                    System.out.print("Input your database prefix to open it (ex: Fortune500): ");
                    prefix = scanner.nextLine().trim();
                    boolean opened = db.open(prefix);
                    if (opened) {
                        System.out.println("Opened database: " + prefix);
                    } else {
                        System.out.println("Failed to open database " + prefix + ". Check that the files exist and the prefix is correct.");
                        System.out.println("You should try opening the pre-installed database, Fortune500.");
                    }
                    break;

                case 3: //Closing the DB
                    if (!db.isOpen()) {
                        System.out.println("No database is currently open.");
                        break;
                    }
                    db.close();
                    System.out.println("Database closed.");
                    break;

                case 4: //Displaying any record
                    if (!db.isOpen()) {
                        System.out.println("Open a database first.");
                        break;
                    }
                    System.out.print("Enter company name (primary key): ");
                    String key = scanner.nextLine();
                    db.displayRecord(key);
                    break;

                case 5: //Updating records
                    if (!db.isOpen()) {
                        System.out.println("Open a database first.");
                        break;
                    }
                    System.out.print("Enter company name (primary key): ");
                    key = scanner.nextLine();
                    db.displayRecord(key);

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
                    //Print statements to allow for the new values to be added
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
                    System.out.println(ok ? "Record added (appended)." : "Add failed.");
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
            // lists errors for input + output operations
            System.out.println("Input/Outputerror: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
}
