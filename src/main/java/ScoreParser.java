import java.util.ArrayList;
import java.util.List;


public class ScoreParser {


    private static final String INVALID_INPUT_LENGTH =
            "input length is invalid";

    private static final String INVALID_INPUT_CHAR =
            "invalid character found when parsing input";

    private static final String INCOMPLETE_LINE =
            "the parsed result is not a completed line with 10 frames";



    public static int parseLineIntoTotalScore(String line) {
        List<AbstractFrame> frames = parseLineIntoFrames(line);
        return frames.stream().mapToInt(AbstractFrame::getTotalScore).sum();
    }



    public static List<AbstractFrame> parseLineIntoFrames(String line) {

        int[] scores = getScores(line);
        List<AbstractFrame> frames = new ArrayList<>(10);

        boolean isNewFrameNext = true;

        for (int score : scores) {

            AbstractFrame frame;

            if (isNewFrameNext) {
                if (frames.size() < 9) {
                    frame = new Frame();
                } else {
                    frame = new LastFrame();
                }
                frames.add(frame);
            } else {
                frame = frames.get(frames.size() - 1);
            }

            frame.throwBall(score);

            for (int i = frames.size() - 2; i >= 0; i--) {
                Frame olderFrame = (Frame) frames.get(i);
                if (olderFrame.getRemainingScoringThrows() > 0) {
                    olderFrame.addAdditionalScore(score);
                }
            }

            isNewFrameNext = frame.isThrowComplete();
        }

        if (frames.size() != 10)
            throw new ParseException(INCOMPLETE_LINE);

        return frames;
    }


    private static int[] getScores(String line) {

        String[] split = line.split(",");

        if (split.length < 11 || split.length > 21)
            throw new ParseException(INVALID_INPUT_LENGTH);

        int[] input = new int[split.length];

        for (int i = 0; i < split.length; i++) {
            try {
                input[i] = Integer.parseInt(split[i]);
            } catch (NumberFormatException e) {
                throw new ParseException(INVALID_INPUT_CHAR);
            }
        }

        return input;
    }


}
