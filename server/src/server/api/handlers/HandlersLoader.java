package server.api.handlers;

import core.checkers.ICheckersServer;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.reflections.Reflections;
import tools.QueryParser;

public class HandlersLoader {
  private static final Reflections reflections = new Reflections("server.api.handlers");
  private static final List<Class<? extends Handler>> handlersClasses;

  static {
    handlersClasses = loadHandlersClasses();
  }

  public static List<Handler> createHandlers(QueryParser parser, ICheckersServer checkersServer)
      throws NoSuchMethodException {
    var handlers = new ArrayList<Handler>();

    for (var handlerClass : handlersClasses) {
      try {
        var ctor = handlerClass.getConstructor(QueryParser.class, ICheckersServer.class);
        var instance = ctor.newInstance(parser, checkersServer);

        handlers.add(instance);
      } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
        throw new NoSuchMethodException(
            String.format("Cant get public ctor (QueryParser, ICheckersServer) for class %s", handlerClass));
      }
    }

    return handlers;
  }

  private static List<Class<? extends Handler>> loadHandlersClasses() {
    return new ArrayList<>(reflections.getSubTypesOf(Handler.class));
  }
}
