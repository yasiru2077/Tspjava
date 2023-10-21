import java.io.*;

public class DeserializeMain {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("main.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Main deserializedMain = (Main) ois.readObject();
            ois.close();
            fis.close();

            // Now you have the deserializedMain object.
            // You can perform operations on it or access its methods and fields as needed.

            // For example, you can call the main method of the deserializedMain object to execute its logic again.
            deserializedMain.main(args);

            System.out.println("Main object has been deserialized.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
