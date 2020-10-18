import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author fc
 */
public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
            Class hello = new HelloClassLoader().findClass("Hello");
            Method helloMethod = hello.getMethod("hello");
            helloMethod.invoke(hello.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File helloxlass = new File(this.getClass().getResource("Hello.xlass").getPath());
        int fileLength = (int) helloxlass.length();
        byte[] classByte = new byte[fileLength];
        try {
            FileInputStream in = new FileInputStream(helloxlass);
            in.read(classByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < fileLength; i++) {
            int num = classByte[i];
            num = 255 - num;
            classByte[i] = (byte) num;
        }
        return defineClass(name, classByte, 0, classByte.length);
    }

}