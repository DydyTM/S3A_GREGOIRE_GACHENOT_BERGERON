package launcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

        Optional<ArrayList> parsed = parse(Arrays.stream(args).skip(1).reduce("", (acc, s) -> acc + " " + s));
        if (!parsed.isPresent()) {
            System.out.println("Could not parse command line.");
            return;
        }

        ArrayList args2 = parsed.get();

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
                            ArrayList<Object> varArgs = new ArrayList<>(Arrays.asList(funArgs));
                            varArgs.addAll(args2);
                            m.invoke(m.getDeclaringClass().newInstance(), varArgs.toArray());
                        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                            System.out.printf("Failed to execute function `%s.%s()`.\n", m.getDeclaringClass().getName(), m.getName());
                        } catch (IllegalArgumentException e) {
                            System.out.printf("Failed to execute action #%d: %s\n", i, e.getMessage());
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

    @SuppressWarnings("unchecked")
    private Optional<ArrayList> parse(String input) {
        Optional<ArrayList<Parser.Node>> a = Parser.parse(input);
        if (!a.isPresent())
            return Optional.empty();

        ArrayList<Parser.Node> list = a.get();
        if (list.isEmpty())
            return Optional.of(new ArrayList<>());

        return Optional.of((ArrayList) list.stream().map(Parser.Node::getValue).collect(Collectors.toList()));
    }

    private static class Parser {
        private static boolean isString = false;
        private static String buffer = "";

        private static Optional<ArrayList<Node>> parse(String in) {
            return parser(in, new ArrayList<>());
        }

        private static Optional<ArrayList<Node>> parser(String input, ArrayList<Node> args) {
            if (input.isEmpty()) {
                if (isString)
                    return Optional.empty();

                if (buffer.isEmpty())
                    return Optional.of(args);

                Optional<Node<?>> n = determine(buffer);
                buffer = "";

                if (!n.isPresent())
                    return Optional.empty();

                args.add(n.get());
                return Optional.of(args);
            }

            char c = input.charAt(0);
            switch (c) {
                case '"': {
                    buffer += c;
                    if (!isString) {
                        isString = true;
                        break;
                    }

                    isString = false;
                    Optional<Node<?>> n = determine(buffer);
                    buffer = "";

                    n.ifPresent(args::add);
                    break;
                }
                case ' ':
                case '\t': {
                    if (!isString) {
                        if (!buffer.isEmpty()) {
                            Optional<Node<?>> n = determine(buffer);
                            buffer = "";

                            if (n.isPresent())
                                args.add(n.get());
                            else
                                return Optional.empty();
                        }

                        break;
                    }
                }
                default:
                    buffer += c;
                    break;
            }
            input = input.substring(1);

            return parser(input, args);
        }

        private static Optional<Node<?>> determine(String in) {
            Pattern string = Pattern.compile("\"([^\"]*?)\"");
            Pattern integer = Pattern.compile("[0-9]+");

            if (integer.matcher(in).matches())
                return Optional.of(new IntNode(Integer.parseInt(in)));

            Matcher m = string.matcher(in);
            if (m.matches())
                return Optional.of(new StringNode(m.group()));
            return Optional.empty();
        }

        public abstract static class Node<T> {
            protected T val;

            public Node(T v) {
                val = v;
            }

            public T getValue() {
                return val;
            }

            @Override
            public String toString() {
                return val.toString();
            }
        }
        private static class StringNode extends Node<String> {
            public StringNode(String v) {
                super(v);
            }
        }
        private static class IntNode extends Node<Integer> {
            public IntNode(Integer v) {
                super(v);
            }
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

        List<String> list = this.methods.stream().map(this::formatMethodName).collect(Collectors.toList());
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

    private String formatMethodName(Method m) {
        String s = Arrays.stream(m.getName().split("_"))
                         .reduce("", (acc, s1) -> acc + " " + s1);

        s += Arrays.stream(m.getParameters())
                   .skip(1)
                   .map(p -> {
                        if (p.isAnnotationPresent(Param.class))
                            return p.getDeclaredAnnotation(Param.class).name();
                        else
                            return p.getName();
                   })
                   .reduce("", (acc, p) -> acc + " " + p);

        return s;
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

    private static class Tuple<T, U> {
        public T fst;
        public U snd;

        public Tuple(T t, U u) {
            fst = t;
            snd = u;
        }
    }
}
