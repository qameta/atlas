package io.qameta.atlas.core.util;

import io.qameta.atlas.core.AtlasException;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Reflection utils.
 */
public final class ReflectionUtils {

    private ReflectionUtils() {
    }

    private static List<Class<?>> getAllInterfaces(final Class<?>... classes) {
        final List<Class<?>> result = new ArrayList<>();
        Arrays.stream(classes).forEach(clazz -> {
            result.addAll(ClassUtils.getAllInterfaces(clazz));
            result.add(clazz);
        });
        return result;
    }

    public static List<Method> getMethods(final Class<?>... clazz) {

        return getAllInterfaces(clazz).stream()
                .flatMap(m -> Arrays.stream(m.getDeclaredMethods()))
                .collect(Collectors.toList());
    }

    public static <T> T newInstance(final Class<T> clazz) {
        try {
            return ConstructorUtils.invokeConstructor(clazz);
        } catch (Exception e) {
            throw new AtlasException("Can't instantiate class " + clazz, e);
        }
    }


//    public static List<Class<?>> getAllInterfacesAndAllSuperClasses(final Class<?> cls){
//
//
//        final Class<?>[] interfacesOfCurrentClass = cls.getInterfaces(); //Получил все интерфейсы текущего класса
//        final LinkedHashSet<Class<?>> allEntities = new LinkedHashSet<>(Arrays.asList(interfacesOfCurrentClass)); //Сразу добавить по рекурсии
//
//        Class<?> superclass = cls.getSuperclass(); //Получили суперкласс
//
//
//        //Выполнить хоть раз, так как могут быть случаии когда класс реализует инерфейс, но не наследуется от другого класса
//
//
//        while (superclass != null) {
//            allEntities.add(superclass);
//            superclass = superclass.getSuperclass();
//        }
//
//
//
//
//        return null;
//    }

//    public static Set<Class<?>> getAllInterfaces(final Class<?> cls) {
//        final LinkedHashSet<Class<?>> interfacesFound = new LinkedHashSet<>();
//        getAllInterfaces(cls, interfacesFound);
//        return interfacesFound;
//    }
//
//    private static void getAllInterfaces(Class<?> cls, final HashSet<Class<?>> interfacesFound) {
//        while (cls != null) {
//            final Class<?>[] interfaces = cls.getInterfaces();
//            for (final Class<?> i : interfaces) {
//                if (interfacesFound.add(i)) {
//                    getAllInterfaces(i, interfacesFound);
//                }
//            }
//            cls = cls.getSuperclass();
//        }
//    }
//
//    public static Set<Class<?>> getAllSuperClasses(final Class<?> cls) {
//        final LinkedHashSet<Class<?>> classes = new LinkedHashSet<>();
//        Class<?> superclass = cls.getSuperclass();
//        while (superclass != null) {
//            classes.add(superclass);
//            superclass = superclass.getSuperclass();
//        }
//        return classes;
//    }

    public static Method getMatchingMethod(final Class<?> cls, final String methodName, final Class<?>... parameterTypes) {
        if (!Objects.nonNull(cls) && !Objects.requireNonNull(methodName).isEmpty())
            throw new AtlasException("Null class not allowed.");

        ////////////


        Predicate<Method> filter1 = method -> methodName.equals(method.getName()) && Objects.deepEquals(parameterTypes, method.getParameterTypes());
//


        Predicate<Method> filter2 = method -> methodName.equals(method.getName()) && ClassUtils.isAssignable(parameterTypes, method.getParameterTypes(), true);
//
//   Stream.of(getAllInterfaces(cls), getAllSuperClasses(cls), Arrays.asList(cls))
//                .flatMap(Collection::stream)
//                .map(Class::getDeclaredMethods)
//                .flatMap(Arrays::stream)
//                .filter(equals.and(equals2)).peek(System.out::println)
//                .findFirst().get();
//



        /////////////  Старый путь //////////


      // Method[] methodArray = cls.getDeclaredMethods();

//        final List<Class<?>> superclassList = ClassUtils.getAllSuperclasses(cls);
//        superclassList.addAll(ClassUtils.getAllInterfaces(cls));

        //-  toArray[Methos::new] //toMap(Class::cast)!!



        //final List<Class<?>> superInterface = ClassUtils.getAllSuperclasses(cls);

        final List<Method> methodList = Stream.of(Collections.singletonList(cls), ClassUtils.getAllSuperclasses(cls), ClassUtils.getAllInterfaces(cls))
                .flatMap(Collection::stream).map(Class::getDeclaredMethods).flatMap(Arrays::stream).collect(Collectors.toList());

        //Сначала проверяем класс, потом суперклассы и только потом интерфейсы
        //public abstract void io.qameta.atlas.core.TargetMethodTest$SayHello.hello()
        //public final void io.qameta.atlas.core.TargetMethodTest$SayHello$$EnhancerByMockitoWithCGLIB$$989b04c.hello()


        Method method1 = Stream.of(Collections.singletonList(cls), ClassUtils.getAllSuperclasses(cls), ClassUtils.getAllInterfaces(cls))
                .flatMap(Collection::stream).map(Class::getDeclaredMethods).flatMap(Arrays::stream).filter(filter1.or(filter2)).peek(str -> System.out.println(str)).findFirst().get();

        //System.out.println("a " + a);

//        Method inexactMatch = null;
//        for (final Method method : methodList) {
//            if (methodName.equals(method.getName()) && Objects.deepEquals(parameterTypes, method.getParameterTypes())) {
//                return method;
//            } else if (methodName.equals(method.getName()) && ClassUtils.isAssignable(parameterTypes, method.getParameterTypes(), true)) {
//                if (inexactMatch == null) {
//                    System.out.println("methodName.equals(method.getName()) " + methodName);
//                    inexactMatch = method;
//                } else if (distance(parameterTypes, method.getParameterTypes())
//                        < distance(parameterTypes, inexactMatch.getParameterTypes())) {
//                    inexactMatch = method;
//                }
//            }
//        }
//        return inexactMatch;


return method1;

    }

    private static int distance(final Class<?>[] classArray, final Class<?>[] toClassArray) {
        int answer = 0;

        if (!ClassUtils.isAssignable(classArray, toClassArray, true)) {
            return -1;
        }
        for (int offset = 0; offset < classArray.length; offset++) {
            // Note InheritanceUtils.distance() uses different scoring system.
            if (classArray[offset].equals(toClassArray[offset])) {
                continue;
            } else if (ClassUtils.isAssignable(classArray[offset], toClassArray[offset], true)
                    && !ClassUtils.isAssignable(classArray[offset], toClassArray[offset], false)) {
                answer++;
            } else {
                answer = answer + 2;
            }
        }

        return answer;
    }
}
