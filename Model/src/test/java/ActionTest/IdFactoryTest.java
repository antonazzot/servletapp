package ActionTest;

import action.IdFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class IdFactoryTest {

    @Test
    public void idBuilder() {
        int actual = IdFactory.idBuilder();
        int axpected = IdFactory.idBuilder()-1;
        assertTrue(actual==axpected);
    }
}