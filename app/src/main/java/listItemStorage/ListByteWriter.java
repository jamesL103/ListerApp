package listItemStorage;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ListByteWriter implements Closeable {

    private final File OUT;
    private final FileOutputStream STREAM;
    private final byte[] SOURCE;

    public ListByteWriter(File output, byte[] source) throws IOException {
        OUT = output;
        if (!output.exists()) {
            throw new IOException("Cannot find file " + output.getName());
        }
        STREAM = new FileOutputStream(OUT);
        SOURCE = source;
    }

    public void write() throws IOException{
        STREAM.write(SOURCE);
    }

    @Override
    public void close() throws IOException {
        STREAM.close();
    }
}
