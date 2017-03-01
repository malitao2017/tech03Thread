package t0temp;

public class Jobt<Jobb extends Runnable> {
	
	public static void main(String[] args) {
//		Jobb 
		
	}
	public void run() {
		Jobb job = null;
            if (job != null) {
                try {
                    job.run();
                } catch (Exception ex) {
                    // 忽略Job执行中的Exception
                }
            }
        }

}
