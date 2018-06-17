package fallk.dev;

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
    public void safeCreation() {
        CharDeque cd = new CharDeque();
        
        checkSizeIs0(cd);
    }

    @Test
    public void safeCreation2() {
        CharDeque cd = new CharDeque(new ArrayList<Character>(Arrays.asList(new Character[] {1, 2, 3})));

        Assert.assertFalse(cd.isEmpty());
        Assert.assertArrayEquals(new char[] {1, 2, 3}, cd.toArray());
        Assert.assertTrue(cd.size() == 3);
        Assert.assertTrue(cd.space() >= 3);
        
        cd.jUnitAssertInvariants();
    }

    @Test
    public void safeCreation3() {
        CharDeque cd = new CharDeque(420);

        Assert.assertTrue(cd.isEmpty());
        Assert.assertTrue(cd.size() == 0);
        Assert.assertTrue(cd.space() >= 420);
        
        cd.jUnitAssertInvariants();
    }

    @Test
    public void safeAdd() {
        CharDeque cd = new CharDeque();

        checkSizeIs0(cd);
        
        cd.add('a');
        
        checkSizeIs1(cd);
    }

    @Test
    public void safeAddMultiple() {
        CharDeque cd = new CharDeque();

        checkSizeIs0(cd);

        cd.add('a');
        
        checkSizeIs1(cd);

        cd.add('b');
        
        checkSizeIs2(cd);
    }
    

    @Test
    public void safeAddDelete() {
        CharDeque cd = new CharDeque();

        checkSizeIs0(cd);

        cd.add('a');
        
        checkSizeIs1(cd);

        cd.delete(0);

        checkSizeIs0(cd);
    }
    

    @Test
    public void safeAddRemove() {
        CharDeque cd = new CharDeque();

        checkSizeIs0(cd);

        cd.add('a');
        
        checkSizeIs1(cd);

        Assert.assertTrue(cd.remove('a'));

        checkSizeIs0(cd);
    }

    @Test
    public void safeAddDeleteWithLeftover() {
        CharDeque cd = new CharDeque();

        checkSizeIs0(cd);

        cd.add('a');
        
        checkSizeIs1(cd);

        cd.add('b');
        
        checkSizeIs2(cd);

        cd.add('c');
        
        checkSizeIs3(cd);

        cd.delete(1);

        checkSizeIs2(cd);
    }
    

    @Test
    public void safeAddRemoveWithLeftover() {
        CharDeque cd = new CharDeque();

        checkSizeIs0(cd);

        cd.add('a');
        
        checkSizeIs1(cd);

        cd.add('b');
        
        checkSizeIs2(cd);

        cd.add('c');
        
        checkSizeIs3(cd);

        Assert.assertTrue(cd.remove('b'));

        checkSizeIs2(cd);
    }

    private void checkSizeIs3(CharDeque cd) {
        Assert.assertFalse(cd.isEmpty());
        Assert.assertTrue(cd.size() == 3);
        cd.jUnitAssertInvariants();
    }

    private void checkSizeIs2(CharDeque cd) {
        Assert.assertFalse(cd.isEmpty());
        Assert.assertTrue(cd.size() == 2);
        cd.jUnitAssertInvariants();
    }

    private void checkSizeIs1(CharDeque cd) {
        Assert.assertFalse(cd.isEmpty());
        Assert.assertTrue(cd.size() == 1);
        cd.jUnitAssertInvariants();
    }

    private void checkSizeIs0(CharDeque cd) {
        Assert.assertTrue(cd.isEmpty());
        Assert.assertTrue(cd.size() == 0);
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

    @Test
    public void unsafeCreation() {
        CharDequeUnsafe cd = new CharDequeUnsafe();
        
        checkSizeIs0(cd);
    }

    @Test
    public void unsafeCreation2() {
        CharDequeUnsafe cd = new CharDequeUnsafe(new ArrayList<Character>(Arrays.asList(new Character[] {1, 2, 3})));

        Assert.assertFalse(cd.isEmpty());
        Assert.assertArrayEquals(new char[] {1, 2, 3}, cd.toArray());
        Assert.assertTrue(cd.size() == 3);
        Assert.assertTrue(cd.space() >= 3);
        
        cd.jUnitAssertInvariants();
    }

    @Test
    public void unsafeCreation3() {
        CharDequeUnsafe cd = new CharDequeUnsafe(420);

        Assert.assertTrue(cd.isEmpty());
        Assert.assertTrue(cd.size() == 0);
        Assert.assertTrue(cd.space() >= 420);
        
        cd.jUnitAssertInvariants();
    }

    @Test
    public void unsafeAdd() {
        CharDequeUnsafe cd = new CharDequeUnsafe();

        checkSizeIs0(cd);
        
        cd.add('a');
        
        checkSizeIs1(cd);
    }

    @Test
    public void unsafeAddMultiple() {
        CharDequeUnsafe cd = new CharDequeUnsafe();

        checkSizeIs0(cd);

        cd.add('a');
        
        checkSizeIs1(cd);

        cd.add('b');
        
        checkSizeIs2(cd);
    }
    

    @Test
    public void unsafeAddDelete() {
        CharDequeUnsafe cd = new CharDequeUnsafe();

        checkSizeIs0(cd);

        cd.add('a');
        
        checkSizeIs1(cd);

        cd.delete(0);

        checkSizeIs0(cd);
    }
    

    @Test
    public void unsafeAddRemove() {
        CharDequeUnsafe cd = new CharDequeUnsafe();

        checkSizeIs0(cd);

        cd.add('a');
        
        checkSizeIs1(cd);

        Assert.assertTrue(cd.remove('a'));

        checkSizeIs0(cd);
    }

    @Test
    public void unsafeAddDeleteWithLeftover() {
        CharDequeUnsafe cd = new CharDequeUnsafe();

        checkSizeIs0(cd);

        cd.add('a');
        
        checkSizeIs1(cd);

        cd.add('b');
        
        checkSizeIs2(cd);

        cd.add('c');
        
        Assert.assertFalse(cd.isEmpty());
        Assert.assertTrue(cd.size() == 3);
        cd.jUnitAssertInvariants();

        cd.delete(1);

        checkSizeIs2(cd);
    }
    

    @Test
    public void unsafeAddRemoveWithLeftover() {
        CharDequeUnsafe cd = new CharDequeUnsafe();

        checkSizeIs0(cd);

        cd.add('a');
        
        checkSizeIs1(cd);

        cd.add('b');
        
        checkSizeIs2(cd);

        cd.add('c');
        
        Assert.assertFalse(cd.isEmpty());
        Assert.assertTrue(cd.size() == 3);
        cd.jUnitAssertInvariants();

        Assert.assertTrue(cd.remove('b'));

        checkSizeIs2(cd);
    }

    private void checkSizeIs2(CharDequeUnsafe cd) {
        Assert.assertFalse(cd.isEmpty());
        Assert.assertTrue(cd.size() == 2);
        cd.jUnitAssertInvariants();
    }
    
    @Test
    public void checkDevelopmentFileEqualityUnsafe() throws IOException {
        List<String> firstFile = Files.readAllLines(Paths.get("./src/main/java/club/bonerbrew/dev/CharDequeUnsafe.java"), StandardCharsets.UTF_8);
        List<String> secondFile = Files.readAllLines(Paths.get("./src/main/java/club/bonerbrew/neatarrays/CharDequeUnsafe.java"), StandardCharsets.UTF_8);
        
        for (int i = 0; i < firstFile.size(); i++) {
            String fLine = firstFile.get(i);
            if (fLine.startsWith("package")) continue; // package def is always different
            
            Assert.assertEquals("The files differ at line " + i, fLine, secondFile.get(i));
        }
    }


    @Test
    public void unsafeAddNullValue() {
        CharDequeUnsafe cd = new CharDequeUnsafe();

        checkSizeIs0(cd);
        
        cd.add(Character.MIN_VALUE);
        
        checkSizeIs1(cd);
    }


    @Test
    public void unsafeAddMultipleNullValue() {
        CharDequeUnsafe cd = new CharDequeUnsafe();

        checkSizeIs0(cd);

        cd.add(Character.MIN_VALUE);
        
        checkSizeIs1(cd);

        cd.add(Character.MIN_VALUE);
        
        checkSizeIs2(cd);
    }
    
    @Test
    public void unsafeAddDeleteNullValue() {
        CharDequeUnsafe cd = new CharDequeUnsafe();

        checkSizeIs0(cd);

        cd.add(Character.MIN_VALUE);
        
        checkSizeIs1(cd);

        cd.delete(0);

        checkSizeIs0(cd);
    }


    @Test
    public void unsafeAddRemoveNullValue() {
        CharDequeUnsafe cd = new CharDequeUnsafe();

        checkSizeIs0(cd);

        cd.add(Character.MIN_VALUE);
        
        checkSizeIs1(cd);

        Assert.assertTrue(cd.remove(Character.MIN_VALUE));

        checkSizeIs0(cd);
    }

    @Test
    public void unsafeAddGetNullValue() {
        CharDequeUnsafe cd = new CharDequeUnsafe();

        checkSizeIs0(cd);

        cd.add(Character.MIN_VALUE);
        
        checkSizeIs1(cd);

        Assert.assertTrue(cd.getLast() == Character.MIN_VALUE);
        Assert.assertTrue(cd.getFirst() == Character.MIN_VALUE);
        
        checkSizeIs1(cd);
    }

    @Test
    public void unsafeAddGetNullValueWithLeftover() {
        CharDequeUnsafe cd = new CharDequeUnsafe();

        checkSizeIs0(cd);

        cd.add(Character.MIN_VALUE);
        
        checkSizeIs1(cd);

        cd.add('b');

        checkSizeIs2(cd);

        Assert.assertTrue(cd.getFirst() == Character.MIN_VALUE);
        Assert.assertTrue(cd.getLast() == 'b');
        
        checkSizeIs2(cd);
    }

    private void checkSizeIs0(CharDequeUnsafe cd) {
        Assert.assertTrue(cd.isEmpty());
        Assert.assertTrue(cd.size() == 0);
        cd.jUnitAssertInvariants();
    }

    private void checkSizeIs1(CharDequeUnsafe cd) {
        Assert.assertFalse(cd.isEmpty());
        Assert.assertTrue(cd.size() == 1);
        cd.jUnitAssertInvariants();
    }

}
