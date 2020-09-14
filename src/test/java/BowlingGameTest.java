import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BowlingGameTest {

    @Test
    void should_construct_empty_frame() {
        Frame expected = new Frame();
        assertEquals(expected.getCurrThrow(), 0);
        assertArrayEquals(expected.getThrew(), new int[] { -1, -1, -1 });
    }
}
