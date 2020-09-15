@SuppressWarnings("WeakerAccess")
public abstract class AbstractFrame {


    protected static final int TOTAL_PINS = 10;

    protected static final String THROW_MORE_THAN_ALLOWED_TIMES_MSG =
            "you can't continue a frame which has already been completed";

    protected static final String STRIKING_NEGATIVE_PINS_MSG =
            "you can't strike down a negative number of pins";

    protected static final String STRIKING_TOO_MANY_PINS_MSG =
            "you can't strike down more pins than what's in this frame";



    protected int remainingPins;
    protected int[] scores;

    protected int currThrow;
    protected boolean throwComplete;

    protected int totalScore;


    protected AbstractFrame() {
        remainingPins = TOTAL_PINS;
        currThrow = 0;
        throwComplete = false;
        totalScore = 0;
    }


    protected void checkInput(int pinsDown) {

        if (throwComplete)
            throw new IllegalStateException(THROW_MORE_THAN_ALLOWED_TIMES_MSG);

        if (pinsDown < 0)
            throw new IllegalArgumentException(STRIKING_NEGATIVE_PINS_MSG);

        if (pinsDown > remainingPins)
            throw new IllegalArgumentException(STRIKING_TOO_MANY_PINS_MSG);
    }


    protected void strike(int pinsDown) {
        remainingPins -= pinsDown;
        scores[currThrow] = pinsDown;
        currThrow++;
        totalScore += pinsDown;
    }


    protected abstract void throwBall(int pinsDown);


    protected abstract void checkFrameComplete();


    int getRemainingPins() {
        return remainingPins;
    }

    int[] getScores() {
        return scores;
    }

    int getCurrThrow() {
        return currThrow;
    }

    boolean isThrowComplete() {
        return throwComplete;
    }

    int getTotalScore() {
        return totalScore;
    }
}
