class Frame {

    private int[] scores;
    private int currThrow;
    private boolean throwComplete;
    private int remainingScoringThrows;

    Frame() {
        scores = new int[] { -1, -1, -1 } ;
        currThrow = 0;
        throwComplete = false;
        remainingScoringThrows = 2;
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
            scores[currThrow] = pinsDown;
            currThrow++;
            throwComplete = true;
        }
    }
}
