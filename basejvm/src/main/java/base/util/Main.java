package base.util;


public class Main {
    public static void main(String[] args) {
//        http://www.itstack.org/2019/05/19/%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E4%B8%89%E7%AB%A0%E3%80%8A%E8%A7%A3%E6%9E%90class%E6%96%87%E4%BB%B6%E3%80%8B/
        Cmd cmd = Cmd.parse(args);
        if (!cmd.ok || cmd.helpFlag) {
            System.out.println("Usage: <main class> [-options] class [args...]");
            return;
        }
        if (cmd.versionFlag) {
            System.out.println("java version \"1.8.0\"");
            return;
        }
        startJVM(cmd);
    }

    private static void startJVM(Cmd cmd) {
        System.out.printf("classpath:%s class:%s args:%s\n", cmd.classpath, cmd.getMainClass(), cmd.getAppArgs());
    }

}
