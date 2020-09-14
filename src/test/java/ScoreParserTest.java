import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ScoreParserTest {


    private static final String[] INPUTS = {
            "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0",
            "10,10,10,10,10,10,10,10,10,10,10,10"
    };
    
    
    @Test
    void should_all_frames_complete_and_scores_added_given_line_input() {
        for (String input : INPUTS) {
            should_all_frames_complete_and_scores_added_given_line_input(input);
        }
    }

    private void should_all_frames_complete_and_scores_added_given_line_input(String input) {

        List<AbstractFrame> frames = ScoreParser.parseLineIntoFrames(input);

        assertEquals(frames.size(), 10);

        for (int i = 0; i < frames.size(); i++) {
            AbstractFrame f = frames.get(i);
            assertTrue(f.isThrowComplete());
            if (i < frames.size() - 1) {
                assertEquals(f.getClass(), Frame.class);
                assertEquals(((Frame) f).getRemainingScoringThrows(), 0);
            } else {
                assertEquals(f.getClass(), LastFrame.class);
            }
        }
    }
    
}
