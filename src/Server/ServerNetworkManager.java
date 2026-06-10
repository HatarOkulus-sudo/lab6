package Server;

import Common.dto.CommandRequest;
import Common.dto.CommandResponse;
import Server.commands.ServerCommand;
import Server.managers.ServerCommandsManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ServerNetworkManager {
    private final int port;
    private final ServerCommandsManager commandsManager;

    public ServerNetworkManager(int port, ServerCommandsManager commandsManager) {
        this.port = port;
        this.commandsManager = commandsManager;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту " + port);

            while (true) {
                try (Socket client = serverSocket.accept()) {
                    System.out.println("Клиент подключился: " + client.getRemoteSocketAddress());

                    InputStream in = client.getInputStream();
                    OutputStream out = client.getOutputStream();

                    while (true) {
                        CommandRequest request;
                        try {
                            request = readRequest(in);
                        } catch (EOFException eof) {
                            break;
                        }

                        CommandResponse response = handle(request);
                        writeResponse(out, response);
                    }

                } catch (IOException e) {
                    System.out.println("Ошибка при работе с клиентом: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println("Не удалось запустить сервер: " + e.getMessage());
        }
    }

    private CommandResponse handle(CommandRequest request) {
        if (request == null) {
            return new CommandResponse(false, "Пустой запрос (request == null).");
        }

        String name = request.getCommandName();
        if (name == null || name.isBlank()) {
            return new CommandResponse(false, "Пустое имя команды.");
        }

        ServerCommand cmd = commandsManager.getCommandByName(name);
        if (cmd == null) {
            return new CommandResponse(false, "Неизвестная команда: " + name);
        }

        try {
            return cmd.execute(request);
        } catch (Exception e) {
            return new CommandResponse(false, "Ошибка выполнения команды '" + name + "': " + e.getMessage());
        }
    }

    private static CommandRequest readRequest(InputStream in) throws IOException {
        byte[] lenBytes = new byte[4];
        readFully(in, lenBytes);

        int len = ByteBuffer.wrap(lenBytes).getInt();
        if (len <= 0 || len > 50_000_000) {
            throw new IOException("Некорректная длина запроса: " + len);
        }

        byte[] body = new byte[len];
        readFully(in, body);

        Object obj = deserialize(body);
        return (CommandRequest) obj;
    }

    private static void writeResponse(OutputStream out, CommandResponse response) throws IOException {
        byte[] body = serialize(response);

        byte[] lenBytes = ByteBuffer.allocate(4).putInt(body.length).array();
        out.write(lenBytes);
        out.write(body);
        out.flush();
    }

    private static void readFully(InputStream in, byte[] buf) throws IOException {
        int off = 0;
        while (off < buf.length) {
            int r = in.read(buf, off, buf.length - off);
            if (r == -1) throw new EOFException("Поток закрыт при чтении.");
            off += r;
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