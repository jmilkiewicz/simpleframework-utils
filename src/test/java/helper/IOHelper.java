package helper;
import java.io.*;
import java.nio.charset.Charset;

import com.google.common.io.*;

public class IOHelper {
    public static String getContentAsString(InputStream content,
            Charset encoding) throws IOException {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(content, encoding);
            return CharStreams.toString(inputStreamReader);
        } finally {
            Closeables.closeQuietly(content);
            Closeables.closeQuietly(inputStreamReader);
        }
    }
}
