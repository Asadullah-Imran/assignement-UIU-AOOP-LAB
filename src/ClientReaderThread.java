import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientReaderThread implements Runnable {
    ObjectInputStream ois;
    Socket socket;
    public ClientReaderThread(Socket socket) {
        this.socket=socket;
        Thread thread= new Thread(this);
        thread.start();
    }
    @Override
    public void run() {

            try {
                ois = new ObjectInputStream(socket.getInputStream());
                String msg=(String)ois.readObject();
                System.out.println(msg);
                String prevMsg=(String)ois.readObject();
                if(prevMsg!=null){
                    String []prevConversation=prevMsg.split("\\$");
                    for(int i=0;i<prevConversation.length;i++){
                        System.out.println(prevConversation[i]);
                    }
                }
                while(true){
                    ois = new ObjectInputStream(socket.getInputStream());
                    String msg2=(String)ois.readObject();
                    System.out.println(msg2);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

    }
}
