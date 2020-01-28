import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass
{

    @Test
    public void testGetLocalNumber() {
        int expected = 14;
        int actual = this.getLocalNumber();

        Assert.assertFalse("func getLocalNumber return " + actual + " instead " + expected,actual != expected);
    }

}
