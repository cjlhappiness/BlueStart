package data;

public class SixthData extends mData{

    private int freeCount;
    private int point;

    public SixthData(int id, int userId, int point, int freeCount) {
        super(id, userId);
        this.point = point;
        this.freeCount = freeCount;
    }

    public int getFreeCount() {
        return freeCount;
    }

    public void setFreeCount(int freeCount) {
        this.freeCount = freeCount;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
