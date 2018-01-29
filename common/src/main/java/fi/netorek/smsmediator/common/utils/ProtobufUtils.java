package fi.netorek.smsmediator.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.protobuf.MessageLite;

public class ProtobufUtils {
    private ProtobufUtils() {
    }

    /**
     * Convert a Protobuf object to byte array
     *
     * @param message object for conversion
     * @return byte array
     * @throws fi.netorek.smsmediator.common.exception.IOException
     */
    public static byte[] getBytes(MessageLite message) {
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            message.writeTo(bout);
            return bout.toByteArray();
        } catch (IOException e) {
            throw new fi.netorek.smsmediator.common.exception.IOException(e);
        }
    }
}
