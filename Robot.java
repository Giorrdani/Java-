public class Robot implements Actions {
    int endurance;
    int powerJump;

    public Robot (int endurance, int powerJump){
        this.endurance = endurance;
        this.powerJump = powerJump;
    }

    @Override
    public void running() {
        if (endurance>=Obstacles.TrackRun.distance){
            System.out.println("Робот пробежал");
        }else {
            System.out.println("Робот не пробежал");
        }
    }

    @Override
    public void jumping() {
        if (powerJump>=Obstacles.Wall.height){
            System.out.println("Робот перепрыгнул");
        }else {
            System.out.println("Робот не перепрыгнул");
        }
    }
}
