import java.io.IOException;
import java.io.RandomAccessFile;

public class DB
{
  public static final int name_w = 60, rank_w = 7, city_w = 29, state_w = 5, zip_w = 16, employees_w = 16;  //Widths of each field for clarity
  public static final int RECORD_SIZE = name_w + rank_w + city_w + state_w + zip_w + employees_w + 1; //Summed widths of all fields + newline
  public int numSortedRecords; //Total number of sorted records in the database
  public int numUnsortedRecords; //Total number of unsorted records in the database
  public int recordSize = RECORD_SIZE; //Size of each record in the database
  private int num_records;
  
  private RandomAccessFile Dinout;
  private String currentPrefix;


  //Altered constructor to fit new csv
  public DB()
  {
    this.Dinout = null;
    this.num_records = 0;
    this.numSortedRecords = 0;
    this.numUnsortedRecords = 0;
    this.recordSize = RECORD_SIZE;
    this.currentPrefix = null;
  }

  /**
   * Opens the file in read/write mode
   * 
   * @param prefix (e.g., Fortune500_small)
   * @return status true if operation successful
   */
  public boolean open(String prefix)
  {
    if (isOpen()){
      System.out.println("Your database is already open. Close it first.");
      return false;
    }
    //Set the number of records
    this.num_records = 0;
    this.numUnsortedRecords = 0;
    this.numSortedRecords = 0;
    //Open file in read/write mode
    try
    {
      RandomAccessFile cfg = new RandomAccessFile(prefix + ".config", "r");  //Read config file for number of records
      String line;
      while ((line = cfg.readLine()) != null) {
        line = line.trim();
        if (line.startsWith("numSortedRecords=")) { //Parse number of sorted records 
          this.numSortedRecords = Integer.parseInt(line.substring("numSortedRecords=".length())); // same for unsorted records and record size
        } else if (line.startsWith("numUnsortedRecords=")) {
          this.numUnsortedRecords = Integer.parseInt(line.substring("numUnsortedRecords=".length()));
        } else if (line.startsWith("recordSize=")) {
          this.recordSize = Integer.parseInt(line.substring("recordSize=".length()));
        }
      }
      if (recordSize != RECORD_SIZE) { //Check if record size in config file matches expected record size
        System.out.println("Record size mismatch.");
        cfg.close();
        return false;
      }
      cfg.close();
      } catch (IOException e) {
      System.out.println("Could not open config file " + prefix + ".config\n");
      //e.printStackTrace();
      return false;
      } 

      try {
        this.Dinout = new RandomAccessFile(prefix + ".data", "rw"); //Open data file in read/write mode
        this.currentPrefix = prefix;
        this.num_records = this.numSortedRecords + this.numUnsortedRecords;
        return true;
      } catch (IOException e) {
        System.out.println("Could not open data file " + prefix + ".data\n");
        //e.printStackTrace();
        this.Dinout = null;
        return false;
      }
    }
  

  private static String nn(String s) { //Helper function to prevent null pointer exceptions with empty user
    if (s == null)
      return "";
    else
      return s;
  }
  /** 
   * Writes the data to the location specified by file parameter
   *  
   */
  public void writeRecord(RandomAccessFile file, String Name, String Rank, String City, String State, String Zip, String Employees )
  { //Writes record to file with padding for uniform file reading

    //Prevent null pointer exceptions
    Name = nn(Name);
    Rank = nn(Rank);
    City = nn(City);
    State = nn(State);
    Zip = nn(Zip);
    Employees = nn(Employees);

    //Format input values to be put in record
    Name = String.format("%-" + name_w + "s", Name.length() > name_w ? Name.substring(0, name_w) : Name);
    Rank = String.format("%-" + rank_w + "s", Rank.length() > rank_w ? Rank.substring(0, rank_w) : Rank);
    City = String.format("%-" + city_w + "s", City.length() > city_w ? City.substring(0, city_w) : City);
    State = String.format("%-" + state_w + "s", State.length() > state_w ? State.substring(0, state_w) : State);
    Zip = String.format("%-" + zip_w + "s", Zip.length() > zip_w ? Zip.substring(0, zip_w) : Zip);
    Employees = String.format("%-" + employees_w + "s", Employees.length() > employees_w ? Employees.substring(0, employees_w) : Employees);
    try {
      file.writeBytes(Name + Rank + City + State + Zip + Employees+"\n");
    } catch (IOException e) {
      //e.printStackTrace();
    }  
  }
  
