package observer.test;

/**
 * Created with IntelliJ IDEA.
 * User: haolu
 * Date: 15-10-8
 * Time: обнГ3:00
 * To change this template use File | Settings | File Templates.
 */
public class ObserverTest {
    public static void main(String[] args){
        Watched w1 = new ConcreteWatched();
        Watcher watcher1 = new ConcreteWatcher();
        Watcher watcher2 = new ConcreteWatcher();
        Watcher watcher3 = new ConcreteWatcher();
        w1.addWatcher(watcher1);
        w1.addWatcher(watcher2);
        w1.addWatcher(watcher3);
        w1.notifyWatchers("happiness...");
    }
}
