package client;

import Common.dto.CommandRequest;
import Common.dto.CommandResponse;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientNetworkManager {
    private final String host;
    private final int port;
    private static final long READ_TIMEOUT_MS = 100;

    private SocketChannel channel;

    public ClientNetworkManager(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean isConnected() {
        return channel != null && channel.isOpen() && channel.isConnected();
    }

    public boolean connect() {
        if (isConnected()) return true;

        try {
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress(host, port));

            while (!channel.finishConnect()) {
                Thread.yield();
            }
            return true;
        } catch (IOException e) {
            safeClose();
            return false;
        }
    }

    public CommandResponse send(CommandRequest request) throws IOException {
        if (!connect()) {
            throw new IOException("Не удалось подключиться к серверу " + host + ":" + port);
        }

        try {
            byte[] requestBytes = serialize(request);

            ByteBuffer lenBuf = ByteBuffer.allocate(4);
            lenBuf.putInt(requestBytes.length);
            lenBuf.flip();
            writeFully(lenBuf);

            writeFully(ByteBuffer.wrap(requestBytes));

            ByteBuffer respLenBuf = ByteBuffer.allocate(4);
            readFully(respLenBuf);
            respLenBuf.flip();
            int respLen = respLenBuf.getInt();

            if (respLen <= 0 || respLen > 50_000_000) {
                throw new IOException("Некорректная длина ответа: " + respLen);
            }

            ByteBuffer respBody = ByteBuffer.allocate(respLen);
            readFully(respBody);

            Object obj = deserialize(respBody.array());
            return (CommandResponse) obj;

        } catch (IOException e) {
            safeClose();
            throw e;
        }
    }

    public void close() {
        safeClose();
    }

    private void safeClose() {
        if (channel != null) {
            try {
                channel.close();
            } catch (IOException ignored) {
            } finally {
                channel = null;
            }
        }
    }

    private void writeFully(ByteBuffer buf) throws IOException {
        while (buf.hasRemaining()) {
            int written = channel.write(buf);
            if (written == 0) Thread.yield();
        }
    }

    private void readFully(ByteBuffer buf) throws IOException {
        long start = System.currentTimeMillis();

        while (buf.hasRemaining()) {
            int read = channel.read(buf);

            if (read == -1) throw new IOException("Соединение закрыто сервером.");

            if (read == 0) {
                if (System.currentTimeMillis() - start > READ_TIMEOUT_MS) {
                    throw new IOException("Сервер занят другим клиентом или не отвечает (таймаут " + READ_TIMEOUT_MS + "ms).");
                }
                Thread.yield();
                continue;
            }

            // Если реально прочитали байты — обновляем старт, чтобы таймаут считался на “период бездействия”
            start = System.currentTimeMillis();
        }
    }

    private static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
        }
        return baos.toByteArray();
    }

    private static Object deserialize(byte[] bytes) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            try {
                return ois.readObject();
            } catch (ClassNotFoundException e) {
                throw new IOException("Класс не найден при десериализации: " + e.getMessage(), e);
            }
        }
    }
}