  /**Takes in record number of record to be changed as well as what values to overwrite with 
   * 
   *  
   */ 
  public void overwriteRecord(int record_num, String Name, String Rank, String City, String State, String Zip, String Employees) {
    if (Dinout == null) {
      System.out.println("Database file is not open. Cannot overwrite record.\n");
      return;
    }
    if ((record_num >= 0) && (record_num < this.num_records))
    {
      try {
        Dinout.seek(0); //Return to the top of the file
        Dinout.skipBytes(record_num * recordSize);
	      //Overwrite the specified record
        writeRecord (Dinout, Name, Rank, City, State, Zip, Employees);
        System.out.println("Record Successfully Overwritten");
      } catch (IOException e) {
        System.out.println("There was an error while attempting to overwrite a record from the database file.\n");
        //e.printStackTrace();
      }
    }
  }

  /** Opens CSV file and creates new data file
   * Reads CSV file attributes
   * @throws IOException 
   */
  public void createDB(String prefix) throws IOException
  {
    RandomAccessFile Din = new RandomAccessFile(prefix+".csv", "r");
    RandomAccessFile Dout = new RandomAccessFile(prefix+".data","rw");
    Dout.setLength(0); // clear file if it already exists 
    currentPrefix = prefix;
    String line;
    int count = 0;
    this.recordSize = RECORD_SIZE;
    
    while ((line = Din.readLine()) != null)
    {
      String[] attribute = line.split(",", -1); //-1 to include trailing empty strings
      if (attribute.length != 6) {
         System.out.println("Malformed line at record " + count + ": " + line);
        continue;
      } //Skip malformed lines
      //Trim whitespace from each attribute
      for (int k = 0; k < 6; k++) {
          attribute[k] = attribute[k].replace("\r", "").trim();
      }
      writeRecord(Dout, attribute[0], attribute[1], attribute[2], attribute[3], attribute[4], attribute[5]); //Write record to data file
      count++; //Update record count
    }

    Din.close();
    Dout.close();
    numSortedRecords = count;
    numUnsortedRecords = 0;
    num_records = numSortedRecords + numUnsortedRecords;
    writeConfig(); //Write config file with record counts and record size

  }
 
  /**
   * Close the database file
   */
  public void close() {
    if (Dinout == null) // guard so it doesnt crash if open fails
      return;

    writeConfig();
    try {
      Dinout.close();
      Dinout = null;
      // reset variables
      currentPrefix = null;
      num_records = 0;
      numSortedRecords = 0;
      numUnsortedRecords = 0;
      recordSize = RECORD_SIZE;
    } catch (IOException e) {
      System.out.println("There was an error while attempting to close the database file.\n");
      //e.printStackTrace();
    }
  }

  /**
   * Get record number n (Records numbered from 0 to NUM_RECORDS-1)
   * 
   * @param record_num
   * @return values of the fields with the name of the field and
   *         the values read from the record
   */
  public Record readRecord(int record_num) {
    Record record = new Record();
    String[] fields;
    if (Dinout == null) return record;
    if ((record_num >= 0) && (record_num < this.num_records)) {
      try {
        Dinout.seek(0); // return to the top of the file
        Dinout.skipBytes(record_num * recordSize);
        // parse record and update fields
        String line = Dinout.readLine();
        if (line == null) {
          return record;
        }
        fields = new String[6];
        int i = 0;

        fields[0] = line.substring(i, i+=name_w).trim(); // Name
        fields[1] = line.substring(i, i+=rank_w).trim();  // Rank
        fields[2] = line.substring(i, i+=city_w).trim(); // City
        fields[3] = line.substring(i, i+=state_w).trim();  // State
        fields[4] = line.substring(i, i+=zip_w).trim(); // Zip
        fields[5] = line.substring(i, i+=employees_w).trim(); // Employees
        record.updateFields(fields);
      } catch (IOException e) {
        System.out.println("There was an error while attempting to read a record from the database file.\n");
        //e.printStackTrace();
      }
    }
    return record;
  }

  /**
   * Binary Search by record id
   * 
   * @param name
   * @return Record number (which can then be used by read to
   *         get the fields) or -1 if id not found
   */
  public int binarySearch(String name) {
    int Low = 0;
    int High = this.numSortedRecords - 1;
    int Middle = 0;
    boolean Found = false;
    Record record;

    while (!Found && (High >= Low)) {
      Middle = (Low + High) / 2;
      record = readRecord(Middle);
      String MiddleName = normName(record.Name);

      int result = MiddleName.compareTo(normName(name)); // DOES STRING COMPARE of MiddleName and name
      if (result == 0)
        Found = true;
      else if (result < 0)
        Low = Middle + 1;
      else
        High = Middle - 1;
    }
    if (Found) {
      return Middle; // the record number of the record
    } else
      return -1;
  }

