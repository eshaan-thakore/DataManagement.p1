import java.io.IOException;
import java.io.RandomAccessFile;

public class DB {
  public static final int NUM_RECORDS = 500; // total number of records in the database
  public static final int RECORD_SIZE = 93; // summed widths of all fields + newline (40 + 5 + 25 + 2 + 10 + 10 +1=93)
  public static final int name_w = 40, rank_w = 5, city_w = 25, state_w = 2, zip_w = 10, employees_w = 10;  // widths of each field for clarity

  private RandomAccessFile Dinout;
  private int num_records;

  // Fields to hold record data temporarily
  private String Name;
  private String Rank;
  private String City;
  private String State;
  private String Zip;
  private String Employees;

  // altered constructor to fit new csv
  public DB() {
    this.Dinout = null;
    this.num_records = 0;
    this.Name = "NAME";
    this.Rank = "RANK";
    this.City = "CITY";
    this.State = "STATE";
    this.Zip = "ZIP";
    this.Employees = "EMPLOYEES";
  }

  /**
   * Opens the file in read/write mode
   * 
   * @param prefix (e.g., Fortune500_small)
   * @return status true if operation successful
   */
  public void  open(String prefix) {
    // Set the number of records
    this.num_records = 0;

    // Open file in read/write mode
    try {
      RandomAccessFile cfg = new RandomAccessFile(prefix + ".config", "r");  // read config file for number of records
      String line;
      while ((line = cfg.readLine()) != null) {
        line = line.trim();
        if (line.startsWith("numSortedRecords=")) {
          String val = line.substring("numSortedRecords=".length()).trim();
          this.num_records = Integer.parseInt(val);
        }
      }
      cfg.close();
      this.Dinout = new RandomAccessFile(prefix + ".data", "rw");
    } catch (IOException e) {
      System.out.println("Could not open file\n");
      e.printStackTrace();
      this.Dinout = null;
      this.num_records = 0;
    }
  }
  
  /** 
   * Writes the data to the location specified by file parameter
   *  
   */
  public void writeRecord(RandomAccessFile file, String Name, String Rank, String City, String State, String Zip, String Employees ) { //writes record to file with padding for uniform file reading
    	//format input values to be put in record
        this.Name = String.format("%-" + name_w + "s", Name.length() > name_w ? Name.substring(0, name_w) : Name);
        this.Rank = String.format("%-" + rank_w + "s", Rank.length() > rank_w ? Rank.substring(0, rank_w) : Rank);
        this.City = String.format("%-" + city_w + "s", City.length() > city_w ? City.substring(0, city_w) : City);
        this.State = String.format("%-" + state_w + "s", State.length() > state_w ? State.substring(0, state_w) : State);
        this.Zip = String.format("%-" + zip_w + "s", Zip.length() > zip_w ? Zip.substring(0, zip_w) : Zip);
        this.Employees = String.format("%-" + employees_w + "s", Employees.length() > employees_w ? Employees.substring(0, employees_w) : Employees);
	try {
		file.writeBytes(this.Name + this.Rank + this.City + this.State + this.Zip + this.Employees+"\n");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  
  }
  
  /**Takes in record number of record to be changed as well as what values to overwrite with 
   * 
   *  
   */ 
  public void overwriteRecord(int record_num, String Name, String Rank, String City, String State, String Zip, String Employees) {
    if ((record_num >= 0) && (record_num < this.num_records)) {
      try {
        Dinout.seek(0); // return to the top of the file
        Dinout.skipBytes(record_num * RECORD_SIZE);
	//overwrite the specified record
        writeRecord (Dinout, Name, Rank, City, State, Zip, Employees);
      } catch (IOException e) {
        System.out.println("There was an error while attempting to overwrite a record from the database file.\n");
        e.printStackTrace();
      }
    }
	System.out.println("Record Successfully Overwritten");
  }

  /** Opens CSV file and creates new data file
   * Reads CSV file attributes
   * @throws IOException 
   */
  public void createDB(String prefix) throws IOException {
      RandomAccessFile Din = new RandomAccessFile(prefix+".csv", "r");
      RandomAccessFile Dout = new RandomAccessFile(prefix+".data","rw");
      Dout.setLength(0); // clear file if it already exists 
      
      String line;
      int count = 0;
      
      while ((line = Din.readLine()) != null) {
          String[] attribute = line.split(",", -1); // -1 to include trailing empty strings
          if (attribute.length > 6) continue; // skip malformed lines
          // trim whitespace from each attribute
          for (int k = 0; k < 6; k++) {
              attribute[k] = attribute[k].trim();
          }
          writeRecord (Dout, attribute[0], attribute[1], attribute[2], attribute[3], attribute[4], attribute[5]); //added one more attribute to fit new csv
      
          count++;
      }
      Din.close();
      Dout.close();

      RandomAccessFile cfg = new RandomAccessFile(prefix+".config", "rw");  // adding config file
      cfg.setLength(0); // clear file if it already exists
      cfg.writeBytes("numSortedRecords="+count+"\n");
      cfg.writeBytes("recordSize=" + RECORD_SIZE + "\n");
      cfg.writeBytes("numUnsortedRecords=0\n");
      cfg.close();
  }
 
  /**
   * Close the database file
   */
  public void close() {
    if (Dinout == null) // guard so it doesnt crash if open fails
      return;
    try {
      Dinout.close();
    } catch (IOException e) {
      System.out.println("There was an error while attempting to close the database file.\n");
      e.printStackTrace();
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
    if ((record_num >= 0) && (record_num < this.num_records)) {
      try {
        Dinout.seek(0); // return to the top of the file
        Dinout.skipBytes(record_num * RECORD_SIZE);
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
        e.printStackTrace();
      }
    }
    return record;
  }

  /**
   * Binary Search by record id
   * 
   * @param id
   * @return Record number (which can then be used by read to
   *         get the fields) or -1 if id not found
   */
  public int binarySearch(String name) {
    int Low = 0;
    int High = this.num_records - 1;
    int Middle = 0;
    boolean Found = false;
    Record record;

    while (!Found && (High >= Low)) {
      Middle = (Low + High) / 2;
      record = readRecord(Middle);
      String MiddleName = record.Name;

      // int result = MiddleId[0].compareTo(id); // DOES STRING COMPARE
      int result = MiddleName.compareTo(name); // DOES STRING COMPARE of MiddleName and name
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

  public int findRecord(String name) { //wrapper for binary search to check if file is open
    if (Dinout == null) {
      return -1;
    } else
      return binarySearch(name.trim());
  }

  public int updateRecord(int record_num, String Name, String Rank, String City, String State, String Zip, String Employees) { //wrapper for overwrite record to check if file is open
    if (Dinout == null) {
      return -1;
    } else {
      overwriteRecord(record_num, Name, Rank, City, State, Zip, Employees);
      return 0;
    }
  }

  public boolean deleteRecord(String name) {
    if (Dinout == null)
      return false;
    int record_num = findRecord(name);
    if (record_num == -1)
      return false;
    overwriteRecord(record_num, "", "", "", "", "", "");

    return true;  
  }
}
