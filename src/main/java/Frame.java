class Frame {

    private int remainingBalls;
    private int[] scores;
    private int currThrow;
    private boolean throwComplete;
    private int remainingScoringThrows;


    Frame() {
        remainingBalls = 10;
        scores = new int[] { -1, -1, -1 } ;
        currThrow = 0;
        throwComplete = false;
        remainingScoringThrows = 2;
    }

    int getRemainingBalls() {
        return remainingBalls;
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

    int getRemainingScoringThrows() {
        return remainingScoringThrows;
    }

    void throwBall(int pinsDown) {
        if (pinsDown == 10) {
            remainingBalls -= pinsDown;
            scores[currThrow] = pinsDown;
            currThrow++;
            throwComplete = true;
        } else {
            remainingBalls -= pinsDown;
            scores[currThrow] = pinsDown;
            currThrow++;
            throwComplete = true;
            if (currThrow > 1) {
                remainingScoringThrows--;
            }
        }
    }
}
