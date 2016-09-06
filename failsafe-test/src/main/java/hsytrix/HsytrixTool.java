package hsytrix;

public class HsytrixTool {
    public static void main(String[] args) {
        
        try {
            for (int i = 1; i <=5; i++) {
                //同步
                MyCommand command=new MyCommand("group1","command1","thread1");
                System.out.println(i+command.execute());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
