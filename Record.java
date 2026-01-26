import java.io.IOException;

public class Record {

  private boolean empty;

  public String Name;
  public String Rank;
  public String City;
  public String State;
  public String Zip;
  public String Employees;

  public Record() {
    empty = true;
  }

  /**
   * Update the fields of a record from an array of fields
   * 
   * @param fields array with values of fields
   * @return nothing
   * @throws IOException
   */
  public void updateFields(String[] fields) throws IOException {
    if (fields.length == 6) {
      this.Name = fields[0];
      this.Rank = fields[1];
      this.City = fields[2];
      this.State = fields[3];
      this.Zip = fields[4];
      this.Employees = fields[5];

      empty = false;
    } else
      throw new IOException();
  }

  /**
   * Check if record fields have been updated
   * 
   * @return true if record is empty otherwise false
   */
  public boolean isEmpty() {
    return empty;
  }

  public String toString() {
    return "Name: " + this.Name +
        ", Rank: " + this.Rank +
        ", City: " + this.City +
        ", State: " + this.State +
        ", Zip: " + this.Zip +
        ", Employees: " + this.Employees;
  }

}
