package observer.test;

/**
 * Created with IntelliJ IDEA.
 * User: haolu
 * Date: 15-10-8
 * Time: обнГ2:55
 * To change this template use File | Settings | File Templates.
 */
public interface Watched {
    public void addWatcher(Watcher watcher);
    public void removeWatcher(Watcher watcher);
    public void notifyWatchers(String str);
}
