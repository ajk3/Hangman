package finalProject;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author anurag 
 * this class accepts the generic data type and is implemented as an ArrayList
 */

public class GenericList<E>
{
    private List<E> myList = new ArrayList<>();
    
    public GenericList() {   }
    
    // uses add method
    public void addRecord(E record)
    {
        myList.add(record);
    }
    
    @Override
    public String toString()
    {
        return String.format("%s", myList);
    }  
    
}
