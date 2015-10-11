package observer.test;

/**
 * Created with IntelliJ IDEA.
 * User: haolu
 * Date: 15-10-8
 * Time: обнГ2:56
 * To change this template use File | Settings | File Templates.
 */
public class ConcreteWatcher implements Watcher {
    @Override
    public void update(String str) {
        System.out.println(str);
    }
}
