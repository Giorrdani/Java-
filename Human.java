public class Human implements Actions {


    int endurance;
    int powerJump;

    public Human(int endurance, int powerJump){
        this.endurance = endurance;
        this.powerJump = powerJump;
    }

    @Override
    public void running() {
        if (endurance>=Obstacles.TrackRun.distance){
                System.out.println("Человек пробежал");
            }else {
                System.out.println("Человек не пробежал");
            }
        }
    @Override
    public void jumping() {
        if (powerJump>=Obstacles.Wall.height){
            System.out.println("Человек перепрыгнул");
        }else {
            System.out.println("Человек не перепрыгнул");
        }
    }
}
