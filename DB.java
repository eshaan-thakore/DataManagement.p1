import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DB {
  public static final int NUM_RECORDS = 10;
  public static final int RECORD_SIZE = 71;

  private RandomAccessFile Dinout;
  private int num_records;
  private String Id;
  private String Rank;
  private String City;
  private String State;
  private String Zip;
  private String Employees;

  public DB() {
    this.Dinout = null;
    this.num_records = 0;
    this.Id = "ID";
    this.Rank = "RANK";
    this.City = "CITY";
    this.State = "STATE";
    this.Zip = "ZIP";
    this.Employees = "EMPLOYEES";
  }

  /**
   * Opens the file in read/write mode
   * 
   * @param filename (e.g., input.data)
   * @return status true if operation successful
   */
  public void  open(String filename) {
    // Set the number of records
    this.num_records = NUM_RECORDS;

    // Open file in read/write mode
    try {
      this.Dinout = new RandomAccessFile(filename, "rw");
    } catch (FileNotFoundException e) {
      System.out.println("Could not open file\n");
      e.printStackTrace();
    }
  }
  
  /** 
   * Writes the data to the location specified by file parameter
   *  
   */
  public void writeRecord(RandomAccessFile file, String Id, String Experience, String Married, String Wage, String Industry ) {
    	//format input values to be put in record
        this.Id = String.format("%-10s", Id.length() > 8 ? Id.substring(0, 8) : Id);
        this.Rank = String.format("%-5s", Experience.length() > 3 ? Experience.substring(0, 3) : Experience);
        this.City = String.format("%-5s", Married.length() > 3 ? Married.substring(0, 3) : Married);
        this.State = String.format("%-20s", Wage.length() > 18 ? Wage.substring(0, 18) : Wage);
        this.Zip = String.format("%-30s", Industry.length() > 28 ? Industry.substring(0, 28) : Industry);
	try {
		file.writeBytes(this.Id + this.Rank + this.City + this.State + this.Zip + this.Employees+"\n");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  
  }
  
  /**Takes in record number of record to be changed as well as what values to overwrite with 
   * 
   *  
   */ 
  public void overwriteRecord(int record_num, String Id, String Experience, String Married, String Wage, String Industry ) {
    if ((record_num >= 0) && (record_num < this.num_records)) {
      try {
        Dinout.seek(0); // return to the top of the file
        Dinout.skipBytes(record_num * RECORD_SIZE);
	//overwrite the specified record
        writeRecord (Dinout, Id, Experience, Married, Wage, Industry);
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
  public void createDB(String filename) throws IOException {
      RandomAccessFile Din = new RandomAccessFile(filename+".csv", "r");
      RandomAccessFile Dout = new RandomAccessFile(filename+".data","rw"); 
      String line;
      while ((line = Din.readLine()) != null) {
          String[] attribute = line.split(",");
          writeRecord (Dout, attribute[0], attribute[1], attribute[2], attribute[3], attribute[4]);
      }
      Dout.close();
  }

  /**
   * Close the database file
   */
  public void close() {
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
        fields = Dinout.readLine().split("\\s{2,}", 0);
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
  public int binarySearch(String id) {
    int Low = 0;
    int High = NUM_RECORDS - 1;
    int Middle = 0;
    boolean Found = false;
    Record record;

    while (!Found && (High >= Low)) {
      Middle = (Low + High) / 2;
      record = readRecord(Middle);
      String MiddleId = record.Id;

      // int result = MiddleId[0].compareTo(id); // DOES STRING COMPARE
      int result = Integer.parseInt(MiddleId) - Integer.parseInt(id); // DOES INT COMPARE of MiddleId[0] and id
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
}
