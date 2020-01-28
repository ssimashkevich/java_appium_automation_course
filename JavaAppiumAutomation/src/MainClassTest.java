import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;

public class MainClassTest extends MainClass
{

    @Test
    public void testGetClassString() {
        String [] expected = {"Hello","hello"};
        int actualLength = 0;
        String parentString = this.getClassString();

        for (String s : expected) {
            if (parentString.contains(s)) {
                actualLength++;
            }
        }
        Assert.assertTrue("func getClassString return \"" + parentString + "\", that  not contains all of: " + Arrays.toString(expected), actualLength != 0);
    }

}
