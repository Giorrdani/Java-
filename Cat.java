
public class Cat implements Actions {
    int endurance;
    int powerJump;

    public Cat(int endurance, int powerJump){
        this.endurance = endurance;
        this.powerJump = powerJump;
    }

    @Override
    public void running() {
        if (endurance>=Obstacles.TrackRun.distance){
               System.out.println("Кот пробежал");
           }else {
               System.out.println("Кот не пробежал");
           }
    }

    @Override
    public void jumping() {
        if (powerJump>=Obstacles.Wall.height){
            System.out.println("Кот перепрыгнул");
        }else {
            System.out.println("Кот не перепрыгнул");
        }
    }

}
