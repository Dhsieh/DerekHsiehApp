package Utils;

import android.util.Log;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by derekhsieh on 6/18/15.
 */
public class Serializer {
    //Static methods of serializing and deserializing
    private static Gson gson = new Gson();
    public Serializer(){

    }

    public static byte[] toByteArray(Object object){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Hessian2Output serializer = new Hessian2Output(outputStream);
        try {
            serializer.writeObject(object);
            serializer.flushBuffer();
            serializer.close();
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }
        return outputStream.toByteArray();
    }

    public static Object toObject(byte[] byteArray){
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        Hessian2Input deserializer = new Hessian2Input(inputStream);
        try {
            return deserializer.readObject();
        } catch (IOException e) {
            // TODO: find out why this returns E/IOException: No Classes defined at reference '6e'
            Log.e("IOException", e.getMessage());
        }finally {
            try {
                deserializer.close();
            } catch (IOException e) {
                Log.e("IOException", e.getMessage());
            }
        }
        return null;
    }

    public static Object toObject(String string){
        return gson.fromJson(string, Object.class);
    }
}
