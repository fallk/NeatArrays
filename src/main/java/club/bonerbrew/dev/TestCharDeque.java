package club.bonerbrew.dev;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TestCharDeque {

    @Test
    public void testCreation() {
        CharDeque cd = new CharDeque();
        
        Assert.assertTrue(cd.isEmpty());
        Assert.assertTrue(cd.size() == 0);
        
        cd.jUnitAssertInvariants();
    }

    @Test
    public void testCreation2() {
        CharDeque cd = new CharDeque(new ArrayList<Character>(Arrays.asList(new Character[] {1, 2, 3})));

        Assert.assertFalse(cd.isEmpty());
        Assert.assertArrayEquals(new char[] {1, 2, 3}, cd.toArray());
        Assert.assertTrue(cd.size() == 3);
        Assert.assertTrue(cd.space() >= 3);
        
        cd.jUnitAssertInvariants();
    }

    @Test
    public void testCreation3() {
        CharDeque cd = new CharDeque(420);

        Assert.assertTrue(cd.isEmpty());
        Assert.assertTrue(cd.size() == 0);
        Assert.assertTrue(cd.space() >= 420);
        
        cd.jUnitAssertInvariants();
    }

    @Test
    public void testAdd() {
        CharDeque cd = new CharDeque();

        Assert.assertTrue(cd.isEmpty());
        Assert.assertTrue(cd.size() == 0);
        cd.jUnitAssertInvariants();
        
        cd.add('a');
        
        Assert.assertFalse(cd.isEmpty());
        Assert.assertTrue(cd.size() == 1);
        cd.jUnitAssertInvariants();
    }

    @Test
    public void testAddMultiple() {
        CharDeque cd = new CharDeque();

        Assert.assertTrue(cd.isEmpty());
        Assert.assertTrue(cd.size() == 0);
        cd.jUnitAssertInvariants();

        cd.add('a');
        
        Assert.assertFalse(cd.isEmpty());
        Assert.assertTrue(cd.size() == 1);
        cd.jUnitAssertInvariants();

        cd.add('b');
        
        Assert.assertFalse(cd.isEmpty());
        Assert.assertTrue(cd.size() == 2);
        cd.jUnitAssertInvariants();
    }
    

    @Test
    public void testAddDelete() {
        CharDeque cd = new CharDeque();

        Assert.assertTrue(cd.isEmpty());
        Assert.assertTrue(cd.size() == 0);
        cd.jUnitAssertInvariants();

        cd.add('a');
        
        Assert.assertFalse(cd.isEmpty());
        Assert.assertTrue(cd.size() == 1);
        cd.jUnitAssertInvariants();

        cd.delete(0);

        Assert.assertTrue(cd.isEmpty());
        Assert.assertTrue(cd.size() == 0);
        cd.jUnitAssertInvariants();
    }
    

    @Test
    public void testAddRemove() {
        CharDeque cd = new CharDeque();

        Assert.assertTrue(cd.isEmpty());
        Assert.assertTrue(cd.size() == 0);
        cd.jUnitAssertInvariants();

        cd.add('a');
        
        Assert.assertFalse(cd.isEmpty());
        Assert.assertTrue(cd.size() == 1);
        cd.jUnitAssertInvariants();

        Assert.assertTrue(cd.remove('a'));

        Assert.assertTrue(cd.isEmpty());
        Assert.assertTrue(cd.size() == 0);
        cd.jUnitAssertInvariants();
    }

    @Test
    public void testAddDeleteWithLeftover() {
        CharDeque cd = new CharDeque();

        Assert.assertTrue(cd.isEmpty());
        Assert.assertTrue(cd.size() == 0);
        cd.jUnitAssertInvariants();

        cd.add('a');
        
        Assert.assertFalse(cd.isEmpty());
        Assert.assertTrue(cd.size() == 1);
        cd.jUnitAssertInvariants();

        cd.add('b');
        
        Assert.assertFalse(cd.isEmpty());
        Assert.assertTrue(cd.size() == 2);
        cd.jUnitAssertInvariants();

        cd.add('c');
        
        Assert.assertFalse(cd.isEmpty());
        Assert.assertTrue(cd.size() == 3);
        cd.jUnitAssertInvariants();

        cd.delete(1);

        Assert.assertFalse(cd.isEmpty());
        Assert.assertTrue(cd.size() == 2);
        cd.jUnitAssertInvariants();
    }
    

    @Test
    public void testAddRemoveWithLeftover() {
        CharDeque cd = new CharDeque();

        Assert.assertTrue(cd.isEmpty());
        Assert.assertTrue(cd.size() == 0);
        cd.jUnitAssertInvariants();

        cd.add('a');
        
        Assert.assertFalse(cd.isEmpty());
        Assert.assertTrue(cd.size() == 1);
        cd.jUnitAssertInvariants();

        cd.add('b');
        
        Assert.assertFalse(cd.isEmpty());
        Assert.assertTrue(cd.size() == 2);
        cd.jUnitAssertInvariants();

        cd.add('c');
        
        Assert.assertFalse(cd.isEmpty());
        Assert.assertTrue(cd.size() == 3);
        cd.jUnitAssertInvariants();

        Assert.assertTrue(cd.remove('b'));

        Assert.assertFalse(cd.isEmpty());
        Assert.assertTrue(cd.size() == 2);
        cd.jUnitAssertInvariants();
    }
    
    @Test
    public void checkDevelopmentFileEquality() throws IOException {
        List<String> firstFile = Files.readAllLines(Paths.get("./src/main/java/club/bonerbrew/dev/CharDeque.java"), StandardCharsets.UTF_8);
        List<String> secondFile = Files.readAllLines(Paths.get("./src/main/java/club/bonerbrew/neatarrays/CharDeque.java"), StandardCharsets.UTF_8);
        
        for (int i = 0; i < firstFile.size(); i++) {
            String fLine = firstFile.get(i);
            if (fLine.startsWith("package")) continue; // package def is always different
            
            Assert.assertEquals("The files differ at line " + i, fLine, secondFile.get(i));
        }
    }
}
