package launcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Launcher {
    private List<Method> methods;

    private Launcher(Method[] ms) {
        this.methods = Arrays.stream(ms)
                             .filter(m -> m.isAnnotationPresent(LauncherEntry.class))
                             .sorted(new LauncherEntryComparator())
                             .collect(Collectors.toList());
    }

    public static Launcher from(Class<?> c) {
        Method[] ms = c.getDeclaredMethods();
        return new Launcher(ms);
    }

    public void launch(Object... args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Launcher for project.\tType \":h\" for help.");

        while (true) {
            showMenu();

            System.out.print("╼ ");
            String input = sc.nextLine();
            parseAndEvalInput(input, args);
        }
    }

    private void parseAndEvalInput(String input, Object... funArgs) {
        String[] args = input.split("\\s");
        if (args.length == 0)
            return;

        String cmd = args[0];
        ArrayList<String> args2 = new ArrayList<>(Arrays.asList(args));
        args2.remove(0);

        switch (cmd.toLowerCase()) {
            case ":q":
                if (args2.size() != 0) {
                    System.out.printf("Wrong number of arguments: expected 0, got %d.\n", args2.size());
                    return;
                }

                System.out.println("Exiting app...");
                System.exit(0);
                break;
            case ":h":
            case ":?":
                if (args2.size() != 0) {
                    System.out.printf("Wrong number of arguments: expected 0, got %d.\n", args2.size());
                    return;
                }

                System.out.println("Help menu:\n├ `:q` : Quit app\n├ `:h`|`:?` : Help menu\n└ `<number>` : Execute function #`<number>`");

                break;
            default:
                try {
                    int i = Integer.parseInt(cmd);
                    try {
                        Method m = this.methods.get(i - 1);
                        try {
                            m.invoke(m.getDeclaringClass().newInstance(), funArgs);
                        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                            System.out.printf("Failed to execute function `%s.%s()`.\n", m.getDeclaringClass().getName(), m.getName());
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.printf("No such action identifier: %d.\n", i);
                    }
                } catch (NumberFormatException e) {
                    System.out.printf("Failed to interpret number: `%s` is not an integer.\n", cmd);
                }
                break;
        }
    }

    private void showMenu() {
        Function<List<String>, String> longestIn =
                l -> l.stream().reduce("", (s, s1) -> s.length() >= s1.length() ? s : s1);
        BiFunction<String, Integer, String> padding = (pad, size) -> {
            AtomicReference<String> s = new AtomicReference<>("");
            IntStream.range(0, size).forEach(i -> s.updateAndGet(s_ -> s_ += pad));
            return s.get();
        };

        List<String> list = this.methods.stream().map(m -> formatMethodName(m.getName())).collect(Collectors.toList());
        String longest = longestIn.apply(list);
        System.out.println("╔" + padding.apply("═", longest.length() + 3 + Integer.toString(list.size()).length()) + "╗");

        AtomicReference<Integer> counter = new AtomicReference<>(1);
        list.forEach(m -> {
            String index = Integer.toString(counter.get());
            System.out.println("║ " + index
                + padding.apply(" ", Integer.toString(list.size()).length() - index.length())
                + ":" + m
                + padding.apply(" ", longest.length() - m.length())
                + " ║");
            counter.getAndUpdate(i -> i += 1);
        });

        System.out.print("╙─");
    }

    private String formatMethodName(String name) {
        return Arrays.stream(name.split("_"))
                     .reduce("", (acc, s) -> acc + " " + s);
    }

    private static class LauncherEntryComparator implements Comparator<Method> {
        public LauncherEntryComparator() {}

        @Override
        public int compare(Method m1, Method m2) {
            if (m1 == null || m2 == null)
                return 0;

            if (!m1.isAnnotationPresent(LauncherEntry.class))
                throw new IllegalArgumentException("Method m1 does not have a @LauncherEntry attached to it.");
            if (!m2.isAnnotationPresent(LauncherEntry.class))
                throw new IllegalArgumentException("Method m2 does not have a @LauncherEntry attached to it.");

            return Integer.compare(
                m1.getAnnotation(LauncherEntry.class).order(),
                m2.getAnnotation(LauncherEntry.class).order()
            );
        }
    }
}
