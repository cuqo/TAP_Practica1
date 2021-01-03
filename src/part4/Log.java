package part4;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Log implements InvocationHandler {

    private final Object target;

    /**
     * Method that instantiate the object through Log class
     * @param target -> object to instantiate
     * @return new instance of the object
     */
    public static Object newInstance(Object target) {
        Class targetClass = target.getClass();
        Class[] interfaces = targetClass.getInterfaces();
        return Proxy.newProxyInstance(targetClass.getClassLoader(),
                interfaces, new Log(target));
    }

    /**
     * Constructor
     * @param target -> object to instantiate
     */
    private Log(Object target) { this.target = target; }

    /**
     * Method that is invoked when a method of object instantiated is invoked
     * @param proxy -> instantiated object
     * @param method -> method called
     * @param args -> args of method called
     * @return
     */
    public Object invoke(Object proxy, Method method, Object[] args) {
        Object invocationResult = null;
        try {
            System.out.println("Method called: " + method.getName());
            invocationResult = method.invoke(this.target, args);
        } catch (InvocationTargetException ite) {
            throw ite.getTargetException();
        } catch (Exception e) {
            System.err.println("Invocation of " + method.getName() + " failed");
        } finally {
            return invocationResult;
        }
    }
}
