class Frame {

    private int[] threw;
    private int currThrow;

    Frame() {
        threw = new int[] { -1, -1, -1 } ;
        currThrow = 0;
    }

    int[] getThrew() {
        return threw;
    }

    int getCurrThrow() {
        return currThrow;
    }

}
