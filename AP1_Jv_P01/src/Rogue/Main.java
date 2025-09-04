import Controller.Controller;
import jcurses.system.Toolkit;
import sun.misc.Signal;
import View.IOHandler;
import Domain.GameSession;
import View.Render;

public class Main {

    public static void main(String[] args) {
        Toolkit.init();
        Signal.handle(new Signal("INT"), signal -> {
            Toolkit.shutdown();
            System.out.println("Interrupted with ctrl+c");
            System.exit(2);
        });

        IOHandler handler = new IOHandler();
        Render render = new Render();
        GameSession session = handler.handleMenu(render, null);
        if (session == null) {
            Toolkit.shutdown();
            return;
        }
        Controller controller = new Controller(handler, session, render);
        controller.runGameSession();
        Toolkit.shutdown();
    }
}