  private String normName(String s) { // helper function since im calling name variable multiple tiems
    if (s == null)
      return "";
    else
      return s.trim().toUpperCase();
  }
  public int findRecord(String name) { //wrapper for binary search to check if file is open
    if (Dinout == null)
      return -1;

    String target = normName(name); // normalize the target name for consistent searching

    int idx = binarySearch(target); // first try to find the record in the sorted portion of the database
    if (idx != -1) {
      return idx;
    }

    for (int i = numSortedRecords; i < num_records; i++) { // if not found in sorted portion, search through unsorted portion of the database
      Record r = readRecord(i);
      if (!r.isEmpty() && normName(r.Name).equals(target)) 
        return i;
    }
    return -1;
  }

  public boolean updateRecordByName(String keyName, String Rank, String City, String State, String Zip, String Employees) { //wrapper for overwrite record to check if file is open
    if (Dinout == null) {
      return false;
    }
    int idx = findRecord(normName(keyName));
    if (idx == -1) {
      return false;
    }
    return updateRecord(idx, Rank, City, State, Zip, Employees);
  }

  public boolean updateRecord(int record_num, String Rank, String City, String State, String Zip, String Employees) { //wrapper for overwrite record to check if file is open
    if (Dinout == null) {
      return false;
    } else {
      Record old = readRecord(record_num);

      String NewRank = old.Rank;
      String NewCity = old.City;
      String NewState = old.State;
      String NewZip = old.Zip;
      String NewEmployees = old.Employees;

      // only update fields that are not null or empty
      if (Rank!=null && !Rank.trim().isEmpty()) NewRank = Rank.trim();
      if (City!=null && !City.trim().isEmpty()) NewCity = City.trim();
      if (State!=null && !State.trim().isEmpty()) NewState = State.trim();
      if (Zip!=null && !Zip.trim().isEmpty()) NewZip = Zip.trim();
      if (Employees!=null && !Employees.trim().isEmpty()) NewEmployees = Employees.trim();
      overwriteRecord(record_num, old.Name, NewRank, NewCity, NewState, NewZip, NewEmployees);
      return true;
    }
  }

  public boolean deleteRecord(String name) { //wrapper for overwrite record to check if file is open and delete the record by overwriting with empty values
    if (Dinout == null)
      return false;
    int record_num = findRecord(normName(name));
    if (record_num == -1)
      return false;
    Record old = readRecord(record_num);

    overwriteRecord(record_num, old.Name, "", "", "", "", ""); // overwrite with empty values to maintain record numbering
    return true;  
  }

  public boolean isOpen() { //Checks if the database file is open
    return (Dinout != null);
  }

  public void displayRecord(String name) //wrapper for read record to check if file is open and display the record in a user friendly way
  {
    int idx = findRecord(name);
    if (idx == -1) {
        System.out.println("Record not found.");
        return;
    }
    Record r = readRecord(idx);
    System.out.println("Name: " + r.Name);
    System.out.println("Rank: " + r.Rank);
    System.out.println("City: " + r.City);
    System.out.println("State: " + r.State);
    System.out.println("Zip: " + r.Zip);
    System.out.println("Employees: " + r.Employees);
  }

  public boolean addRecord(String Name, String Rank, String City, String State, String Zip, String Employees) { //wrapper for write record to check if file is open and update number of records
    if (Dinout == null) 
      return false;
    try {
      Dinout.seek(Dinout.length()); // move to end of file
      writeRecord (Dinout, Name, Rank, City, State, Zip, Employees); // write new record
      num_records++;
      numUnsortedRecords++; // update record counts
      writeConfig(); // update config file with new record counts
      return true;
    } catch (IOException e) {
      System.out.println("Error while adding a record to the database file.\n");
      //e.printStackTrace();
    }
    return false;
  }

  public void printReport() { //Prints the first 10 records in the database for testing purposes
    int limit = Math.min(10, num_records); 
    for (int i = 0; i < limit; i++) {
        Record r = readRecord(i);
        if (r.isEmpty()) continue;
        System.out.printf("%2d) %-" + name_w + "s %-" + rank_w + "s %-" + city_w + "s %-" +  // updated so that the fields are aligned when printed
                  state_w + "s %-" + zip_w + "s %-" + employees_w + "s%n",
                  i+1, r.Name, r.Rank, r.City, r.State, r.Zip, r.Employees);
    }
  }

  public void writeConfig() { //Writes the config file with the current number of sorted and unsorted records and record size
    if (currentPrefix == null)
      return;
    try {
      RandomAccessFile cfg = new RandomAccessFile(currentPrefix + ".config", "rw");
      cfg.setLength(0);
      cfg.writeBytes("numSortedRecords=" + numSortedRecords + "\n");
      cfg.writeBytes("numUnsortedRecords=" + numUnsortedRecords + "\n");
      cfg.writeBytes("recordSize=" + recordSize + "\n");
      cfg.close();
    } catch (IOException e) {
      System.out.println("Error while writing config to the database file.\n");
      //e.printStackTrace();
    }
  }
}
